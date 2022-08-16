package com.pinquiry.api.model.rest;

public class HeaderKeyValue {
    private String header;
    private String value;

    public HeaderKeyValue() {
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
