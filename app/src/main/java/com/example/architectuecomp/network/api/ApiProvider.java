package com.example.architectuecomp.network.api;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiProvider {
    private static ApiProvider instance;

    public static ApiProvider getInstance(){
        if(instance == null){
            instance = new ApiProvider();
        }
        return instance;
    }
    public CovidApi provide()
    {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder()
                                .addHeader("x-rapidapi-host", "covid-193.p.rapidapi.com")
                                .addHeader("x-rapidapi-key", "PLACE_YOUR_OWN")
                                .build();

                        return chain.proceed(newRequest);
                    }
                })
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://covid-193.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(CovidApi.class);
    }
}
