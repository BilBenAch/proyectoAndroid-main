package com.example.proyectoandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectoandroid.databinding.FragmentAnadirProductoBinding;
import com.example.proyectoandroid.model.Producto;

import java.util.Arrays;
import java.util.List;

public class AnadirProductoFragment extends Fragment {
    private FragmentAnadirProductoBinding binding;
    private AppViewModel appViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentAnadirProductoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        List<Producto> productos = Arrays.asList(
                new Producto("abrigo marron", Arrays.asList(R.drawable.abrigo1, R.drawable.abrigo2,R.drawable.abrigo3, R.drawable.abrigo4)),
                new Producto("abrigo gris", Arrays.asList(R.drawable.abrigo1, R.drawable.abrigo2,R.drawable.abrigo3, R.drawable.abrigo4)),
                new Producto("abrigo verde", Arrays.asList(R.drawable.abrigo1, R.drawable.abrigo2,R.drawable.abrigo3, R.drawable.abrigo4)),
                new Producto("abrigo rojo", Arrays.asList(R.drawable.abrigo1, R.drawable.abrigo2,R.drawable.abrigo3, R.drawable.abrigo4)),
                new Producto("chandal marron", Arrays.asList(R.drawable.abrigo1, R.drawable.abrigo2,R.drawable.abrigo3, R.drawable.abrigo4)),
                new Producto("camiseta marron", Arrays.asList(R.drawable.abrigo1, R.drawable.abrigo2,R.drawable.abrigo3, R.drawable.abrigo4))

        );
        appViewModel.insertar(productos);
    }
}