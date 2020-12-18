package com.kelompoklaptas.laptas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailLaporan extends AppCompatActivity {

    String[] descriptionData = {"Belum\nDiperbaiki", "Dalam\nPerbaikan", "Sudah\nDiperbaiki"};
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan);
        getDocument();

        ImageView button_back = (ImageView) findViewById(R.id.buttonBackDetail);
        button_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getDocument() {
        Intent intent = getIntent();
        String id_laporan = intent.getStringExtra(login.EXTRA_MESSAGE);
        assert id_laporan != null;

        DocumentReference docRef = db.collection("laporan").document(id_laporan);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        ImageView image = findViewById(R.id.imageDetail);
                        Picasso.get().load(document.getString("image")).into(image);

                        TextView title = findViewById(R.id.titleDetail);
                        title.setText(document.getString("title"));

                        StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.status_bar_laporan);
                        stateProgressBar.setStateDescriptionData(descriptionData);

                        if(Objects.equals(document.getString("status"), "Belum Diperbaiki"))
                            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                        else if(Objects.equals(document.getString("status"), "Dalam Perbaikan"))
                            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                        else if(Objects.equals(document.getString("status"), "Sudah Diperbaiki"))
                            stateProgressBar.setAllStatesCompleted(true);
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }
}