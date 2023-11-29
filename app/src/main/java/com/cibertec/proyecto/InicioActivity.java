package com.cibertec.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.cibertec.proyecto.util.NewAppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
        // If using GoogleSignInClient, sign out from Google as well
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Do something after Google sign out
                    }
                });
        // Ir a la actividad de inicio de sesión
        irMain();
    }

    private void irMain() {
        Intent intent = new Intent(InicioActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }




}