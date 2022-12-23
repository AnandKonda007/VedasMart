package com.example.vedasmart.DashBordServerResponseModels;

import java.util.ArrayList;

public class Sign_in_Model {
    private int response;
    private String message;
    private String jsontoken;

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJsontoken() {
        return jsontoken;
    }

    public void setJsontoken(String jsontoken) {
        this.jsontoken = jsontoken;
    }

    public ArrayList<UserInfo> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ArrayList<UserInfo> userInfo) {
        this.userInfo = userInfo;
    }

    ArrayList<UserInfo> userInfo;

}
