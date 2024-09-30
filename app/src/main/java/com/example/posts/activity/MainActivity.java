package com.example.posts.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.post.R;
import com.example.posts.model.Noticia;
import com.example.posts.my_interface.interfaz;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> arrayAdapter; // Declarar el adaptador correctamente
    private ListView list;
    private ArrayList<String> titles = new ArrayList<>();
    private static final String URL_BASE = "https://jsonplaceholder.typicode.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar la ListView y el ArrayAdapter
        list = findViewById(R.id.list);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);

        // Asignar el adaptador a la ListView
        list.setAdapter(arrayAdapter);

        // Llamar al método para obtener los datos
        getPosts();
    }

    private void getPosts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfaz postService = retrofit.create(interfaz.class);
        Call<List<Noticia>> call = postService.getPost();

        call.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                // Verificar que la respuesta no sea nula
                if (response.body() != null) {
                    for (Noticia post : response.body()) {
                        titles.add(post.getTitle());
                    }
                    // Notificar al adaptador que los datos han cambiado
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                // Manejar el error aquí si lo deseas
            }
        });
    }
}


