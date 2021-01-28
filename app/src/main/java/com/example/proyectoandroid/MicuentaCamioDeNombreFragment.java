package com.example.proyectoandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoandroid.databinding.FragmentMiCuentaBinding;
import com.example.proyectoandroid.databinding.FragmentMicuentaCambioDeNombreBinding;
import com.example.proyectoandroid.modelLogin.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MicuentaCamioDeNombreFragment extends BaseFragment {
    FragmentMicuentaCambioDeNombreBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentMicuentaCambioDeNombreBinding.inflate(inflater, container, false)).getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation);
        usuario = appViewModel.usuarioAutenticado.getValue();
        userId = usuario.id;

        appViewModel.obtenerUsuario(userId).observe(getViewLifecycleOwner(), usuario -> {
            binding.nombre.setText(String.valueOf(usuario.nombre));
            binding.apellido.setText(String.valueOf(usuario.apellido));


            binding.volverMiCuenta.setOnClickListener(v -> {
                navController.navigate(R.id.action_global_miCuentaFragment);
            });

            //preguntar a Gerard como hacer con callbacks
            binding.botonGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nombre = binding.editTextNombre.getText().toString();
                    String apellido = binding.editTextApellido.getText().toString();

                    boolean error = false;


                    if (nombre.isEmpty()) {
                        binding.editTextNombre.setError("El campo nombre no puede estar vacío ");
                        error = true;
                    }
                    if (apellido.isEmpty()) {
                        binding.editTextApellido.setError("El campo apellido no puede estar vacío ");
                        error = true;
                    }

                    if (!error) {
                        appViewModel.cambiarNombreApellido(nombre, apellido, userId);
                        Snackbar snackbar = Snackbar.make(view, "Nombre y apellidos cambiados correctamente", Snackbar.LENGTH_SHORT);
                        snackbar.setAnchorView(navBar);
                        snackbar.show();
                    }
                }
            });
        });

    }
}