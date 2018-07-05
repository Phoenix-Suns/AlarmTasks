package com.windyroad.nghia.common.network;

/**
 * Created by Nghia-PC on 9/16/2015.
 * Form MIME Type of Webservice
 */
public enum FormMIMEType {
    X_WWW_FORM_URLENDCODED("application/x-www-form-urlencoded"),
    FORM_DATA("form-data");

    private final String text;
    FormMIMEType(final String text) {
        this.text = text;
    }
    @Override
    public String toString() {
        return text;
    }
}
