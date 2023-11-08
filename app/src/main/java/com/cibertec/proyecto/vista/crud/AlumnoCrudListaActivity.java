package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.util.NewAppCompatActivity;


public class AlumnoCrudListaActivity extends NewAppCompatActivity {


    Button btnLista;
    Button btnRegistra;
    ListView lstLista;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_crud_lista);

        btnLista = findViewById(R.id.idCrudAlumnoLista);
        btnRegistra = findViewById(R.id.idCrudAlumnoRegistrar);
        lstLista = findViewById(R.id.idCrudAlumnoListView);

        btnRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        AlumnoCrudListaActivity.this,
                        AlumnoCrudFormularioActivity.class);
                intent.putExtra("var_tipos", "REGISTRAR");
                startActivity(intent);
            }
        });
    }





}