package com.court.app.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.court.app.data.db.CourtDatabase;
import com.court.app.data.db.FavoritoDao;
import com.court.app.data.model.Ejercicio;
import com.court.app.data.model.Favorito;

import java.util.List;

public class FavoritoRepository {

    private final FavoritoDao favoritoDao;
    // Executor heredado de CourtDatabase para operaciones en hilo de fondo
    private final java.util.concurrent.ExecutorService executor =
            CourtDatabase.dbExecutor;

    public FavoritoRepository(Application application) {
        CourtDatabase db = CourtDatabase.getInstance(application);
        favoritoDao = db.favoritoDao();
    }

    // Guardar un ejercicio como favorito
    public void guardar(int idEjercicio) {
        executor.execute(() ->
            favoritoDao.insertar(new Favorito(idEjercicio, System.currentTimeMillis()))
        );
    }

    // Eliminar un ejercicio de favoritos
    public void eliminar(int idEjercicio) {
        executor.execute(() ->
            favoritoDao.eliminarPorEjercicio(idEjercicio)
        );
    }

    // Lista de ejercicios favoritos (para la pantalla de favoritos)
    public LiveData<List<Ejercicio>> obtenerFavoritos() {
        return favoritoDao.obtenerEjerciciosFavoritos();
    }

    // Comprobar si un ejercicio concreto es favorito (para el icono estrella)
    public LiveData<Integer> esFavorito(int idEjercicio) {
        return favoritoDao.esFavorito(idEjercicio);
    }
}
