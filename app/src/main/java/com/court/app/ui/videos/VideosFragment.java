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
import com.court.app.ui.roles.OnboardingActivity;
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

        // Configurar RecyclerView
        RecyclerView rvVideos = view.findViewById(R.id.rv_videos);
        adapter = new VideoAdapter(video -> {
            // Abrir YouTube con la URL del vídeo
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getYoutubeUrl()));
            startActivity(intent);
        });
        rvVideos.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvVideos.setAdapter(adapter);

        // Cargar vídeos del rol activo
        viewModel = new ViewModelProvider(this).get(VideoViewModel.class);
        viewModel.obtenerPorRol(idRol).observe(getViewLifecycleOwner(), videos -> {
            if (videos != null) adapter.setVideos(videos);
        });
    }
}
