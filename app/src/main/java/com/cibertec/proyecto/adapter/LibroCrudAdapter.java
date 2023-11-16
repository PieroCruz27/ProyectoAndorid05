package com.cibertec.proyecto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Libro;

import java.util.List;

public class LibroCrudAdapter extends ArrayAdapter<Libro>  {

    private Context context;
    private List<Libro> lista;

    public LibroCrudAdapter(@NonNull Context context, int resource, @NonNull List<Libro> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_libro_crud_item, parent, false);

        Libro objLibro = lista.get(position);

        TextView txtId = row.findViewById(R.id.txtItemId);
        txtId.setText(String.valueOf(objLibro.getIdLibro()));

        TextView txtTitulo = row.findViewById(R.id.txtItemTitulo);
        txtTitulo.setText("Titulo :" + objLibro.getTitulo());

        TextView txtAnio = row.findViewById(R.id.txtItemAnio);
        txtAnio.setText("Año :" + objLibro.getAnio());

        TextView txtSerie = row.findViewById(R.id.txtItemSerie);
        txtSerie.setText("Serie :" + objLibro.getSerie());

        TextView txtCategoria = row.findViewById(R.id.txtItemCategoria);
        txtCategoria.setText("Categoria :" + objLibro.getCategoria().getDescripcion());

        TextView txtPais = row.findViewById(R.id.txtItemPais);
        txtPais.setText("País :" + objLibro.getPais().getNombre());

        return row;
    }
}
