package com.court.app.data.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.court.app.data.model.Ejercicio;
import com.court.app.data.model.Favorito;
import com.court.app.data.model.Rol;
import com.court.app.data.model.Video;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
    entities  = { Rol.class, Ejercicio.class, Video.class, Favorito.class },
    version   = 1,
    exportSchema = false
)
public abstract class CourtDatabase extends RoomDatabase {

    public abstract RolDao      rolDao();
    public abstract EjercicioDao ejercicioDao();
    public abstract VideoDao    videoDao();
    public abstract FavoritoDao  favoritoDao();

    private static volatile CourtDatabase INSTANCE;

    public static final ExecutorService dbExecutor = Executors.newFixedThreadPool(4);

    public static CourtDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (CourtDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            CourtDatabase.class,
                            "court_database"
                        )
                        .addCallback(poblarDatosIniciales)
                        .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback poblarDatosIniciales = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            dbExecutor.execute(() -> {
                RolDao dao = INSTANCE.rolDao();
                dao.insertarRoles(
                    new Rol("Base",      "Organizador del juego. Manejo, visión y velocidad.",    "rol_base.png"),
                    new Rol("Escolta",   "Anotador exterior. Tiro y penetración.",                "rol_escolta.png"),
                    new Rol("Alero",     "Versátil. Tiro, rebote y defensa perimetral.",          "rol_alero.png"),
                    new Rol("Ala-pivot", "Juego interior y exterior. Fuerza y tiro medio.",      "rol_ala_pivot.png"),
                    new Rol("Pivot",     "Dominio bajo el aro. Posteo, rebote y defensa interior.","rol_pivot.png")
                );

            });
        }
    };
}
