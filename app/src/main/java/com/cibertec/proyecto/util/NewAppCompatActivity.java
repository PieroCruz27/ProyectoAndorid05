package com.cibertec.proyecto.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cibertec.proyecto.InicioActivity;
import com.cibertec.proyecto.R;
import com.cibertec.proyecto.vista.consulta.AlumnoConsultaActivity;
import com.cibertec.proyecto.vista.consulta.AutorConsultaActivity;
import com.cibertec.proyecto.vista.consulta.EditorialConsultaActivity;
import com.cibertec.proyecto.vista.consulta.LibroConsultaActivity;
import com.cibertec.proyecto.vista.consulta.ProveedorConsultaActivity;
import com.cibertec.proyecto.vista.consulta.RevistaConsultaActivity;
import com.cibertec.proyecto.vista.consulta.SalaConsultaActivity;
import com.cibertec.proyecto.vista.consulta.UsuarioConsultaActivity;
import com.cibertec.proyecto.vista.crud.AlumnoCrudListaActivity;
import com.cibertec.proyecto.vista.crud.AutorCrudListaActivity;
import com.cibertec.proyecto.vista.crud.EditorialCrudListaActivity;
import com.cibertec.proyecto.vista.crud.LibroCrudListaActivity;
import com.cibertec.proyecto.vista.crud.ProveedorCrudListaActivity;
import com.cibertec.proyecto.vista.crud.RevistaCrudListaActivity;
import com.cibertec.proyecto.vista.crud.SalaCrudListaActivity;
import com.cibertec.proyecto.vista.crud.UsuarioCrudListaActivity;
import com.cibertec.proyecto.vista.registra.AlumnoRegistraActivity;
import com.cibertec.proyecto.vista.registra.AutorRegistraActivity;
import com.cibertec.proyecto.vista.registra.EditorialRegistraActivity;
import com.cibertec.proyecto.vista.registra.LibroRegistraActivity;
import com.cibertec.proyecto.vista.registra.ProveedorRegistraActivity;
import com.cibertec.proyecto.vista.registra.RevistaRegistraActivity;
import com.cibertec.proyecto.vista.registra.SalaRegistraActivity;
import com.cibertec.proyecto.vista.registra.UsuarioRegistraActivity;

public class NewAppCompatActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //Opciones
        if (id == R.id.idMenu){
            Intent intent = new Intent(this, InicioActivity.class);
            startActivity(intent);
            return true;
        }

        //Registra
        if (id == R.id.idMenuRegAlumno){
            Intent intent = new Intent(this, AlumnoRegistraActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuRegAutor){
            Intent intent = new Intent(this, AutorRegistraActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuRegEditorial){
            Intent intent = new Intent(this, EditorialRegistraActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuRegLibro){
            Intent intent = new Intent(this, LibroRegistraActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuRegProveedor){
            Intent intent = new Intent(this, ProveedorRegistraActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuRegRevista){
            Intent intent = new Intent(this, RevistaRegistraActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuRegSala){
            Intent intent = new Intent(this, SalaRegistraActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuRegUsuario){
            Intent intent = new Intent(this, UsuarioRegistraActivity.class);
            startActivity(intent);
            return true;
        }

        //CRUD
        if (id == R.id.idMenuCrudAlumno){
            Intent intent = new Intent(this, AlumnoCrudListaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuCrudAutor){
            Intent intent = new Intent(this, AutorCrudListaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuCrudEditorial){
            Intent intent = new Intent(this, EditorialCrudListaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuCrudLibro){
            Intent intent = new Intent(this, LibroCrudListaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuCrudProveedor){
            Intent intent = new Intent(this, ProveedorCrudListaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuCrudRevista){
            Intent intent = new Intent(this, RevistaCrudListaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuCrudSala){
            Intent intent = new Intent(this, SalaCrudListaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuCrudUsuario){
            Intent intent = new Intent(this, UsuarioCrudListaActivity.class);
            startActivity(intent);
            return true;
        }

        //Consulta
        if (id == R.id.idMenuConsultaAlumno){
            Intent intent = new Intent(this, AlumnoConsultaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuConsultaAutor){
            Intent intent = new Intent(this, AutorConsultaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuConsultaEditorial){
            Intent intent = new Intent(this, EditorialConsultaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuConsultaLibro){
            Intent intent = new Intent(this, LibroConsultaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuConsultaProveedor){
            Intent intent = new Intent(this, ProveedorConsultaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuConsultaRevista){
            Intent intent = new Intent(this, RevistaConsultaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuConsultaSala){
            Intent intent = new Intent(this, SalaConsultaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuConsultaUsuario){
            Intent intent = new Intent(this, UsuarioConsultaActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    public void mensajeToastLong(String msg){
        Toast toast1 =  Toast.makeText(this,msg, Toast.LENGTH_LONG);
        toast1.show();
    }
    public void mensajeToastShort(String msg){
        Toast toast1 =  Toast.makeText(this,msg, Toast.LENGTH_SHORT);
        toast1.show();
    }



}
