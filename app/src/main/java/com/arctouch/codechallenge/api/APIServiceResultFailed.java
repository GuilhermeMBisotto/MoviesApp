package com.arctouch.codechallenge.api;

import com.google.gson.annotations.SerializedName;

public class APIServiceResultFailed {

    @SerializedName("status_code")
    private int code;
    public int getCode() {
        return code;
    }

    @SerializedName("status_message")
    private String message;
    public String getMessage() {
        return message == null ? "No message" : message;
    }

    public static APIServiceResultFailed createResultFailed(String errorMessage) {
        APIServiceResultFailed result = new APIServiceResultFailed();
        result.code = 999;
        result.message = errorMessage;

        return result;
    }

    public static APIServiceResultFailed createResultFailed(int code, String errorMessage) {
        APIServiceResultFailed result = new APIServiceResultFailed();
        result.code = code;
        result.message = errorMessage;

        return result;
    }
}
