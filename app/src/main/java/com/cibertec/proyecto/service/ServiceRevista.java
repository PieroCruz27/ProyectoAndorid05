package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Editorial;
import com.cibertec.proyecto.entity.Revista;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceRevista {

    @POST("revista")
    public abstract Call<Revista> insertRevista(@Body Revista revista);


    @GET("revista/porNombre/{nombre}")
    public Call<List<Revista>> listaPorNombre(@Path("nombre")String nombre);
}
