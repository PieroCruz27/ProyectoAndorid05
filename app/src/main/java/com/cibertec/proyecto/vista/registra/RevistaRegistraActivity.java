package com.cibertec.proyecto.vista.registra;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Editorial;
import com.cibertec.proyecto.entity.Modalidad;
import com.cibertec.proyecto.entity.Pais;
import com.cibertec.proyecto.entity.Revista;
import com.cibertec.proyecto.service.ServiceModalidad;
import com.cibertec.proyecto.service.ServicePais;
import com.cibertec.proyecto.service.ServiceRevista;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.NewAppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public class RevistaRegistraActivity extends NewAppCompatActivity {
    private static final String TAG = "RevistaRegistraActivity";
    private EditText etFechaCreacion;
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    //Pais
    Spinner spnPais;
    ArrayAdapter<String> adapterPais;
    ArrayList<String> paisesId = new ArrayList<String>();
    ArrayList<String> paisesNombres = new ArrayList<String>();
    ServicePais servicePais;
    //Modalidad
    Spinner spnModalidad;
    ArrayAdapter<String> adapterModalidad;
    ArrayList<String> modalidadesId = new ArrayList<>();
    ArrayList<String> modalidadesNombres = new ArrayList<>();
    ServiceModalidad serviceModalidad;

    //Revista
    ServiceRevista serviceRevista;

    //EditText
    private EditText etNombre, etFrecuencia;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_revista_registra);
        fechaCreacionView();
        //CONECCION SERVICIOS REST
        serviceModalidad = ConnectionRest.getConnection().create(ServiceModalidad.class);
        serviceRevista = ConnectionRest.getConnection().create(ServiceRevista.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);

        adapterModalidad = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, modalidadesNombres);
        spnModalidad = findViewById(R.id.spnModalidad);
        spnModalidad.setAdapter(adapterModalidad);
        listaModalidades();

        adapterPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paisesNombres);
        spnPais = findViewById(R.id.spnPais);
        spnPais.setAdapter(adapterPais);
        listaPais();

        etNombre = findViewById(R.id.etNombre);
        etFrecuencia = findViewById(R.id.etFrecuencia);
        etFechaCreacion = findViewById(R.id.etFechaCreacion);
        spnModalidad = findViewById(R.id.spnModalidad);
        spnPais = findViewById(R.id.spnPais);

        btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(view -> {
                    String nombre = etNombre.getText().toString();
                    String frecuencia = etFrecuencia.getText().toString();
                    String fechaCreacion = etFechaCreacion.getText().toString();

                    String idModalidad = modalidadesId.get((int) spnModalidad.getSelectedItemId());
                    System.out.println("ID MODALIDADDDDDD : " + idModalidad);
                    Modalidad objModalidad = new Modalidad();
                    objModalidad.setIdModalidad(Integer.parseInt(idModalidad));
                    System.out.println("ID OBJECT MODALIDADDDDDD : " + objModalidad.getIdModalidad());

                    String idPais = paisesId.get((int) spnPais.getSelectedItemId());
                    System.out.println("ID PAISSSS : " + idPais);
                    Pais objPais = new Pais();
                    objPais.setIdPais(Integer.parseInt(idPais));
                    System.out.println("ID OBJECT PAISSSS : " + objPais.getIdPais());

                    Revista objRevista = new Revista();
                    objRevista.setNombre(nombre);
                    objRevista.setFrecuencia(frecuencia);
                    objRevista.setFechaCreacion(fechaCreacion);
                    objRevista.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    objRevista.setEstado(FunctionUtil.ESTADO_ACTIVO);
                    objRevista.setPais(objPais);
                    objRevista.setModalidad(objModalidad);
                    insertRevista(objRevista);


                }

        );
    }

    public void insertRevista(Revista obj) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);
        mensajeAlert(json);

        Call<Revista> call = serviceRevista.insertRevista(obj);
        call.enqueue(new Callback<Revista>() {
            @Override
            public void onResponse(Call<Revista> call, Response<Revista> response) {
                if (response.isSuccessful()) {
                    Revista revista = response.body();
                    mensajeToastShort("¡REGISTRO EXITOSO!");

                } else {

                    mensajeToastLong("ERROR CON SERVICIO REST");
                }
            }

            @Override
            public void onFailure(Call<Revista> call, Throwable t) {
                mensajeToastLong("ERROR CON SERVICIO REST" + t.getMessage());
            }
        });

    }

    private void listaModalidades() {
        Call<List<Modalidad>> callModalidad = serviceModalidad.listaTodos();
        callModalidad.enqueue(new Callback<List<Modalidad>>() {
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                if (response.isSuccessful()) {
                    List<Modalidad> listModalidad = response.body();
                    for (Modalidad modalidad : listModalidad) {
                        modalidadesId.add(String.valueOf(modalidad.getIdModalidad()));
                        modalidadesNombres.add(modalidad.getDescripcion());
                    }
                    adapterModalidad.notifyDataSetChanged();

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
                    for (Pais pais : listaPais) {
                        paisesId.add(String.valueOf(pais.getIdPais()));
                        paisesNombres.add(pais.getNombre());
                    }
                    adapterPais.notifyDataSetChanged();
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
        etFechaCreacion = (EditText) findViewById(R.id.etFechaCreacion);

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
                    RevistaRegistraActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog,
                    mOnDateSetListener,
                    year, mes, dia
            );
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        });
        mOnDateSetListener = (datePicker, year, month, day) -> {

            month = month + 1;
            Log.d(TAG, "onDateSet: date " + year + "/" + month + "/" + day);
            String formatMonth = String.valueOf(month);
            if (month < 10) {
                formatMonth = "0" + month;
            }
            String date = year + "-" + formatMonth + "-" + day;
            etFechaCreacion.setText(date);
        };
    }
}