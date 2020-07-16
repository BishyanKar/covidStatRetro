package com.example.architectuecomp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CovidApi {

    @GET("statistics")
    Call<Covid> getCovidData();
}
