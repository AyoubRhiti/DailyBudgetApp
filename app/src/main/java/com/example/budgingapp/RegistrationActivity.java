package com.example.budgingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;

public class RegistrationActivity extends AppCompatActivity {

    private EditText email, password, name, phone;
    private Button RegisterBtn;
    private TextView RegisterQn;

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);


        RegisterBtn = findViewById(R.id.RegisterBtn);
        RegisterQn = findViewById(R.id.RegisterQn);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        RegisterQn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();
                String nameString = name.getText().toString();
                String phoneString = phone.getText().toString();


                if (TextUtils.isEmpty(emailString)){
                    email.setError("Email is required");
                }
                if (TextUtils.isEmpty(passwordString)){
                    password.setError("Password is required");
                }
                if (TextUtils.isEmpty(nameString)){
                    name.setError("Password is name");
                }
                if (TextUtils.isEmpty(phoneString)){
                    phone.setError("Password is phone");
                }

                else {

                    progressDialog.setMessage("registration in progress");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();


                    mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(task -> {

                        if (task.isSuccessful()){
                            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegistrationActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    });

                }
            }
        });
    }
}