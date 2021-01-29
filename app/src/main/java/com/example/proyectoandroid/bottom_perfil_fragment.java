package com.example.proyectoandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyectoandroid.databinding.FragmentBottomPerfilBinding;
import com.example.proyectoandroid.databinding.FragmentSearchViewBinding;

public class bottom_perfil_fragment extends BaseFragment {
    FragmentBottomPerfilBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentBottomPerfilBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.miCuenta.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_miCuentaFragment);
        });
        binding.misNotificaciones.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_bandeja_notificaciones);
        });

        binding.segundo.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_pedidoFragment);
        });
        binding.cuarto.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_anadirProductoFragment);
            //anadir etiquetas hombre o mujer
        });
    }
}