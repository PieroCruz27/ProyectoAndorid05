package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.EditorialAdapter;
import com.cibertec.proyecto.entity.Editorial;
import com.cibertec.proyecto.service.ServiceEditorial;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditorialCrudListaActivity extends NewAppCompatActivity {

    Button btnLista;
    Button btnRegistra;
    ListView lstLista;

    ArrayList<Editorial> data = new ArrayList<>();
    EditorialAdapter adapatador;

    ServiceEditorial serviceEditorial;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_crud_lista);

        btnLista = findViewById(R.id.idCrudEditorialLista);
        btnRegistra = findViewById(R.id.idCrudEditorialRegistra);
        lstLista = findViewById(R.id.idCrudEditorialListView);

        adapatador = new EditorialAdapter(this, R.layout.activity_editorial_crud_item, data);
        lstLista.setAdapter(adapatador);

        serviceEditorial = ConnectionRest.getConnection().create(ServiceEditorial.class);

        btnRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        EditorialCrudListaActivity.this,
                        EditorialCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "REGISTRAR");
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

                Editorial objDataSeleccionada =  data.get(i);

                Intent intent = new Intent(
                        EditorialCrudListaActivity.this,
                        EditorialCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "Actualizar");
                intent.putExtra("var_seleccionado", objDataSeleccionada);
                startActivity(intent);
            }
        });
        lista();
    }

        public void lista(){
            Call<List<Editorial>> call = serviceEditorial.listaEditorial();
            call.enqueue(new Callback<List<Editorial>>() {
                @Override
                public void onResponse(Call<List<Editorial>> call, Response<List<Editorial>> response) {
                    if (response.isSuccessful()){
                        List<Editorial> lstSalida = response.body();
                        data.clear();
                        data.addAll(lstSalida);
                        adapatador.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<List<Editorial>> call, Throwable t) {}
            });
        }
    }





