package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Alumno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceAlumno {
    

    @POST("alumno")
    public abstract Call<Alumno> insertaAlumno(@Body Alumno objAlumno);

    @GET("alumno")
    public abstract Call<List<Alumno>> listaAlumno();


    @GET("alumno/porNombre/{nombre}")
    public Call<List<Alumno>> listaAlumnosPorNombre(@Path("nombre")String nombre);


    @POST("alumno")
    public abstract Call<Alumno> registra(@Body Alumno objAlumno);

    @PUT("alumno")
    public abstract Call<Alumno> actualiza(@Body Alumno objAlumno);


}
