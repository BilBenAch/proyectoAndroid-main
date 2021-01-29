package com.example.proyectoandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoandroid.databinding.FragmentBottomFavoritosBinding;
import com.example.proyectoandroid.databinding.FragmentMetodoPagoBinding;


public class MetodoPagoFragment extends BaseFragment {
    FragmentMetodoPagoBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentMetodoPagoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.volverDireccion.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_direccionEnvioFragment);
        });
        binding.segundoMicuenta.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_pagarFragment);
        });

        binding.tercero.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_pagarFragment);
        });

        binding.cuarto.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_pagarFragment);
        });
    }
}