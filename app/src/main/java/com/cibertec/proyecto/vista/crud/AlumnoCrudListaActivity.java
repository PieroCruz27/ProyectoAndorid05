package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.AlumnoAdapter;
import com.cibertec.proyecto.entity.Alumno;
import com.cibertec.proyecto.service.ServiceAlumno;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AlumnoCrudListaActivity extends NewAppCompatActivity {


    Button btnLista;
    Button btnRegistra;
    ListView lstLista;

    ArrayList<Alumno> data = new ArrayList<>();
    AlumnoAdapter adapatador;
    ServiceAlumno serviceAlumP;

   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_crud_lista);

        btnLista = findViewById(R.id.idCrudAlumnoLista);
        btnRegistra = findViewById(R.id.idCrudAlumnoRegistrar);
        lstLista = findViewById(R.id.idCrudAlumnoListView);

        adapatador = new AlumnoAdapter(this, R.layout.activity_alumno_crud_item, data);
        lstLista.setAdapter(adapatador);

        serviceAlumP = ConnectionRest.getConnection().create(ServiceAlumno.class);

        btnRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        AlumnoCrudListaActivity.this,
                        AlumnoCrudFormularioActivity.class);
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

                Alumno objDataSeleccionada = data.get(i);

                Intent intent = new Intent(
                        AlumnoCrudListaActivity.this,
                        AlumnoCrudFormularioActivity.class);

                intent.putExtra("var_tipo", "Actualizar");
                intent.putExtra("var_seleccionado", objDataSeleccionada);
                startActivity(intent);
            }
        });

        lista();

    }


    public void lista() {
        Call<List<Alumno>> call = serviceAlumP.listaAlumno();
        call.enqueue(new Callback<List<Alumno>>() {
            @Override
            public void onResponse(Call<List<Alumno>> call, Response<List<Alumno>> response) {
                if (response.isSuccessful()) {
                    List<Alumno> lstSalida = response.body();
                    data.clear();
                    ;
                    data.addAll(lstSalida);
                    adapatador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Alumno>> call, Throwable t) {

            }
        });

    }

}