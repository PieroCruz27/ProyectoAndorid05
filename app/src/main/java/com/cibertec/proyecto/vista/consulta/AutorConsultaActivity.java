package com.cibertec.proyecto.vista.consulta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.AutorAdapter;
import com.cibertec.proyecto.entity.Autor;
import com.cibertec.proyecto.service.ServiceAutor;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutorConsultaActivity extends NewAppCompatActivity {
    Button btnListaAutor;

    EditText txtNombresAutor;
    ListView lstAutor;
    ArrayList<Autor> data = new ArrayList<>();
    AutorAdapter adaptador;
    ServiceAutor serviceAutor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_autor_consulta);
        btnListaAutor = findViewById(R.id.btnListaAutor);
        lstAutor = findViewById(R.id.lstAutor);
        txtNombresAutor = findViewById(R.id.txtNombresAutor);
        adaptador = new AutorAdapter(this, R.layout.activity_autor_item_nombre, data);
        lstAutor.setAdapter(adaptador);

        serviceAutor = ConnectionRest.getConnection().create(ServiceAutor.class);
        btnListaAutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaAutores(txtNombresAutor.getText().toString().trim());
            }
        });

    }

    public void listaAutores(String txtNombreAutor){
        Call<List<Autor>> call = serviceAutor.listaAutorPorNombre(txtNombreAutor);
        call.enqueue(new Callback<List<Autor>>() {
            @Override
            public void onResponse(Call<List<Autor>> call, Response<List<Autor>> response) {
                if (response.isSuccessful()){
                    List<Autor> lstSalida = response.body();
                    //mensajeAlert(""+lstSalida.size());
                    data.clear();
                    data.addAll(lstSalida);
                    adaptador.notifyDataSetChanged(); //que notifique y actualice el listView (se parece al spinner, solo que el lstView
                    // tiene su propio adaptador )
                }
            }

            @Override
            public void onFailure(Call<List<Autor>> call, Throwable t) {

            }
        });
    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    public void mensajeToastLong(String msg){
        Toast toast1 =  Toast.makeText(this,msg, Toast.LENGTH_LONG);
        toast1.show();
    }
    public void mensajeToastShort(String msg){
        Toast toast1 =  Toast.makeText(this,msg, Toast.LENGTH_SHORT);
        toast1.show();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

}