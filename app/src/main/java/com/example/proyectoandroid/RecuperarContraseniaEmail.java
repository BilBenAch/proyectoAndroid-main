package com.example.proyectoandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoandroid.databinding.FragmentRecuperarContraseniaEmailBinding;

public class RecuperarContraseniaEmail extends BaseFragment {
    FragmentRecuperarContraseniaEmailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentRecuperarContraseniaEmailBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.YaEresMiembroIniciaSesion.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_loginFragment);
        });

        binding.botonEnviarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.crearCuentaNombreUsuario.getText().toString();

                boolean error = false;


                if (email.isEmpty()) {
                    binding.crearCuentaNombreUsuario.setError("Este campo no puede estar vacío");
                    error = true;
                }


                if (!error) {
                    navController.navigate(R.id.action_global_mensajeEmailEnviadoFragment);
                }
            }
        });

    }
}