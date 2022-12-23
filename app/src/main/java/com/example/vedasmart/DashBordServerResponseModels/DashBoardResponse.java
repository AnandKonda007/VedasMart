package com.example.vedasmart.DashBordServerResponseModels;

import java.util.ArrayList;

public class DashBoardResponse {
    private int response;
    private String message;
    private ArrayList<CategoryInfo> CategoryInfo;
    private ArrayList<DashBoardData> DashBoardData;
    private ArrayList<DailyDeals> DailyDeals;
    private ArrayList<BestSellings> bestSellings;

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

    public ArrayList<com.example.vedasmart.DashBordServerResponseModels.CategoryInfo> getCategoryInfo() {
        return CategoryInfo;
    }

    public void setCategoryInfo(ArrayList<com.example.vedasmart.DashBordServerResponseModels.CategoryInfo> categoryInfo) {
        CategoryInfo = categoryInfo;
    }

    public ArrayList<com.example.vedasmart.DashBordServerResponseModels.DashBoardData> getDashBoardData() {
        return DashBoardData;
    }

    public void setDashBoardData(ArrayList<com.example.vedasmart.DashBordServerResponseModels.DashBoardData> dashBoardData) {
        DashBoardData = dashBoardData;
    }

    public ArrayList<com.example.vedasmart.DashBordServerResponseModels.DailyDeals> getDailyDeals() {
        return DailyDeals;
    }

    public void setDailyDeals(ArrayList<com.example.vedasmart.DashBordServerResponseModels.DailyDeals> dailyDeals) {
        DailyDeals = dailyDeals;
    }

    public ArrayList<BestSellings> getBestSellings() {
        return bestSellings;
    }

    public void setBestSellings(ArrayList<BestSellings> bestSellings) {
        this.bestSellings = bestSellings;
    }
}
