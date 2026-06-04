package com.court.app.data.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "completados",
        foreignKeys = @ForeignKey(
                entity        = Ejercicio.class,
                parentColumns = "idEjercicio",
                childColumns  = "idEjercicio",
                onDelete      = ForeignKey.CASCADE
        ),
        indices = { @Index(value = "idEjercicio", unique = true) }
)
public class Completado {

    @PrimaryKey(autoGenerate = true)
    private int idCompletado;

    private int idEjercicio;
    private long fechaCompletado;

    public Completado(int idEjercicio, long fechaCompletado) {
        this.idEjercicio     = idEjercicio;
        this.fechaCompletado = fechaCompletado;
    }

    public int getIdCompletado()                 { return idCompletado; }
    public void setIdCompletado(int id)          { this.idCompletado = id; }
    public int getIdEjercicio()                  { return idEjercicio; }
    public void setIdEjercicio(int id)           { this.idEjercicio = id; }
    public long getFechaCompletado()             { return fechaCompletado; }
    public void setFechaCompletado(long fecha)   { this.fechaCompletado = fecha; }
}
