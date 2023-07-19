package com.abeldandi.alitasecurity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abeldandi.alitasecurity.ui.ChatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText loginEmail, loginPassword;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        loginEmail = findViewById(R.id.email);
        loginPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(view -> {
            String email = loginEmail.getText().toString();
            String password = loginPassword.getText().toString();

            if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (!password.isEmpty()) {
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener(authResult -> {
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, EmployeeActivity.class));
                                finish();
                            }).addOnFailureListener(e -> {
                                String errorMessage = e.getMessage();
                                Toast.makeText(MainActivity.this, "Login Failed" + errorMessage, Toast.LENGTH_SHORT).show();
                            });
                } else {
                    loginPassword.setError("Password cannot be empty");
                }
            } else if(email.isEmpty()) {
                loginEmail.setError("Email cannot be empty");
            } else {
                loginEmail.setError("Please enter valid email");
            }
        });
    }
}