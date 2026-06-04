package com.court.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CompletadoViewModel extends AndroidViewModel {

    private final com.court.app.data.repository.CompletadoRepository repository;

    public CompletadoViewModel(@NonNull Application application) {
        super(application);
        repository = new com.court.app.data.repository.CompletadoRepository(application);
    }

    public void marcar(int idEjercicio) {
        repository.marcar(idEjercicio);
    }

    public void desmarcar(int idEjercicio) {
        repository.desmarcar(idEjercicio);
    }

    public void resetAll() {
        repository.resetAll();
    }

    public LiveData<Integer> estaCompletado(int idEjercicio) {
        return repository.estaCompletado(idEjercicio);
    }
}
