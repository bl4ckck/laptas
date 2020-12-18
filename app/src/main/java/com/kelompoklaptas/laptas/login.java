package com.kelompoklaptas.laptas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class login extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "laptas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void goToDetail(View view) {
        Intent intent = new Intent(this, DetailLaporan.class);
        String message = "Ar0EzxoBqBXjdvktIvIB";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}