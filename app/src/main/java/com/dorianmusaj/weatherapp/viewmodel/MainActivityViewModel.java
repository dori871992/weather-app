package com.dorianmusaj.weatherapp.viewmodel;

import android.location.Location;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.dorianmusaj.weatherapp.MainActivity;
import com.dorianmusaj.weatherapp.WeatherObject;
import com.dorianmusaj.weatherapp.network.ApiClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel extends ViewModel {

    public interface ViewModelCallback   {

        void onStartLoading();
        void onStopLoading(String error);
        void bindWeatherData(WeatherObject weatherObject);

    }

    private ViewModelCallback callback;
    private CompositeDisposable compositeDisposable;
    private ApiClient apiClient;

    public MainActivityViewModel (){
        compositeDisposable = new CompositeDisposable();
        apiClient= new ApiClient();
    }


    public void setCallback(ViewModelCallback callback){
        this.callback = callback;
    }


    public void fetchWeatherDataForLocation(Location location) {

        callback.onStartLoading();

        compositeDisposable.add(
                apiClient.fetchWeather(location)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableObserver<WeatherObject>() {
                                           @Override
                                           public void onNext(WeatherObject weatherObject) {

                                               callback.bindWeatherData(weatherObject);
                                           }

                                           @Override
                                           public void onError(Throwable e) {
                                               callback.onStopLoading("An error has occured!");
                                               }

                                           @Override
                                           public void onComplete() {

                                           }
                                       }

                        ));

    }

}
