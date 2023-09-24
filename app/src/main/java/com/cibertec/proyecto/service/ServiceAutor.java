package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Autor;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceAutor {
    @POST("autor")
    public abstract Call<Autor> insertaAutor(@Body Autor objAutor);
}
