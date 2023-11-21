package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.RevistaAdapter;
import com.cibertec.proyecto.entity.Modalidad;
import com.cibertec.proyecto.entity.Revista;
import com.cibertec.proyecto.service.ServiceRevista;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RevistaCrudListaActivity extends NewAppCompatActivity {
    private Button btnLista;
    private Button btnRegistra;
    private ListView listRevista;
    ArrayList<Revista> data = new ArrayList<>();
    private RevistaAdapter adapter;
    private ServiceRevista serviceRevista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revista_crud_lista);

        btnLista = findViewById(R.id.btCrudRevistaLista);
        btnRegistra = findViewById(R.id.btCrudRevistaRegistra);
        listRevista = findViewById(R.id.lvCrudRevista);

        adapter = new RevistaAdapter(this, R.layout.activity_revista_item_nombre, data);
        listRevista.setAdapter(adapter);

        serviceRevista = ConnectionRest.getConnection().create(ServiceRevista.class);

        btnRegistra.setOnClickListener(view -> {
            Intent intent = new Intent(RevistaCrudListaActivity.this,
                    RevistaCrudFormularioActivity.class);
            intent.putExtra("var_tipo", "Registrar");
            startActivity(intent);
        });

        btnLista.setOnClickListener(view -> lista());

        listRevista.setOnItemClickListener((adapterView, view, i, l) -> {
            Revista revistaSeleccionada = data.get(i);
            Intent intent = new Intent(RevistaCrudListaActivity.this,
                    RevistaCrudFormularioActivity.class);
            intent.putExtra("var_tipo", "Actualizar");
            intent.putExtra("var_seleccionado", revistaSeleccionada);
            startActivity(intent);
        });


        lista();

    }

    private void lista() {
        Call<List<Revista>> call = serviceRevista.listAll();
        call.enqueue(new Callback<List<Revista>>() {
            @Override
            public void onResponse(Call<List<Revista>> call, Response<List<Revista>> response) {
                if (response.isSuccessful()) {
                    List<Revista> lstRevistas = response.body();
                    data.clear();
                    assert lstRevistas != null;
                    for (Revista obj : lstRevistas) {
                        if (obj.getModalidad().getIdModalidad() == 1) {
                            obj.getModalidad().setDescripcion("Físico");
                        }
                        if (obj.getPais().getIdPais() == 1) {
                            obj.getPais().setNombre("Afganistán");
                        }
                    }
                    data.addAll(lstRevistas);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Revista>> call, Throwable t) {

            }
        });
    }


}