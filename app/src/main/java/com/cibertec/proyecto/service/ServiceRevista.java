package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Revista;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceRevista {

    @POST("revista")
    public abstract Call<Revista> insertRevista(@Body Revista revista);

}
