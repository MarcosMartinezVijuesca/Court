package com.court.app.ui.ejercicios;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.court.app.R;
import com.court.app.ui.roles.OnboardingActivity;
import com.court.app.viewmodel.EjercicioViewModel;
import com.court.app.viewmodel.RolViewModel;

import static android.content.Context.MODE_PRIVATE;

public class EjerciciosFragment extends Fragment {

    private EjercicioViewModel ejercicioViewModel;
    private RolViewModel rolViewModel;
    private EjercicioAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ejercicios, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener el rol guardado en SharedPreferences
        SharedPreferences prefs = requireActivity()
                .getSharedPreferences(OnboardingActivity.PREFS_NAME, MODE_PRIVATE);
        int idRol = prefs.getInt(OnboardingActivity.KEY_ID_ROL, 1);

        // Configurar RecyclerView
        RecyclerView rvEjercicios = view.findViewById(R.id.rv_ejercicios);
        adapter = new EjercicioAdapter(ejercicio -> {
            Bundle args = new Bundle();
            args.putInt(DetalleEjercicioFragment.ARG_ID_EJERCICIO, ejercicio.getIdEjercicio());
            androidx.navigation.Navigation.findNavController(requireView())
                    .navigate(R.id.action_ejercicios_to_detalle, args);
        });
        rvEjercicios.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvEjercicios.setAdapter(adapter);

        // Mostrar nombre del rol en la cabecera
        rolViewModel = new ViewModelProvider(this).get(RolViewModel.class);
        rolViewModel.obtenerPorId(idRol).observe(getViewLifecycleOwner(), rol -> {
            if (rol != null) {
                TextView tvRolActivo = view.findViewById(R.id.tv_rol_activo);
                tvRolActivo.setText(obtenerNombreLocalizado(rol.getNombre()));
            }
        });

        // Cargar ejercicios del rol
        ejercicioViewModel = new ViewModelProvider(this).get(EjercicioViewModel.class);
        ejercicioViewModel.setRol(idRol);
        ejercicioViewModel.obtenerSinVideoPorRol(idRol).observe(getViewLifecycleOwner(), ejercicios -> {
            if (ejercicios != null) adapter.setEjercicios(ejercicios);
        });

        // Cambiar rol
        view.findViewById(R.id.tv_cambiar_rol).setOnClickListener(v -> {
            prefs.edit()
                    .remove(OnboardingActivity.KEY_ID_ROL)
                    .putBoolean(OnboardingActivity.KEY_ONBOARDING, false)
                    .apply();
            Intent intent = new Intent(requireActivity(), OnboardingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish();
        });
    }
    private String obtenerNombreLocalizado(String nombreRol) {
        switch (nombreRol) {
            case "Base":      return getString(R.string.rol_base);
            case "Escolta":   return getString(R.string.rol_escolta);
            case "Alero":     return getString(R.string.rol_alero);
            case "Ala-pivot": return getString(R.string.rol_ala_pivot);
            case "Pivot":     return getString(R.string.rol_pivot);
            default:          return nombreRol;
        }
    }
}
