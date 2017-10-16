/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.network.webclientconfig.client;

/**
 *
 * @author Chathun
   Author:- Chathuranga Bandara
   Email :- chathunbandara@gmail.com
   Mobile:- +94716271637 
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import application.constance.Uri;
import application.network.webclientconfig.interfaces.APIService;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    
    private static APIService REST_CLIENT;
    
 
    static {
        setupRestClient();
    }
 
    private ApiClient() {}
 
    public static APIService get() {
        return REST_CLIENT;
    }
 
    private static void setupRestClient() {
            Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Uri.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
               // .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                .client(new okhttp3.OkHttpClient.Builder()
                        .connectTimeout(180 , TimeUnit.SECONDS)
                        .readTimeout(180, TimeUnit.SECONDS)
                        .writeTimeout(180, TimeUnit.SECONDS)
                        .addInterceptor(logging)
                        .build())
                .build();
               REST_CLIENT = retrofit.create(APIService.class);
    }
}