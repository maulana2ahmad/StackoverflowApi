package com.example.stackoverflowapi.rest;

import com.example.stackoverflowapi.model.UsersRecived;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserEndPoints {

    //https://api.stackexchange.com/2.2/users?page=1&pagesize=5&order=desc&site=stackoverflow
    //2.2/users?page=1&pagesize=5&order=desc&site=stackoverflow
    @GET("2.2/users?page=1&pagesize=5&order=desc&site=stackoverflow")
    Call<UsersRecived> getUsers (@Query("sort") String sort);

}