package com.example.watertracker;

public class IntakeRecord {
    // Declare variables
    private String date;
    private int oz;

    public IntakeRecord() {
        // empty constructor
    }

    public IntakeRecord(String date, int oz) {
        this.date = date;
        this.oz = oz;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOz() {
        return oz;
    }

    public void setOz(int oz) {
        this.oz = oz;
    }
}
