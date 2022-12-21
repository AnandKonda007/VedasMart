package com.example.vedasmart.ServerResponseModels;

import java.util.ArrayList;

public class DashBoardResponse {
    private int response;
    private String message;
    private ArrayList<CategoryInfo> CategoryInfo;
    private ArrayList<DashBoardData> DashBoardData;
    private ArrayList<DailyDeals> DailyDeals;
    private ArrayList<bestSellings> bestSellings;

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

    public ArrayList<com.example.vedasmart.ServerResponseModels.CategoryInfo> getCategoryInfo() {
        return CategoryInfo;
    }

    public void setCategoryInfo(ArrayList<com.example.vedasmart.ServerResponseModels.CategoryInfo> categoryInfo) {
        CategoryInfo = categoryInfo;
    }

    public ArrayList<com.example.vedasmart.ServerResponseModels.DashBoardData> getDashBoardData() {
        return DashBoardData;
    }

    public void setDashBoardData(ArrayList<com.example.vedasmart.ServerResponseModels.DashBoardData> dashBoardData) {
        DashBoardData = dashBoardData;
    }

    public ArrayList<com.example.vedasmart.ServerResponseModels.DailyDeals> getDailyDeals() {
        return DailyDeals;
    }

    public void setDailyDeals(ArrayList<com.example.vedasmart.ServerResponseModels.DailyDeals> dailyDeals) {
        DailyDeals = dailyDeals;
    }

    public ArrayList<com.example.vedasmart.ServerResponseModels.bestSellings> getBestSellings() {
        return bestSellings;
    }

    public void setBestSellings(ArrayList<com.example.vedasmart.ServerResponseModels.bestSellings> bestSellings) {
        this.bestSellings = bestSellings;
    }
}
