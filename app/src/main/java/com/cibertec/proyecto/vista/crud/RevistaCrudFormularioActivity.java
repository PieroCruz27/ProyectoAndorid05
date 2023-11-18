package com.cibertec.proyecto.vista.crud;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Modalidad;
import com.cibertec.proyecto.entity.Pais;
import com.cibertec.proyecto.entity.Revista;
import com.cibertec.proyecto.service.ServiceModalidad;
import com.cibertec.proyecto.service.ServicePais;
import com.cibertec.proyecto.service.ServiceRevista;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevistaCrudFormularioActivity extends NewAppCompatActivity {
    //EditText
    EditText etNombre;
    EditText etFrecuencia;
    //EditText Date
    private EditText etFechaCreacion;

    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    //Buttons
    Button btnProcesar;
    Button btnRegresar;
    //Revista
    ServiceRevista serviceRevista;
    //TextViews
    TextView tvCrudMantenimientoRevista;

    //Pais
    Spinner spnPais;
    ArrayAdapter<String> adapterPais;
    ArrayList<String> paisesId = new ArrayList<String>();
    ArrayList<String> paisesNombres = new ArrayList<String>();
    ServicePais servicePais;
    //Modalidad
    ArrayAdapter<String> adapterModalidad;
    ArrayList<String> modalidadesId = new ArrayList<>();
    Spinner spnModalidad;
    ArrayList<String> modalidadesNombres = new ArrayList<>();
    ServiceModalidad serviceModalidad;

    Revista objRevistaSeleccionada;


    String tipo;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revista_crud_formulario);

        //TextViews
        tvCrudMantenimientoRevista = findViewById(R.id.tvCrudMantenimientoRevista);

        //EditTexts
        etNombre = findViewById(R.id.etCrudRevistaFormNombre);
        etFrecuencia = findViewById(R.id.etCrudRevistaFormFrecuencia);

        //EditTex Date
        fechaCreacionView();
        etFechaCreacion = findViewById(R.id.etCrudRevistaFormFechaCreacion);

        //Spinners
        spnModalidad = findViewById(R.id.spnCrudRevistaFormModalidad);
        spnPais = findViewById(R.id.spnCrudRevistaFormPais);

        //Buttons
        btnProcesar = findViewById(R.id.btnCrudRevistaFormRegistrar);
        btnRegresar = findViewById(R.id.btnCrudRevistaFormRegresar);


        //CONECCION SERVICIOS REST
        serviceModalidad = ConnectionRest.getConnection().create(ServiceModalidad.class);
        serviceRevista = ConnectionRest.getConnection().create(ServiceRevista.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);

        //LISTADO DE SPINNERS
        adapterModalidad = new ArrayAdapter<String>(this, R.drawable.spinner_item_revista, modalidadesNombres);
        spnModalidad = findViewById(R.id.spnCrudRevistaFormModalidad);
        spnModalidad.setAdapter(adapterModalidad);
        listaModalidades();

        adapterPais = new ArrayAdapter<String>(this, R.drawable.spinner_item_revista, paisesNombres);
        spnPais = findViewById(R.id.spnCrudRevistaFormPais);
        spnPais.setAdapter(adapterPais);
        listaPais();

        //Llenar Campos si es Actualizar
        Bundle extras = getIntent().getExtras();
        tipo = (String) extras.get("var_tipo");
        tvCrudMantenimientoRevista.setText(tvCrudMantenimientoRevista.getText() + " - " + tipo);

        if (tipo.equals("Actualizar")) {
            objRevistaSeleccionada = (Revista) extras.get("var_seleccionado");
            etNombre.setText(objRevistaSeleccionada.getNombre());
            etFrecuencia.setText(objRevistaSeleccionada.getFrecuencia());
            etFechaCreacion.setText(objRevistaSeleccionada.getFechaCreacion());
            btnProcesar.setText("Actualizar");
        } else {
            btnProcesar.setText("Registrar");
        }

        eventProcesarFormulario();
        //Regresar
        btnRegresar.setOnClickListener(view -> {
            close();
        });

    }

    private void eventProcesarFormulario() {
        btnProcesar.setOnClickListener(view -> {
            String nombre = etNombre.getText().toString();
            String frecuencia = etFrecuencia.getText().toString();
            String fechaCreacion = etFechaCreacion.getText().toString();

            String idModalidad = modalidadesId.get((int) spnModalidad.getSelectedItemId());
            Modalidad objModalidad = new Modalidad();
            objModalidad.setIdModalidad(Integer.parseInt(idModalidad));

            String idPais = paisesId.get((int) spnPais.getSelectedItemId());
            Pais objPais = new Pais();
            objPais.setIdPais(Integer.parseInt(idPais));

            Revista objRevista = new Revista();
            objRevista.setNombre(nombre);
            objRevista.setFrecuencia(frecuencia);
            objRevista.setFechaCreacion(fechaCreacion);
            objRevista.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
            objRevista.setEstado(FunctionUtil.ESTADO_ACTIVO);
            objRevista.setPais(objPais);
            objRevista.setModalidad(objModalidad);

            if (tipo.equals("Actualizar")) {
                objRevista.setIdRevisa(objRevistaSeleccionada.getIdRevisa());
                actualiza(objRevista);
            } else {
                objRevista.setIdRevisa(0);
                registra(objRevista);
            }
        });
    }

    private void actualiza(Revista objRevista) {
        Call<Revista> call = serviceRevista.actualizar(objRevista);
        call.enqueue(new Callback<Revista>() {
            @Override
            public void onResponse(Call<Revista> call, Response<Revista> response) {
                if (response.isSuccessful()) {
                    Revista objSalida = response.body();
                    showAlertDialogButtonClicked(" Actualización exitosa 🎊\n [ { ID : " + objSalida.getIdRevisa() + "}]");

                } else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Revista> call, Throwable t) {

            }
        });
    }

    private void registra(Revista objRevista) {
        Call<Revista> call = serviceRevista.insertRevista(objRevista);
        call.enqueue(new Callback<Revista>() {
            @Override
            public void onResponse(Call<Revista> call, Response<Revista> response) {
                if (response.isSuccessful()) {
                    Revista objSalida = response.body();
                    showAlertDialogButtonClicked(" Registro exitoso  >>> ID >> " + objSalida.getIdRevisa());
                } else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Revista> call, Throwable t) {

            }
        });
    }


    private void close() {
        Intent intent = new Intent(
                RevistaCrudFormularioActivity.this,
                RevistaCrudListaActivity.class
        );
        startActivity(intent);
    }

    private void listaModalidades() {
        Call<List<Modalidad>> callModalidad = serviceModalidad.listaTodos();
        callModalidad.enqueue(new Callback<List<Modalidad>>() {
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                if (response.isSuccessful()) {
                    List<Modalidad> listModalidad = response.body();
                    int idSeleccionado = -1;

                    for (Modalidad modalidad : listModalidad) {
                        if (modalidad.getIdModalidad() == 1) {
                            modalidad.setDescripcion("Físico");
                        }
                        modalidadesId.add(String.valueOf(modalidad.getIdModalidad()));
                        modalidadesNombres.add(modalidad.getDescripcion());

                        if (tipo.equals("Actualizar")) {
                            if (modalidad.getIdModalidad() == objRevistaSeleccionada.getModalidad().getIdModalidad()) {
                                idSeleccionado = listModalidad.indexOf(modalidad);
                            }
                        }
                    }
                    adapterModalidad.notifyDataSetChanged();
                    if (tipo.equals("Actualizar")) {
                        spnModalidad.setSelection(idSeleccionado);
                    }
                } else {
                    mensajeToastShort("Error en la coneccion REST >>>");
                }

            }

            @Override
            public void onFailure(Call<List<Modalidad>> call, Throwable t) {
                mensajeToastShort("Error en la coneccion REST >>> " + t.getMessage());
            }
        });
    }

    private void listaPais() {
        Call<List<Pais>> callPais = servicePais.listaTodos();
        callPais.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()) {
                    List<Pais> listaPais = response.body();
                    int idSeleccionado = -1;
                    for (Pais pais : listaPais) {
                        paisesId.add(String.valueOf(pais.getIdPais()));
                        if (pais.getIdPais() == 1) {
                            pais.setNombre("Afganistán");
                        }
                        paisesNombres.add(pais.getNombre());
                        if (tipo.equals("Actualizar")) {
                            if (pais.getIdPais() == objRevistaSeleccionada.getPais().getIdPais()) {
                                idSeleccionado = listaPais.indexOf(pais);
                            }
                        }
                    }
                    adapterPais.notifyDataSetChanged();
                    if (tipo.equals("Actualizar")) {
                        spnPais.setSelection(idSeleccionado);
                    }
                } else {
                    mensajeToastShort("Error al acceder al servicio REST >>> ");
                }
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeToastShort("Error al acceder al servicio REST >>> " + t.getMessage());
            }

        });
    }

    private void fechaCreacionView() {
        etFechaCreacion = (EditText) findViewById(R.id.etCrudRevistaFormFechaCreacion);

        etFechaCreacion.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int mes = cal.get(Calendar.MONTH);
            int dia = cal.get(Calendar.DAY_OF_MONTH);
            if (!etFechaCreacion.getText().toString().matches("")) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date date = dateFormat.parse(etFechaCreacion.getText().toString());
                    cal.setTime(date);

                    year = cal.get(Calendar.YEAR);
                    mes = cal.get(Calendar.MONTH);
                    dia = cal.get(Calendar.DAY_OF_MONTH);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            DatePickerDialog dialog = new DatePickerDialog(
                    RevistaCrudFormularioActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog,
                    mOnDateSetListener,
                    year, mes, dia
            );
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        });
        mOnDateSetListener = (datePicker, year, month, day) -> {

            month = month + 1;
            String formatMonth = String.valueOf(month);
            if (month < 10) {
                formatMonth = "0" + month;
            }
            String date = year + "-" + formatMonth + "-" + day;
            etFechaCreacion.setText(date);
        };
    }

    public void showAlertDialogButtonClicked(String msg) {
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¡Success!");
        builder.setMessage(msg);

        final View customLayout = getLayoutInflater().inflate(R.layout.alert_dialog_layout_crud_revista, null);
        builder.setView(customLayout);

        builder.setPositiveButton("Aceptar", (dialog, which) -> {

            Intent intent = new Intent(
                    RevistaCrudFormularioActivity.this,
                    RevistaCrudListaActivity.class
            );
            startActivity(intent);
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}