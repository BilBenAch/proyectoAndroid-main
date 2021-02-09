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
import android.widget.Switch;

import com.example.proyectoandroid.databinding.FragmentDireccionEnvioBinding;
import com.example.proyectoandroid.databinding.FragmentInsertarDireccionBinding;
import com.google.android.material.snackbar.Snackbar;

public class InsertarDireccionFragment extends BaseFragment {
    FragmentInsertarDireccionBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return (binding = FragmentInsertarDireccionBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usuario = appViewModel.usuarioAutenticado.getValue();
        userId = usuario.id;

        appViewModel.obtenerDirecciones(userId).observe(getViewLifecycleOwner(), direccionList -> {

            binding.volverCarrito.setOnClickListener(v -> {
                navController.navigate(R.id.action_global_direccionEnvioFragment);
            });

            //preguntar a Gerard como hacer con callbacks
            binding.botonGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String direccion = binding.editTextDireccion.getText().toString();
                    String telefono = binding.editTextTelefono.getText().toString();

                    boolean error = false;


                    if (direccion.isEmpty()) {
                        binding.editTextDireccion.setError("El campo direccion no puede estar vacio ");
                        error = true;
                    }
                    if (telefono.isEmpty()) {
                        binding.editTextTelefono.setError("El campo telefono no puede estar vacío");
                        error = true;
                    }

                    if (!error) {
                        appViewModel.insertUpdateDireccion(userId, direccion, telefono);
                        showToastDireccion("Direccion guardada correctamente");


                        MutableLiveData<Boolean> finishedLoading = new MutableLiveData<>();


                        finishedLoading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean aBoolean) {
                                navController.navigate(R.id.action_global_direccionEnvioFragment);
                            }
                        });

                        // esto deberia estar en el Model y llamarlo a traves del ViewModel

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
        });

    }
}