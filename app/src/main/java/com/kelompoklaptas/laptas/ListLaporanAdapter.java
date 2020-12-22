package com.kelompoklaptas.laptas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ListLaporanAdapter extends FirestoreRecyclerAdapter<Laporan, ListLaporanAdapter.Holder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    public ListLaporanAdapter(@NonNull FirestoreRecyclerOptions<Laporan> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ListLaporanAdapter.Holder holder, int position, @NonNull final Laporan model) {
        String formatDate = String.valueOf(model.getDate().toDate().toLocaleString());

        holder.tvJudul.setText(model.getTitle());
        holder.tvStatus.setText(model.getStatus());
        holder.tvTanggal.setText(formatDate);
        holder.tvNama.setText(model.getId_pelapor());

        Picasso.get()
                .load(model.getImage())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailLaporan.class);
                intent.putExtra("judul", model.getTitle());
                intent.putExtra("status", model.getStatus());
                intent.putExtra("tanggal", model.getDate());
                intent.putExtra("nama", model.getId_pelapor());
                intent.putExtra("image", model.getImage());
                intent.putExtra("berwenang", model.getId_berwenang());
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public ListLaporanAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
        return new Holder(view);
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tvJudul;
        TextView tvNama;
        TextView tvTanggal;
        TextView tvStatus;

        ImageView imageView;
        public Holder(@NonNull View itemView) {
            super(itemView);

            tvJudul = itemView.findViewById(R.id.tv_card_title);
            tvNama = itemView.findViewById(R.id.tv_name_card);
            tvTanggal = itemView.findViewById(R.id.tv_card_date);
            tvStatus = itemView.findViewById(R.id.iv_card_status);

            imageView = itemView.findViewById(R.id.iv_report);
        }
    }
}
