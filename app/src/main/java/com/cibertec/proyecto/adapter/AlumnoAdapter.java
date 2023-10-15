package com.cibertec.proyecto.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Alumno;


import java.util.List;

public class AlumnoAdapter extends ArrayAdapter<Alumno>  {

    private Context context;
    private List<Alumno> lista;

    public AlumnoAdapter(@NonNull Context context, int resource, @NonNull List<Alumno> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_alumno_item_nombre, parent, false);

        Alumno objAlumno = lista.get(position);

        TextView txtID = row.findViewById(R.id.itemIdAlumno);
        txtID.setText(String.valueOf(objAlumno.getIdAlumno()));

        TextView txtNombre = row.findViewById(R.id.itemNombreAlumno);
        txtNombre.setText(String.valueOf(objAlumno.getNombres()));

        TextView txtApellido = row.findViewById(R.id.itemApellidoAlumno);
        txtApellido.setText(String.valueOf(objAlumno.getApellidos()));

        TextView txtTelefono = row.findViewById(R.id.itemTelefonoAlumno);
        txtTelefono.setText(String.valueOf(objAlumno.getTelefono()));

        TextView txtPais = row.findViewById(R.id.itemPaisnNomAlumno);
        txtPais.setText(String.valueOf(objAlumno.getPais().getNombre()));

        return row;


    }

}
