package models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class AddressList {
    @Getter @Setter @JsonProperty("hits")
    private List<Address> addressList;

    @Getter @Setter
    public static final class Address {
        private String name;
        private String country;
        private String city;
    }
}
