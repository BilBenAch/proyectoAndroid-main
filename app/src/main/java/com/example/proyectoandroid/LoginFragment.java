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

import com.example.proyectoandroid.databinding.FragmentLoginBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.proyectoandroid.AppViewModel.EstadoDelRegistro.INICIO_DEL_REGISTRO;
import static com.example.proyectoandroid.AppViewModel.EstadoDelRegistro.NOMBRE_NO_DISPONIBLE;
import static com.example.proyectoandroid.AppViewModel.EstadoDelRegistro.REGISTRO_COMPLETADO;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private NavController navController;
    private AppViewModel appViewModel;
    //private TextView textView = binding.noEresMiembroRegsitrate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return (binding = FragmentLoginBinding.inflate(inflater, container, false)).getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //appViewModel.noAutenticado();
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        navController = Navigation.findNavController(view);
        binding.noEresMiembroRegsitrate.setOnClickListener(v -> {
            //no hace nada
            //appViewModel.usuarioRegistrado.postValue(NOMBRE_NO_DISPONIBLE);
            navController.navigate(R.id.action_loginFragment_to_registrarse);
        });

        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation);
        navBar.setVisibility(View.GONE);

        binding.botonInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.editTextNombreUsuario.getText().toString();
                String password = binding.editPassword.getText().toString();
                appViewModel.iniciarSesion(username, password);
            }
        });
        binding.botonInicioSesionGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.editTextNombreUsuario.getText().toString();
                String password = binding.editPassword.getText().toString();
                appViewModel.iniciarSesion(username, password);
            }
        });
        binding.botonInicioSesionFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.editTextNombreUsuario.getText().toString();
                String password = binding.editPassword.getText().toString();
                appViewModel.iniciarSesion(username, password);
            }
        });

        binding.textoHasOlvidadoLaContrasenya.setOnClickListener(v -> {
           // appViewModel.estadoDeLaAutenticacion.postValue(AppViewModel.EstadoDeLaAutenticacion.AUTENTICADO);
            navController.navigate(R.id.action_global_recuperarContraseniaEmail);
        });



                appViewModel.estadoDeLaAutenticacion.observe(getViewLifecycleOwner(), new Observer<AppViewModel.EstadoDeLaAutenticacion>() {
            @Override
            public void onChanged(AppViewModel.EstadoDeLaAutenticacion estadoDeLaAutenticacion) {
                switch (estadoDeLaAutenticacion) {
                    case AUTENTICADO:
                        navController.navigate(R.id.action_loginFragment_to_bottom_home_fragment2);
                        navBar.setVisibility(View.VISIBLE);
                        break;

                    case AUTENTICACION_INVALIDA:
                        Toast.makeText(getContext(), "CREDENCIALES NO VALIDAS SI NO TIENES UNA CUENTA REGISTRATE", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

}
