package com.dorianmusaj.weatherapp.network;

import com.dorianmusaj.weatherapp.WeatherObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("/forecast/2bb07c3bece89caf533ac9a5d23d8417/{latitude},{longtitude}")
    Observable<WeatherObject> getWeatherForLocation(@Path("latitude") String latitude, @Path("longtitude") String longtitude);

}
