package com.court.app.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.court.app.data.db.CourtDatabase;
import com.court.app.data.db.VideoDao;
import com.court.app.data.model.Video;

import java.util.List;

public class VideoRepository {

    private final VideoDao videoDao;

    public VideoRepository(Application application) {
        CourtDatabase db = CourtDatabase.getInstance(application);
        videoDao = db.videoDao();
    }

    // Vídeos de técnica vinculados a un rol
    public LiveData<List<Video>> obtenerPorRol(int idRol) {
        return videoDao.obtenerPorRol(idRol);
    }

    // Vídeos generales de fitness (sin rol, idRol = NULL)
    public LiveData<List<Video>> obtenerGenerales() {
        return videoDao.obtenerGenerales();
    }

    // Vídeos filtrados por categoría (fuerza, salto, resistencia)
    public LiveData<List<Video>> obtenerPorCategoria(String categoria) {
        return videoDao.obtenerPorCategoria(categoria);
    }

    // Un vídeo concreto por ID
    public LiveData<List<Video>> obtenerPorId(int id) {
        return videoDao.obtenerPorRol(id);
    }
}
