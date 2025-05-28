package com.example.emsismartpresence;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signin extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;

    private static final String TAG = "Signin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activation EdgeToEdge (issu du code 2)
        // Note : EdgeToEdge.enable(this); est de la lib androidx.activity:activity:1.6+
        // Si tu as la dépendance, tu peux garder, sinon cette ligne est facultative
        try {
            Class.forName("androidx.activity.EdgeToEdge").getMethod("enable", AppCompatActivity.class).invoke(null, this);
        } catch (Exception ignored) {}

        setContentView(R.layout.activity_signin);

        // Gestion padding des insets système (bordures écran)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialisation Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Références aux vues
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        TextView btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(Signin.this, register.class);
            startActivity(intent);
        });

        // Gestion du clic sur le bouton de connexion
        btnLogin.setOnClickListener(v -> authenticateUser());
    }

    private void authenticateUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(Signin.this, "Échec de l'authentification", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(this, "Authentification réussie: " + user.getEmail(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Signin.this, dashboard.class)); // ou MainActivity
            finish();
        } else {
            Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
        }
    }
}
