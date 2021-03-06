package com.example.architectuecomp.network.api;

import com.example.architectuecomp.model.Covid;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CovidApi {

    @GET("statistics")
    Call<Covid> getCovidData();
}
