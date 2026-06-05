package com.court.app;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class OnboardingTest {

    @Test
    public void onboardingMuestraListaDeRoles() {
        ActivityScenario.launch(com.court.app.ui.roles.OnboardingActivity.class);

        // El RecyclerView de roles es visible
        onView(withId(R.id.rv_roles))
                .check(matches(isDisplayed()));
    }

    @Test
    public void botonEmpezarDesactivadoSinSeleccion() {
        ActivityScenario.launch(com.court.app.ui.roles.OnboardingActivity.class);

        // El botón está desactivado hasta seleccionar rol
        onView(withId(R.id.btn_empezar))
                .check(matches(isNotEnabled()));
    }

    @Test
    public void seleccionarRolActivaBoton() {
        ActivityScenario.launch(com.court.app.ui.roles.OnboardingActivity.class);

        // Pulsar el primer rol de la lista
        onView(withId(R.id.rv_roles))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // El botón debe activarse
        onView(withId(R.id.btn_empezar))
                .check(matches(isEnabled()));
    }

    @Test
    public void tituloOnboardingVisible() {
        ActivityScenario.launch(com.court.app.ui.roles.OnboardingActivity.class);

        onView(withId(R.id.tv_titulo))
                .check(matches(isDisplayed()));
    }
}
