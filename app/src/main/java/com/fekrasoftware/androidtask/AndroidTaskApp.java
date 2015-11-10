package com.fekrasoftware.androidtask;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by mohamedzakaria on 11/7/15.
 */
public class AndroidTaskApp extends Application {
    public static Retrofit retrofit;
    public static DisplayImageOptions options;

    @Override
    public void onCreate() {
        super.onCreate();
        // init Retrofit
        initRetroFit();
        // init ORM
        ActiveAndroid.initialize(this);


    }

    private void initRetroFit() {
        OkHttpClient okClient = new OkHttpClient();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        okClient.interceptors().add(interceptor);
        okClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                return response;
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("http://grapesnberries.getsandbox.com/")
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


}
