package com.court.app.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.court.app.data.db.CourtDatabase;
import com.court.app.data.db.CompletadoDao;
import com.court.app.data.model.Completado;

public class CompletadoRepository {

    private final CompletadoDao completadoDao;
    private final java.util.concurrent.ExecutorService executor = CourtDatabase.dbExecutor;

    public CompletadoRepository(Application application) {
        CourtDatabase db = CourtDatabase.getInstance(application);
        completadoDao = db.completadoDao();
    }

    public void marcar(int idEjercicio) {
        executor.execute(() ->
                completadoDao.insertar(new Completado(idEjercicio, System.currentTimeMillis()))
        );
    }

    public void desmarcar(int idEjercicio) {
        executor.execute(() ->
                completadoDao.eliminarPorEjercicio(idEjercicio)
        );
    }

    public void resetAll() {
        executor.execute(completadoDao::resetAll);
    }

    public LiveData<Integer> estaCompletado(int idEjercicio) {
        return completadoDao.estaCompletado(idEjercicio);
    }
}
