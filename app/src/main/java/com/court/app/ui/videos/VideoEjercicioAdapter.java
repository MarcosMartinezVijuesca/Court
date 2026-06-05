package com.court.app.ui.videos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.court.app.R;
import com.court.app.data.model.Ejercicio;
import com.court.app.ui.ejercicios.EjercicioAdapter;
import com.court.app.viewmodel.CompletadoViewModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class VideoEjercicioAdapter extends RecyclerView.Adapter<VideoEjercicioAdapter.ViewHolder> {

    private List<Ejercicio> ejercicios = new ArrayList<>();
    private final EjercicioAdapter.OnEjercicioClickListener listener;
    private CompletadoViewModel completadoViewModel;
    private androidx.lifecycle.LifecycleOwner lifecycleOwner;

    public VideoEjercicioAdapter(EjercicioAdapter.OnEjercicioClickListener listener) {
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video_ejercicio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ejercicio ejercicio = ejercicios.get(position);

        holder.tvTitulo.setText(ejercicio.getTitulo());
        holder.tvDesc.setText(ejercicio.getDescripcion());
        holder.ivEjercicio.setImageResource(R.drawable.court_videos);

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
    }

    @Override
    public int getItemCount() {
        return ejercicios.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView card;
        TextView tvTitulo, tvDesc, tvNivel, tvOrigen;
        ImageView ivEjercicio;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            card     = itemView.findViewById(R.id.card_ejercicio);
            tvTitulo = itemView.findViewById(R.id.tv_titulo_ejercicio);
            tvDesc   = itemView.findViewById(R.id.tv_desc_ejercicio);
            tvNivel  = itemView.findViewById(R.id.tv_nivel);
            tvOrigen = itemView.findViewById(R.id.tv_origen);
            ivEjercicio = itemView.findViewById(R.id.iv_ejercicio);
        }
    }
}