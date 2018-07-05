package com.windyroad.nghia.common.models;

/**
 * Created by Nghia-PC on 7/14/2015.
 */
public class BaseWebserviceGet {
    // properties
    public static final int CODE_RESULT_SUCCESS = 1;
    public static final int CODE_RESULT_UNKNOWN = -1;

    private int CodeResult;
    private String MessageResult;

    public BaseWebserviceGet() {
    }

    public BaseWebserviceGet(int codeResult, String messageResult) {
        CodeResult = codeResult;
        MessageResult = messageResult;
    }

    public static int getCodeResultSuccess() {
        return CODE_RESULT_SUCCESS;
    }

    public static int getCodeResultUnknown() {
        return CODE_RESULT_UNKNOWN;
    }

    public int getCodeResult() {
        return CodeResult;
    }

    public void setCodeResult(int codeResult) {
        CodeResult = codeResult;
    }

    public String getMessageResult() {
        return MessageResult;
    }

    public void setMessageResult(String messageResult) {
        MessageResult = messageResult;
    }
}
