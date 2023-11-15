package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Modalidad;
import com.cibertec.proyecto.entity.Sede;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SedeService {

    @GET("util/listaSede")
    public Call<List<Sede>> listaTodos();

}
