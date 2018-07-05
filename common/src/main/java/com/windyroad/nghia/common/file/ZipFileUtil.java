package com.windyroad.nghia.common.file;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Imark-N on 12/18/2015.
 * Hỗ trợ file Zip, nén
 */
public class ZipFileUtil {

    private static final String TAG = ZipFileUtil.class.getName();

    //region Write Zip faster
    /**
     * Make file Zip from Folder
     * Example: zipFileAtPath("downloads/myfolder", "downloads/myFolder.zip");
     * Info: http://stackoverflow.com/questions/6683600/zip-compress-a-folder-full-of-files-on-android
     * @param sourceDirPath directory path
     * @param targetZipPath zip file result path
     * @return
     */
    public static boolean makeZipFile(String sourceDirPath, String targetZipPath) {

        try {
            // repair
            FileOutputStream fos = new FileOutputStream(targetZipPath);
            ZipOutputStream zos = new ZipOutputStream(fos);

            File srcFile = new File(sourceDirPath);

            if (srcFile.isDirectory()) {
                // compress dir
                zipSubDir(zos, srcFile, srcFile.getParent().length());

            } else {
                // compress file
                writeZipEntry(zos, srcFile, srcFile.getName());
            }

            zos.close();

            Log.e(TAG, "Write file finish");
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Compress Zip Sub Directory (folder)
     * @param zos
     * @param folder
     * @param folderStartIndex chỉ số bắt đầu của folder, để cắt ra folder con
     */
    private static void zipSubDir(ZipOutputStream zos, File folder, int folderStartIndex) throws IOException {
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                // compress dir
                zipSubDir(zos, file, folderStartIndex);
            } else {
                // compress file
                String entryPath = file.getPath().substring(folderStartIndex);
                writeZipEntry(zos, file, entryPath);
            }
        }
    }
    //endregion

    /**
     * Write file to ZipOutputStream Entry
     * @param zos
     * @param file
     * @param entryZipPath file path on zip. Example: abc.zip/path/file.xyz, path/file.xyz = entryPath
     * @throws IOException
     */
    public static void writeZipEntry(ZipOutputStream zos, File file, String entryZipPath) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        zos.putNextEntry(new ZipEntry(entryZipPath));

        FileUtil.writeStream(fis, zos);

        zos.closeEntry();
        fis.close();

        Log.e(TAG, "Write file: " + file.getName());
    }
}
