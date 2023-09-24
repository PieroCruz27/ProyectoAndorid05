package com.cibertec.proyecto.vista.registra;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Autor;
import com.cibertec.proyecto.entity.Grado;
import com.cibertec.proyecto.entity.Pais;
import com.cibertec.proyecto.service.ServiceAutor;
import com.cibertec.proyecto.service.ServiceGrado;
import com.cibertec.proyecto.service.ServicePais;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutorRegistraActivity extends NewAppCompatActivity {
    //Categoria
    Spinner spnGrado;
    ArrayAdapter<String> adaptadorGrado;
    ArrayList<String> grados = new ArrayList<String>();
    //Pais

    Spinner spnPais;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> paises = new ArrayList<String>();
    //Servicio
    ServiceAutor serviceAutor;
    ServiceGrado serviceGrado;
    ServicePais servicePais;
    //Componentes del formulario
    Button btnRegistrar;
    EditText txtNombres, txtApellidos, txtCorreo, txtFechaNacimiento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_autor_registra);
        //conecta a los servicios Rest
        serviceAutor = ConnectionRest.getConnection().create(ServiceAutor.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceGrado = ConnectionRest.getConnection().create(ServiceGrado.class);
        //Relacionar los variables con la variables de la GUI
        txtNombres = findViewById(R.id.txtRegEdiNombres);
        txtApellidos = findViewById(R.id.txtRegEdiApellidos);
        txtCorreo = findViewById(R.id.txtRegEdiCorreo);
        txtFechaNacimiento = findViewById(R.id.txtRegEdiFechaNacimiento);
        btnRegistrar = findViewById(R.id.btnRegEdiEnviar);

        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPais = findViewById(R.id.spnRegEdiPais);
        spnPais.setAdapter(adaptadorPais);

        adaptadorGrado = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, grados);
        spnGrado = findViewById(R.id.spnRegEdiGrado);
        spnGrado.setAdapter(adaptadorGrado);
        cargaPais();
        cargaGrado();
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombres = txtNombres.getText().toString();
                String apellidos = txtApellidos.getText().toString();
                String correo = txtCorreo.getText().toString();
                String fechaNacimiento = txtFechaNacimiento.getText().toString();

                String idPais = spnPais.getSelectedItem().toString().split(":")[0];
                String iso = spnPais.getSelectedItem().toString().split(":")[1];
                String nombre = spnPais.getSelectedItem().toString().split(":")[2];
                String idGrado = spnGrado.getSelectedItem().toString().split(":")[0];
                String descripcion = spnGrado.getSelectedItem().toString().split(":")[1];
                Pais objPais = new Pais();
                objPais.setIdPais(Integer.parseInt(idPais));
                objPais.setIso(iso);
                objPais.setNombre(nombre);
                Grado objGrado = new Grado();
                objGrado.setIdGrado(Integer.parseInt(idGrado));
                objGrado.setDescripcion(descripcion);
                Autor objAutor = new Autor();
                objAutor.setNombres(nombres);
                objAutor.setApellidos(apellidos);
                objAutor.setCorreo(correo);
                objAutor.setFechaNacimiento(fechaNacimiento);
                objAutor.setPais(objPais);
                objAutor.setGrado(objGrado);
                objAutor.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                objAutor.setEstado(1);
                insertaAutor(objAutor);
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
        Call<List<Pais>> call = servicePais.listaPais();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()){
                    List<Pais> lstPaises =  response.body();
                    for(Pais obj: lstPaises){
                        paises.add(obj.getIdPais() +":"+ obj.getNombre());
                    }
                    adaptadorPais.notifyDataSetChanged();
                }else{
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }
            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }
    public void cargaGrado(){
        Call<List<Grado>> call = serviceGrado.listaGrado();
        call.enqueue(new Callback<List<Grado>>() {
            @Override
            public void onResponse(Call<List<Grado>> call, Response<List<Grado>> response) {
                if (response.isSuccessful()){
                    List<Grado> lst =  response.body();
                    for(Grado obj: lst){
                        grados.add(obj.getIdGrado() +":"+ obj.getDescripcion());
                    }
                    adaptadorGrado.notifyDataSetChanged();
                }else{
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }
            @Override
            public void onFailure(Call<List<Grado>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }
    public  void insertaAutor(Autor objAutor){
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //String json = gson.toJson(objAutor);
        //mensajeAlert(json);

        Call<Autor> call = serviceAutor.insertaAutor(objAutor);
        call.enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if (response.isSuccessful()){
                    Autor objSalida = response.body();
                    mensajeAlert(" Registro exitoso  >>> ID >> " + objSalida.getIdAutor()
                            + " >>> Apellidos >>> " +  objSalida.getApellidos());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }
}