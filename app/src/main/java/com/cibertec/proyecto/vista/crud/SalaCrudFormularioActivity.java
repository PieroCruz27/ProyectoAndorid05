package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Modalidad;
import com.cibertec.proyecto.entity.Sala;
import com.cibertec.proyecto.entity.Sede;
import com.cibertec.proyecto.service.SedeService;
import com.cibertec.proyecto.service.ServiceModalidad;
import com.cibertec.proyecto.service.ServiceSala;
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

public class SalaCrudFormularioActivity extends NewAppCompatActivity {

    Button btnRegresar, btnProcesar;
    TextView  txtTitulo, txtNumero, txtPiso, txtNumAlu, txtRecursos;

    Spinner spnEstado;
    ArrayAdapter<String> adaptadorEstado;
    ArrayList<String> estados = new ArrayList<String>();

    Spinner spnSede;
    ArrayAdapter<String> adaptadorSede;
    ArrayList<String> sedes = new ArrayList<String>();

    Spinner spnModalidad;
    ArrayAdapter<String> adaptadorModalidad;
    ArrayList<String> modalidades = new ArrayList<String>();


    SedeService sedeService;
    ServiceSala salaService;
    ServiceModalidad modalidadService;

    String tipo;
    Sala objSalaSeleccionada;

    ServiceSala serviceSala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_crud_formulario);

        serviceSala = ConnectionRest.getConnection().create(ServiceSala.class);

        btnRegresar = findViewById(R.id.btnCrudSalaRegresar);
        btnProcesar = findViewById(R.id.btnCrudSalaRegistrar);

        txtTitulo = findViewById(R.id.txtCrudTitulo);
        txtNumero = findViewById(R.id.txtCrudNumero);
        txtPiso = findViewById(R.id.txtCrudPiso);
        txtNumAlu = findViewById(R.id.txtCrudNumAlumnos);
        txtRecursos = findViewById(R.id.txtCrudRecursos);


        adaptadorEstado = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, estados);
        spnEstado = findViewById(R.id.spnCrudSalaEstado);
        spnEstado.setAdapter(adaptadorEstado);

        adaptadorSede = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sedes);
        spnSede = findViewById(R.id.spnCrudSalaSede);
        spnSede.setAdapter(adaptadorSede);

        adaptadorModalidad = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, modalidades);
        spnModalidad = findViewById(R.id.spnCrudSalaModalidad);
        spnModalidad.setAdapter(adaptadorModalidad);

        sedeService = ConnectionRest.getConnection().create(SedeService.class);
        salaService = ConnectionRest.getConnection().create(ServiceSala.class);
        modalidadService = ConnectionRest.getConnection().create(ServiceModalidad.class);

        Bundle extras = getIntent().getExtras();
        tipo = (String)extras.get("var_tipo");
        txtTitulo.setText( txtTitulo.getText() + " - " + tipo);

        if (tipo.equals("Actualizar")){
            objSalaSeleccionada = (Sala) extras.get("var_seleccionado");
            txtNumero.setText(objSalaSeleccionada.getNumero());
            txtPiso.setText(String.valueOf(objSalaSeleccionada.getPiso()));
            txtNumAlu.setText(String.valueOf(objSalaSeleccionada.getNumAlumnos()));
            txtRecursos.setText(objSalaSeleccionada.getRecursos());
            btnProcesar.setText("Actualizar");
        }else{
            btnProcesar.setText("Registrar");
        }

        cargaSede();
        cargaModalidad ();
        cargaEstado();

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        SalaCrudFormularioActivity.this,
                        SalaCrudListaActivity.class);
                  startActivity(intent);

            }
        });

        btnProcesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sede = spnSede.getSelectedItem().toString().split(":")[0].trim().toString();
                String modalidad = spnModalidad.getSelectedItem().toString().split(":")[0].trim().toString();
                String estado = spnEstado.getSelectedItem().toString().split(":")[0].trim().toString();

                Sede objSede = new Sede();
                objSede.setIdSede(Integer.parseInt(sede));

                Modalidad objModalidad = new Modalidad();
                objModalidad.setIdModalidad(Integer.parseInt(modalidad));

                Sala objSala = new Sala();
                objSala.setNumero(txtNumero.getText().toString());
                objSala.setPiso(Integer.parseInt(txtPiso.getText().toString()));
                objSala.setNumAlumnos(Integer.parseInt(txtNumAlu.getText().toString()));
                objSala.setRecursos(txtRecursos.getText().toString());
                objSala.setSede(objSede);
                objSala.setModalidad(objModalidad);
                objSala.setEstado(Integer.parseInt(estado));
                objSala.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());


                if (tipo.equals("Actualizar")){
                    objSala.setIdSala(objSalaSeleccionada.getIdSala());
                    actualiza(objSala);
                }else{
                    objSala.setIdSala(0);
                    registra(objSala);
                }
            }
        });

    }

    public void registra(Sala objSala){
       /*Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objSala);
        mensajeAlert(json);*/

        Call<Sala> call = serviceSala.registra(objSala);
        call.enqueue(new Callback<Sala>() {
            @Override
            public void onResponse(Call<Sala> call, Response<Sala> response) {
                if (response.isSuccessful()){
                    Sala objSalida = response.body();
                    mensajeAlert(" Registro exitoso  >>> ID >> " + objSalida.getIdSala());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Sala> call, Throwable t) {}
        });
    }
    public void actualiza(Sala objSala){
         /*Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objSala);
        mensajeAlert(json);*/
        Call<Sala> call = serviceSala.actualiza(objSala);
        call.enqueue(new Callback<Sala>() {
            @Override
            public void onResponse(Call<Sala> call, Response<Sala> response) {
                if (response.isSuccessful()){
                    Sala objSalida = response.body();
                    mensajeAlert(" Actualización exitosa  >>> ID >> " + objSalida.getIdSala());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Sala> call, Throwable t) {}
        });
    }

    public void cargaSede (){
        Call<List<Sede>> call = sedeService.listaTodos();
        call.enqueue(new Callback<List<Sede>>() {
            @Override
            public void onResponse(Call<List<Sede>> call, Response<List<Sede>> response) {
                if (response.isSuccessful()){
                    List<Sede> lstSalida = response.body();
                    sedes.clear();
                    sedes.add(" [ Seleccione Sede ] ");
                    int idSeleccionado = -1;
                    for(Sede objSede: lstSalida){
                        sedes.add(objSede.getIdSede()  + " : " + objSede.getNombre());
                        if (tipo.equals("Actualizar")){
                            if (objSede.getIdSede() == objSalaSeleccionada.getSede().getIdSede()){
                                idSeleccionado = lstSalida.indexOf(objSede);
                            }
                        }
                    }
                    adaptadorSede.notifyDataSetChanged();
                    if (tipo.equals("Actualizar")){
                        spnSede.setSelection(idSeleccionado + 1);
                    }

                }
            }
           @Override
            public void onFailure(Call<List<Sede>> call, Throwable t) {}
        });
    }

    public void cargaModalidad (){
        Call<List<Modalidad>> call = modalidadService.listaTodos();
        call.enqueue(new Callback<List<Modalidad>>() {
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                if (response.isSuccessful()){
                    List<Modalidad> lstSalida = response.body();
                    modalidades.clear();
                    modalidades.add(" [ Seleccione Modalidad ] ");
                    int idSeleccionado = -1;
                    for(Modalidad objModalidad: lstSalida){
                        modalidades.add(objModalidad.getIdModalidad()  + " : " + objModalidad.getDescripcion());
                        if (tipo.equals("Actualizar")){
                            if (objModalidad.getIdModalidad() == objSalaSeleccionada.getModalidad().getIdModalidad()){
                                idSeleccionado = lstSalida.indexOf(objModalidad);
                            }
                        }
                    }
                    adaptadorModalidad.notifyDataSetChanged();
                    if (tipo.equals("Actualizar")){
                        spnModalidad.setSelection(idSeleccionado + 1);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Modalidad>> call, Throwable t) {}
        });
    }

    public void cargaEstado(){
        estados.clear();
        estados.add(" [ Seleccione Estado ] ");
        estados.add(" 0 : Inactivo ");
        estados.add(" 1 : Activo ");
        adaptadorEstado.notifyDataSetChanged();
        if (tipo.equals("Actualizar")){
            int estado = objSalaSeleccionada.getEstado();
            if (estado == 0){//Inactivo
                spnEstado.setSelection(1);
            }
            if (estado == 1){//Activo
                spnEstado.setSelection(2);
            }
        }
    }


}