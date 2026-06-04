package com.court.app.ui.videos;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.court.app.ui.ejercicios.DetalleEjercicioFragment;
import com.court.app.ui.ejercicios.EjercicioAdapter;
import com.court.app.ui.roles.OnboardingActivity;
import com.court.app.viewmodel.EjercicioViewModel;
import com.court.app.viewmodel.VideoViewModel;

import static android.content.Context.MODE_PRIVATE;

public class VideosFragment extends Fragment {

    private VideoViewModel viewModel;
    private VideoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_videos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener rol activo
        SharedPreferences prefs = requireActivity()
                .getSharedPreferences(OnboardingActivity.PREFS_NAME, MODE_PRIVATE);
        int idRol = prefs.getInt(OnboardingActivity.KEY_ID_ROL, 1);

        RecyclerView rvVideos = view.findViewById(R.id.rv_videos);

        // Reutilizamos EjercicioAdapter ya que ahora Videos muestra ejercicios con URL
        VideoEjercicioAdapter adapter = new VideoEjercicioAdapter(ejercicio -> {
            Bundle args = new Bundle();
            args.putInt(DetalleEjercicioFragment.ARG_ID_EJERCICIO, ejercicio.getIdEjercicio());
            androidx.navigation.Navigation.findNavController(requireView())
                    .navigate(R.id.action_videos_to_detalle, args);
        });
        rvVideos.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvVideos.setAdapter(adapter);

        EjercicioViewModel viewModel = new ViewModelProvider(this).get(EjercicioViewModel.class);
        viewModel.obtenerConVideoPorRol(idRol).observe(getViewLifecycleOwner(), ejercicios -> {
            if (ejercicios != null) adapter.setEjercicios(ejercicios);
        });

        // Cambiar rol
        view.findViewById(R.id.tv_cambiar_rol_videos).setOnClickListener(v -> {
            prefs.edit()
                    .remove(OnboardingActivity.KEY_ID_ROL)
                    .putBoolean(OnboardingActivity.KEY_ONBOARDING, false)
                    .apply();
            Intent intent = new Intent(requireActivity(), OnboardingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish();
        });
    }
}
