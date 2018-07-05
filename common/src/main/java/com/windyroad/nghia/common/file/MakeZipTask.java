package com.windyroad.nghia.common.file;

import android.os.AsyncTask;


import com.windyroad.nghia.common.models.BatchActionResult;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * Created by Imark-N on 12/19/2015.
 */
public class MakeZipTask extends AsyncTask<Void, BatchActionResult, BatchActionResult> {


    private final String mSourcePath;
    private final String mTargetZipPath;
    private BatchActionResult mProgressResult;
    private Listener mListener;

    public MakeZipTask(String sourcePath, String targetZipPath) {
        this.mSourcePath = sourcePath;
        this.mTargetZipPath = targetZipPath;
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
        this.mProgressResult = new BatchActionResult();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected BatchActionResult doInBackground(Void... params) {

        try {
            // repair var
            FileOutputStream fos = new FileOutputStream(mTargetZipPath);
            ZipOutputStream zos = new ZipOutputStream(fos);

            // Calculate total files
            List<File> childFiles = new ArrayList<>();
            File srcFile = new File((mSourcePath));
            FileUtil.getFiles(srcFile, childFiles);

            this.mProgressResult.setTotal(childFiles.size());

            // run each file, return process update
            int folderStartIndex = srcFile.getParent().length();  // position index Source Folder
            BatchActionResult result = new BatchActionResult();
            for (File currFile : childFiles) {
                String entryZipPath = currFile.getPath().substring(folderStartIndex);
                ZipFileUtil.writeZipEntry(zos, currFile, entryZipPath);

                result.setTotalSuccess(1);
                result.setDesciption(currFile.getPath());
                publishProgress(result);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            mProgressResult.setDesciption(ex.getMessage());
        }
        return mProgressResult;
    }

    @Override
    protected void onProgressUpdate(BatchActionResult... values) {
        super.onProgressUpdate(values);

        // calculate return value, add result
        for (BatchActionResult result : values) {
            mProgressResult.add(result);
            mProgressResult.setDesciption(result.getDesciption());
        }

        //return listener
        if (mListener != null)
            mListener.onProgressUpdate(mProgressResult);
    }

    @Override
    protected void onPostExecute(BatchActionResult actionResult) {
        super.onPostExecute(actionResult);

        if (mListener!=null)
            mListener.onPostExecute(actionResult);
    }

    /** Lắng nghe hành động trả về */
    public interface Listener {
        /** Update giá trị */
        void onProgressUpdate(BatchActionResult batchResult);
        /** lấy kết quả hành đổng */
        void onPostExecute(BatchActionResult batchResult);
    }
}
