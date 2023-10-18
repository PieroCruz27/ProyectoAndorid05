package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Autor;
import com.cibertec.proyecto.entity.Sala;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceSala {

    @GET("sala/porNumero/{numero}")
    public Call<List<Sala>> listaSala (@Path("numero") String numero);

}
