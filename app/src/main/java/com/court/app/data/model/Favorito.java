package com.court.app.data.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "favoritos",
    foreignKeys = @ForeignKey(
        entity        = Ejercicio.class,
        parentColumns = "idEjercicio",
        childColumns  = "idEjercicio",
        onDelete      = ForeignKey.CASCADE    // Si se borra el ejercicio, se borra el favorito
    ),
    indices = { @Index(value = "idEjercicio", unique = true) }  // Un ejercicio solo puede ser favorito una vez
)
public class Favorito {

    @PrimaryKey(autoGenerate = true)
    private int idFavorito;

    private int idEjercicio;          // FK → Ejercicio

    private long fechaGuardado;       // Timestamp en milisegundos (System.currentTimeMillis())

    // ── Constructor ──────────────────────────────────────────
    public Favorito(int idEjercicio, long fechaGuardado) {
        this.idEjercicio   = idEjercicio;
        this.fechaGuardado = fechaGuardado;
    }

    // ── Getters y Setters ────────────────────────────────────
    public int getIdFavorito()                   { return idFavorito; }
    public void setIdFavorito(int id)            { this.idFavorito = id; }

    public int getIdEjercicio()                  { return idEjercicio; }
    public void setIdEjercicio(int id)           { this.idEjercicio = id; }

    public long getFechaGuardado()               { return fechaGuardado; }
    public void setFechaGuardado(long fecha)     { this.fechaGuardado = fecha; }
}
