package com.court.app.data.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@Entity(
    tableName = "ejercicios",
    foreignKeys = @ForeignKey(
        entity     = Rol.class,
        parentColumns = "idRol",
        childColumns  = "idRol",
        onDelete   = ForeignKey.CASCADE   // Si se borra un rol, se borran sus ejercicios
    ),
    indices = { @Index("idRol") }         // Índice para acelerar consultas por rol
)
public class Ejercicio {

    @PrimaryKey(autoGenerate = true)
    private int idEjercicio;

    private int idRol;                    // FK → Rol (0 = ejercicio básico compartido por todos)

    @NonNull
    private String titulo;                // "Manejo de balón con ambas manos"

    private String descripcion;           // Explicación del ejercicio

    @NonNull
    private String nivel;                 // "basico" o "avanzado"

    @Nullable
    private String imagenNombre;          // Nombre archivo en assets/ ej: "ej_bote.png"

    @Nullable
    private String youtubeUrl;            // URL YouTube opcional ej: "https://youtu.be/xxx"

    // ── Constructor ──────────────────────────────────────────
    public Ejercicio(int idRol, @NonNull String titulo, String descripcion,
                     @NonNull String nivel, @Nullable String imagenNombre,
                     @Nullable String youtubeUrl) {
        this.idRol        = idRol;
        this.titulo       = titulo;
        this.descripcion  = descripcion;
        this.nivel        = nivel;
        this.imagenNombre = imagenNombre;
        this.youtubeUrl   = youtubeUrl;
    }

    // ── Getters y Setters ────────────────────────────────────
    public int getIdEjercicio()                      { return idEjercicio; }
    public void setIdEjercicio(int id)               { this.idEjercicio = id; }

    public int getIdRol()                            { return idRol; }
    public void setIdRol(int idRol)                  { this.idRol = idRol; }

    @NonNull
    public String getTitulo()                        { return titulo; }
    public void setTitulo(@NonNull String titulo)    { this.titulo = titulo; }

    public String getDescripcion()                   { return descripcion; }
    public void setDescripcion(String d)             { this.descripcion = d; }

    @NonNull
    public String getNivel()                         { return nivel; }
    public void setNivel(@NonNull String nivel)      { this.nivel = nivel; }

    @Nullable
    public String getImagenNombre()                  { return imagenNombre; }
    public void setImagenNombre(@Nullable String i)  { this.imagenNombre = i; }

    @Nullable
    public String getYoutubeUrl()                    { return youtubeUrl; }
    public void setYoutubeUrl(@Nullable String url)  { this.youtubeUrl = url; }
}
