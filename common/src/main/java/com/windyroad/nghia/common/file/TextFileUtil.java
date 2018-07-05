package com.windyroad.nghia.common.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Imark-N on 11/19/2015.
 */
public class TextFileUtil {

    /**
     * Lưu text vào file
     * Code sample: TextFileUtil.writeToFile(filePath, message);
     */
    public static boolean writeToFile(String filePath, String data) {
        try {

            if (!FileUtil.createNewFile(filePath)) return false;

            OutputStream outputStream = new FileOutputStream(filePath);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(data);

            outputStreamWriter.close();
            outputStream.close();

            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Đọc text từ file
     */
    public static String readFromFile(String filePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return readFromStream(inputStream);
    }

    public static String readFromStream(InputStream inputStream) {
        String ret = "";
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((receiveString = bufferedReader.readLine()) != null) {
                stringBuilder.append(receiveString);
            }

            inputStream.close();
            ret = stringBuilder.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Thêm 1 dòng vào file
     * @return
     */
    public static boolean appendLine(String filePath, String logMessage) {

        try
        {
            if (!FileUtil.createNewFile(filePath)) return false;

            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(filePath, true));
            buf.append(logMessage);
            buf.newLine();
            buf.close();

            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Thêm 1 "log line" theo thời gian hiện tại
     * Code Sample:
     * String message = editText.getText()+"";
     * String filePath = Environment.getExternalStorageDirectory() + "/nghia.txt";
     * TextFileUtil.appendLogLine(filePath, message);
     */
    public static boolean appendLogLine(String filePath, String logMessage)
    {
        // date text
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        String date = sdf.format(Calendar.getInstance().getTime());

        // message text
        logMessage = date + " ==> " + logMessage;

        return appendLine(filePath, logMessage);
    }


    /**
     * Ghi dòng Log vào file log
     * Code Sample:
     * TextFileUtil.appendLogLine(filePath, this, message);
     */
    public static boolean appendLogLine(String filePath, Object tag, String message)
    {
        String logMessage = tag + ":\t" + message;
        return appendLogLine(filePath, logMessage);
    }
}
