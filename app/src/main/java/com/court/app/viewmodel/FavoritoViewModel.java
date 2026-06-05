package com.court.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.court.app.data.model.Ejercicio;
import com.court.app.data.repository.FavoritoRepository;

import java.util.List;

public class FavoritoViewModel extends AndroidViewModel {

    private final FavoritoRepository repository;

    public FavoritoViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoritoRepository(application);
    }

    // Lista de ejercicios guardados como favoritos
    public LiveData<List<Ejercicio>> obtenerFavoritos() {
        return repository.obtenerFavoritos();
    }

    // Comprueba si un ejercicio concreto es favorito (devuelve 1 o 0)
    public LiveData<Integer> esFavorito(int idEjercicio) {
        return repository.esFavorito(idEjercicio);
    }

    // Guardar en favoritos
    public void guardar(int idEjercicio) {
        repository.guardar(idEjercicio);
    }

    // Eliminar de favoritos
    public void eliminar(int idEjercicio) {
        repository.eliminar(idEjercicio);
    }
}
