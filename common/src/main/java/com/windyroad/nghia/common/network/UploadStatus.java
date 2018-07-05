package com.windyroad.nghia.common.network;

/**
 * Created by Nghia-PC on 7/14/2015.
 */
public enum UploadStatus {
    /**
     * Chờ upload
     */
    WAITING_UPLOAD("WATTING UPLOAD"),
    /**
     * Đang Upload
     */
    UPLOADING("UPLOADING"),
    /**
     * Đã upload
     */
    UPLOADED("UPLOADED");

    private final String text;

    UploadStatus(final String text) {
        if (text == null || text.isEmpty()) {
            this.text = "WATTING UPLOAD";
            return;
        }
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
