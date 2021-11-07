package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WeatherInfo {
    private int visibility;

    @JsonProperty("weather")
    private List<General> general;
    public static final class General {
        private String main;
        private String description;
    }

    @JsonProperty("main")
    private Parameters parameters;
    public static final class Parameters {
        private int pressure;
        private int humidity;
        @JsonProperty("temp")
        private double temperature;
        @JsonProperty("feels_like")
        private double feelsLike;
        @JsonProperty("temp_min")
        private double temperatureMin;
        @JsonProperty("temp_max")
        private double temperatureMax;
        @JsonProperty("sea_level")
        private int seaLevel;
        @JsonProperty("grnd_level")
        private int groundLevel;
    }

    public Wind wind;
    public static final class Wind {
        private double speed;
        private double gust;
        @JsonProperty("deg")
        private int degree;
    }

    public Clouds clouds;
    public static final class Clouds {
        @JsonProperty("all")
        private int clouds;
    }
}
