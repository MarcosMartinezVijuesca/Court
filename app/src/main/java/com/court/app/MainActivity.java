package com.court.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Respetar barras del sistema (status bar y navigation bar)
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        setContentView(R.layout.activity_main);

        // Conectar Navigation Component con el Bottom Navigation Bar
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        NavController navController = navHostFragment.getNavController();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.ejerciciosFragment,
                R.id.videosFragment,
                R.id.fitnessFragment,
                R.id.favoritosFragment
        ).build();

        NavigationUI.setupWithNavController(bottomNav, navController);

        bottomNav.setOnItemSelectedListener(item -> {
            NavigationUI.onNavDestinationSelected(item, navController);
            navController.popBackStack(item.getItemId(), false);
            return true;
        });

        // Forzar colores del nav item
        int[][] states = new int[][] {
                new int[] { android.R.attr.state_checked },
                new int[] { -android.R.attr.state_checked }
        };
        int[] colors = new int[] {
                getResources().getColor(R.color.acento, getTheme()),
                0xCCFFFFFF
        };
        android.content.res.ColorStateList colorStateList =
                new android.content.res.ColorStateList(states, colors);

        bottomNav.setItemIconTintList(colorStateList);
        bottomNav.setItemTextColor(colorStateList);


    }
}
