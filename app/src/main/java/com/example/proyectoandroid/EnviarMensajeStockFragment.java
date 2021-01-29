package com.example.proyectoandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoandroid.databinding.FragmentEnviarMensajeStockBinding;
import com.example.proyectoandroid.databinding.FragmentMostrarProductoBinding;

public class EnviarMensajeStockFragment extends BaseFragment {
    FragmentEnviarMensajeStockBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentEnviarMensajeStockBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MutableLiveData<Boolean> finishedLoading = new MutableLiveData<>();


        finishedLoading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
               navController.navigate(R.id.action_global_bottom_explorar_fragment2);

            }
        });

        binding.botonEnviarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.crearCuentaNombreUsuario.getText().toString();

                boolean error = false;


                if (email.isEmpty()) {
                    binding.crearCuentaNombreUsuario.setError("El campo nombre no puede estar vacío ");
                    error = true;
                }

                if (!error) {
                    showToastDireccion("Email enviado correctamente");
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // simular la carga de recursos
                                Thread.sleep(1800);
                                finishedLoading.postValue(true);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            }
        });
    }
}