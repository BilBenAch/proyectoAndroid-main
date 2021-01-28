package com.example.proyectoandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoandroid.databinding.FragmentCambiarTelefonoBinding;
import com.example.proyectoandroid.databinding.FragmentMiCuentaCambiarContraseniaBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;


public class MiCuentaCambiarContraseniaFragment extends BaseFragment {
    FragmentMiCuentaCambiarContraseniaBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return (binding = FragmentMiCuentaCambiarContraseniaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation);
        usuario = appViewModel.usuarioAutenticado.getValue();
        userId = usuario.id;

        appViewModel.obtenerUsuario(userId).observe(getViewLifecycleOwner(), usuario -> {

            binding.volverMiCuenta.setOnClickListener(v -> {
                navController.navigate(R.id.action_global_miCuentaFragment);
            });

            //preguntar a Gerard como hacer con callbacks
            binding.botonGuardarContra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String primeraContra = binding.editTextPrimeraContra.getText().toString();
                    String segundaContra = binding.editTextSegundaContra.getText().toString();
                    String terceraContra = binding.editTextTerceraContra.getText().toString();

                    boolean error = false;


                    if (primeraContra.isEmpty()) {
                        binding.editTextPrimeraContra.setError("Este campo no puede estar vacío ");
                        error = true;
                    }
                    if (!primeraContra.equalsIgnoreCase(usuario.password)) {
                        binding.editTextPrimeraContra.setError("La contraseña antigua no coincide ");
                        error = true;
                    }
                    if (segundaContra.isEmpty()) {
                        binding.editTextSegundaContra.setError("Este campo no puede estar vacío ");
                        error = true;
                    }

                    if (terceraContra.isEmpty()) {
                        binding.editTextTerceraContra.setError("Este campo no puede estar vacío ");
                        error = true;
                    }
                    if (!segundaContra.equalsIgnoreCase(terceraContra)) {
                        binding.editTextSegundaContra.setError("Las contraseñas no coinciden ");
                        binding.editTextTerceraContra.setError("Las contraseñas no coinciden ");
                        error = true;
                    }

                    if (!error) {
                        appViewModel.cambiarContra(segundaContra, userId);
                        Snackbar snackbar = Snackbar.make(view, "Contraseña cambiada correctamente", Snackbar.LENGTH_SHORT);
                        snackbar.setAnchorView(navBar);
                        snackbar.show();
                    }
                }
            });
        });


    }
}