package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Alumno;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceAlumno {
    @POST("alumno")
    public abstract Call<Alumno> insertaAlumno(@Body Alumno objAlumno);
}
