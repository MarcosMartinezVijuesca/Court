package com.court.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.court.app.data.model.Ejercicio;
import com.court.app.data.repository.EjercicioRepository;

import java.util.List;

public class EjercicioViewModel extends AndroidViewModel {

    private final EjercicioRepository repository;
    private final MutableLiveData<Integer> idRolActivo = new MutableLiveData<>();
    public final LiveData<List<Ejercicio>> ejerciciosPorRol;

    public EjercicioViewModel(@NonNull Application application) {
        super(application);
        repository = new EjercicioRepository(application);
        ejerciciosPorRol = Transformations.switchMap(idRolActivo, repository::obtenerPorRol);
    }

    public void setRol(int idRol) {
        idRolActivo.setValue(idRol);
    }

    public LiveData<Ejercicio> obtenerPorId(int id) {
        return repository.obtenerPorId(id);
    }
}
