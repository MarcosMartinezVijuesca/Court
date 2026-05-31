package com.court.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.court.app.data.model.Rol;
import com.court.app.data.repository.RolRepository;

import java.util.List;

public class RolViewModel extends AndroidViewModel {

    private final RolRepository repository;

    public RolViewModel(@NonNull Application application) {
        super(application);
        repository = new RolRepository(application);
    }

    public LiveData<List<Rol>> obtenerTodos() {
        return repository.obtenerTodos();
    }

    public LiveData<Rol> obtenerPorId(int id) {
        return repository.obtenerPorId(id);
    }
}
