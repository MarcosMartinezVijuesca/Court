package com.court.app.ui.roles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.court.app.R;
import com.court.app.data.model.Rol;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class RolAdapter extends RecyclerView.Adapter<RolAdapter.RolViewHolder> {

    // Iconos por posición (emojis de baloncesto para cada rol)
    private static final String[] ICONOS = {"🎯", "🎯", "🏃", "💪", "🏆"};

    private List<Rol> roles = new ArrayList<>();
    private int posicionSeleccionada = -1;
    private final OnRolSeleccionadoListener listener;

    public interface OnRolSeleccionadoListener {
        void onRolSeleccionado(Rol rol);
    }

    public RolAdapter(OnRolSeleccionadoListener listener) {
        this.listener = listener;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rol, parent, false);
        return new RolViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RolViewHolder holder, int position) {
        Rol rol = roles.get(position);
        boolean seleccionado = position == posicionSeleccionada;

        holder.tvNombre.setText(obtenerNombreLocalizado(holder.itemView.getContext(), rol.getNombre()));
        holder.tvDesc.setText(obtenerDescLocalizada(holder.itemView.getContext(), rol.getNombre()));
        holder.tvIcono.setText(ICONOS[position % ICONOS.length]);

        // Resaltar tarjeta seleccionada
        holder.card.setChecked(seleccionado);
        holder.ivCheck.setVisibility(seleccionado ? View.VISIBLE : View.INVISIBLE);

        holder.card.setOnClickListener(v -> {
            int anterior = posicionSeleccionada;
            posicionSeleccionada = holder.getAdapterPosition();
            if (anterior != -1) notifyItemChanged(anterior);
            notifyItemChanged(posicionSeleccionada);
            listener.onRolSeleccionado(rol);
        });
    }

    @Override
    public int getItemCount() {
        return roles.size();
    }

    static class RolViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView card;
        TextView tvIcono, tvNombre, tvDesc;
        ImageView ivCheck;

        RolViewHolder(@NonNull View itemView) {
            super(itemView);
            card     = (MaterialCardView) itemView.findViewById(R.id.card_rol);
            tvIcono  = itemView.findViewById(R.id.tv_icono_rol);
            tvNombre = itemView.findViewById(R.id.tv_nombre_rol);
            tvDesc   = itemView.findViewById(R.id.tv_desc_rol);
            ivCheck  = itemView.findViewById(R.id.iv_check);
        }
    }

    private String obtenerNombreLocalizado(android.content.Context context, String nombreRol) {
        switch (nombreRol) {
            case "Base":      return context.getString(R.string.rol_base);
            case "Escolta":   return context.getString(R.string.rol_escolta);
            case "Alero":     return context.getString(R.string.rol_alero);
            case "Ala-pivot": return context.getString(R.string.rol_ala_pivot);
            case "Pivot":     return context.getString(R.string.rol_pivot);
            default:          return nombreRol;
        }
    }

    private String obtenerDescLocalizada(android.content.Context context, String nombreRol) {
        switch (nombreRol) {
            case "Base":      return context.getString(R.string.desc_base);
            case "Escolta":   return context.getString(R.string.desc_escolta);
            case "Alero":     return context.getString(R.string.desc_alero);
            case "Ala-pivot": return context.getString(R.string.desc_ala_pivot);
            case "Pivot":     return context.getString(R.string.desc_pivot);
            default:          return "";
        }
    }
}
