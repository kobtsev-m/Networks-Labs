package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {
    public String name;
    public String country;
    public String city;
    public String state;
    public String street;
    @JsonProperty("housenumber")
    public String houseNumber;
    @JsonProperty("countrycode")
    public String countryCode;
    @JsonProperty("point")
    public Coordinates coordinates;
    public static class Coordinates {
        public double lat;
        public double lng;
    }
}
