package api;

import constants.Constants;
import constants.EnvVariables;

import okhttp3.HttpUrl;
import okhttp3.Request;

public interface AddressService {

    String GRAPH_HOPPER_URL = "https://graphhopper.com/api/1/%s";

    static Request getAddressList(String query) {
        String baseUrl = String.format(GRAPH_HOPPER_URL, "geocode");
        HttpUrl url = HttpUrl
            .parse(baseUrl)
            .newBuilder()
            .addQueryParameter("q", query.toLowerCase())
            .addQueryParameter("locale", Constants.LANGUAGE)
            .addQueryParameter("limit", Integer.toString(Constants.ITEMS_NUMBER))
            .addQueryParameter("debug", "true")
            .addQueryParameter("key", EnvVariables.get("GRAPH_HOPPER_API_KEY"))
            .build();
        return new Request.Builder().url(url).build();
    }
}
