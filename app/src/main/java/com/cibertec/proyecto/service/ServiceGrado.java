package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Grado;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceGrado {

    @GET("util/listaGrado")
    public Call<List<Grado>> listaGrado();

}
