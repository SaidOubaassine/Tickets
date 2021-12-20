package com.example.demo;

public class TicketItem {

    String date1, date2, status, mealType;

    public TicketItem(String date1, String date2, String status, String mealType) {
        this.date1 = date1;
        this.date2 = date2;
        this.status = status;
        this.mealType = mealType;
    }

    public TicketItem() {
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getdate2() {
        return date2;
    }

    public void setdate2(String date2) {
        this.date2 = date2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
}
