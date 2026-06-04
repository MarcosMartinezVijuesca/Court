package com.court.app.ui.videos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

    public void setCompletadoViewModel(CompletadoViewModel viewModel,
                                       androidx.lifecycle.LifecycleOwner owner) {
        this.completadoViewModel = viewModel;
        this.lifecycleOwner = owner;
    }

    public VideoEjercicioAdapter(EjercicioAdapter.OnEjercicioClickListener listener) {
        this.listener = listener;
    }

    public void setEjercicios(List<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
        notifyDataSetChanged();
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
            final boolean[] completadoActual = {false};

            completadoViewModel.estaCompletado(ejercicio.getIdEjercicio())
                    .observe(lifecycleOwner, count -> {
                        completadoActual[0] = count != null && count > 0;
                        holder.btnCompletado.setImageResource(
                                completadoActual[0] ? R.drawable.ic_check_lleno : R.drawable.ic_check_vacio
                        );
                    });

            holder.btnCompletado.setOnClickListener(v -> {
                if (completadoActual[0]) {
                    completadoViewModel.desmarcar(ejercicio.getIdEjercicio());
                } else {
                    completadoViewModel.marcar(ejercicio.getIdEjercicio());
                }
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
        ImageButton btnCompletado;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            card     = itemView.findViewById(R.id.card_ejercicio);
            tvTitulo = itemView.findViewById(R.id.tv_titulo_ejercicio);
            tvDesc   = itemView.findViewById(R.id.tv_desc_ejercicio);
            tvNivel  = itemView.findViewById(R.id.tv_nivel);
            tvOrigen = itemView.findViewById(R.id.tv_origen);
            btnCompletado = itemView.findViewById(R.id.btn_completado);
        }
    }
}