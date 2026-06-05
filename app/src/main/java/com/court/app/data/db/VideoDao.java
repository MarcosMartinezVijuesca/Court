package com.court.app.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.court.app.data.model.Video;

import java.util.List;

@Dao
public interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertarVideos(Video... videos);

    // Videos de un rol especifico (tecnica)
    @Query("SELECT * FROM videos WHERE idRol = :idRol ORDER BY titulo ASC")
    LiveData<List<Video>> obtenerPorRol(int idRol);

    // Videos generales de fitness (idRol es NULL)
    @Query("SELECT * FROM videos WHERE idRol IS NULL ORDER BY categoria ASC, titulo ASC")
    LiveData<List<Video>> obtenerGenerales();

    // Videos por categoria (fuerza, salto, resistencia)
    @Query("SELECT * FROM videos WHERE categoria = :categoria ORDER BY titulo ASC")
    LiveData<List<Video>> obtenerPorCategoria(String categoria);

    // Obtener un video por ID
    @Query("SELECT * FROM videos WHERE idVideo = :id LIMIT 1")
    LiveData<Video> obtenerPorId(int id);
}
