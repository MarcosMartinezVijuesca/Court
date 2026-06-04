package com.court.app.data.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.court.app.R;
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

    public abstract RolDao       rolDao();
    public abstract EjercicioDao ejercicioDao();
    public abstract VideoDao     videoDao();
    public abstract FavoritoDao  favoritoDao();

    private static volatile CourtDatabase INSTANCE;
    public static final ExecutorService dbExecutor = Executors.newFixedThreadPool(4);

    private static Context appContext;
    public static CourtDatabase getInstance(final Context context) {
        appContext = context.getApplicationContext();
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

                RolDao rolDao = INSTANCE.rolDao();
                EjercicioDao ejDao = INSTANCE.ejercicioDao();
                VideoDao videoDao = INSTANCE.videoDao();
                Context c = appContext;

                // ── ROLES ────────────────────────────────────────────────
                Rol base     = new Rol("Base",      c.getString(R.string.desc_base), "rol_base.png");
                Rol escolta  = new Rol("Escolta",   c.getString(R.string.desc_escolta),"rol_escolta.png");
                Rol alero    = new Rol("Alero",     c.getString(R.string.desc_alero),"rol_alero.png");
                Rol alaPivot = new Rol("Ala-pivot", c.getString(R.string.desc_ala_pivot),"rol_ala_pivot.png");
                Rol pivot    = new Rol("Pivot",     c.getString(R.string.desc_pivot), "rol_pivot.png");

                rolDao.insertarRoles(base, escolta, alero, alaPivot, pivot);

                // IDs asignados por Room en orden de inserción
                int ID_BASE      = 1;
                int ID_ESCOLTA   = 2;
                int ID_ALERO     = 3;
                int ID_ALA_PIVOT = 4;
                int ID_PIVOT     = 5;


                ejDao.insertarEjercicios(

                        // ── BASE ───────────────────────────────────────
                        new Ejercicio(ID_BASE, c.getString(R.string.ej_bote_titulo),
                                c.getString(R.string.ej_bote_desc),
                                "basico", null, "https://youtu.be/a6rPVGkGpds?t=65"),

                        new Ejercicio(ID_BASE, c.getString(R.string.ej_tiro_titulo),
                                c.getString(R.string.ej_tiro_desc),
                                "basico", null, "https://youtu.be/UcnB9e5O5NY?t=30"),

                        new Ejercicio(ID_BASE, c.getString(R.string.ej_base_mov_titulo),
                                c.getString(R.string.ej_base_mov_desc),
                                "basico", null, "https://youtu.be/IX-VSlVfvmI?t=22"),

                        new Ejercicio(ID_BASE, c.getString(R.string.vid_base_avanzado_titulo),
                                c.getString(R.string.vid_base_avanzado_desc),
                                "avanzado", null, "https://youtu.be/nRjWnw0EPbg?t=56"),

                        new Ejercicio(ID_BASE, c.getString(R.string.ej_defensa_titulo),
                                c.getString(R.string.ej_defensa_desc),
                                "basico", null, null),

                        new Ejercicio(ID_BASE, c.getString(R.string.ej_pases_titulo),
                                c.getString(R.string.ej_pases_desc),
                                "basico", null, null),

                        new Ejercicio(ID_BASE, c.getString(R.string.ej_base_pickroll_titulo),
                                c.getString(R.string.ej_base_pickroll_desc),
                                "basico", null, null),

                        new Ejercicio(ID_BASE, c.getString(R.string.ej_base_ritmo_titulo),
                                c.getString(R.string.ej_base_ritmo_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_BASE, c.getString(R.string.ej_figure_eight_titulo),
                                c.getString(R.string.ej_figure_eight_desc),
                                "basico", null, null),

                        new Ejercicio(ID_BASE, c.getString(R.string.ej_base_diapos_titulo),
                                c.getString(R.string.ej_base_diapos_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_BASE, c.getString(R.string.ej_base_presion_titulo),
                                c.getString(R.string.ej_base_presion_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_BASE, c.getString(R.string.ej_base_suspension_titulo),
                                c.getString(R.string.ej_base_suspension_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_BASE, c.getString(R.string.ej_base_lectura_titulo),
                                c.getString(R.string.ej_base_lectura_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_BASE, c.getString(R.string.ej_base_vision_titulo),
                                c.getString(R.string.ej_base_vision_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_BASE, c.getString(R.string.ej_base_penetracion_titulo),
                                c.getString(R.string.ej_base_penetracion_desc),
                                "avanzado", null, null),

                        // ── ESCOLTA ────────────────────────────────────
                        new Ejercicio(ID_ESCOLTA, c.getString(R.string.ej_escolta_bote_titulo),
                                c.getString(R.string.ej_escolta_bote_desc),
                                "avanzado", null, "https://youtu.be/3AKCf4xbu2k?t=14"),
                        new Ejercicio(ID_ESCOLTA, c.getString(R.string.vid_bote_titulo),
                                c.getString(R.string.ej_bote_desc),
                                "basico", null, "https://youtu.be/a6rPVGkGpds?t=65"),

                        new Ejercicio(ID_ESCOLTA, c.getString(R.string.vid_tiro_titulo),
                                c.getString(R.string.ej_tiro_desc),
                                "basico", null, "https://youtu.be/UcnB9e5O5NY?t=30"),
                        new Ejercicio(ID_ESCOLTA, c.getString(R.string.vid_escolta_ataque_titulo),
                                c.getString(R.string.vid_escolta_ataque_desc),
                                "basico", null, "https://youtu.be/_ySaZPYDM-Q?t=21"),

                        new Ejercicio(ID_ESCOLTA, c.getString(R.string.vid_escolta_avanzado_titulo),
                                c.getString(R.string.vid_escolta_avanzado_desc),
                                "avanzado", null, "https://youtu.be/nRjWnw0EPbg?t=56"),

                        new Ejercicio(ID_ESCOLTA, c.getString(R.string.ej_escolta_catch_titulo),
                                c.getString(R.string.ej_escolta_catch_desc),
                                "basico", null, null),

                        new Ejercicio(ID_ESCOLTA, c.getString(R.string.ej_escolta_corte_titulo),
                                c.getString(R.string.ej_escolta_corte_desc),
                                "basico", null, null),

                        new Ejercicio(ID_ESCOLTA, c.getString(R.string.ej_defensa_titulo),
                                c.getString(R.string.ej_defensa_desc),
                                "basico", null, null),

                        new Ejercicio(ID_ESCOLTA, c.getString(R.string.ej_pases_titulo),
                                c.getString(R.string.ej_pases_desc),
                                "basico", null, null),
                        new Ejercicio(ID_ESCOLTA, c.getString(R.string.ej_figure_eight_titulo),
                                c.getString(R.string.ej_figure_eight_desc),
                                "basico", null, null),

                        new Ejercicio(ID_ESCOLTA, c.getString(R.string.ej_escolta_floater_titulo),
                                c.getString(R.string.ej_escolta_floater_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_ESCOLTA, c.getString(R.string.ej_escolta_esquinas_titulo),
                                c.getString(R.string.ej_escolta_esquinas_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_ESCOLTA, c.getString(R.string.ej_escolta_closeout_titulo),
                                c.getString(R.string.ej_escolta_closeout_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_ESCOLTA, c.getString(R.string.ej_escolta_defcorte_titulo),
                                c.getString(R.string.ej_escolta_defcorte_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_ESCOLTA, c.getString(R.string.ej_escolta_diapos_titulo),
                                c.getString(R.string.ej_escolta_diapos_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_ESCOLTA, c.getString(R.string.ej_escolta_suspension_titulo),
                                c.getString(R.string.ej_escolta_suspension_desc),
                                "avanzado", null, null),

                        // ── ALERO ──────────────────────────────────────
                        new Ejercicio(ID_ALERO, c.getString(R.string.ej_alero_mov_titulo),
                                c.getString(R.string.ej_alero_mov_desc),
                                "basico", null, "https://youtu.be/BGuOzD0h854?t=35"),
                        new Ejercicio(ID_ALERO, c.getString(R.string.vid_bote_titulo),
                                c.getString(R.string.ej_bote_desc),

                                "basico", null, "https://youtu.be/a6rPVGkGpds?t=65"),
                        new Ejercicio(ID_ALERO, c.getString(R.string.vid_tiro_titulo),
                                c.getString(R.string.ej_tiro_desc),
                                "basico", null, "https://youtu.be/UcnB9e5O5NY?t=30"),

                        new Ejercicio(ID_ALERO, c.getString(R.string.vid_alero_ofensivo_titulo),
                                c.getString(R.string.ej_alero_mov_desc),
                                "avanzado", null, "https://www.youtube.com/watch?v=2yOL2S3j1Ds"),

                        new Ejercicio(ID_ALERO, c.getString(R.string.vid_alero_avanzado_titulo),
                                c.getString(R.string.ej_alero_mov_desc),
                                "avanzado", null, "https://youtu.be/nRjWnw0EPbg?t=56"),


                        new Ejercicio(ID_ALERO, c.getString(R.string.ej_alero_rebote_titulo),
                                c.getString(R.string.ej_alero_rebote_desc),
                                "basico", null, null),
                        new Ejercicio(ID_ALERO, c.getString(R.string.ej_defensa_titulo),
                                c.getString(R.string.ej_defensa_desc),
                                "basico", null, null),

                        new Ejercicio(ID_ALERO, c.getString(R.string.ej_pases_titulo),
                                c.getString(R.string.ej_pases_desc),
                                "basico", null, null),

                        new Ejercicio(ID_ALERO, c.getString(R.string.ej_figure_eight_titulo),
                                c.getString(R.string.ej_figure_eight_desc),
                                "basico", null, null),

                        new Ejercicio(ID_ALERO, c.getString(R.string.ej_alero_defensa_titulo),
                                c.getString(R.string.ej_alero_defensa_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_ALERO, c.getString(R.string.ej_alero_triple_titulo),
                                c.getString(R.string.ej_alero_triple_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_ALERO, c.getString(R.string.ej_alero_kickout_titulo),
                                c.getString(R.string.ej_alero_kickout_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_ALERO, c.getString(R.string.ej_alero_bloqueo_titulo),
                                c.getString(R.string.ej_alero_bloqueo_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_ALERO, c.getString(R.string.ej_alero_versatil_titulo),
                                c.getString(R.string.ej_alero_versatil_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_ALERO, c.getString(R.string.ej_alero_closeout_titulo),
                                c.getString(R.string.ej_alero_closeout_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_ALERO, c.getString(R.string.ej_alero_media_titulo),
                                c.getString(R.string.ej_alero_media_desc),
                                "avanzado", null, null),

                        // ── ALA-PÍVOT ──────────────────────────────────
                        new Ejercicio(ID_ALA_PIVOT, c.getString(R.string.ej_alapivot_poste_titulo),
                                c.getString(R.string.ej_alapivot_poste_desc),
                                "basico", null, "https://youtu.be/0hRQ0eltVzQ?t=2"),

                        new Ejercicio(ID_ALA_PIVOT, c.getString(R.string.vid_poste_simple_titulo),
                                c.getString(R.string.ej_alapivot_poste_desc),
                                "basico", null, "https://youtu.be/1BkPSQL1ZzM?t=15"),

                        new Ejercicio(ID_ALA_PIVOT, c.getString(R.string.vid_bote_titulo),
                                c.getString(R.string.ej_bote_desc),
                                "basico", null, "https://youtu.be/a6rPVGkGpds?t=65"),

                        new Ejercicio(ID_ALA_PIVOT, c.getString(R.string.vid_tiro_titulo),
                                c.getString(R.string.ej_tiro_desc),
                                "basico", null, "https://youtu.be/UcnB9e5O5NY?t=30"),

                        new Ejercicio(ID_ALA_PIVOT, c.getString(R.string.vid_poste_avanzado_titulo),
                                c.getString(R.string.ej_alapivot_poste_desc),
                                "avanzado", null, "https://www.youtube.com/watch?v=yF0vQGFpHrs"),

                        new Ejercicio(ID_ALA_PIVOT, c.getString(R.string.ej_alapivot_rebote_titulo),
                                c.getString(R.string.ej_alapivot_rebote_desc),
                                "basico", null, null),

                        new Ejercicio(ID_ALA_PIVOT, c.getString(R.string.ej_defensa_titulo),
                                c.getString(R.string.ej_defensa_desc),
                                "basico", null, null),

                        new Ejercicio(ID_ALA_PIVOT, c.getString(R.string.ej_pases_titulo),
                                c.getString(R.string.ej_pases_desc),
                                "basico", null, null),

                        new Ejercicio(ID_ALA_PIVOT, c.getString(R.string.ej_figure_eight_titulo),
                                c.getString(R.string.ej_figure_eight_desc),
                                "basico", null, null),

                        new Ejercicio(ID_ALA_PIVOT, c.getString(R.string.ej_alapivot_tres_titulo),
                                c.getString(R.string.ej_alapivot_tres_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_ALA_PIVOT, c.getString(R.string.ej_alapivot_poste_titulo),
                                c.getString(R.string.ej_alapivot_poste_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_ALA_PIVOT, c.getString(R.string.ej_alapivot_bloqueo_titulo),
                                c.getString(R.string.ej_alapivot_bloqueo_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_ALA_PIVOT, c.getString(R.string.ej_alapivot_pies_titulo),
                                c.getString(R.string.ej_alapivot_pies_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_ALA_PIVOT, c.getString(R.string.ej_alapivot_defposte_titulo),
                                c.getString(R.string.ej_alapivot_defposte_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_ALA_PIVOT, c.getString(R.string.ej_alapivot_defperim_titulo),
                                c.getString(R.string.ej_alapivot_defperim_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_ALA_PIVOT, c.getString(R.string.ej_alapivot_rebofens_titulo),
                                c.getString(R.string.ej_alapivot_rebofens_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_ALA_PIVOT, c.getString(R.string.ej_alapivot_media_titulo),
                                c.getString(R.string.ej_alapivot_media_desc),
                                "avanzado", null, null),

                        // ── PÍVOT ──────────────────────────────────────
                        new Ejercicio(ID_PIVOT, c.getString(R.string.ej_pivot_poste_titulo),
                                c.getString(R.string.ej_pivot_poste_desc),
                                "avanzado", null, "https://youtu.be/dkxFtT0GUP0?t=13"),

                        new Ejercicio(ID_PIVOT, c.getString(R.string.vid_bote_titulo),
                                c.getString(R.string.ej_bote_desc),
                                "basico", null, "https://youtu.be/a6rPVGkGpds?t=65"),

                        new Ejercicio(ID_PIVOT, c.getString(R.string.vid_tiro_titulo),
                                c.getString(R.string.ej_tiro_desc),
                                "basico", null, "https://youtu.be/UcnB9e5O5NY?t=30"),

                        new Ejercicio(ID_PIVOT, c.getString(R.string.vid_poste_simple_titulo),
                                c.getString(R.string.ej_pivot_poste_desc),
                                "basico", null, "https://youtu.be/1BkPSQL1ZzM?t=15"),

                        new Ejercicio(ID_PIVOT, c.getString(R.string.vid_poste_avanzado_titulo),
                                c.getString(R.string.ej_pivot_poste_desc),
                                "avanzado", null, "https://www.youtube.com/watch?v=yF0vQGFpHrs"),

                        new Ejercicio(ID_PIVOT, c.getString(R.string.ej_defensa_titulo),
                                c.getString(R.string.ej_defensa_desc), "basico", null, null),

                        new Ejercicio(ID_PIVOT, c.getString(R.string.ej_pases_titulo),
                                c.getString(R.string.ej_pases_desc), "basico", null, null),

                        new Ejercicio(ID_PIVOT, c.getString(R.string.ej_figure_eight_titulo),
                                c.getString(R.string.ej_figure_eight_desc), "basico", null, null),

                        new Ejercicio(ID_PIVOT, c.getString(R.string.ej_pivot_gancho_titulo),
                                c.getString(R.string.ej_pivot_gancho_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_PIVOT, c.getString(R.string.ej_pivot_zona_titulo),
                                c.getString(R.string.ej_pivot_zona_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_PIVOT, c.getString(R.string.ej_pivot_dropstep_titulo),
                                c.getString(R.string.ej_pivot_dropstep_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_PIVOT, c.getString(R.string.ej_pivot_pickroll_titulo),
                                c.getString(R.string.ej_pivot_pickroll_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_PIVOT, c.getString(R.string.ej_pivot_tapon_titulo),
                                c.getString(R.string.ej_pivot_tapon_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_PIVOT, c.getString(R.string.ej_pivot_defpivot_titulo),
                                c.getString(R.string.ej_pivot_defpivot_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_PIVOT, c.getString(R.string.ej_pivot_rebdef_titulo),
                                c.getString(R.string.ej_pivot_rebdef_desc),
                                "avanzado", null, null),

                        new Ejercicio(ID_PIVOT, c.getString(R.string.ej_pivot_zonapintada_titulo),
                                c.getString(R.string.ej_pivot_zonapintada_desc),
                                "avanzado", null, null)

                );

                // ── VÍDEOS FITNESS GENERAL ───────────────────────────────
                videoDao.insertarVideos(
                        new Video(null, c.getString(R.string.vid_salto_titulo),
                                "https://www.youtube.com/watch?v=WA564xCGgnc&t=93s",
                                "salto", 941, null),

                        new Video(null, c.getString(R.string.vid_fuerza_titulo),
                                "https://youtu.be/t0xEqm-0CTw?t=47",
                                "fuerza", 285, null),

                        new Video(null, c.getString(R.string.vid_resistencia_titulo),
                                "https://youtu.be/b7rr3IfRcyo?t=24",
                                "resistencia", 220 , null)
                );
            });
        }
    };
}
