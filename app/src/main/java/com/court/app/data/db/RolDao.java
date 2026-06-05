package com.court.app.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.court.app.data.model.Rol;

import java.util.List;

@Dao
public interface RolDao {

    // Insertar uno o varios roles (usado en el pre-populate inicial)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertarRoles(Rol... roles);

    // Obtener todos los roles — LiveData para que la UI se actualice automáticamente
    @Query("SELECT * FROM roles ORDER BY idRol ASC")
    LiveData<List<Rol>> obtenerTodos();

    // Obtener un rol por su ID
    @Query("SELECT * FROM roles WHERE idRol = :id LIMIT 1")
    LiveData<Rol> obtenerPorId(int id);
}
