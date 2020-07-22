package com.example.architectuecomp.reponses;
import com.example.architectuecomp.model.Cases;
import com.example.architectuecomp.model.Deaths;
import com.example.architectuecomp.model.Tests;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("cases")
    @Expose
    private Cases cases;
    @SerializedName("deaths")
    @Expose
    private Deaths deaths;
    @SerializedName("tests")
    @Expose
    private Tests tests;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("time")
    @Expose
    private String time;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Cases getCases() {
        return cases;
    }

    public void setCases(Cases cases) {
        this.cases = cases;
    }

    public Deaths getDeaths() {
        return deaths;
    }

    public void setDeaths(Deaths deaths) {
        this.deaths = deaths;
    }

    public Tests getTests() {
        return tests;
    }

    public void setTests(Tests tests) {
        this.tests = tests;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}