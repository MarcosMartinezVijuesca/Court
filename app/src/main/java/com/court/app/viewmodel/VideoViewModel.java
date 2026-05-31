package com.court.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.court.app.data.model.Video;
import com.court.app.data.repository.VideoRepository;

import java.util.List;

public class VideoViewModel extends AndroidViewModel {

    private final VideoRepository repository;

    public VideoViewModel(@NonNull Application application) {
        super(application);
        repository = new VideoRepository(application);
    }

    // Vídeos de técnica de un rol concreto
    public LiveData<List<Video>> obtenerPorRol(int idRol) {
        return repository.obtenerPorRol(idRol);
    }

    // Vídeos generales de fitness (salto, fuerza, resistencia)
    public LiveData<List<Video>> obtenerGenerales() {
        return repository.obtenerGenerales();
    }

    // Filtrar por categoría dentro de fitness
    public LiveData<List<Video>> obtenerPorCategoria(String categoria) {
        return repository.obtenerPorCategoria(categoria);
    }
}
