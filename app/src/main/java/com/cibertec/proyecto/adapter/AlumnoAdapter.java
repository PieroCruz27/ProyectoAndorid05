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
        View row = inflater.inflate(R.layout.activity_alumno_crud_item, parent, false);

        Alumno objAlumno = lista.get(position);

        TextView txtID = row.findViewById(R.id.txtItemALumnoId);
        txtID.setText(String.valueOf(objAlumno.getIdAlumno()));

        TextView txtNombre = row.findViewById(R.id.txtItemNombre);
        txtNombre.setText("Nombre: " + objAlumno.getNombres());

        TextView txtApellido = row.findViewById(R.id.txtItemApellido);
        txtApellido.setText("Apellido: " +objAlumno.getApellidos());

        TextView txtTelefono = row.findViewById(R.id.txtItemTelefono);
        txtTelefono.setText("Teléfono: " +objAlumno.getTelefono());

        TextView txtDNI = row.findViewById(R.id.txtItemDNI);
        txtDNI.setText("DNI: " +objAlumno.getDni());

        TextView txtCorreo = row.findViewById(R.id.txtItemCorreo);
        txtCorreo.setText("Correo: " +objAlumno.getCorreo());

        TextView txtDireccion = row.findViewById(R.id.txtItemDireccion);
        txtDireccion.setText("Dirección: " +objAlumno.getDireccion());

        TextView txtFecha = row.findViewById(R.id.txtItemDireccion);
        txtFecha.setText("Fecha: " +objAlumno.getFechaNacimiento());


        TextView txtEstado = row.findViewById(R.id.txtItemEstado);
        txtEstado.setText(String.valueOf("Estado: "+objAlumno.getEstado()));

        TextView txtPais = row.findViewById(R.id.txtItemPais);
        txtPais.setText("País: "+objAlumno.getPais().getNombre());

        TextView txtmod = row.findViewById(R.id.txtItemModalidad);
        txtmod.setText("Modalidad: "+objAlumno.getModalidad().getDescripcion());

        return row;


    }

}
