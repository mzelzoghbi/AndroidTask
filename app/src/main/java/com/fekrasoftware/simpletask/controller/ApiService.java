package com.fekrasoftware.simpletask.controller;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by mohamedzakaria on 11/7/15.
 */
public interface ApiService {
    @GET("/grap.json")
    Call<List<Repo>> listRepos(@Path("user") String user);
}
