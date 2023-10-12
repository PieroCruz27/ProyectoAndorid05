package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Editorial;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceEditorial {
    //Para el registrar
    @POST("editorial")
    public Call<Editorial> insertaEditorial(@Body Editorial objEditorial);

   //Para el listar registrarS
    @GET("editorial/porNombre/{nombre}")
    public Call<List<Editorial>> listaPorNombre(@Path("nombre")String nombre);
}
