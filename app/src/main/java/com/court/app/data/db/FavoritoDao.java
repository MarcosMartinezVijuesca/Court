package com.court.app.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.court.app.data.model.Favorito;
import com.court.app.data.model.Ejercicio;

import java.util.List;

@Dao
public interface FavoritoDao {

    // Guardar un favorito
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertar(Favorito favorito);

    // Eliminar un favorito por idEjercicio
    @Query("DELETE FROM favoritos WHERE idEjercicio = :idEjercicio")
    void eliminarPorEjercicio(int idEjercicio);

    // Obtener todos los ejercicios favoritos (JOIN con ejercicios)
    @Query("SELECT e.* FROM ejercicios e " +
           "INNER JOIN favoritos f ON e.idEjercicio = f.idEjercicio " +
           "ORDER BY f.fechaGuardado DESC")
    LiveData<List<Ejercicio>> obtenerEjerciciosFavoritos();

    // Comprobar si un ejercicio ya es favorito (para mostrar icono relleno)
    @Query("SELECT COUNT(*) FROM favoritos WHERE idEjercicio = :idEjercicio")
    LiveData<Integer> esFavorito(int idEjercicio);
}
