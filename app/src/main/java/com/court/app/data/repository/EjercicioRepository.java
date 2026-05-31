package com.court.app.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.court.app.data.db.CourtDatabase;
import com.court.app.data.db.EjercicioDao;
import com.court.app.data.model.Ejercicio;

import java.util.List;

public class EjercicioRepository {

    private final EjercicioDao ejercicioDao;

    public EjercicioRepository(Application application) {
        CourtDatabase db = CourtDatabase.getInstance(application);
        ejercicioDao = db.ejercicioDao();
    }

    // Todos los ejercicios de un rol (básicos + avanzados)
    public LiveData<List<Ejercicio>> obtenerPorRol(int idRol) {
        return ejercicioDao.obtenerPorRol(idRol);
    }

    // Solo los ejercicios básicos de un rol
    public LiveData<List<Ejercicio>> obtenerBasicosPorRol(int idRol) {
        return ejercicioDao.obtenerBasicosPorRol(idRol);
    }

    // Solo los ejercicios avanzados de un rol
    public LiveData<List<Ejercicio>> obtenerAvanzadosPorRol(int idRol) {
        return ejercicioDao.obtenerAvanzadosPorRol(idRol);
    }

    // Un ejercicio concreto por ID (para la pantalla de detalle)
    public LiveData<Ejercicio> obtenerPorId(int id) {
        return ejercicioDao.obtenerPorId(id);
    }
}
