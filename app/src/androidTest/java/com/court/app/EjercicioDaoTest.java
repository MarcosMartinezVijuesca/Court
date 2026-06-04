package com.court.app;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.court.app.data.db.CourtDatabase;
import com.court.app.data.db.EjercicioDao;
import com.court.app.data.db.RolDao;
import com.court.app.data.model.Ejercicio;
import com.court.app.data.model.Rol;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class EjercicioDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private CourtDatabase db;
    private EjercicioDao ejercicioDao;
    private RolDao rolDao;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, CourtDatabase.class)
                .allowMainThreadQueries()
                .build();
        ejercicioDao = db.ejercicioDao();
        rolDao = db.rolDao();

        rolDao.insertarRoles(new Rol("Base", "Desc", "img.png"));
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void insertarYObtenerEjercicio() throws InterruptedException {
        Ejercicio ej = new Ejercicio(1, "Bote con ambas manos",
                "Descripción", "basico", null, null);
        ejercicioDao.insertarEjercicios(ej);

        List<Ejercicio> lista = getValueFromLiveData(ejercicioDao.obtenerPorRol(1));
        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("Bote con ambas manos", lista.get(0).getTitulo());
    }

    @Test
    public void filtrarPorNivelBasico() throws InterruptedException {
        ejercicioDao.insertarEjercicios(
            new Ejercicio(1, "Ejercicio básico", "Desc", "basico", null, null),
            new Ejercicio(1, "Ejercicio avanzado", "Desc", "avanzado", null, null)
        );

        List<Ejercicio> basicos = getValueFromLiveData(ejercicioDao.obtenerBasicosPorRol(1));
        assertEquals(1, basicos.size());
        assertEquals("basico", basicos.get(0).getNivel());
    }

    @Test
    public void filtrarEjerciciosSinVideo() throws InterruptedException {
        ejercicioDao.insertarEjercicios(
            new Ejercicio(1, "Sin vídeo", "Desc", "basico", null, null),
            new Ejercicio(1, "Con vídeo", "Desc", "avanzado", null, "https://youtu.be/xxx")
        );

        List<Ejercicio> sinVideo = getValueFromLiveData(ejercicioDao.obtenerSinVideoPorRol(1));
        assertEquals(1, sinVideo.size());
        assertNull(sinVideo.get(0).getYoutubeUrl());
    }

    @Test
    public void filtrarEjerciciosConVideo() throws InterruptedException {
        ejercicioDao.insertarEjercicios(
            new Ejercicio(1, "Sin vídeo", "Desc", "basico", null, null),
            new Ejercicio(1, "Con vídeo", "Desc", "avanzado", null, "https://youtu.be/xxx")
        );

        List<Ejercicio> conVideo = getValueFromLiveData(ejercicioDao.obtenerConVideoPorRol(1));
        assertEquals(1, conVideo.size());
        assertNotNull(conVideo.get(0).getYoutubeUrl());
    }

    @Test
    public void obtenerEjercicioPorId() throws InterruptedException {
        Ejercicio ej = new Ejercicio(1, "Pick and Roll", "Desc", "avanzado", null, null);
        ejercicioDao.insertarEjercicios(ej);

        List<Ejercicio> lista = getValueFromLiveData(ejercicioDao.obtenerPorRol(1));
        int id = lista.get(0).getIdEjercicio();

        Ejercicio obtenido = getValueFromLiveData(ejercicioDao.obtenerPorId(id));
        assertNotNull(obtenido);
        assertEquals("Pick and Roll", obtenido.getTitulo());
    }

    private <T> T getValueFromLiveData(LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = value -> {
            data[0] = value;
            latch.countDown();
        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);
        liveData.removeObserver(observer);
        //noinspection unchecked
        return (T) data[0];
    }
}
