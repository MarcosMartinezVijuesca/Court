package com.court.app.ui.fitness;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.court.app.R;
import com.court.app.ui.videos.VideoAdapter;
import com.court.app.viewmodel.VideoViewModel;
import com.google.android.material.chip.ChipGroup;

public class FitnessFragment extends Fragment {

    private VideoViewModel viewModel;
    private VideoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fitness, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configurar RecyclerView
        RecyclerView rvFitness = view.findViewById(R.id.rv_fitness);
        adapter = new VideoAdapter(video -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getYoutubeUrl()));
            startActivity(intent);
        });
        rvFitness.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvFitness.setAdapter(adapter);

        // Cargar todos los vídeos generales de fitness
        viewModel = new ViewModelProvider(this).get(VideoViewModel.class);
        cargarTodos();

        // Filtros por categoría
        ChipGroup chipGroup = view.findViewById(R.id.chip_group_categoria);
        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;
            int chipId = checkedIds.get(0);
            if (chipId == R.id.chip_salto) {
                viewModel.obtenerPorCategoria("salto")
                        .observe(getViewLifecycleOwner(), videos -> {
                            if (videos != null) adapter.setVideos(videos);
                        });
            } else if (chipId == R.id.chip_fuerza) {
                viewModel.obtenerPorCategoria("fuerza")
                        .observe(getViewLifecycleOwner(), videos -> {
                            if (videos != null) adapter.setVideos(videos);
                        });
            } else if (chipId == R.id.chip_resistencia) {
                viewModel.obtenerPorCategoria("resistencia")
                        .observe(getViewLifecycleOwner(), videos -> {
                            if (videos != null) adapter.setVideos(videos);
                        });
            } else {
                cargarTodos();
            }
        });
    }

    private void cargarTodos() {
        viewModel.obtenerGenerales().observe(getViewLifecycleOwner(), videos -> {
            if (videos != null) adapter.setVideos(videos);
        });
    }
}
