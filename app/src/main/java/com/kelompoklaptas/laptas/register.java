package com.kelompoklaptas.laptas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class register extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private EditText etUsername, etPassword, etEmail;
    private TextView btLogin;
    private Button btSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        btSignup = findViewById(R.id.btSignUp);
        btSignup.setOnClickListener(this);
        btLogin = (TextView) findViewById(R.id.btLogin);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btSignUp:
                registerUser();
                break;
            case R.id.btLogin:
                startActivity(new Intent(register.this, login.class));
        }


    }

    private void registerUser(){
        final String username = etUsername.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        if (username.isEmpty()){
            etUsername.setError("username harus di isi");
            etUsername.requestFocus();
            return;
        }
        else if (email.isEmpty()){
            etEmail.setError("email harus di isi");
            etEmail.requestFocus();
            return;
        }
        else if (password.isEmpty()){
            etPassword.setError("password harus di isi");
            etPassword.requestFocus();
            return;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("tolong isi email dengan benar");
            etEmail.requestFocus();
            return;
        }
        else if (password.length() < 6){
            etPassword.setError("minimal panjang password 6 karakter");
            etPassword.requestFocus();
            return;
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(username, email, password);
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(register.this, "akun berhasil registrasi", Toast.LENGTH_LONG).show();
                                       }
                                    
                                    }
                                });
                            }else {
                                Toast.makeText(register.this, "registrasi gagal gagal", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}