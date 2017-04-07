package com.focus.projectx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Focus on 10.03.2017.
 */

public class RegisterRequestStatus {
    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("error")
    @Expose
    private String statusMessage;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
