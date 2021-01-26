package com.example.proyectoandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoandroid.databinding.FragmentBandejaNotificacionesBinding;
import com.example.proyectoandroid.databinding.FragmentSearchViewBinding;

public class BandejaNotificacionesFragment extends BaseFragment {
    FragmentBandejaNotificacionesBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // return (binding = FragmentSearchViewBinding.inflate(inflater, container, false)).getRoot();
        return (binding = FragmentBandejaNotificacionesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.VolverExplorador.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_bottom_explorar_fragment2);
        });
    }
}