package com.cibertec.proyecto.vista.consulta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.LibroAdapter;
import com.cibertec.proyecto.entity.Libro;
import com.cibertec.proyecto.service.ServiceLibro;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibroConsultaActivity extends NewAppCompatActivity {

    Button btnConsultar;

    ListView lstConsultaLibro;
    ArrayList<Libro> data = new ArrayList<>();
    LibroAdapter adapter;
    ServiceLibro serviceLibro;

    EditText txtConsultar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_consulta);

        btnConsultar = findViewById(R.id.btnConsultar);
        txtConsultar = findViewById(R.id.txtConsultar);
        lstConsultaLibro = findViewById(R.id.lstConsultaLibro);
        adapter = new LibroAdapter(this, R.layout.activity_libro_consulta_item, data);
        lstConsultaLibro.setAdapter(adapter);

        serviceLibro = ConnectionRest.getConnection().create(ServiceLibro.class);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consulta();
            }
        });


    }

    void consulta() {
        String filtro = txtConsultar.getText().toString().trim();
        Call<List<Libro>> call = serviceLibro.listaLibro(filtro);
        call.enqueue(new Callback<List<Libro>>() {
            @Override
            public void onResponse(Call<List<Libro>> call, Response<List<Libro>> response) {
                if (response.isSuccessful()) {
                    List<Libro> lstSalida = response.body();
                    data.clear();
                    data.addAll(lstSalida);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Libro>> call, Throwable t) {
                mensajeAlert("Mensaje => " + t.getMessage());
            }
        });
    }

}