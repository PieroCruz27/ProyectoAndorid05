package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Autor;
import com.cibertec.proyecto.entity.Grado;
import com.cibertec.proyecto.entity.Modalidad;
import com.cibertec.proyecto.entity.Pais;
import com.cibertec.proyecto.entity.Sala;
import com.cibertec.proyecto.entity.Sede;
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

public class AutorCrudFormularioActivity extends NewAppCompatActivity {
    Button btnRegresar, btnProcesar;
    TextView  txtTitulo, txtNombres, txtApellidos, txtCorreo, txtFechaNacimiento;

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
    String tipo;
    Autor objAutorSeleccionada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_crud_formulario);

        btnRegresar = findViewById(R.id.btnCrudAutorRegresar);
        btnProcesar = findViewById(R.id.btnCrudAutorRegistrar);

        //conecta a los servicios Rest
        serviceAutor = ConnectionRest.getConnection().create(ServiceAutor.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceGrado = ConnectionRest.getConnection().create(ServiceGrado.class);
        //Relacionar los variables con la variables de la GUI
        txtNombres = findViewById(R.id.txtRegEdiNombres);
        txtApellidos = findViewById(R.id.txtRegEdiApellidos);
        txtCorreo = findViewById(R.id.txtRegEdiCorreo);
        txtFechaNacimiento = findViewById(R.id.txtRegEdiFechaNacimiento);

        txtTitulo = findViewById(R.id.txtCrudTitulo);

        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPais = findViewById(R.id.spnRegEdiPais);
        spnPais.setAdapter(adaptadorPais);

        adaptadorGrado = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, grados);
        spnGrado = findViewById(R.id.spnRegEdiGrado);
        spnGrado.setAdapter(adaptadorGrado);

        Bundle extras = getIntent().getExtras();
        tipo = (String)extras.get("var_tipo");
        txtTitulo.setText( txtTitulo.getText() + " - " + tipo);

        if (tipo.equals("Actualizar")){
            objAutorSeleccionada = (Autor) extras.get("var_seleccionado");
            txtNombres.setText(objAutorSeleccionada.getNombres());
            txtApellidos.setText(String.valueOf(objAutorSeleccionada.getApellidos()));
            txtCorreo.setText(String.valueOf(objAutorSeleccionada.getCorreo()));
            txtFechaNacimiento.setText(objAutorSeleccionada.getFechaNacimiento());
            btnProcesar.setText("Actualizar");
        }
        else{
            btnProcesar.setText("Registrar");
        }
        cargaPais();
        cargaGrado();
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        AutorCrudFormularioActivity.this,
                        AutorCrudListaActivity.class);
                startActivity(intent);

            }
        });
        btnProcesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pais = spnPais.getSelectedItem().toString().split(":")[0].trim().toString();
                String grado = spnGrado.getSelectedItem().toString().split(":")[0].trim().toString();

                Pais objPais = new Pais();
                objPais.setIdPais(Integer.parseInt(pais));

                Grado objGrado = new Grado();
                objGrado.setIdGrado(Integer.parseInt(grado));

                Autor objAutor = new Autor();
                objAutor.setNombres(txtNombres.getText().toString());
                objAutor.setApellidos(txtApellidos.getText().toString());
                objAutor.setCorreo(txtCorreo.getText().toString());
                objAutor.setFechaNacimiento(txtFechaNacimiento.getText().toString());
                objAutor.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                objAutor.setPais(objPais);
                objAutor.setGrado(objGrado);


                if (tipo.equals("Actualizar")){
                    objAutor.setIdAutor(objAutorSeleccionada.getIdAutor());
                    actualiza(objAutor);
                }else{
                    objAutor.setIdAutor(0);
                    registra(objAutor);
                }
            }
        });


    }




    public void registra(Autor objAutor){
        Call<Autor> call = serviceAutor.insertaAutor(objAutor);
        call.enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if (response.isSuccessful()){
                    Autor objSalida = response.body();
                    mensajeAlert(" Registro exitoso  >>> ID >> " + objSalida.getIdAutor());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Autor> call, Throwable t) {}
        });
    }

    public void actualiza(Autor objAutor){
        Call<Autor> call = serviceAutor.actualiza(objAutor);
        call.enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if (response.isSuccessful()){
                    Autor objSalida = response.body();
                    mensajeAlert(" Actualización exitosa  >>> ID >> " + objSalida.getIdAutor());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Autor> call, Throwable t) {}
        });
    }

    public void cargaPais(){
        Call<List<Pais>> call = servicePais.listaPais();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()){
                    paises.clear();
                    paises.add("Seleccione un país");
                    int idSeleccionado = -1;
                    List<Pais> lstPaises =  response.body();
                    for(Pais obj: lstPaises){
                        paises.add(obj.getIdPais() +":"+ obj.getNombre());
                        if (tipo.equals("Actualizar")){
                            if (obj.getIdPais() == objAutorSeleccionada.getPais().getIdPais()){
                                idSeleccionado = lstPaises.indexOf(obj);
                            }
                        }
                    }
                    adaptadorPais.notifyDataSetChanged();
                    if (tipo.equals("Actualizar")){
                        spnPais.setSelection(idSeleccionado + 1);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {

            }
        });
    }
    public void cargaGrado(){
        Call<List<Grado>> call = serviceGrado.listaGrado();
        call.enqueue(new Callback<List<Grado>>() {
            @Override
            public void onResponse(Call<List<Grado>> call, Response<List<Grado>> response) {
                if (response.isSuccessful()){
                    grados.clear();
                    grados.add("Seleccione un grado");

                    List<Grado> lst =  response.body();
                    int idSeleccionado = -1;
                    for(Grado obj: lst){
                        grados.add(obj.getIdGrado() +":"+ obj.getDescripcion());
                        if (tipo.equals("Actualizar")){
                            if (obj.getIdGrado() == objAutorSeleccionada.getGrado().getIdGrado()){
                                idSeleccionado = lst.indexOf(obj);
                            }
                        }
                    }
                    adaptadorGrado.notifyDataSetChanged();
                    if (tipo.equals("Actualizar")){
                        spnGrado.setSelection(idSeleccionado + 1);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Grado>> call, Throwable t) {

            }
        });
    }

}