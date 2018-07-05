package com.windyroad.nghia.common.models;

/**
 * Created by Nghia-PC on 7/17/2015.
 * Khi thực hiện hành động => trả về kết quả
 * Kết quả + Tin nhắn
 */
public class ActionResult {
    private ResultStatus result;
    private String message;

    public ActionResult() {
    }

    public ActionResult(ResultStatus result, String message) {
        this.result = result;
        this.message = message;
    }

    public ResultStatus getResult() {
        return result;
    }

    public void setResult(ResultStatus result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public enum  ResultStatus{
        SUCCESS("SUCCESS"),
        FAIL("FAIL");

        private final String mText;
        /**
         * Enum khởi tạo, truyền TextName vào
         * @param text
         */
        ResultStatus(String text) {
            this.mText = text;
        }
        @Override
        public String toString() {
            return mText;
        }
    }
}
