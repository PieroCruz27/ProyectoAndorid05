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
import com.cibertec.proyecto.entity.Editorial;

import java.util.List;
//adaptador :nexo entre el diseño y la data
//clase que relaciona el diseño con la data
public class EditorialAdapter extends ArrayAdapter<Editorial>  {

    private Context context;
    private List<Editorial> lista;

    public EditorialAdapter(@NonNull Context context, int resource, @NonNull List<Editorial> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        //SE RELACIONA CON EL activity_editorial_item_nombre
        View row = inflater.inflate(R.layout.activity_editorial_item_nombre, parent, false);

        Editorial obj = lista.get(position);
        //ID EDITORIAL
        TextView txtID = row.findViewById(R.id.IdEditorial);
        txtID.setText("ID : "+String.valueOf(obj.getIdEditorial()));
        //NOMBRE-RAZON SOCIAL
        TextView txtNombre = row.findViewById(R.id.NombreEditorial);
        txtNombre.setText("Nombre : "+String.valueOf(obj.getRazonSocial()));
        //PAIS
        TextView txtPais = row.findViewById(R.id.PaisEditorial);
        txtPais.setText("País : "+String.valueOf(obj.getPais().getNombre()));
        //CATEGORIA
        TextView txtCategoria = row.findViewById(R.id.CategoriaEditorial);
        txtCategoria.setText("Categoría : "+String.valueOf(obj.getCategoria().getDescripcion()));
        //FECHA CREACION
        TextView txtCreacion = row.findViewById(R.id.CreacionEditorial);
        txtCreacion.setText("Fecha Creación : "+String.valueOf(obj.getFechaCreacion()));
        return row;
    }
}
