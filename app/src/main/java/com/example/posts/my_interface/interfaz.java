package com.example.posts.my_interface;

import com.example.posts.model.Noticia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface interfaz {

    String API_ROUTE = "/posts";

    @GET(API_ROUTE)
    Call<List<Noticia>> getPost();
}
