package com.cibertec.proyecto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cibertec.proyecto.entity.Sala;
import com.cibertec.proyecto.R;

import java.util.List;


import java.util.List;

public class SalaAdapter extends ArrayAdapter<Sala>  {

    private Context context;
    private List<Sala> lista;

    public SalaAdapter(@NonNull Context context, int resource, @NonNull List<Sala> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_sala_crud_item, parent, false);

        Sala objSala = lista.get(position);

        TextView txtID = row.findViewById(R.id.txtItemId);
        txtID.setText("ID: " + String.valueOf(objSala.getIdSala()));

        TextView txtNumero = row.findViewById(R.id.txtItemNumero);
        txtNumero.setText("Número: " + objSala.getNumero());

        TextView txtPiso = row.findViewById(R.id.txtItemPiso);
        txtPiso.setText("Piso: " + String.valueOf(objSala.getPiso()));

        TextView txtNumAlumno = row.findViewById(R.id.txtItemNumeroAlumnos);
        txtNumAlumno.setText("Piso: " + String.valueOf(objSala.getNumAlumnos()));

        TextView txtSede = row.findViewById(R.id.txtItemSede);
        txtSede.setText("Sede: " + objSala.getSede().getNombre());

        TextView txtModalidad = row.findViewById(R.id.txtItemModalidad);
        txtModalidad.setText("Modalidad: " + objSala.getModalidad().getDescripcion());


        return row;
    }

}
