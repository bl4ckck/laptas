package com.kelompoklaptas.laptas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.kofigyan.stateprogressbar.components.StateItem;
import com.kofigyan.stateprogressbar.listeners.OnStateItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailLaporan extends AppCompatActivity {
    String[] descriptionData = {"Belum\nDiperbaiki", "Dalam\nPerbaikan", "Sudah\nDiperbaiki"};
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan);

        Intent intent = getIntent();
        final String id_laporan = intent.getStringExtra("id_doc");
        assert id_laporan != null;

        ImageView button_back = (ImageView) findViewById(R.id.buttonBackDetail);
        button_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        getDocument(id_laporan);

        if (user.getUid().equals("Sz8eVlTFM1bu0D97nIwZehvEa5A2")) {
            StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.status_bar_laporan);

            stateProgressBar.setOnStateItemClickListener(new OnStateItemClickListener() {
                @Override
                public void onStateItemClick(StateProgressBar stateProgressBar, StateItem stateItem, int stateNumber, boolean isCurrentState) {
                    setStatus(stateNumber, stateProgressBar, id_laporan);
                }
            });
        }
    }

    private void getDocument(String id_laporan) {
        DocumentReference docRef = db.collection("laporan").document(id_laporan);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;

                    if (document.exists()) {
                        //Set image
                        ImageView image = findViewById(R.id.imageDetail);
                        Picasso.get().load(document.getString("image")).into(image);

                        //Set title
                        TextView title = findViewById(R.id.titleDetail);
                        title.setText(document.getString("title"));

                        //Set Date
                        String formatDate = String.valueOf(Objects.requireNonNull(document.getTimestamp("date")).toDate().toLocaleString());
                        TextView date = findViewById(R.id.dateDetail);
                        date.setText(formatDate);

                        //Set Progress Bar
                        StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.status_bar_laporan);
                        stateProgressBar.setStateDescriptionData(descriptionData);
                        setStateNumber(document, stateProgressBar);

                        //Set description
                        TextView desc = findViewById(R.id.descDetail);
                        desc.setText(document.getString("description"));
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }

    private void setStateNumber(DocumentSnapshot document, StateProgressBar stateProgressBar) {
        if (Objects.equals(document.getString("status"), "Belum Diperbaiki"))
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
        else if (Objects.equals(document.getString("status"), "Dalam Perbaikan"))
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
        else if (Objects.equals(document.getString("status"), "Sudah Diperbaiki"))
            stateProgressBar.setAllStatesCompleted(true);
    }

    private void setStatus(int selectedState, StateProgressBar stateProgressBar, String id_laporan) {
        String currentStatus = "";

        if (selectedState == 1) {
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
            currentStatus = "Belum Diperbaiki";
        }
        else if (selectedState == 2) {
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
            currentStatus = "Dalam Perbaikan";
        }
        else if (selectedState == 3) {
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
            currentStatus = "Sudah Diperbaiki";
        }

        DocumentReference laporanRef = db.collection("laporan").document(id_laporan);
        laporanRef
                .update("status", currentStatus)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error updating document", e);
                    }
                });
    }
}