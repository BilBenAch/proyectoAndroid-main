package com.example.proyectoandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoandroid.databinding.FragmentDetallaesPedidoBinding;
import com.example.proyectoandroid.databinding.FragmentPedidoBinding;

public class DetallaesPedidoFragment extends BaseFragment {
    FragmentDetallaesPedidoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentDetallaesPedidoBinding.inflate(inflater, container, false)).getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.volverMiCuenta.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_pedidoFragment);
        });
    }
}