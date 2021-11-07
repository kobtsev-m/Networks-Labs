package models;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Coordinates {
    @JsonAlias("lat")
    public double lat;
    @JsonAlias({"lng", "lon"})
    public double lng;
}
