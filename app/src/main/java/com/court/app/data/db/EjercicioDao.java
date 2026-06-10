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
    @Query("SELECT * FROM ejercicios WHERE idRol = :idRol AND nivel = 'basico' AND youtubeUrl IS NULL ORDER BY titulo ASC")
    LiveData<List<Ejercicio>> obtenerBasicosPorRol(int idRol);

    // Solo ejercicios avanzados de un rol
    @Query("SELECT * FROM ejercicios WHERE idRol = :idRol AND nivel = 'avanzado' AND youtubeUrl IS NULL ORDER BY titulo ASC")

    LiveData<List<Ejercicio>> obtenerAvanzadosPorRol(int idRol);

    // Obtener un ejercicio por ID (pantalla de detalle)
    @Query("SELECT * FROM ejercicios WHERE idEjercicio = :id LIMIT 1")
    LiveData<Ejercicio> obtenerPorId(int id);

    // Solo ejercicios SIN vídeo (para la pestaña Drills)
    @Query("SELECT * FROM ejercicios WHERE idRol = :idRol AND youtubeUrl IS NULL " +
            "ORDER BY CASE nivel WHEN 'basico' THEN 0 WHEN 'avanzado' THEN 1 END ASC, titulo ASC")
    LiveData<List<Ejercicio>> obtenerSinVideoPorRol(int idRol);

    // Solo ejercicios CON vídeo (para la pestaña Videos)
    @Query("SELECT * FROM ejercicios WHERE idRol = :idRol AND youtubeUrl IS NOT NULL " +
            "ORDER BY CASE nivel WHEN 'basico' THEN 0 WHEN 'avanzado' THEN 1 END ASC, titulo ASC")
    LiveData<List<Ejercicio>> obtenerConVideoPorRol(int idRol);
}
