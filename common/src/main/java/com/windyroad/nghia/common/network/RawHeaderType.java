package com.windyroad.nghia.common.network;

/**
 * Created by Nghia-PC on 9/16/2015.
 * Fix Header gởi Webservice Post Raw
 */
public enum RawHeaderType {
    TEXT("text/plain"),
    JSON("application/json"),
    JAVASCRIPT("application/javascript"),
    XML_APPLICATION("application/xml"),
    XML_TEXT("text/xml"),
    HTML("text/html");

    private final String text;
    RawHeaderType(final String text) {
        this.text = text;
    }
    @Override
    public String toString() {
        return text;
    }
}