package com.cibertec.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cibertec.proyecto.util.NewAppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.io.File;
public class InicioActivity extends NewAppCompatActivity {
    Button mButtonCerrarSesion;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mButtonCerrarSesion = findViewById(R.id.btnCerrarSesion);
        mButtonCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            irMain();
        }
    }

    private void logout(){
        // Cerrar sesión en Firebase
        mAuth.signOut();
        clearApplicationData();

        // Ir a la actividad de inicio de sesión
        irMain();
    }

    private void irMain() {
        Intent intent = new Intent(InicioActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void clearApplicationData() {
        // Obtener el directorio de archivos de caché de la aplicación
        File cacheDirectory = getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());

        // Eliminar archivos en el directorio de caché
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }
    }

    private static boolean deleteFile(File file) {
        boolean deletedAll = true;

        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (String child : children) {
                    deletedAll = deleteFile(new File(file, child)) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }

        return deletedAll;
    }
}