package com.example.proyectoandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.proyectoandroid.databinding.FragmentRecuperarContraseniaBinding;


public class RecuperarContraseniaFragment extends BaseFragment {

    private FragmentRecuperarContraseniaBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentRecuperarContraseniaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        usuario = appViewModel.usuarioAutenticado.getValue();
//        assert usuario != null;
//        userId = usuario.id;

//        appViewModel.usuarioAutenticado.observe(getViewLifecycleOwner(), usuario -> {
//            userId = usuario.id;
//        });

        binding.YaEresMiembroIniciaSesion.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_loginFragment);
        });


        //preguntar a Gerard como hacer con callbacks
        binding.botonCambiarContrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreUsuario = binding.introducirUsuario.getText().toString();
                String emailUsuario = binding.introducirEmail.getText().toString();
                String nuevaContra = binding.introducirContrasenia.getText().toString();
//                    appViewModel.actualizarPassword(nombreUsuario, emailUsuario, nuevaContra);
                boolean error = false;


                if (nombreUsuario.isEmpty()) {
                    binding.introducirUsuario.setError("Este campo no puede estar vacío ");
                    error = true;
                }
                if (emailUsuario.isEmpty()) {
                    binding.introducirEmail.setError("Este campo no puede estar vacío ");
                    error = true;
                }
                if (nuevaContra.isEmpty()) {
                    binding.introducirContrasenia.setError("Este campo no puede estar vacío ");
                    error = true;
                }


                if (!error) {
                    appViewModel.actualizarPassword(nombreUsuario, emailUsuario, nuevaContra);
                    //navController.navigate(R.id.action_global_contraseniaCambiadaLoginFragment);
                }
            }
        });

        appViewModel.estadoUsuarioContrasenia.observe(getViewLifecycleOwner(), new Observer<AppViewModel.EstadoDelCambioDePassword>() {
            @Override
            public void onChanged(AppViewModel.EstadoDelCambioDePassword estadoDelCambioDePassword) {
                switch (estadoDelCambioDePassword) {
                    case CONTRASENIA_CAMBIADA:
                        navController.navigate(R.id.action_global_contraseniaCambiadaLoginFragment);

                        break;

                    case USUARIO_INCORRECTO:
                        Toast.makeText(getContext(), "No existe este usuario en el sistema comprueba los datos", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }
}