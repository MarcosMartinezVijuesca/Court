package com.court.app.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.court.app.data.model.Ejercicio;

import java.util.List;

@Dao
public interface EjercicioDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertarEjercicios(Ejercicio... ejercicios);

    // Todos los ejercicios de un rol (basicos + avanzados)
    @Query("SELECT * FROM ejercicios WHERE idRol = :idRol ORDER BY nivel ASC, titulo ASC")
    LiveData<List<Ejercicio>> obtenerPorRol(int idRol);

    // Solo ejercicios basicos de un rol
    @Query("SELECT * FROM ejercicios WHERE idRol = :idRol AND nivel = 'basico'")
    LiveData<List<Ejercicio>> obtenerBasicosPorRol(int idRol);

    // Solo ejercicios avanzados de un rol
    @Query("SELECT * FROM ejercicios WHERE idRol = :idRol AND nivel = 'avanzado'")
    LiveData<List<Ejercicio>> obtenerAvanzadosPorRol(int idRol);

    // Obtener un ejercicio por ID (pantalla de detalle)
    @Query("SELECT * FROM ejercicios WHERE idEjercicio = :id LIMIT 1")
    LiveData<Ejercicio> obtenerPorId(int id);
}
