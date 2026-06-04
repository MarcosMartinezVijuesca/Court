package com.court.app.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.court.app.data.model.Completado;

@Dao
public interface CompletadoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertar(Completado completado);

    @Query("DELETE FROM completados WHERE idEjercicio = :idEjercicio")
    void eliminarPorEjercicio(int idEjercicio);

    @Query("DELETE FROM completados")
    void resetAll();

    @Query("SELECT COUNT(*) FROM completados WHERE idEjercicio = :idEjercicio")
    LiveData<Integer> estaCompletado(int idEjercicio);
}