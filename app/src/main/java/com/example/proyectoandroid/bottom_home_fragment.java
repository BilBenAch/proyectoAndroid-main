package com.example.proyectoandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.proyectoandroid.databinding.FragmentBottomHomeBinding;

public class bottom_home_fragment extends Fragment {
    private FragmentBottomHomeBinding binding;
    private NavController navController;
    AppViewModel appViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentBottomHomeBinding.inflate(inflater, container, false)).getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        navController = Navigation.findNavController(view);
        binding.botonComprarAbrigoHombre.setOnClickListener(v -> {
            appViewModel.buscar("abrigo hombre");
            navController.navigate(R.id.action_global_productosFragment);

        });
        binding.botonComprarAbrigoMujer.setOnClickListener(v -> {
            appViewModel.buscar("vestido");
            navController.navigate(R.id.action_global_productosFragment);

        });

        binding.buscarProducto.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_searchView);
        });

        binding.notificacionesHome.setOnClickListener(v -> {
            //navController.navigate(R.id.action_global_bandeja_notificaciones);
            navController.navigate(R.id.action_global_direccionEnvioFragment);
        });

        binding.favoritosHome.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_bottom_favoritos_fragment2);
        });
        binding.botonComprarHombre.setOnClickListener(v -> {
            appViewModel.buscar("hombre");
            navController.navigate(R.id.action_global_productosFragment);
        });
        binding.botonComprarMujer.setOnClickListener(v -> {
            appViewModel.buscar("mujer");
            navController.navigate(R.id.action_global_productosFragment);
        });
    }
}