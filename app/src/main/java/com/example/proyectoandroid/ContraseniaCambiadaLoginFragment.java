package com.example.proyectoandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoandroid.databinding.FragmentContraseniaCambiadaLoginBinding;
import com.example.proyectoandroid.databinding.FragmentRecuperarContraseniaBinding;

import static com.example.proyectoandroid.AppViewModel.EstadoDelRegistro.REGISTRO_COMPLETADO;


public class ContraseniaCambiadaLoginFragment extends BaseFragment {
    FragmentContraseniaCambiadaLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentContraseniaCambiadaLoginBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        MutableLiveData<Boolean> finishedLoading = new MutableLiveData<>();


        finishedLoading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                navController.navigate(R.id.action_global_loginFragment);
                appViewModel.estadoUsuarioContrasenia.postValue(AppViewModel.EstadoDelCambioDePassword.INICIO_DEL_CAMBIO);

            }
        });

        // esto deberia estar en el Model y llamarlo a traves del ViewModel
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // simular la carga de recursos
                    Thread.sleep(1000);
                    finishedLoading.postValue(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}