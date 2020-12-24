package com.kelompoklaptas.laptas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class dashboard_user extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mRecyclerView;
    private ListLaporanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);

        String getName = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();
        String displayName = "Welcome, "+(getName==null ? "Admin" : getName)+"!";

        TextView textView = (TextView) findViewById(R.id.tv_welcome);
        textView.setText(displayName);

        TextView sign_out = (TextView) findViewById(R.id.tv_signout);
        sign_out.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(getApplicationContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                finish();
                                startActivity(new Intent(getApplicationContext(), login.class));
                            }
                        });
            }
        });

        firebaseFirestore = FirebaseFirestore.getInstance();
        mRecyclerView = findViewById(R.id.rv_list_laporan);

        CollectionReference query = firebaseFirestore.collection("laporan");
        Query query1 = query.orderBy("date", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Laporan> options = new FirestoreRecyclerOptions.Builder<Laporan>()
                .setQuery(query1, Laporan.class)
                .build();
         adapter = new ListLaporanAdapter(options, this);

         mRecyclerView.setHasFixedSize(true);
         mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
         mRecyclerView.setAdapter(adapter);
    }

    public void goToLapor(View view) {
        Intent intent = new Intent(this, MakeReports.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}