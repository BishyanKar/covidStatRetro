package com.example.architectuecomp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "case_table")
public class Case {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String country,timeStamp,total,deaths,tests;

    public Case(String country, String timeStamp, String total, String deaths, String tests) {
        this.country = country;
        this.timeStamp = timeStamp;
        this.total = total;
        this.deaths = deaths;
        this.tests = tests;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }
}
