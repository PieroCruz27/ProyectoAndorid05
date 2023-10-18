package com.cibertec.proyecto.vista.consulta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.SalaAdapter;
import com.cibertec.proyecto.entity.Sala;
import com.cibertec.proyecto.service.ServiceSala;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalaConsultaActivity extends NewAppCompatActivity {

    Button btnListar;

    ListView lstSalas;
    ArrayList<Sala> data = new ArrayList<>();
    SalaAdapter adaptador;

    ServiceSala serviceSala;
    EditText txtFiltro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_consulta);

        btnListar = findViewById(R.id.idBtnSalaConsulta);
        txtFiltro = findViewById(R.id.idTxtSalaConsulta);
        lstSalas = findViewById(R.id.idLstSalaConsulta);
        adaptador = new SalaAdapter(this, R.layout.activity_sala_consulta_item, data);
        lstSalas.setAdapter(adaptador);

        serviceSala = ConnectionRest.getConnection().create(ServiceSala.class);


        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consulta();
            }
        });


    }

    void consulta(){
        String filtro = txtFiltro.getText().toString().trim();

        Call<List<Sala>> call = serviceSala.listaSala(filtro);
        call.enqueue(new Callback<List<Sala>>() {
            @Override
            public void onResponse(Call<List<Sala>> call, Response<List<Sala>> response) {
                   if (response.isSuccessful()){
                       List<Sala> lstSalida =   response.body();
                       data.clear();
                       data.addAll(lstSalida);
                       adaptador.notifyDataSetChanged();

                   }
            }

            @Override
            public void onFailure(Call<List<Sala>> call, Throwable t) {
                mensajeAlert("Mensaje => "  + t.getMessage());
            }
        });



    }



}