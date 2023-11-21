package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Autor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceAutor {
    @GET("autor")
    public abstract Call<List<Autor>> listaAutor();
    @POST("autor")
    public abstract Call<Autor> insertaAutor(@Body Autor objAutor);

    @GET("autor/porNombre/{nombre}")
    public Call<List<Autor>> listaAutorPorNombre (@Path("nombre") String nombre);

    @PUT("autor")
    public abstract Call<Autor> actualiza(@Body Autor objAutor);


}
