package ru.yksoft.padmitriy.owmapi;

import android.app.Application;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.yksoft.padmitriy.owmapi.model.RectangTownListResponse;

import static ru.yksoft.padmitriy.owmapi.model.Constants.API_URL;

/**
 * Created by padmitriy on 07.08.17.
 */

public class MyApplication extends Application {

    public static Retrofit retrofitOwm;

    public static List<RectangTownListResponse.TList> townGlobalList;


    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request request = chain
                                .request()
                                .newBuilder()
                                .build();
                        return chain.proceed(request);
                    }
                }).build();

        retrofitOwm = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }


}
