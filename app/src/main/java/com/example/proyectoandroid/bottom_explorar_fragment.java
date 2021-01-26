package com.example.proyectoandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyectoandroid.databinding.FragmentBottomExplorarBinding;


public class bottom_explorar_fragment extends BaseFragment {
    FragmentBottomExplorarBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentBottomExplorarBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //redireccion productos hombres
        binding.imagenComprarAbrigos.setOnClickListener(v -> {
            appViewModel.buscar("abrigo");
            navController.navigate(R.id.action_global_productosFragment);
        });
        binding.imagenComprarComplementosHombre.setOnClickListener(v -> {
            appViewModel.buscar("complemento");
            navController.navigate(R.id.action_global_productosFragment);
        });
        binding.imagenComprarCamisetaHombre.setOnClickListener(v -> {
            appViewModel.buscar("camiseta hombre");
            navController.navigate(R.id.action_global_productosFragment);
        });
        binding.imagenCalzadoHombre.setOnClickListener(v -> {
            appViewModel.buscar("calzado hombre");
            navController.navigate(R.id.action_global_productosFragment);
        });
        binding.imagenPantalonesHombre.setOnClickListener(v -> {
            appViewModel.buscar("pantalon hombre");
            navController.navigate(R.id.action_global_productosFragment);
        });
        binding.imagenComprarRopaInterior.setOnClickListener(v -> {
            appViewModel.buscar("ropa interior hombre");
            navController.navigate(R.id.action_global_productosFragment);
        });
        binding.imagenComprarVestido.setOnClickListener(v -> {
            appViewModel.buscar("vestido");
            navController.navigate(R.id.action_global_productosFragment);
        });
        binding.imagenCamisetasMujer.setOnClickListener(v -> {
            appViewModel.buscar("camiseta mujer");
            navController.navigate(R.id.action_global_productosFragment);
        });
        binding.imagenPantalonesMujer.setOnClickListener(v -> {
            appViewModel.buscar("pantalon mujer");
            navController.navigate(R.id.action_global_productosFragment);
        });
        binding.imagenFaldasMujer.setOnClickListener(v -> {
            appViewModel.buscar("falda");
            navController.navigate(R.id.action_global_productosFragment);
        });
        binding.imagenBolsos.setOnClickListener(v -> {
            appViewModel.buscar("bolso");
            navController.navigate(R.id.action_global_productosFragment);
        });
        binding.imagenCalzadoMujer.setOnClickListener(v -> {
            appViewModel.buscar("calzado mujer");
            navController.navigate(R.id.action_global_productosFragment);
        });
        binding.imagenRopaInteriorMujer.setOnClickListener(v -> {
            appViewModel.buscar("ropa interior mujer");
            navController.navigate(R.id.action_global_productosFragment);
        });


        binding.notificacionesHome.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_bandeja_notificaciones);
        });
        binding.favoritosHome.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_bottom_favoritos_fragment2);
        });
        binding.buscarProducto.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_searchView);
        });
    }
}