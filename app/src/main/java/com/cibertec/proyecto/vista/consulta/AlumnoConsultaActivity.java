package com.cibertec.proyecto.vista.consulta;


import androidx.appcompat.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.AlumnoAdapter;
import com.cibertec.proyecto.entity.Alumno;
import com.cibertec.proyecto.service.ServiceAlumno;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import android.widget.Toast;
import android.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class AlumnoConsultaActivity extends NewAppCompatActivity {

    EditText txtNombre;
    Button btnFiltrar;


    //obtener listview

    ListView lstConsultaAlumno;
    ArrayList<Alumno> data = new ArrayList<Alumno>();
    AlumnoAdapter adapatador;

    ServiceAlumno serviceAlumno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alumno_consulta);

        txtNombre = findViewById(R.id.txtRegAluPorNombres);
        lstConsultaAlumno = findViewById(R.id.lstConsultaAlumnos);
        adapatador = new AlumnoAdapter(this, R.layout.activity_alumno_item_nombre, data);

        lstConsultaAlumno.setAdapter(adapatador);

        btnFiltrar = findViewById(R.id.btnRegAluFiltrar);

        serviceAlumno = ConnectionRest.getConnection().create(ServiceAlumno.class);

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String filtroAlum = txtNombre.getText().toString();
               consulta(filtroAlum);
            }
        });
    }

    public void consulta(String filtroAlum){
        Call<List<Alumno>> call = serviceAlumno.listaAlumnosPorNombre(filtroAlum);
        call.enqueue(new Callback<List<Alumno>>() {
            @Override
            public void onResponse(Call<List<Alumno>> call, Response<List<Alumno>> response) {
                if (response.isSuccessful()){
                    List<Alumno> ltSalAl = response.body();
                    data.clear();;
                    data.addAll(ltSalAl);
                    adapatador.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Alumno>> call, Throwable t) {
                mensajeAlert("ERROR -> ERROR EN LA RESPUESTA" + t.getMessage());
            }
        });

    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    public void mensajeToastLong(String msg){
        Toast toast1 =  Toast.makeText(this,msg, Toast.LENGTH_LONG);
        toast1.show();
    }
    public void mensajeToastShort(String msg){
        Toast toast1 =  Toast.makeText(this,msg, Toast.LENGTH_SHORT);
        toast1.show();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

    }


}