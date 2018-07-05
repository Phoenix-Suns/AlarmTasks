package com.windyroad.nghia.common.network;

import android.support.annotation.Nullable;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nghia-PC on 7/15/2015.
 * Multipart Unity Upload file
 * http://www.codejava.net/java-se/networking/upload-files-by-sending-multipart-request-programmatically
 */
public class WebserviceUtil {

    private static final int CONNECTION_TIME_OUT = 5000;  // thời gian dừng kết nối
    static String CRLF = "\r\n";  // Xuống hàng về đầu dòng
    static String TWO_HYPHENS = "--";
    static String BOUNDARY =  "*****"; // Có lẽ là mã đồng bộ, để biết dừng form
    static String ENCODING = "UTF-8";

    //region old
    /**
     * Gởi Webservice
     * @param method
     * @param contentType
     * @param strUrl
     * @param listParams
     * @return
     *//*
    public static InputStream sendWebservice(SendMethod method, UrlContentType contentType, String strUrl, List<UrlParam> listParams){
        DataOutputStream outputStream = null;
        InputStream inputStream = null;
        BOUNDARY = "===IMarkFormBoundary" + Long.toHexString(System.currentTimeMillis()) + "===";

        try {
            switch (method){
                case GET:
                    // Get response
                    if (listParams != null)
                        strUrl += "?" + QueryStringUtil.parseQuery(listParams);  // Chuyển thành QueryString
                    URL url1 = new URL(strUrl);
                    inputStream = url1.openStream();
                    break;

                case POST:
                    //-----Init connection-----
                    URL url2 = new URL(strUrl);
                    HttpURLConnection connection = (HttpURLConnection)url2.openConnection();
                    connection.setUseCaches(false);  // Don't use Cached Copy
                    connection.setDoInput(true);  // Allow Inputs
                    connection.setDoOutput(true);  // Allow Outputs
                    connection.setRequestMethod("POST");

                    switch (contentType){
                        case X_WWW_FORM_URLENDCODED:
                            // set data
                            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            String requestData = QueryStringUtil.parseQuery(listParams);  // Chuyển thành QueryString
                            // set độ dài chuyển đi
                            byte[] outputBytes = requestData.getBytes(ENCODING);
                            connection.setRequestProperty("Content-Length", Integer.toString(outputBytes.length));

                            //-----Send request-----
                            outputStream = new DataOutputStream(connection.getOutputStream());
                            outputStream.write(outputBytes);
                            outputStream.flush();
                            break;
                        case FORM_DATA:
                            // set data
                            connection.setRequestProperty("Connection", "Keep-Alive");
                            connection.setRequestProperty("Cache-Control", "no-cache");
                            connection.setRequestProperty("Content-Type", "multipart/form-data;BOUNDARY=" + BOUNDARY);

                            //-----Send request-----
                            outputStream = new DataOutputStream(connection.getOutputStream());
                            // write data into stream
                            for(UrlParam param : listParams){
                                if (param.getType() == UrlParam.ParamType.TEXT){
                                    writeTextField(outputStream, param);
                                } else if (param.getType() == UrlParam.ParamType.FILE){
                                    writeFileField(outputStream, param);
                                }
                            }
                            writeFooter(outputStream);
                            break;
                        default:
                            break;
                    }
                    outputStream.close();

                    //-----Get response------
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        inputStream = connection.getInputStream();
                    }
                    break;
                case PUT:

                    break;
                case DELETE:

                    break;
                default:
                    break;
            }

        } catch (Exception ex){
            ex.printStackTrace();
        }

        return inputStream;
    }*/
    //endregion

    /** Gởi Webservice phương thức GET
     *
     * @param strUrl
     * @param listParams
     * @return
     */
    public static InputStream sendGet(String strUrl, @Nullable ArrayList<UrlParam> listParams){
        InputStream inputStream = null;
        try {

            if (listParams != null)
                strUrl += "?" + QueryStringUtil.parseQuery(listParams);  // Chuyển thành QueryString
            URL url1 = new URL(strUrl);
            //inputStream = url1.openStream();
            URLConnection con = url1.openConnection();
            con.setConnectTimeout(CONNECTION_TIME_OUT);
            con.setReadTimeout(CONNECTION_TIME_OUT);
            inputStream = con.getInputStream();

        } catch (Exception ex){
            ex.printStackTrace();
        }
        return inputStream;
    }


