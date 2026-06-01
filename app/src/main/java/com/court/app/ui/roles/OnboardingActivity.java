package com.court.app.ui.roles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.court.app.MainActivity;
import com.court.app.R;
import com.court.app.data.model.Rol;
import com.court.app.viewmodel.RolViewModel;
import com.google.android.material.button.MaterialButton;

public class OnboardingActivity extends AppCompatActivity {

    public static final String PREFS_NAME    = "court_prefs";
    public static final String KEY_ID_ROL    = "id_rol_seleccionado";
    public static final String KEY_ONBOARDING = "onboarding_completado";

    private RolViewModel viewModel;
    private RolAdapter adapter;
    private Rol rolSeleccionado = null;
    private MaterialButton btnEmpezar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Si el usuario ya eligió rol, ir directo a MainActivity
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (prefs.getBoolean(KEY_ONBOARDING, false)) {
            irAMain();
            return;
        }

        // Edge-to-edge API 35+
        androidx.core.view.WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        androidx.core.view.WindowInsetsControllerCompat controller =
                new androidx.core.view.WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(false);
        getWindow().setStatusBarColor(
                getResources().getColor(R.color.principal, getTheme())
        );


        setContentView(R.layout.activity_onboarding);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_onboarding), (v, insets) -> {
            int bottomInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom;
            v.setPadding(
                    v.getPaddingLeft(),
                    v.getPaddingTop(),
                    v.getPaddingRight(),
                    bottomInset + 4
            );
            return insets;
        });

        btnEmpezar = findViewById(R.id.btn_empezar);
        RecyclerView rvRoles = findViewById(R.id.rv_roles);

        // Configurar RecyclerView
        adapter = new RolAdapter(rol -> {
            rolSeleccionado = rol;
            btnEmpezar.setEnabled(true);
        });
        rvRoles.setLayoutManager(new LinearLayoutManager(this));
        rvRoles.setAdapter(adapter);

        // Observar roles desde Room
        viewModel = new ViewModelProvider(this).get(RolViewModel.class);
        viewModel.obtenerTodos().observe(this, roles -> {
            if (roles != null) adapter.setRoles(roles);
        });

        // Guardar rol y continuar
        btnEmpezar.setOnClickListener(v -> {
            if (rolSeleccionado != null) {
                prefs.edit()
                        .putInt(KEY_ID_ROL, rolSeleccionado.getIdRol())
                        .putBoolean(KEY_ONBOARDING, true)
                        .apply();
                irAMain();
            }
        });
    }

    private void irAMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
