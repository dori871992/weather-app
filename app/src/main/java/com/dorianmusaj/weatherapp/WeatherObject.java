package com.dorianmusaj.weatherapp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherObject {

    @JsonProperty("latitude")
    private double latitude;
    @JsonProperty("longitude")
    private double longitude;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("currently")
    private Currently currently;

    public WeatherObject() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Currently getCurrently() {
        return currently;
    }

    public void setCurrently(Currently currently) {
        this.currently = currently;
    }

    public class Currently {

        @JsonProperty("time")
        private long time;
        @JsonProperty("summary")
        private String summary;
        @JsonProperty("icon")
        private String icon;
        @JsonProperty("temperature")
        private double temperature;
        @JsonProperty("apparentTemperature")
        private double apparentTemperature;
        @JsonProperty("dewPoint")
        private double dewPoint;


        public Currently (){

        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public double getApparentTemperature() {
            return apparentTemperature;
        }

        public void setApparentTemperature(double apparentTemperature) {
            this.apparentTemperature = apparentTemperature;
        }

        public double getDewPoint() {
            return dewPoint;
        }

        public void setDewPoint(double dewPoint) {
            this.dewPoint = dewPoint;
        }
    }


}
