package com.windyroad.nghia.common.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Imark-N on 11/19/2015.
 */
public class CSVFileUtil<T> {

    /**
     * Xuất list Item thành CSV file
     * Code Using: new CSVFileUtil<EditComment>().exportToFile(filePath, list);
     */
    public boolean exportToFile(String filePath, List<T> listObject) {
        String value = toCSVText(listObject);
        return TextFileUtil.writeToFile(filePath, value);
    }

    /**
     * Thêm dòng vào file CSV
     * Code Using: new CSVFileUtil<>().appendLine(filePath, list);
     */
    public boolean appendLine(String filePath, T object) {
        try {
            StringBuilder builder = new StringBuilder();

            // file chưa tồn tại => add header
            File file = new File(filePath);
            if (!file.exists() || !FileUtil.hasData(filePath)) {
                if (!FileUtil.createNewFile(filePath)) return false;

                appendCSVHeader(object.getClass(), builder);
            }

            appendCSVLine(object, builder);

            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
            buf.append(builder.toString());
            buf.close();
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String toCSVText(List<T> listObject) {
        return toCSVText(listObject, true);
    }

    public String toCSVText(List<T> listObject, boolean includeHeaderLine) {
        StringBuilder builder = new StringBuilder();

        // add header line
        if (includeHeaderLine) {
            appendCSVHeader(Object.class, builder);
        }

        for(Object item : listObject)
        {
            appendCSVLine(item, builder);
        }


        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

    private static void appendCSVLine(Object item, StringBuilder builder) {
        Class classType1 = item.getClass();
        Method[] methods1 = classType1.getDeclaredMethods();
        for(Method m : methods1)
        {
            if(m.getParameterTypes().length==0)
            {
                if(m.getName().startsWith("get") || m.getName().startsWith("is"))
                {
                    try {
                        builder.append(m.invoke(item).toString()).append(',');
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        builder.append("error:").append(ex.getMessage()).append(',');
                    }
                }
            }
        }
        builder.deleteCharAt(builder.length()-1);  // bỏ "," cuối
        builder.append('\n');
    }

    private static void appendCSVHeader(Class classType, StringBuilder builder) {
        Method[] methods = classType.getDeclaredMethods();
        for (Method m : methods) {
            if(m.getParameterTypes().length==0)
            {
                if(m.getName().startsWith("get"))
                {
                    builder.append(m.getName().substring(3)).append(',');
                }
                else if(m.getName().startsWith("is"))
                {
                    builder.append(m.getName().substring(2)).append(',');
                }
            }
        }
        builder.deleteCharAt(builder.length()-1);  // bỏ "," cuối
        builder.append("\n");
    }
}
