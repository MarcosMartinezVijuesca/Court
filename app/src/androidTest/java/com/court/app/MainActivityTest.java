package com.court.app;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.court.app.ui.roles.OnboardingActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        context.getSharedPreferences(
                        com.court.app.ui.roles.OnboardingActivity.PREFS_NAME,
                        Context.MODE_PRIVATE)
                .edit().clear().apply();
    }

    @Test
    public void mainActivityMuestraBottomNav() {
        ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.bottom_nav))
                .check(matches(isDisplayed()));
    }

    @Test
    public void navegarAVideos() {
        ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.videosFragment))
                .perform(click());

        onView(withId(R.id.rv_videos))
                .check(matches(isDisplayed()));
    }

    @Test
    public void navegarAFitness() {
        ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.fitnessFragment))
                .perform(click());

        onView(withId(R.id.rv_fitness))
                .check(matches(isDisplayed()));
    }

    @Test
    public void navegarAFavoritos() {
        ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.favoritosFragment))
                .perform(click());

        // Verificamos el texto vacío que aparece cuando no hay favoritos
        onView(withId(R.id.tv_favoritos_vacio))
                .check(matches(isDisplayed()));
    }
}
