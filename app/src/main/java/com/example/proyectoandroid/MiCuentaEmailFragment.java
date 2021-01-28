package com.example.proyectoandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoandroid.databinding.FragmentMiCuentaEmailBinding;
import com.example.proyectoandroid.databinding.FragmentMicuentaCambioDeNombreBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;


public class MiCuentaEmailFragment extends BaseFragment {
    FragmentMiCuentaEmailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentMiCuentaEmailBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation);
        binding.volverMiCuenta.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_miCuentaFragment);
        });
        usuario = appViewModel.usuarioAutenticado.getValue();
        userId = usuario.id;

        binding.botonGuardarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editTextEmail.getText().toString();

                boolean error = false;


                if (email.isEmpty()) {
                    binding.editTextEmail.setError("El campo nombre no puede estar vacío ");
                    error = true;
                }

                if (!error) {
                    appViewModel.cambiarEmail(email, userId);
                    Snackbar snackbar = Snackbar.make(view, "Email cambiado correctamente", Snackbar.LENGTH_SHORT);
                    snackbar.setAnchorView(navBar);
                    snackbar.show();
                }
            }
        });
    }
}