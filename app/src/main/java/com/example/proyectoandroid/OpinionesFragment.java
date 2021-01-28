package com.example.proyectoandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoandroid.databinding.FragmentOpinionesBinding;
import com.example.proyectoandroid.model.ProductoFavorito;
import com.example.proyectoandroid.modelLogin.Usuario;


 public class OpinionesFragment extends BaseFragment {
    private FragmentOpinionesBinding binding;
    private Usuario usuario;
    private int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return (binding = FragmentOpinionesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usuario = appViewModel.usuarioAutenticado.getValue();
        userId = usuario.id;


        appViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<ProductoFavorito>() {

            @Override
            public void onChanged(ProductoFavorito productoFavorito) {
                binding.primeraImagenProducto.setImageResource(productoFavorito.imagenes.get(0));
                binding.segundoImagenProducto.setImageResource(productoFavorito.imagenes.get(1));
                binding.terceraImagenProducto.setImageResource(productoFavorito.imagenes.get(2));

                binding.volver.setOnClickListener(v -> {
                    navController.navigate(R.id.action_global_bottom_explorar_fragment2);
                });
            }


        });
    }
}