package com.example.fs;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonLog;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonLog = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.click_here_to_register);
        mAuth = FirebaseAuth.getInstance();

        // Load the bounce animation
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);

        buttonLog.setOnClickListener(v -> {
            v.startAnimation(animation);

            String email, password;
            progressBar.setVisibility(View.VISIBLE);
            email = String.valueOf(editTextEmail.getText());
            password = String.valueOf(editTextPassword.getText());

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(login.this, "Enter Email", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(login.this, "Enter Password", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Authentication successful.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), logo.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        textView.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), register.class);
            startActivity(intent);
            finish();
        });
    }
}
