package com.court.app.data.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@Entity(
    tableName = "videos",
    foreignKeys = @ForeignKey(
        entity        = Rol.class,
        parentColumns = "idRol",
        childColumns  = "idRol",
        onDelete      = ForeignKey.SET_NULL   // Si se borra el rol, el vídeo queda como general
    ),
    indices = { @Index("idRol") }
)
public class Video {

    @PrimaryKey(autoGenerate = true)
    private int idVideo;

    @Nullable
    private Integer idRol;            // NULL = vídeo de fitness general (salto, fuerza, resistencia)

    @NonNull
    private String titulo;            // "Ejercicios de triple amenaza — Base"

    @NonNull
    private String youtubeUrl;        // "https://youtu.be/xxx"

    @NonNull
    private String categoria;         // "tecnica", "fuerza", "salto", "resistencia"

    private int duracionSeg;          // Duración en segundos (para mostrar en la tarjeta)

    @Nullable
    private String imagenNombre;      // Miniatura local opcional en assets/

    // ── Constructor ──────────────────────────────────────────
    public Video(@Nullable Integer idRol, @NonNull String titulo,
                 @NonNull String youtubeUrl, @NonNull String categoria,
                 int duracionSeg, @Nullable String imagenNombre) {
        this.idRol       = idRol;
        this.titulo      = titulo;
        this.youtubeUrl  = youtubeUrl;
        this.categoria   = categoria;
        this.duracionSeg = duracionSeg;
        this.imagenNombre = imagenNombre;
    }

    // ── Getters y Setters ────────────────────────────────────
    public int getIdVideo()                          { return idVideo; }
    public void setIdVideo(int id)                   { this.idVideo = id; }

    @Nullable
    public Integer getIdRol()                        { return idRol; }
    public void setIdRol(@Nullable Integer idRol)    { this.idRol = idRol; }

    @NonNull
    public String getTitulo()                        { return titulo; }
    public void setTitulo(@NonNull String t)         { this.titulo = t; }

    @NonNull
    public String getYoutubeUrl()                    { return youtubeUrl; }
    public void setYoutubeUrl(@NonNull String url)   { this.youtubeUrl = url; }

    @NonNull
    public String getCategoria()                     { return categoria; }
    public void setCategoria(@NonNull String c)      { this.categoria = c; }

    public int getDuracionSeg()                      { return duracionSeg; }
    public void setDuracionSeg(int d)                { this.duracionSeg = d; }

    @Nullable
    public String getImagenNombre()                  { return imagenNombre; }
    public void setImagenNombre(@Nullable String i)  { this.imagenNombre = i; }
}
