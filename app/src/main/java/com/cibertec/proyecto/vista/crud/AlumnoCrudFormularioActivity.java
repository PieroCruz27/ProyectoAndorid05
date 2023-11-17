package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnoCrudFormularioActivity extends NewAppCompatActivity {


    Button btnRegresar, btnProcesar;
    TextView  txtNombre,txtApellidos,txtTelefono,txtDNi,txtCorreo
            ,txtDireccion,txtfecha;
    TextView txtTituloAl;
    Alumno objAlumnoSeleccionada;

    Spinner spnPaises;
    ArrayAdapter<String> adaptadorPaises;
    ArrayList<String> paises = new ArrayList<String>();

    Spinner spnEstado;
    ArrayAdapter<String> adaptadorEstado;
    ArrayList<String> estados = new ArrayList<String>();

    Spinner spnModalidad;
    ArrayAdapter<String> adaptadorModalidades;
    ArrayList<String> modalidadesP = new ArrayList<String>();

    ServicePais paisService;
    ServiceModalidad modalidadService;
    ServiceAlumno alumnoService;

    String tipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_crud_formulario);


        btnRegresar = findViewById(R.id.btnCrudAlumnoRegresar);
        btnProcesar = findViewById(R.id.btnCrudAlumnoRegistrar);
        txtTituloAl = findViewById(R.id.txtCrudTituloAlum);

        txtNombre = findViewById(R.id.txtCrudNombre);
        txtApellidos = findViewById(R.id.txtCrudApellidos);
        txtTelefono = findViewById(R.id.txtCrudTelefono);
        txtDNi = findViewById(R.id.txtCrudDNI);
        txtCorreo = findViewById(R.id.txtCrudCorreo);
        txtDireccion = findViewById(R.id.txtCrudDireccion);
        txtfecha = findViewById(R.id.txtCrudFechaNacimiento);


        adaptadorEstado = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, estados);
        spnEstado = findViewById(R.id.spnCrudSalaEstado);
        spnEstado.setAdapter(adaptadorEstado);

        adaptadorPaises= new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPaises = findViewById(R.id.spnCrudAlumnoPais);
        spnPaises.setAdapter(adaptadorPaises);

        adaptadorModalidades = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, modalidadesP);
        spnModalidad = findViewById(R.id.spnCrudAlumnoModalidad);
        spnModalidad .setAdapter(adaptadorModalidades);

        paisService = ConnectionRest.getConnection().create(ServicePais.class);
         alumnoService = ConnectionRest.getConnection().create(ServiceAlumno.class);
        modalidadService = ConnectionRest.getConnection().create(ServiceModalidad.class);


        Bundle extras = getIntent().getExtras();
         tipo = (String)extras.get("var_tipo");
        txtTituloAl.setText( txtTituloAl.getText() + " - " + tipo);



        if (tipo.equals("Actualizar")){
            objAlumnoSeleccionada = (Alumno) extras.get("var_seleccionado");
            txtNombre.setText(objAlumnoSeleccionada.getNombres());
            txtApellidos.setText(objAlumnoSeleccionada.getApellidos());
            txtTelefono.setText(objAlumnoSeleccionada.getTelefono());
            txtDNi.setText(objAlumnoSeleccionada.getDni());
            txtCorreo.setText(objAlumnoSeleccionada.getCorreo());
            txtDireccion.setText(objAlumnoSeleccionada.getDireccion());
            txtfecha.setText(objAlumnoSeleccionada.getFechaNacimiento());

            btnProcesar.setText("Actualizar");
        }else{
            btnProcesar.setText("Registrar");
        }

        cargaEstado();
        cargaPaisesxA();
        cargaModalidadesP();




        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        AlumnoCrudFormularioActivity.this,
                        AlumnoCrudListaActivity.class);
                startActivity(intent);

            }
        });


        btnProcesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pais = spnPaises.getSelectedItem().toString().split(":")[0].trim().toString();
                String modalidad = spnModalidad.getSelectedItem().toString().split(":")[0].trim().toString();
                String estado = spnEstado.getSelectedItem().toString().split(":")[0].trim().toString();

                Pais objPais = new Pais();
                objPais.setIdPais(Integer.parseInt(pais));

                Modalidad objModalidad = new Modalidad();
                objModalidad.setIdModalidad(Integer.parseInt(modalidad));

                Alumno objAluP = new Alumno();
                objAluP.setNombres(txtNombre.getText().toString());
                objAluP.setApellidos(txtApellidos.getText().toString());
                objAluP.setTelefono(txtTelefono.getText().toString());
                objAluP.setDni(txtDNi.getText().toString());
                objAluP.setCorreo(txtCorreo.getText().toString());
                objAluP.setDireccion(txtDireccion.getText().toString());
                objAluP.setFechaNacimiento(txtfecha.getText().toString());

                objAluP.setPais(objPais);
                objAluP.setModalidad(objModalidad);
                objAluP.setEstado(Integer.parseInt(estado));
                objAluP.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());


                if (tipo.equals("Actualizar")){
                    objAluP.setIdAlumno(objAlumnoSeleccionada.getIdAlumno());
                    actualiza(objAluP);
                }else{
                    objAluP.setIdAlumno(0);
                    registra(objAluP);
                }
            }
        });


    }


    public void registra(Alumno objAlumnoP){
       /*Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objSala);
        mensajeAlert(json);*/

        Call<Alumno> call = alumnoService.registra(objAlumnoP);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if (response.isSuccessful()){
                    Alumno objSalida = response.body();
                    mensajeAlert(" Registro exitoso de Alumno >>> ID >> " + objSalida.getIdAlumno());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {}
        });
    }


    public void actualiza(Alumno objAlumnoP){
       /*Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objSala);
        mensajeAlert(json);*/

        Call<Alumno> call = alumnoService.registra(objAlumnoP);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if (response.isSuccessful()){
                    Alumno objSalida = response.body();
                    mensajeAlert(" Actualizacion exitosa de Alumno >>> ID >> " + objSalida.getIdAlumno());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {}
        });
    }

    public void cargaPaisesxA(){
        Call<List<Pais>> call = paisService.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()){
                    List<Pais> lstSalida = response.body();
                    paises.clear();
                    paises.add(" [ Seleccione el Pais ] ");
                    int idSeleccionado = -1;
                    for(Pais objPais: lstSalida){
                        paises.add(objPais.getIdPais()  + " : " + objPais.getNombre());
                        if (tipo.equals("Actualizar")){
                            if (objPais.getIdPais() == objAlumnoSeleccionada.getPais().getIdPais()){
                                idSeleccionado = lstSalida.indexOf(objPais);
                            }
                        }
                    }
                    adaptadorPaises.notifyDataSetChanged();
                    if (tipo.equals("Actualizar")){
                        spnPaises.setSelection(idSeleccionado + 1);
                    }

                }
            }
            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {}
        });
    }

    public void cargaModalidadesP (){
        Call<List<Modalidad>> call = modalidadService.listaTodos();
        call.enqueue(new Callback<List<Modalidad>>() {
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                if (response.isSuccessful()){
                    List<Modalidad> lstSalida = response.body();
                    modalidadesP.clear();
                    modalidadesP.add(" [ Seleccione Modalidad ] ");

                    int idSeleccionado = -1;
                    for(Modalidad objModalidad: lstSalida){
                        modalidadesP.add(objModalidad.getIdModalidad()  + " : " + objModalidad.getDescripcion());
                        if (tipo.equals("Actualizar")){
                            if (objModalidad.getIdModalidad() == objAlumnoSeleccionada.getModalidad().getIdModalidad()){
                                idSeleccionado = lstSalida.indexOf(objModalidad);
                            }
                        }
                    }
                    adaptadorModalidades.notifyDataSetChanged();
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
            int estado = objAlumnoSeleccionada.getEstado();
            if (estado == 0){//Inactivo
                spnEstado.setSelection(1);
            }
            if (estado == 1){//Activo
                spnEstado.setSelection(2);
            }
        }
    }


}