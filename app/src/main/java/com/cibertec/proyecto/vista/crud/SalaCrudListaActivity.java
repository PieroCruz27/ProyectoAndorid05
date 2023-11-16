package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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


public class SalaCrudListaActivity extends NewAppCompatActivity {

    Button btnLista;
    Button btnRegistra;

    ListView lstLista;
    ArrayList<Sala> data = new ArrayList<>();
    SalaAdapter adapatador;

    ServiceSala serviceSala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_crud_lista);

        btnLista = findViewById(R.id.idCrudSalaLista);
        btnRegistra = findViewById(R.id.idCrudSalaRegistra);
        lstLista = findViewById(R.id.idCrudSalaListView);

        adapatador = new SalaAdapter(this, R.layout.activity_sala_crud_item, data);
        lstLista.setAdapter(adapatador);

        serviceSala = ConnectionRest.getConnection().create(ServiceSala.class);

        btnRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        SalaCrudListaActivity.this,
                                      SalaCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "Registrar");
                startActivity(intent);
            }
        });

        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lista();
            }
        });

        lstLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 //mensajeAlert(">>>>" + i );

                Sala objDataSeleccionada =  data.get(i);

                Intent intent = new Intent(
                         SalaCrudListaActivity.this,
                                    SalaCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "Actualizar");
                intent.putExtra("var_seleccionado", objDataSeleccionada);
                startActivity(intent);
            }
        });

        lista();
    }

    public void lista(){
        Call<List<Sala>> call = serviceSala.listaSala();
        call.enqueue(new Callback<List<Sala>>() {
            @Override
            public void onResponse(Call<List<Sala>> call, Response<List<Sala>> response) {
                if (response.isSuccessful()){
                     List<Sala> lstSalida = response.body();
                     data.clear();;
                     data.addAll(lstSalida);
                     adapatador.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Sala>> call, Throwable t) {}
        });
    }




}