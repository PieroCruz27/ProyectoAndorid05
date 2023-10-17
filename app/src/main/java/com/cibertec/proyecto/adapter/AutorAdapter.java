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
import com.cibertec.proyecto.entity.Autor;

import java.util.List;

public class AutorAdapter extends ArrayAdapter<Autor>  {

    private Context context;
    private List<Autor> lista;

    public AutorAdapter(@NonNull Context context, int resource, @NonNull List<Autor> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_item_autor_nombre, parent, false);

        Autor objAutor = lista.get(position);

        TextView txtID = row.findViewById(R.id.txtIdAutor);
        txtID.setText(String.valueOf(objAutor.getIdAutor()));

        TextView txtNombresAutor = row.findViewById(R.id.txtNombresAutor);
        txtNombresAutor.setText(objAutor.getNombres());

        TextView txtApellidosAutor = row.findViewById(R.id.txtApellidosAutor);
        txtApellidosAutor.setText(objAutor.getApellidos());

        TextView txtCorreoAutor = row.findViewById(R.id.txtCorreoAutor);
        txtCorreoAutor.setText(objAutor.getCorreo());

        TextView txtGradoDescripcionAutor = row.findViewById(R.id.txtGradoDescripcionAutor);
        txtGradoDescripcionAutor.setText(objAutor.getGrado().getDescripcion());

        TextView txtPaisNombreAutor = row.findViewById(R.id.txtPaisNombreAutor);
        txtPaisNombreAutor.setText(objAutor.getPais().getNombre());
        return row;
    }
}
