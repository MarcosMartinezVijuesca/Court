package com.court.app.ui.ejercicios;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.court.app.R;
import com.court.app.data.model.Ejercicio;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class EjercicioAdapter extends RecyclerView.Adapter<EjercicioAdapter.EjercicioViewHolder> {

    public interface OnEjercicioClickListener {
        void onEjercicioClick(Ejercicio ejercicio);
    }

    private List<Ejercicio> ejercicios = new ArrayList<>();
    private final OnEjercicioClickListener listener;

    private boolean mostrarOrigen = false;

    public void setMostrarOrigen(boolean mostrar) {
        this.mostrarOrigen = mostrar;
    }

    public EjercicioAdapter(OnEjercicioClickListener listener) {
        this.listener = listener;
    }

    public void setEjercicios(List<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EjercicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ejercicio, parent, false);
        return new EjercicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EjercicioViewHolder holder, int position) {
        Ejercicio ejercicio = ejercicios.get(position);

        holder.tvTitulo.setText(ejercicio.getTitulo());
        holder.tvDesc.setText(ejercicio.getDescripcion());

        // Badge de nivel localizado
        String nivel = ejercicio.getNivel().equals("basico")
                ? holder.itemView.getContext().getString(R.string.ejercicio_nivel_basico)
                : holder.itemView.getContext().getString(R.string.ejercicio_nivel_avanzado);
        holder.tvNivel.setText(nivel);

        // Color del badge según nivel
        int colorBadge = ejercicio.getNivel().equals("basico")
                ? 0xFF1347EA   // azul principal
                : 0xFFFF6B2B;  // naranja acento
        holder.tvNivel.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(colorBadge));

        holder.card.setOnClickListener(v -> listener.onEjercicioClick(ejercicio));

        if (mostrarOrigen) {
            if (ejercicio.getYoutubeUrl() != null && !ejercicio.getYoutubeUrl().isEmpty()) {
                holder.tvOrigen.setText("▶ Video");
            } else {
                holder.tvOrigen.setText("📋 Drill");
            }
            holder.tvOrigen.setVisibility(View.VISIBLE);
        } else {
            holder.tvOrigen.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return ejercicios.size();
    }

    static class EjercicioViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView card;
        ImageView ivEjercicio;
        TextView tvTitulo, tvDesc, tvNivel, tvOrigen;

        EjercicioViewHolder(@NonNull View itemView) {
            super(itemView);
            card        = itemView.findViewById(R.id.card_ejercicio);
            ivEjercicio = itemView.findViewById(R.id.iv_ejercicio);
            tvTitulo    = itemView.findViewById(R.id.tv_titulo_ejercicio);
            tvDesc      = itemView.findViewById(R.id.tv_desc_ejercicio);
            tvNivel     = itemView.findViewById(R.id.tv_nivel);
            tvOrigen    = itemView.findViewById(R.id.tv_origen);
        }
    }
}
