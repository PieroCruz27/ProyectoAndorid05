package com.cibertec.proyecto.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionRest {

    private static final String URL = "https://api-cibertec-moviles.herokuapp.com/servicio/";
    private static Retrofit retrofit = null ;
    public static Retrofit getConnection(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }


}
