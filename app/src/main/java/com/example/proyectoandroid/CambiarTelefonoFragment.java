package com.example.proyectoandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoandroid.databinding.FragmentCambiarTelefonoBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CambiarTelefonoFragment extends BaseFragment {
    FragmentCambiarTelefonoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return (binding = FragmentCambiarTelefonoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usuario = appViewModel.usuarioAutenticado.getValue();
        userId = usuario.id;
        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation);
        appViewModel.obtenerUsuario(userId).observe(getViewLifecycleOwner(), usuario -> {

            binding.volverMiCuenta.setOnClickListener(v -> {
                navController.navigate(R.id.action_global_miCuentaFragment);
            });

            //preguntar a Gerard como hacer con callbacks
            binding.botonGuardarNumero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String telefono = binding.editTextTelefono.getText().toString();
                    Pattern p = Pattern.compile("([0-9])");
                    Matcher m = p.matcher(telefono);

                    if(m.find()){
                        System.out.println("Hello "+m.find());
                    }
                    boolean error = false;


                    if (telefono.isEmpty()) {
                        binding.editTextTelefono.setError("El campo telefono no puede estar vacío ");
                        error = true;
                    }
                    if (!m.find()) {
                        binding.editTextTelefono.setError("El campo telefono no puede contener letras ");
                        error = true;
                    }

                    if (!error) {
                        appViewModel.cambiarTelefono(telefono, userId);
                        showToastDireccion("Telefono cambiado correctamente");
                    }
                }
            });
        });
    }
}