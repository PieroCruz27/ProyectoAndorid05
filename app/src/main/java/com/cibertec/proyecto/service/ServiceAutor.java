package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Autor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceAutor {
    @POST("autor")
    public abstract Call<Autor> insertaAutor(@Body Autor objAutor);

    @GET("autor/porNombre/{nombre}")
    public Call<List<Autor>> listaAutorPorNombre (@Path("nombre") String nombre);
}
