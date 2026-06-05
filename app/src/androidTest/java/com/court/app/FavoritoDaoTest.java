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
import com.court.app.data.db.FavoritoDao;
import com.court.app.data.model.Ejercicio;
import com.court.app.data.model.Favorito;
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
public class FavoritoDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private CourtDatabase db;
    private FavoritoDao favoritoDao;
    private EjercicioDao ejercicioDao;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, CourtDatabase.class)
                .allowMainThreadQueries()
                .build();
        favoritoDao  = db.favoritoDao();
        ejercicioDao = db.ejercicioDao();

        db.rolDao().insertarRoles(new Rol("Base", "Desc", "img.png"));
        ejercicioDao.insertarEjercicios(
            new Ejercicio(1, "Bote", "Desc", "basico", null, null)
        );
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void guardarFavorito() throws InterruptedException {
        favoritoDao.insertar(new Favorito(1, System.currentTimeMillis()));

        Integer count = getValueFromLiveData(favoritoDao.esFavorito(1));
        assertNotNull(count);
        assertTrue(count > 0);
    }

    @Test
    public void eliminarFavorito() throws InterruptedException {
        favoritoDao.insertar(new Favorito(1, System.currentTimeMillis()));
        favoritoDao.eliminarPorEjercicio(1);

        Integer count = getValueFromLiveData(favoritoDao.esFavorito(1));
        assertNotNull(count);
        assertEquals(0, (int) count);
    }

    @Test
    public void obtenerEjerciciosFavoritos() throws InterruptedException {
        favoritoDao.insertar(new Favorito(1, System.currentTimeMillis()));

        List<Ejercicio> favoritos = getValueFromLiveData(favoritoDao.obtenerEjerciciosFavoritos());
        assertNotNull(favoritos);
        assertEquals(1, favoritos.size());
        assertEquals("Bote", favoritos.get(0).getTitulo());
    }

    @Test
    public void noPermitirFavoritoDuplicado() throws InterruptedException {
        favoritoDao.insertar(new Favorito(1, System.currentTimeMillis()));
        favoritoDao.insertar(new Favorito(1, System.currentTimeMillis())); // debe ignorarse

        List<Ejercicio> favoritos = getValueFromLiveData(favoritoDao.obtenerEjerciciosFavoritos());
        assertEquals(1, favoritos.size());
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
