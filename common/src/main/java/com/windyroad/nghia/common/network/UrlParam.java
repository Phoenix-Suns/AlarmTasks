package com.windyroad.nghia.common.network;

/**
 * Created by Nghia-PC on 7/15/2015.
 * Tham số Thêm vào WebService
 */
public class UrlParam {
    private ParamType type;
    /**Key, Name của Field*/
    private String key;
    /**Nếu Type=File => Value = FilePath
     * phải kiểm tra file tồn tại trước */
    private String value;

    public UrlParam(ParamType type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public ParamType getType() {
        return type;
    }

    public void setType(ParamType type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Kiểu tham số TEXT/FILE
     */
    public enum ParamType {
        TEXT,
        FILE
    }
}
