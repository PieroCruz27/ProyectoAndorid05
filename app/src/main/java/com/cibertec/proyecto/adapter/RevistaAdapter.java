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
import com.cibertec.proyecto.entity.Revista;
import com.cibertec.proyecto.entity.Revista;

import java.util.List;

public class RevistaAdapter extends ArrayAdapter<Revista> {

    private Context context;
    private List<Revista> lista;

    public RevistaAdapter(@NonNull Context context, int resource, @NonNull List<Revista> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_revista_item_nombre, parent, false);

        Revista obj = lista.get(position);
        //ID EDITORIAL
        TextView tvID = row.findViewById(R.id.tvIdRevistaItem);
        tvID.setText("ID:"+String.valueOf(obj.getIdRevisa()));
        //NOMBRES
        TextView tvNombre = row.findViewById(R.id.tvNombreRevistaItem);
        tvNombre.setText("Nombre: "+String.valueOf(obj.getNombre()));
        //FRECUENCIA
        TextView tvFrecuencia = row.findViewById(R.id.tvFrecuenciaItem);
        tvFrecuencia.setText("Frecuencia: "+String.valueOf(obj.getFrecuencia()));
        //FECHA CREACION
        TextView tvCreacion = row.findViewById(R.id.tvCreacionRevistaItem);
        tvCreacion.setText("Creación: "+String.valueOf(obj.getFechaCreacion()));
        //MODALIDAD
        TextView tvModalidad = row.findViewById(R.id.tvModalidadRevistaItem);
        tvModalidad.setText("Modalidad: "+String.valueOf(obj.getModalidad().getDescripcion()));
        //PAIS
        TextView tvPais = row.findViewById(R.id.tvPaisRevistaItem);
        tvPais.setText("País: "+String.valueOf(obj.getPais().getNombre()));
        return row;
    }
}
