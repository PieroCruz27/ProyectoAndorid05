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
import com.cibertec.proyecto.entity.Categoria;
import com.cibertec.proyecto.entity.Editorial;
import com.cibertec.proyecto.entity.Pais;
import com.cibertec.proyecto.service.ServiceCategoria;
import com.cibertec.proyecto.service.ServiceEditorial;
import com.cibertec.proyecto.service.ServicePais;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditorialRegistraActivity extends NewAppCompatActivity {
    //DECLARACION DE VARIABLES
    //Categoria
    Spinner spnCategoria;
    ArrayAdapter<String> adaptadorCategoria;
    ArrayList<String> categorias = new ArrayList<String>();

    //Pais
    Spinner spnPais;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> paises = new ArrayList<String>();

    //Servicio
    ServiceEditorial serviceEditorial;
    ServicePais servicePais;
    ServiceCategoria serviceCategoria;

    //Componentes del formulario
    Button btnRegistrar;
    EditText txtRazonSocial, txtDireccion, txtRuc, txtFechaCreacion;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_registra);
        //conecta a los servicios Rest
        serviceCategoria = ConnectionRest.getConnection().create(ServiceCategoria.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceEditorial = ConnectionRest.getConnection().create(ServiceEditorial.class);

        //Relacionar los variables con la variables de la GUI
        txtRazonSocial = findViewById(R.id.txtRazonSocial);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtRuc = findViewById(R.id.txtRuc);
        txtFechaCreacion = findViewById(R.id.txtFechaCreacion);
        btnRegistrar = findViewById(R.id.btnEnviar);

        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPais = findViewById(R.id.spnPais);
        spnPais.setAdapter(adaptadorPais);

        adaptadorCategoria = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categorias);
        spnCategoria = findViewById(R.id.spnCategoria);
        spnCategoria.setAdapter(adaptadorCategoria);

        cargaPais();
        cargaCategoria();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pedimos todas las variables
                String razsoc = txtRazonSocial.getText().toString();
                String direccion = txtDireccion.getText().toString();
                String ruc = txtRuc.getText().toString();
                String fecCreacion = txtFechaCreacion.getText().toString();
                //para pedir el id de Pais
                String idPais= spnPais.getSelectedItem().toString().split(":")[0];
                //para pedir el id de Categoria,[0] : porque solo queremos el id
                String idCategoria= spnCategoria.getSelectedItem().toString().split(":")[0];

                Pais objPais = new Pais();
                objPais.setIdPais(Integer.parseInt(idPais));

                Categoria objCategoria = new Categoria();
                objCategoria.setIdCategoria(Integer.parseInt(idCategoria));

                Editorial objEditorial = new Editorial();
                objEditorial.setRazonSocial(razsoc);
                objEditorial.setRuc(ruc);
                objEditorial.setDireccion(direccion);
                objEditorial.setFechaCreacion(fecCreacion);
                objEditorial.setPais(objPais);
                objEditorial.setCategoria(objCategoria);
                objEditorial.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                objEditorial.setEstado(1);

                //ingresa el registro de editorial
                insertaEditorial(objEditorial);
            }
        });


    }
            //para generar el mensaje en la parte inferior
            public void mensajeToast(String mensaje){
                Toast toast1 =  Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
                toast1.show();
            }
            //crea el mensaje de alerta
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
                    List<Pais> lst =  response.body();
                    for(Pais obj: lst){
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
    public void cargaCategoria(){
        Call<List<Categoria>> call = serviceCategoria.listaTodos();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()){
                    List<Categoria> lst =  response.body();
                    for(Categoria obj: lst){
                        categorias.add(obj.getIdCategoria() +":"+ obj.getDescripcion());
                    }
                    adaptadorCategoria.notifyDataSetChanged();
                }else{
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }
            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }
    public  void insertaEditorial(Editorial objEditorial){
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //String json = gson.toJson(objEditorial);
        //mensajeAlert(json);

        Call<Editorial> call = serviceEditorial.insertaEditorial(objEditorial);
        call.enqueue(new Callback<Editorial>() {
            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {
                if (response.isSuccessful()){
                    Editorial objSalida = response.body();
                    mensajeAlert(" Registro exitoso  >>> ID >> " + objSalida.getIdEditorial()
                            + " >>> Razón Social >>> " +  objSalida.getRazonSocial());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }


}