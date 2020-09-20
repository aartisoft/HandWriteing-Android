package com.copypasteit.handwriting.competition.main.data.remote;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://www.api.androwep.com/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
