package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Categoria;
import com.cibertec.proyecto.entity.Libro;
import com.cibertec.proyecto.entity.Pais;
import com.cibertec.proyecto.service.ServiceCategoria;
import com.cibertec.proyecto.service.ServiceLibro;
import com.cibertec.proyecto.service.ServicePais;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibroCrudFormularioActivity extends NewAppCompatActivity {

    Button  btnRegresar, btnProcesar;
    TextView txtPrincipal, txtCrudTitulo, txtCrudAnio, txtCrudSerie ;

    Spinner spnEstado;
    ArrayAdapter<String> adaptadorEstado;
    ArrayList<String> estados = new ArrayList<String>();

    Spinner spnCategoria;
    ArrayAdapter<String> adaptadorCategoria;
    ArrayList<String> categorias = new ArrayList<String>();

    Spinner spnPais;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> paises = new ArrayList<String>();

    ServicePais paisService;
    ServiceLibro serviceLibro;
    ServiceCategoria categoriaService;

    String tipo;
    Libro objLibroSeleccionado;

    ServiceLibro libroService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_crud_formulario);

        serviceLibro = ConnectionRest.getConnection().create(ServiceLibro.class);

        btnRegresar = findViewById(R.id.btnCrudLibroRegresar);
        btnProcesar = findViewById(R.id.btnCrudLibroRegistrar);

        txtPrincipal = findViewById(R.id.txtCrudPrincipal);
        txtCrudTitulo = findViewById(R.id.txtCrudTitulo);
        txtCrudAnio = findViewById(R.id.txtCrudAnio);
        txtCrudSerie = findViewById(R.id.txtCrudSerie);

        adaptadorEstado = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, estados);
        spnEstado = findViewById(R.id.spnCrudLibroEstado);
        spnEstado.setAdapter(adaptadorEstado);

        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,paises);
        spnPais = findViewById(R.id.spnCrudLibroPais);
        spnPais.setAdapter(adaptadorPais);

        adaptadorCategoria = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,categorias);
        spnCategoria = findViewById(R.id.spnCrudLibroCategoria);
        spnCategoria.setAdapter(adaptadorCategoria);

        paisService = ConnectionRest.getConnection().create(ServicePais.class);
        libroService = ConnectionRest.getConnection().create(ServiceLibro.class);
        categoriaService = ConnectionRest.getConnection().create(ServiceCategoria.class);

        Bundle extras = getIntent().getExtras();
        String tipo = (String)extras.get("var tipo");
        txtPrincipal.setText(txtPrincipal.getText() + " - " + tipo );

        if(tipo.equals("Actualizar")) {
            Libro objLibroSeleccionado = (Libro) extras.get("var_seleccionado");
            txtCrudTitulo.setText(objLibroSeleccionado.getTitulo());
            txtCrudAnio.setText(String.valueOf(objLibroSeleccionado.getAnio()));
            txtCrudSerie.setText(objLibroSeleccionado.getSerie());
            btnProcesar.setText("Actualizar");
        }else{
            btnProcesar.setText("Registrar");
        }

        cargaPais();
        cargaCategoria();
        cargaEstado();

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        LibroCrudFormularioActivity.this,
                        LibroCrudListaActivity.class);
                startActivity(intent);

            }
        });

        btnProcesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pais = spnPais.getSelectedItem().toString().split(":")[0].trim().toString();
                String categoria = spnCategoria.getSelectedItem().toString().split(":")[0].trim().toString();
                String estado = spnEstado.getSelectedItem().toString().split(":")[0].trim().toString();

                Pais objPais = new Pais();
                objPais.setIdPais(Integer.parseInt(pais));

                Categoria objCategoria = new Categoria();
                objCategoria.setIdCategoria(Integer.parseInt(categoria));

                Libro objLibro = new Libro();
                objLibro.setTitulo(txtCrudTitulo.getText().toString());
                objLibro.setAnio(txtCrudAnio.getText().toString());
                objLibro.setSerie(txtCrudSerie.getText().toString());
                objLibro.setPais(objPais);
                objLibro.setCategoria(objCategoria);
                objLibro.setEstado(Integer.parseInt(estado));
                objLibro.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());

                if(tipo.equals("Actualizar")){
                    objLibro.setIdLibro(objLibroSeleccionado.getIdLibro());
                    actualiza(objLibro);
                }else{
                    objLibro.setIdLibro(0);
                    registra(objLibro);
                }

            }
        });
    }
    public void registra(Libro objLibro){
        Call<Libro> call = serviceLibro.registra(objLibro);
        call.enqueue(new Callback<Libro>() {
            @Override
            public void onResponse(Call<Libro> call, Response<Libro> response) {
                if(response.isSuccessful()){
                    Libro objSalida = response.body();
                    mensajeAlert("Registro Exitoso >>> ID >> " +objSalida.getIdLibro());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Libro> call, Throwable t) {

            }
        });
    }

    public void actualiza(Libro objLibro){
        Call<Libro> call = serviceLibro.actualiza(objLibro);
        call.enqueue(new Callback<Libro>() {
            @Override
            public void onResponse(Call<Libro> call, Response<Libro> response) {
                if(response.isSuccessful()){
                    Libro objSalida = response.body();
                    mensajeAlert("Actualizacion Exitosa >>> ID >>" + objSalida.getIdLibro());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Libro> call, Throwable t) {
            }
        });
    }

    public void cargaPais(){
        Call<List<Pais>> call = paisService.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if(response.isSuccessful()){
                    List<Pais> lstSalida = response.body();
                    paises.clear();
                    paises.add("[SELECCIONE PAIS] ");
                    int idSeleccionado = -1;
                    for (Pais objPais: lstSalida){
                        paises.add(objPais.getIdPais() + ":" + objPais.getNombre());
                        if(tipo.equals("Actualizar")){
                            if(objPais.getIdPais() == objLibroSeleccionado.getPais().getIdPais()){
                                idSeleccionado = lstSalida.indexOf(objPais);
                            }
                        }
                    }
                    adaptadorPais.notifyDataSetChanged();
                    if(tipo.equals("Actualizar")){
                        spnPais.setSelection(idSeleccionado + 1);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
            }
        });
    }

    public void cargaCategoria(){
        Call<List<Categoria>> call = categoriaService.listaTodos();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if(response.isSuccessful()){
                    List<Categoria> lstSalida = response.body();
                    categorias.clear();
                    categorias.add("[SELECCIONE PAIS] ");
                    int idSeleccionado = -1;
                    for (Categoria objCategoria: lstSalida){
                        categorias.add(objCategoria.getIdCategoria() + ":" + objCategoria.getDescripcion());
                        if(tipo.equals("Actualizar")){
                            if(objCategoria.getIdCategoria() == objLibroSeleccionado.getCategoria().getIdCategoria()){
                            idSeleccionado = lstSalida.indexOf(objCategoria);
                        }
                    }
                }
                adaptadorCategoria.notifyDataSetChanged();
                if(tipo.equals("Actualizar")){
                    spnCategoria.setSelection(idSeleccionado + 1);
                }
            }
        }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {

            }
        });
    }
    public void cargaEstado(){
        estados.clear();
        estados.add(" [ Seleccione Estado ] ");
        estados.add(" 0 : Inactivo ");
        estados.add(" 1 : Activo ");
        adaptadorEstado.notifyDataSetChanged();
        if (tipo.equals("Actualizar")){
            int estado = objLibroSeleccionado.getEstado();
            if (estado == 0){//Inactivo
                spnEstado.setSelection(1);
            }
            if (estado == 1){//Activo
                spnEstado.setSelection(2);
            }
        }
    }


}