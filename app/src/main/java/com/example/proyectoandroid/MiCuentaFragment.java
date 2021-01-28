package com.example.proyectoandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoandroid.databinding.FragmentBottomPerfilBinding;
import com.example.proyectoandroid.databinding.FragmentMiCuentaBinding;
import com.example.proyectoandroid.modelLogin.Usuario;

public class MiCuentaFragment extends BaseFragment {
    FragmentMiCuentaBinding binding;
    Usuario usuario;
    private int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentMiCuentaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usuario = appViewModel.usuarioAutenticado.getValue();
        userId = usuario.id;

        appViewModel.obtenerUsuario(userId).observe(getViewLifecycleOwner(), usuario -> {


            //usuario = appViewModel.usuarioAutenticado.getValue();
            //userId = usuario.id;
            binding.nombre.setText(String.valueOf(usuario.nombre));
            binding.apellido.setText(String.valueOf(usuario.apellido));
            binding.segundoNombre.setText(String.valueOf(usuario.username));
            binding.terceroMiEmail.setText(String.valueOf(usuario.email));
            binding.cuartoTelefono.setText(String.valueOf(usuario.numeroTelefono));
            binding.sextoCambiarContra.setText(String.valueOf(usuario.password));

        });

        binding.volverPerfil.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_bottom_perfil_fragment);
        });

        binding.segundoMicuenta.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_micuentaCamioDeNombreFragment);
        });


        //queda el del cumple
        /*binding.terceroMicuenta.setOnClickListener(v -> {
            navController.navigate(R.id.cambiar);
        });
         */
        binding.cuartoMicuenta.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_miCuentaEmailFragment);
        });

        binding.quintoMicuenta.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_cambiarTelefonoFragment);
        });

        binding.sextoMicuenta.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_miCuentaCambiarContraseniaFragment);
        });

    }
}