package com.court.app.ui.ejercicios;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.court.app.R;
import com.court.app.viewmodel.EjercicioViewModel;
import com.court.app.viewmodel.FavoritoViewModel;
import com.google.android.material.button.MaterialButton;

public class DetalleEjercicioFragment extends Fragment {

    public static final String ARG_ID_EJERCICIO = "id_ejercicio";

    private EjercicioViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalle_ejercicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener ID del ejercicio pasado como argumento
        int idEjercicio = getArguments() != null
                ? getArguments().getInt(ARG_ID_EJERCICIO, -1)
                : -1;

        if (idEjercicio == -1) return;

        ImageView ivImagen          = view.findViewById(R.id.iv_detalle_imagen);
        TextView tvTitulo           = view.findViewById(R.id.tv_detalle_titulo);
        TextView tvDescripcion      = view.findViewById(R.id.tv_detalle_descripcion);
        TextView tvNivel            = view.findViewById(R.id.tv_detalle_nivel);
        MaterialButton btnVerVideo  = view.findViewById(R.id.btn_ver_video);
        TextView tvSinVideo         = view.findViewById(R.id.tv_sin_video);
        ImageButton btnFavorito = view.findViewById(R.id.btn_favorito);
        FavoritoViewModel favoritoViewModel = new ViewModelProvider(this).get(FavoritoViewModel.class);


        final boolean[] esFavoritoActual = {false};

        favoritoViewModel.esFavorito(idEjercicio).observe(getViewLifecycleOwner(), count -> {
            esFavoritoActual[0] = count != null && count > 0;
            if (esFavoritoActual[0]) {
                btnFavorito.setImageResource(R.drawable.ic_estrella_llena);
                btnFavorito.setContentDescription(getString(R.string.favoritos_eliminar));
            } else {
                btnFavorito.setImageResource(R.drawable.ic_estrella_vacia);
                btnFavorito.setContentDescription(getString(R.string.favoritos_añadir));
            }
        });

        btnFavorito.setOnClickListener(v -> {
            if (esFavoritoActual[0]) {
                favoritoViewModel.eliminar(idEjercicio);
            } else {
                favoritoViewModel.guardar(idEjercicio);
            }
        });

        viewModel = new ViewModelProvider(this).get(EjercicioViewModel.class);
        viewModel.obtenerPorId(idEjercicio).observe(getViewLifecycleOwner(), ejercicio -> {
            if (ejercicio == null) return;

            tvTitulo.setText(ejercicio.getTitulo());
            tvDescripcion.setText(ejercicio.getDescripcion());

            // Nivel localizado
            String nivel = ejercicio.getNivel().equals("basico")
                    ? getString(R.string.ejercicio_nivel_basico)
                    : getString(R.string.ejercicio_nivel_avanzado);
            tvNivel.setText(nivel);

            // Color badge según nivel
            int colorBadge = ejercicio.getNivel().equals("basico")
                    ? 0xFF1347EA
                    : 0xFFFF6B2B;
            tvNivel.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(colorBadge));

            // Imagen local desde assets (cuando tengamos imágenes)
            // Por ahora mostramos el icono por defecto
            ivImagen.setImageResource(R.drawable.ic_nav_ejercicios);

            // Vídeo de YouTube
            if (ejercicio.getYoutubeUrl() != null && !ejercicio.getYoutubeUrl().isEmpty()) {
                btnVerVideo.setVisibility(View.VISIBLE);
                tvSinVideo.setVisibility(View.GONE);
                btnVerVideo.setOnClickListener(v -> {
                    // Abre YouTube directamente en la app de YouTube o navegador
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(ejercicio.getYoutubeUrl()));
                    startActivity(intent);
                });
            } else {
                btnVerVideo.setVisibility(View.GONE);
                tvSinVideo.setVisibility(View.VISIBLE);
            }
        });
    }
}
