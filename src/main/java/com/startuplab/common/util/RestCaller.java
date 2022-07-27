package com.startuplab.common.util;

import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jaxb.JaxbConverterFactory;

public class RestCaller {

    public enum TYPE {
        JSON, XML
    }

    // private Gson gson;
    private int timeout = 30;
    private Retrofit retrofit = null;
    private OkHttpClient client = null;

    public RestCaller(String url) {
        this(url, TYPE.JSON);
    }

    public RestCaller(String url, TYPE type) {
        // gson = new Gson();

        ConnectionPool pool = new ConnectionPool(50, 10, TimeUnit.SECONDS);
        client = new OkHttpClient.Builder().connectTimeout(timeout, TimeUnit.SECONDS) // 연결 타임아웃 시간 설정
                .writeTimeout(timeout, TimeUnit.SECONDS) // 쓰기 타임아웃 시간 설정
                .readTimeout(timeout, TimeUnit.SECONDS) // 읽기 타임아웃 시간 설정
                .connectionPool(pool) //
                .build();
        retrofit2.Converter.Factory factory = GsonConverterFactory.create();
        switch (type) {
            case JSON:
                factory = GsonConverterFactory.create();
                break;
            case XML:
                factory = JaxbConverterFactory.create();
        }

        retrofit = new Retrofit.Builder().baseUrl(url).client(client).addConverterFactory(factory).build();

    }

    public <T> T getApiCall(Class<T> service) {
        return retrofit.create(service);
    }

}
