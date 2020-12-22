package com.kelompoklaptas.laptas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class dashboard_user extends AppCompatActivity {


    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mRecyclerView;
    private ListLaporanAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_dashboard_user);

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