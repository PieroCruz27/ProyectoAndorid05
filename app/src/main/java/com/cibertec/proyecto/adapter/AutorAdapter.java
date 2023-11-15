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
import com.cibertec.proyecto.entity.Autor;
import com.cibertec.proyecto.entity.Grado;
import com.cibertec.proyecto.entity.Pais;

import java.util.List;

public class AutorAdapter extends ArrayAdapter<Autor>  {

    private Context context;
    private List<Autor> lista;
    private int resource;
    public AutorAdapter(@NonNull Context context, int resource, @NonNull List<Autor> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(resource, parent, false);

        Autor objAutor = lista.get(position);

        TextView txtID = row.findViewById(R.id.txtIdAutor);
        txtID.setText(String.valueOf(objAutor.getIdAutor()));

        TextView txtNombreApellidoAutor = row.findViewById(R.id.txtNombreApellidoAutor);
        txtNombreApellidoAutor.setText("Nombre : " + objAutor.getNombres() + " " +objAutor.getApellidos() );

        /*
        TextView txtNombreApellidoAutor = row.findViewById(R.id.txtNombreApellidoAutor);
        txtNombreApellidoAutor.setText("Nombre : " + objAutor.getNombres());
*/
        TextView txtTelefonoAutor = row.findViewById(R.id.txtTelefonoAutor);
        txtTelefonoAutor.setText("Teléfono : " + objAutor.getTelefono() );

        TextView txtCorreoAutor = row.findViewById(R.id.txtCorreoAutor);
        txtCorreoAutor.setText("Correo : " + objAutor.getCorreo());
/*
        TextView txtGradoDescripcionAutor = row.findViewById(R.id.txtGradoDescripcionAutor);
        txtGradoDescripcionAutor.setText("Grado : " + objAutor.getGrado().getDescripcion());
*/
        TextView txtGradoDescripcionAutor = row.findViewById(R.id.txtGradoDescripcionAutor);
        Grado grado = objAutor.getGrado();
        if (grado != null) {
            txtGradoDescripcionAutor.setText("Grado : " + grado.getDescripcion());
        } else {
            txtGradoDescripcionAutor.setText("Grado : N/A");  // O utiliza cualquier valor predeterminado
        }
        /*
        TextView txtPaisNombreAutor = row.findViewById(R.id.txtPaisNombreAutor);
        txtPaisNombreAutor.setText("Pais :" + objAutor.getPais().getNombre());*/
        TextView txtPaisNombreAutor = row.findViewById(R.id.txtPaisNombreAutor);
        Pais pais = objAutor.getPais();
        if (pais != null) {
            txtPaisNombreAutor.setText("Pais :" + pais.getNombre());
        } else {
            txtPaisNombreAutor.setText("Pais : N/A"); // O utiliza cualquier valor predeterminado
        }

        return row;
    }
}
