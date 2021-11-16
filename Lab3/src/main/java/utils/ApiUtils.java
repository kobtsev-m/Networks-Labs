package utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ApiUtils {

    private static final OkHttpClient httpClient = new OkHttpClient();

    private static final int HTTP_OK = 200;
    private static final int HTTP_MANY_REQUESTS = 429;

    private static final int MAX_REQUESTS_NUMBER = 5;
    private static final int REQUEST_RETRY_TIMEOUT = 500;

    private static Response createCall(Request request, int requestsTotal) {
        if (requestsTotal >= MAX_REQUESTS_NUMBER) {
            return null;
        }
        Response response;
        try {
            response = httpClient.newCall(request).execute();
            if (response.code() == HTTP_MANY_REQUESTS) {
                response.close();
                Thread.sleep(REQUEST_RETRY_TIMEOUT);
                return createCall(request, requestsTotal + 1);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return null;
        }
        if (response.code() != HTTP_OK || response.body() == null) {
            response.close();
            return null;
        }
        return response;
    }

    public static <T> CompletableFuture<T> call(Request request, Class<T> type) {
        return CompletableFuture.supplyAsync(() -> {
            Response response = createCall(request, 0);
            try (response) {
                String jsonString = Objects.requireNonNull(response.body()).string();
                return JsonUtils.parse(jsonString, type);
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
                return null;
            }
        });
    }
}