    /** Gởi Webservice phương thức POST
     *
     * @param mimeType
     * @param strUrl
     * @param listParams
     * @return
     */
    public static InputStream sendPostForm(FormMIMEType mimeType, String strUrl, List<UrlParam> listParams){
        DataOutputStream outputStream = null;
        InputStream inputStream = null;
        BOUNDARY = "===IMarkFormBoundary" + Long.toHexString(System.currentTimeMillis()) + "===";

        try {

            //-----Init connection-----
            URL url2 = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection)url2.openConnection();
            connection.setConnectTimeout(CONNECTION_TIME_OUT);
            connection.setUseCaches(false);  // Don't use Cached Copy
            connection.setDoInput(true);  // Allow Inputs
            connection.setDoOutput(true);  // Allow Outputs
            connection.setRequestMethod("POST");

            switch (mimeType){
                case X_WWW_FORM_URLENDCODED:
                    // set data
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    String requestData = QueryStringUtil.parseQuery(listParams);  // Chuyển thành QueryString
                    // set độ dài chuyển đi
                    byte[] outputBytes = requestData.getBytes(ENCODING);
                    connection.setRequestProperty("Content-Length", Integer.toString(outputBytes.length));

                    //-----Send request-----
                    outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.write(outputBytes);
                    outputStream.flush();
                    break;

                case FORM_DATA:
                    // set data
                    connection.setRequestProperty("Connection", "Keep-Alive");
                    connection.setRequestProperty("Cache-Control", "no-cache");
                    connection.setRequestProperty("Content-Type", "multipart/form-data;BOUNDARY=" + BOUNDARY);

                    //-----Send request-----
                    outputStream = new DataOutputStream(connection.getOutputStream());
                    // write data into stream
                    for(UrlParam param : listParams){
                        if (param.getType() == UrlParam.ParamType.TEXT){
                            writeTextField(outputStream, param);
                        } else if (param.getType() == UrlParam.ParamType.FILE){
                            writeFileField(outputStream, param);
                        }
                    }
                    writeFooter(outputStream);
                    break;

                default:
                    break;
            }
            outputStream.close();

            //-----Get response------
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
            }

        } catch (Exception ex){
            ex.printStackTrace();
        }
        return inputStream;
    }

    private static void writeFooter(DataOutputStream outputStream) throws IOException {
        String data = TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + CRLF;
        outputStream.writeBytes(data);
        outputStream.flush();
    }

    private static void writeFileField(DataOutputStream outputStream, UrlParam param) throws Exception {
        String fileName = param.getValue();
        File file = new File( fileName );

        // write the header
        String mimeType = HttpURLConnection.guessContentTypeFromName(fileName);  // Loại file
        String header = TWO_HYPHENS + BOUNDARY + CRLF
                + "Content-Disposition: form-data; name=\"" + param.getKey() +"\""
                + "; filename=\""+file.getName()+"\""+ CRLF
                + "Content-Type: "+ mimeType + CRLF
                + "Content-Transfer-Encoding: binary" + CRLF
                + CRLF ;
        outputStream.writeBytes(header);

        // write file
        FileInputStream fileInputStream = new FileInputStream(file);
        int bytesAvailable = fileInputStream.available();
        int maxBufferSize = 3 * 1024 * 1024;
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        while(bytesRead > 0) {
            outputStream.write(buffer, 0, bufferSize);

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        // write footer
        outputStream.writeBytes(CRLF);

        outputStream.flush();
    }

    private static void writeTextField(DataOutputStream outputStream, UrlParam param) throws IOException {
        String strData = TWO_HYPHENS + BOUNDARY + CRLF
                + "Content-Disposition: form-data; name=\"" + param.getKey() +"\"" + CRLF
                + "Content-Type: text/plain; charset=" + ENCODING + CRLF
                //+ "Content-Transfer-Encoding: 8bit" + CRLF
                + CRLF + param.getValue() + CRLF;
        byte[] byteData = strData.getBytes("UTF-8");  // chuyển Utf 8

        outputStream.write(byteData);
        outputStream.flush();
    }


    /** Post kiểu Raw với 1 header loại dữ liệu */
    public static InputStream sendPostRaw(
            String strUrl, String requestData, @Nullable RawHeaderType headerType) {
        /* Demo
        Tạo list, chuyển list thành text Json, add header Json gọi hàm

        ArrayList<PostAttendanceImgWSRequest> listParams = new ArrayList<PostAttendanceImgWSRequest>() {{
            add(new PostAttendanceImgWSRequest(
                    WSConfig.Common.CODE, sessionCode, attendanceServerId+"", imageServerId+""));
        }};
        String strJson = new Gson().toJson(listParams, new TypeToken<List<PostAttendanceImgWSRequest>>(){}.getType());

        InputStream inputStream = WebserviceUtil
                .sendPostRaw(WSConfig.PATH_POST_ATTENDANCE_IMAGE, strJson, RawHeaderType.JSON);
         */

        DataOutputStream outputStream = null;
        InputStream inputStream = null;

        try {

            //-----Init connection-----
            URL url2 = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

            connection.setUseCaches(false);  // Don't use Cached Copy
            connection.setDoInput(true);  // Allow Inputs
            connection.setDoOutput(true);  // Allow Outputs
            connection.setRequestMethod("POST");

            // header
            /* Author, chưa kiểm tra
            String userCredentials = "username:password";
            String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
            myURLConnection.setRequestProperty ("Authorization", basicAuth);*/

            if (headerType != null){
                connection.setRequestProperty("Content-Type", headerType.toString());
            }


            // set data
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            // set độ dài chuyển đi
            byte[] outputBytes = requestData.getBytes(ENCODING);
            connection.setRequestProperty("Content-Length", Integer.toString(outputBytes.length));

            //-----Send request-----
            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.write(outputBytes);
            outputStream.flush();
            outputStream.close();

            //-----Get response------
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return inputStream;
    }
}
