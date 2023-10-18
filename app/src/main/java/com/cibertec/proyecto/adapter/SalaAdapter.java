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
        View row = inflater.inflate(R.layout.activity_sala_consulta_item, parent, false);

        Sala objSala = lista.get(position);

        TextView txtID = row.findViewById(R.id.txtId);
        txtID.setText("ID: " + String.valueOf(objSala.getIdSala()));

        TextView txtNumero = row.findViewById(R.id.txtNumero);
        txtNumero.setText("Número: " + objSala.getNumero());

        TextView txtSede = row.findViewById(R.id.txtSede);
        txtSede.setText("Sede: " + objSala.getSede().getNombre());


        return row;
    }
}
