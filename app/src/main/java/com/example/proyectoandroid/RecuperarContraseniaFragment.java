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


public class RecuperarContraseniaFragment extends Fragment {

    private FragmentRecuperarContraseniaBinding binding;
    private NavController navController;
    private AppViewModel appViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recuperar_contrasenia, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        navController = Navigation.findNavController(view);

        binding.botonCambiarContrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.introducirUsuario.getText().toString();
                String email = binding.introducirEmail.getText().toString();
                String password = binding.botonCambiarContrasenia.getText().toString();
                appViewModel.actualizarPassword(username,email,password);
            }
        });

        appViewModel.estadoUsuarioContrasenia.observe(getViewLifecycleOwner(), new Observer<AppViewModel.EstadoDelCambioDePassword>() {
            @Override
            public void onChanged(AppViewModel.EstadoDelCambioDePassword estadoDelCambioDePassword) {
                switch (estadoDelCambioDePassword){
                    case NOMBRE_INCORRECTO:
                        Toast.makeText(getContext(), "NOMBRE DE USUARIO NO VALIDO", Toast.LENGTH_SHORT).show();
                        break;
                    case CONTRASENIA_INCORRECTA:
                        Toast.makeText(getContext(), "CONTRASEÑA NO VALIDA O VACÍA", Toast.LENGTH_SHORT).show();
                        break;
                    case EMAIL_INCORRECTO:
                        Toast.makeText(getContext(), "EMAIL INCORRECTO", Toast.LENGTH_SHORT).show();
                        break;
                    case EMAIL_USUARIO_INCORRECTO:
                        Toast.makeText(getContext(), "USUARIO Y CONTRASEÑA NO COINCIDEN", Toast.LENGTH_SHORT).show();
                        break;
                    case CONTRASENIA_CAMBIADA:
                        Toast.makeText(getContext(), "CONTRASEÑA CAMBIADA CON ÉXITO", Toast.LENGTH_SHORT).show();
                        //navController.navigate(R.id.action_cuentaCreada_to_loginFragment);
                        break;
                }
            }
        });
    }
}