package com.court.app.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "roles")
public class Rol {

    @PrimaryKey(autoGenerate = true)
    private int idRol;

    @NonNull
    private String nombre;          // "Base", "Escolta", "Alero", "Ala-pívot", "Pívot"

    private String descripcion;     // Descripción del rol en cancha

    private String imagenNombre;    // Nombre del archivo en assets/ ej: "rol_base.png"

    // ── Constructor ──────────────────────────────────────────
    public Rol(@NonNull String nombre, String descripcion, String imagenNombre) {
        this.nombre       = nombre;
        this.descripcion  = descripcion;
        this.imagenNombre = imagenNombre;
    }

    // ── Getters y Setters ────────────────────────────────────
    public int getIdRol()                  { return idRol; }
    public void setIdRol(int idRol)        { this.idRol = idRol; }

    @NonNull
    public String getNombre()              { return nombre; }
    public void setNombre(@NonNull String nombre) { this.nombre = nombre; }

    public String getDescripcion()         { return descripcion; }
    public void setDescripcion(String d)   { this.descripcion = d; }

    public String getImagenNombre()        { return imagenNombre; }
    public void setImagenNombre(String i)  { this.imagenNombre = i; }
}
