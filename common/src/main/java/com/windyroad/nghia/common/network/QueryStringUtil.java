package com.windyroad.nghia.common.network;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Nghia-PC on 7/14/2015.
 */
public class QueryStringUtil {

    /**
     * Chuyển QueryString thành Map Object
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, List<String>> splitQuery(URL url) throws UnsupportedEncodingException {
        final Map<String, List<String>> query_pairs = new LinkedHashMap<String, List<String>>();
        final String[] pairs = url.getQuery().split("&");
        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
            if (!query_pairs.containsKey(key)) {
                query_pairs.put(key, new LinkedList<String>());
            }
            final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
            query_pairs.get(key).add(value);
        }
        return query_pairs;

        /* Có thể sử dụng, chưa thử
        String url = "http://www.example.com/something.html?one=1&two=2&three=3&three=3a";
        List<NameValuePair> params = URLEncodedUtils.parse(new URI(url), "UTF-8");

        for (NameValuePair param : params) {
            System.out.println(param.getKey() + " : " + param.getValue());
        }*/
    }

    /**
     * Chuyển Object => query string
     */
    public static String parseQuery(List<UrlParam> params) {
        StringBuilder builder = new StringBuilder();
        try {
            // Chạy hết List Encoder hết
            for (UrlParam item : params) {
                String name = URLEncoder.encode(item.getKey(), "UTF-8");
                String value = URLEncoder.encode(item.getValue(), "UTF-8");

                String param = String.format("&%s=%s", name, value);

                builder.append(param);
            }
            builder.replace(0, 1, "");  // bỏ & đầu tiên

        } catch (Exception ex){
            ex.printStackTrace();
        }
        return builder.toString();
    }
}
