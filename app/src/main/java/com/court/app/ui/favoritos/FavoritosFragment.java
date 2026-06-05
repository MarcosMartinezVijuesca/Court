package com.court.app.ui.favoritos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.court.app.R;
import com.court.app.ui.ejercicios.DetalleEjercicioFragment;
import com.court.app.ui.ejercicios.EjercicioAdapter;
import com.court.app.viewmodel.CompletadoViewModel;
import com.court.app.viewmodel.FavoritoViewModel;

public class FavoritosFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favoritos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvFavoritos = view.findViewById(R.id.rv_favoritos);
        TextView tvVacio = view.findViewById(R.id.tv_favoritos_vacio);

        EjercicioAdapter adapter = new EjercicioAdapter(ejercicio -> {
            Bundle args = new Bundle();
            args.putInt(DetalleEjercicioFragment.ARG_ID_EJERCICIO, ejercicio.getIdEjercicio());
            androidx.navigation.Navigation.findNavController(requireView())
                    .navigate(R.id.action_favoritos_to_detalle, args);
        });
        CompletadoViewModel completadoViewModel = new ViewModelProvider(this).get(CompletadoViewModel.class);
        adapter.setCompletadoViewModel(completadoViewModel, getViewLifecycleOwner());
        rvFavoritos.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvFavoritos.setAdapter(adapter);

        FavoritoViewModel viewModel = new ViewModelProvider(this).get(FavoritoViewModel.class);
        viewModel.obtenerFavoritos().observe(getViewLifecycleOwner(), ejercicios -> {
            if (ejercicios == null || ejercicios.isEmpty()) {
                rvFavoritos.setVisibility(View.GONE);
                tvVacio.setVisibility(View.VISIBLE);
            } else {
                rvFavoritos.setVisibility(View.VISIBLE);
                tvVacio.setVisibility(View.GONE);
                adapter.setEjercicios(ejercicios);
            }
        });
    }
}