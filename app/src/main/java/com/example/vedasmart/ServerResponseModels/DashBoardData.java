package com.example.vedasmart.ServerResponseModels;

import java.util.ArrayList;

public class DashBoardData {
    String PhoneNumber;
    private ArrayList<advertisements> advertisements;
    private ArrayList<banners> baners;

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public ArrayList<com.example.vedasmart.ServerResponseModels.advertisements> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(ArrayList<com.example.vedasmart.ServerResponseModels.advertisements> advertisements) {
        this.advertisements = advertisements;
    }

    public ArrayList<banners> getBaners() {
        return baners;
    }

    public void setBaners(ArrayList<banners> baners) {
        this.baners = baners;
    }
}
