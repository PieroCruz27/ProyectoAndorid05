package com.cibertec.movil_modelo_proyecto_2022_2.service;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ServiceLibro {
    @POST("libro")
    public abstract Call<Libro> ingresarLibro(@Body Libro objLibro);

    @GET("libro")
    public abstract Call<List<Libro>> listarLibro();

    @PUT("libro")
    public abstract Call<Libro> actualizaLibros(@Body Libro objLibro);
}

