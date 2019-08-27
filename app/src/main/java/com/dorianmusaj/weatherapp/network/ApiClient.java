package com.dorianmusaj.weatherapp.network;

import android.location.Location;

import com.dorianmusaj.weatherapp.WeatherObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private ApiInterface api;

    public ApiClient() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        api =  new Retrofit.Builder()
                .baseUrl("https://api.darksky.net/")
                .client(new OkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson)) //creating builder
                .build()
                .create(ApiInterface.class);//creating retrofit


    }

    public Observable<WeatherObject> fetchWeather(Location location) {
        return api.getWeatherForLocation(String.valueOf(location.getLatitude()),String.valueOf( location.getLongitude()));
    }

}
