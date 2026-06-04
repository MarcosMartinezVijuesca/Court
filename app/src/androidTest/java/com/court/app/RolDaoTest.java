package com.court.app;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.court.app.data.db.CourtDatabase;
import com.court.app.data.db.RolDao;
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
public class RolDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private CourtDatabase db;
    private RolDao rolDao;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, CourtDatabase.class)
                .allowMainThreadQueries()
                .build();
        rolDao = db.rolDao();
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void insertarYObtenerRol() throws InterruptedException {
        Rol rol = new Rol("Base", "Organizador del juego", "rol_base.png");
        rolDao.insertarRoles(rol);

        List<Rol> roles = getValueFromLiveData(rolDao.obtenerTodos());
        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals("Base", roles.get(0).getNombre());
    }

    @Test
    public void insertarCincoRoles() throws InterruptedException {
        rolDao.insertarRoles(
            new Rol("Base",      "Desc base",      "img.png"),
            new Rol("Escolta",   "Desc escolta",   "img.png"),
            new Rol("Alero",     "Desc alero",     "img.png"),
            new Rol("Ala-pivot", "Desc ala-pivot", "img.png"),
            new Rol("Pivot",     "Desc pivot",     "img.png")
        );

        List<Rol> roles = getValueFromLiveData(rolDao.obtenerTodos());
        assertEquals(5, roles.size());
    }

    @Test
    public void obtenerRolPorId() throws InterruptedException {
        Rol rol = new Rol("Escolta", "Anotador exterior", "rol_escolta.png");
        rolDao.insertarRoles(rol);

        List<Rol> roles = getValueFromLiveData(rolDao.obtenerTodos());
        int idInsertado = roles.get(0).getIdRol();

        Rol rolObtenido = getValueFromLiveData(rolDao.obtenerPorId(idInsertado));
        assertNotNull(rolObtenido);
        assertEquals("Escolta", rolObtenido.getNombre());
    }

    @Test
    public void ignorarRolDuplicado() throws InterruptedException {
        Rol rol1 = new Rol("Base", "Desc", "img.png");
        rolDao.insertarRoles(rol1);


        List<Rol> roles = getValueFromLiveData(rolDao.obtenerTodos());
        int idInsertado = roles.get(0).getIdRol();

        Rol rol2 = new Rol("Base", "Desc", "img.png");
        rol2.setIdRol(idInsertado);
        rolDao.insertarRoles(rol2);

        List<Rol> rolesFinales = getValueFromLiveData(rolDao.obtenerTodos());
        assertEquals(1, rolesFinales.size());
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
