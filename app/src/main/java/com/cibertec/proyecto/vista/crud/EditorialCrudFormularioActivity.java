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
import com.cibertec.proyecto.entity.Editorial;
import com.cibertec.proyecto.entity.Modalidad;
import com.cibertec.proyecto.entity.Pais;
import com.cibertec.proyecto.entity.Sala;
import com.cibertec.proyecto.entity.Sede;
import com.cibertec.proyecto.service.ServiceCategoria;
import com.cibertec.proyecto.service.ServiceEditorial;
import com.cibertec.proyecto.service.ServiceModalidad;
import com.cibertec.proyecto.service.ServicePais;
import com.cibertec.proyecto.service.ServiceSala;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorialCrudFormularioActivity extends NewAppCompatActivity {

    Button btnRegresar,btnProcesar;
    TextView txtTitulo,txtRazonSocial,txtDireccion,txtRuc,txtFecha;
    String tipo;

    Editorial objEditorialSeleccionada;
    ServiceEditorial serviceEditorial;

    Spinner spnEstado;
    ArrayAdapter<String> adaptadorEstado;
    ArrayList<String> estados = new ArrayList<String>();

    Spinner spnPais;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> paises = new ArrayList<String>();

    Spinner spnCategoria;
    ArrayAdapter<String> adaptadorCategoria;
    ArrayList<String> categorias = new ArrayList<String>();


    ServicePais paisService;
    ServiceCategoria categoriaService;
    ServiceEditorial editorialService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_crud_formulario);
        //acceso al servicio rest
        serviceEditorial = ConnectionRest.getConnection().create(ServiceEditorial.class);

        btnRegresar = findViewById(R.id.btnCrudEditorialRegresar);
        btnProcesar = findViewById(R.id.btnCrudEditorialRegistrar);

        txtTitulo = findViewById(R.id.txtCrudTitulo);
        txtRazonSocial = findViewById(R.id.txtCrudRazonSocial);
        txtDireccion = findViewById(R.id.txtCrudDireccion);
        txtRuc = findViewById(R.id.txtCrudRuc);
        txtFecha = findViewById(R.id.txtCrudFechaCreacion);

        adaptadorEstado = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, estados);
        spnEstado = findViewById(R.id.spnCrudEditorialEstado);
        spnEstado.setAdapter(adaptadorEstado);

        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPais = findViewById(R.id.spnCrudEditorialPais);
        spnPais.setAdapter(adaptadorPais);

        adaptadorCategoria = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categorias);
        spnCategoria  = findViewById(R.id.spnCrudEditorialCategoria);
        spnCategoria .setAdapter(adaptadorCategoria);

        paisService = ConnectionRest.getConnection().create(ServicePais.class);
        editorialService = ConnectionRest.getConnection().create(ServiceEditorial.class);
        categoriaService = ConnectionRest.getConnection().create(ServiceCategoria.class);

        Bundle extras = getIntent().getExtras();

        tipo = (String)extras.get("var_tipo");
        txtTitulo.setText( txtTitulo.getText() + " - " + tipo);


        if (tipo.equals("Actualizar")){
            objEditorialSeleccionada = (Editorial) extras.get("var_seleccionado");
            txtRazonSocial.setText(objEditorialSeleccionada.getRazonSocial());
            txtDireccion.setText(objEditorialSeleccionada.getDireccion());
            txtRuc.setText(objEditorialSeleccionada.getRuc());
            txtFecha.setText(objEditorialSeleccionada.getFechaCreacion());
            btnProcesar.setText("Actualizar");
        }
        else{
            btnProcesar.setText("Registrar");
        }


        cargaPais();
        cargaCategoria();
        cargaEstado();

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        EditorialCrudFormularioActivity.this,
                        EditorialCrudListaActivity.class);
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

                Editorial objEditorial = new Editorial();
                objEditorial.setRazonSocial(txtRazonSocial.getText().toString());
                objEditorial.setDireccion(txtDireccion.getText().toString());
                objEditorial.setRuc(txtRuc.getText().toString());
                objEditorial.setFechaCreacion(txtFecha.getText().toString());
                objEditorial.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                objEditorial.setEstado(Integer.parseInt(estado));
                objEditorial.setPais(objPais);
                objEditorial.setCategoria(objCategoria);


                if (tipo.equals("Actualizar")){
                    objEditorial.setIdEditorial(objEditorialSeleccionada.getIdEditorial());
                    actualiza(objEditorial);
                }else{
                    objEditorial.setIdEditorial(0);
                    registra(objEditorial);
                }
            }
        });

    }


    public void registra(Editorial objEditorial){
       /*Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objSala);
        mensajeAlert(json);*/

        Call<Editorial> call = editorialService.registra(objEditorial);
        call.enqueue(new Callback<Editorial>() {
            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {
                if (response.isSuccessful()){
                    Editorial objSalida = response.body();
                    mensajeAlert(" Registro exitoso  >>> ID >> " + objSalida.getIdEditorial());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {}
        });
    }
    public void actualiza(Editorial objEditorial){
         /*Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objSala);
        mensajeAlert(json);*/
        Call<Editorial> call = editorialService.actualiza(objEditorial);
        call.enqueue(new Callback<Editorial>() {
            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {
                if (response.isSuccessful()){
                    Editorial objSalida = response.body();
                    mensajeAlert(" Actualización exitosa  >>> ID >> " + objSalida.getIdEditorial());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {}
        });
    }




    public void cargaPais (){
        Call<List<Pais>> call = paisService.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()){
                    List<Pais> lstSalida = response.body();
                    paises.clear();
                    paises.add(" [ Seleccione Pais ] ");
                    int idSeleccionado = -1;
                    for(Pais objPais: lstSalida){
                        paises.add(objPais.getIdPais()  + " : " + objPais.getNombre());
                        if (tipo.equals("Actualizar")){
                            if (objPais.getIdPais() == objEditorialSeleccionada.getPais().getIdPais()){
                                idSeleccionado = lstSalida.indexOf(objPais);
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
            public void onFailure(Call<List<Pais>> call, Throwable t) {}
        });
    }

    public void cargaCategoria (){
        Call<List<Categoria>> call = categoriaService.listaTodos();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()){
                    List<Categoria> lstSalida = response.body();
                    categorias.clear();
                    categorias.add(" [ Seleccione Categorias ] ");
                    int idSeleccionado = -1;
                    for(Categoria objCategoria: lstSalida){
                        categorias.add(objCategoria.getIdCategoria()  + " : " + objCategoria.getDescripcion());
                        if (tipo.equals("Actualizar")){
                            if (objCategoria.getIdCategoria() == objEditorialSeleccionada.getCategoria().getIdCategoria()){
                                idSeleccionado = lstSalida.indexOf(objCategoria);
                            }
                        }
                    }
                    adaptadorCategoria.notifyDataSetChanged();
                    if (tipo.equals("Actualizar")){
                        spnCategoria.setSelection(idSeleccionado + 1);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {}
        });
    }

    public void cargaEstado(){
        estados.clear();
        estados.add(" [ Seleccione Estado ] ");
        estados.add(" 0 : Inactivo ");
        estados.add(" 1 : Activo ");
        adaptadorEstado.notifyDataSetChanged();
        if (tipo.equals("Actualizar")){
            int estado = objEditorialSeleccionada.getEstado();
            if (estado == 0){//Inactivo
                spnEstado.setSelection(1);
            }
            if (estado == 1){//Activo
                spnEstado.setSelection(2);
            }
        }
    }
}