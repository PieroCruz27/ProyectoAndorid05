package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.util.NewAppCompatActivity;

public class AlumnoCrudFormularioActivity extends NewAppCompatActivity {


    Button btnRegresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_crud_formulario);

        btnRegresar = findViewById(R.id.btnCrudAlumnoRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        AlumnoCrudFormularioActivity.this,
                        AlumnoCrudListaActivity.class);
                startActivity(intent);

            }
        });

    }


}