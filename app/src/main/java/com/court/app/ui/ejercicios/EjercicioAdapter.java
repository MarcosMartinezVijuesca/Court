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
import com.court.app.viewmodel.CompletadoViewModel;
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
    private CompletadoViewModel completadoViewModel;
    private androidx.lifecycle.LifecycleOwner lifecycleOwner;
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
    public void setCompletadoViewModel(CompletadoViewModel viewModel,
                                       androidx.lifecycle.LifecycleOwner owner) {
        this.completadoViewModel = viewModel;
        this.lifecycleOwner = owner;
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

        String nivel = ejercicio.getNivel().equals("basico")
                ? holder.itemView.getContext().getString(R.string.ejercicio_nivel_basico)
                : holder.itemView.getContext().getString(R.string.ejercicio_nivel_avanzado);
        holder.tvNivel.setText(nivel);


        int colorBadge = ejercicio.getNivel().equals("basico")
                ? 0xFF1347EA
                : 0xFFFF6B2B;
        holder.tvNivel.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(colorBadge));

        holder.card.setOnClickListener(v -> listener.onEjercicioClick(ejercicio));

        if (completadoViewModel != null) {
            completadoViewModel.estaCompletado(ejercicio.getIdEjercicio())
                    .observe(lifecycleOwner, count -> {
                        boolean completado = count != null && count > 0;
                        // Detectar si estamos en tema oscuro
                        boolean isDarkTheme = (holder.itemView.getContext().getResources().getConfiguration().uiMode
                                & android.content.res.Configuration.UI_MODE_NIGHT_MASK)
                                == android.content.res.Configuration.UI_MODE_NIGHT_YES;

                        holder.card.setCardBackgroundColor(
                                completado
                                        ? (isDarkTheme ? 0xFF1A4D2E : 0xFF86EFAC)
                                        : android.graphics.Color.TRANSPARENT
                        );

                        int colorTexto = completado && !isDarkTheme
                                ? 0xFF14532D
                                : (isDarkTheme ? 0xFFFFFFFF : 0xFF0D1B4B);

                        holder.tvTitulo.setTextColor(colorTexto);
                        holder.tvDesc.setTextColor(colorTexto);
                    });
        }

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
