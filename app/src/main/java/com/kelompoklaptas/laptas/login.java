package com.kelompoklaptas.laptas;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class login extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MESSAGE = "todetail";
    private FirebaseAuth mAuth;
    private TextView btndaftar;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String userID;
    private Button btnLogin;
    private EditText etEmail, etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        btndaftar = (TextView) findViewById(R.id.btnDaftar);
        btndaftar.setOnClickListener(this);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        mAuth.signOut();
        if(mAuth.getCurrentUser() != null){
//            if (mAuth.getCurrentUser().getUid().equals("Sz8eVlTFM1bu0D97nIwZehvEa5A2")){
//                finish();
//                startActivity(new Intent(getApplicationContext(), dashboard_admin.class));
//            }

            finish();
            startActivity(new Intent(getApplicationContext(), dashboard_user.class));
        }
    }

//    public void goToDetail(View view) {
//        Intent intent = new Intent(this, DetailLaporan.class);
//        String message = "Ar0EzxoBqBXjdvktIvIB";
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDaftar:
                startActivity(new Intent(this, register.class));
                break;
            case R.id.btnLogin:
                userLogin();
                break;

        }
    }

    private void userLogin(){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (email.isEmpty()){
            etEmail.setError("email harus di isi");
            etEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            etPassword.setError("password harus di isi");
            etPassword.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("tolong isi email dengan benar");
            etEmail.requestFocus();
            return;
        }
        if (password.length() < 6){
            etPassword.setError("minimal panjang password 6 karakter");
            etPassword.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()){
                  user = FirebaseAuth.getInstance().getCurrentUser();
                  userID = user.getUid();
                  if (userID.equals("Sz8eVlTFM1bu0D97nIwZehvEa5A2")){
                      startActivity(new Intent(login.this, dashboard_user.class));
                      Toast.makeText(login.this, "akun admin", Toast.LENGTH_LONG).show();
                  }
                  else {
                      finish();
                      startActivity(new Intent(login.this, dashboard_user.class));
                  }

              }else {
                  Toast.makeText(login.this, "login gagal", Toast.LENGTH_LONG).show();
              }
            }
        });
    }
}