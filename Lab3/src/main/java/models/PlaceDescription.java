package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlaceDescription {
    public String xid;
    public String name;
    public String image;
    public boolean isNull = false;

    public Info info;
    public static final class Info {
        @JsonProperty("descr")
        public String description;
    }

    @JsonProperty("wikipedia_extracts")
    public WikipediaExtracts wikipediaExtracts;
    public static final class WikipediaExtracts {
        private String title;
        private String text;
    }
}
