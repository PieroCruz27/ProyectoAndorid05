package com.cibertec.proyecto.vista.registra;


import android.app.AlertDialog;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Alumno;
import com.cibertec.proyecto.entity.Modalidad;
import com.cibertec.proyecto.entity.Pais;
import com.cibertec.proyecto.service.ServiceAlumno;
import com.cibertec.proyecto.service.ServiceModalidad;
import com.cibertec.proyecto.service.ServicePais;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.NewAppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnoRegistraActivity extends NewAppCompatActivity {

    Spinner spnPais;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> paises = new ArrayList<String>();
    Spinner spnModalidad;
    ArrayAdapter<String> adaptadorModalidad;
    ArrayList<String> modalidades = new ArrayList<String>();


    //Servicio
    ServiceModalidad serviceModalidad;
    ServicePais servicePais;
    ServiceAlumno serviceAlumno;

    EditText txtNombre, txtApellido,txtTelefono,txtDNI,txtCorreo,txtDireccion, txtFechaNacimiento;


    Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alumno_registra);


        serviceModalidad = ConnectionRest.getConnection().create(ServiceModalidad.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceAlumno = ConnectionRest.getConnection().create(ServiceAlumno.class);

        txtNombre = findViewById(R.id.txtRegAluNombres);
        txtApellido = findViewById(R.id.txtRegAluApellidos);
        txtTelefono = findViewById(R.id.txtRegAluTelefono);
        txtDNI = findViewById(R.id.txtRegAluDNI);
        txtCorreo = findViewById(R.id.txtRegAluCorreo);
        txtDireccion = findViewById(R.id.txtRegAluDireccion);
        txtFechaNacimiento = findViewById(R.id.txtRegAluFecha);

        btnEnviar = findViewById(R.id.btnRegAluEnviar);

        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPais = findViewById(R.id.spnRegAluPais);
        spnPais.setAdapter(adaptadorPais);

        adaptadorModalidad = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, modalidades);
        spnModalidad = findViewById(R.id.spnRegAluModalidad);
        spnModalidad.setAdapter(adaptadorModalidad);


        cargaPais();
        cargaModalidad();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = txtNombre.getText().toString();
                String ape = txtApellido.getText().toString();
                String tel = txtTelefono.getText().toString();
                String dni = txtDNI.getText().toString();
                String cor = txtCorreo.getText().toString();
                String dir = txtDireccion.getText().toString();
                String nac = txtFechaNacimiento.getText().toString();
                String idPais= spnPais.getSelectedItem().toString().split(":")[0];
                String idModalidad= spnModalidad.getSelectedItem().toString().split(":")[0];

                Pais objPaisesuu = new Pais();
                objPaisesuu.setIdPais(Integer.parseInt(idPais));

                Modalidad objModaldsdi = new Modalidad();
                objModaldsdi.setIdModalidad(Integer.parseInt(idModalidad));

                Alumno objAlumnoksd = new Alumno();
                objAlumnoksd.setNombres(nom);
                objAlumnoksd.setApellidos(ape);
                objAlumnoksd.setTelefono(tel);
                objAlumnoksd.setDni(dni);
                objAlumnoksd.setCorreo(cor);
                objAlumnoksd.setDireccion(dir);
                objAlumnoksd.setFechaNacimiento(nac);
                objAlumnoksd.setPais(objPaisesuu);
                objAlumnoksd.setModalidad(objModaldsdi);

                objAlumnoksd.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                objAlumnoksd.setEstado(1);


                insertaAlumno(objAlumnoksd);

            }
        });
    }

    public void mensajeToast(String mensaje){
        Toast toast1 =  Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }


    public void cargaPais(){
        Call<List<Pais>> call = servicePais.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()){
                    List<Pais> listapaisxd =  response.body();
                    for(Pais obj: listapaisxd){
                        paises.add(obj.getIdPais() +":"+ obj.getNombre());
                    }
                    adaptadorPais.notifyDataSetChanged();
                }else{
                    mensajeToast("Error al acceder al servi Rest de paises >>> ");
                }
            }
            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeToast("Error al acce al servi Rest de paisess >>> " + t.getMessage());
            }
        });
    }

    public void cargaModalidad(){
        Call<List<Modalidad>> call = serviceModalidad.listaTodos();
        call.enqueue(new Callback<List<Modalidad>>() {
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                if (response.isSuccessful()){
                    List<Modalidad> listaMoalidadxdd =  response.body();
                    for(Modalidad obj: listaMoalidadxdd){
                        modalidades.add(obj.getIdModalidad() +":"+ obj.getDescripcion());
                    }
                    adaptadorModalidad.notifyDataSetChanged();
                }else{
                    mensajeToast("Error al acceder al servi Rest de Modalidad >>> ");
                }
            }
            @Override
            public void onFailure(Call<List<Modalidad>> call, Throwable t) {
                mensajeToast("Error al acceder al servi Rest de modalidad >>> " + t.getMessage());
            }
        });
    }

    public  void insertaAlumno(Alumno objAlumno){

        Call<Alumno> call = serviceAlumno.insertaAlumno(objAlumno);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if (response.isSuccessful()){
                    Alumno objSalida = response.body();
                    mensajeAlert(" Registro exitoso  >>> ID >> " + objSalida.getIdAlumno()
                            + " >>> nombres >>> " +  objSalida.getNombres());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }
}