package com.court.app.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.court.app.data.db.CourtDatabase;
import com.court.app.data.db.RolDao;
import com.court.app.data.model.Rol;

import java.util.List;

public class RolRepository {

    private final RolDao rolDao;

    public RolRepository(Application application) {
        CourtDatabase db = CourtDatabase.getInstance(application);
        rolDao = db.rolDao();
    }

    // Devuelve todos los roles — LiveData para que la UI reaccione automáticamente
    public LiveData<List<Rol>> obtenerTodos() {
        return rolDao.obtenerTodos();
    }

    // Devuelve un rol concreto por su ID
    public LiveData<Rol> obtenerPorId(int id) {
        return rolDao.obtenerPorId(id);
    }
}
