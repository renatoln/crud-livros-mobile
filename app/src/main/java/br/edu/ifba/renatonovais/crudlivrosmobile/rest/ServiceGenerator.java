package br.edu.ifba.renatonovais.crudlivrosmobile.rest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static final String BASE_URL = "http://stadsifba.pythonanywhere.com/";

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static HttpLoggingInterceptor loggingInteceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass) {
        if (!httpClientBuilder.interceptors().contains(loggingInteceptor)) {
            httpClientBuilder.addInterceptor(loggingInteceptor);
            builder = builder.client(httpClientBuilder.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }
    
}
