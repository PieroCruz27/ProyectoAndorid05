package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Sala;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceSala {

    @GET("sala/porNumero/{numero}")
    public Call<List<Sala>> listaSala (@Path("numero") String numero);

    @GET("sala")
    public abstract Call<List<Sala>> listaSala();

    @POST("sala")
    public abstract Call<Sala> registra(@Body Sala objSala);

    @PUT("sala")
    public abstract Call<Sala> actualiza(@Body Sala objSala);

}
