package com.dorianmusaj.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dorianmusaj.weatherapp.viewmodel.MainActivityViewModel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity implements MainActivityViewModel.ViewModelCallback {

    private static int LOCATION_PERMISSION_REQUEST_CODE = 100;

    public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
    public static final int LOCATION_UPDATE_MIN_TIME = 2000;
    public static SimpleDateFormat standardDateFormat = new SimpleDateFormat("EEE,MMMM dd hh:mm a", Locale.getDefault());


    private TextView mCityTv, mDateTv, mDegreeTv, mRangeTv, mCommentTv;
    private ImageView mIconIv;
    private ProgressBar mProgressBar;
    private LocationManager mLocationManager;
    private CompositeDisposable disposable;
    DecimalFormat value;
    private MainActivityViewModel viewModel;

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            if (location != null) {

                viewModel.fetchWeatherDataForLocation(location);
                // mLocationManager.removeUpdates(mLocationListener);
            } else {
                Toast.makeText(MainActivity.this, "Location not updated!", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

            Toast.makeText(MainActivity.this, getString(R.string.enable_gps), Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view references
        mCityTv = findViewById(R.id.textview_city);
        mDateTv = findViewById(R.id.textview_date);
        mDegreeTv = findViewById(R.id.textview_degree);
        mRangeTv = findViewById(R.id.textview_range);
        mCommentTv = findViewById(R.id.textview_comment);

        mIconIv = findViewById(R.id.imageview_icon);
        mProgressBar = findViewById(R.id.progressBar);

        //setters
        disposable = new CompositeDisposable();
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        value = new DecimalFormat("#.#");

        viewModel = new MainActivityViewModel();
        viewModel.setCallback(this);

        fetchUserLocation();

    }


    @Override
    public void onStartLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopLoading(String error) {

        mProgressBar.setVisibility(View.GONE);

        if (error != null)
            Toast.makeText(MainActivity.this, "An error has occured!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void bindWeatherData(WeatherObject weatherObject) {

        onStopLoading(null);

        mCityTv.setText(weatherObject.getTimezone());

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(weatherObject.getCurrently().getTime());
        mDateTv.setText(standardDateFormat.format(c.getTime()));

        mCommentTv.setText(weatherObject.getCurrently().getSummary());
        mRangeTv.setText(fahrenheitToCelcius(weatherObject.getCurrently().getTemperature()) + "°/"
                + fahrenheitToCelcius(weatherObject.getCurrently().getDewPoint()) + "°");

        mDegreeTv.setText(fahrenheitToCelcius(weatherObject.getCurrently().getApparentTemperature()) + "°");

        String summary = weatherObject.getCurrently().getSummary();

        if (summary.contains("Clear"))
            mIconIv.setImageResource(R.drawable.ic_weather_forecast_hot_sun_day_3859136);
        else if (summary.contains("Rain"))
            mIconIv.setImageResource(R.drawable.ic_weather_forecast_night_rain_climate_3859142);
        else if (summary.contains("Storm"))
            mIconIv.setImageResource(R.drawable.ic_weather_forecast_lightning_storm_energy_3859139);
        else if (summary.contains("Light"))
            mIconIv.setImageResource(R.drawable.ic_weather_forecast_lightning_cloud_storm_3859137);
        else if (summary.contains("Snow"))
            mIconIv.setImageResource(R.drawable.ic_weather_forecast_cloud_snowing_cloud_climate_3859131);
        else
            mIconIv.setImageResource(R.drawable.ic_weather_forecast_generic_3859132);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    public void fetchUserLocation() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);

        } else {
            mProgressBar.setVisibility(View.VISIBLE);

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                }
        }
    }

    public String fahrenheitToCelcius(double fahrenheit) {
        double celcius = (5 * (fahrenheit - 32.0)) / 9.0;
        return value.format(celcius);
    }

}
