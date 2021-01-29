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

import com.example.proyectoandroid.databinding.FragmentMetodoPagoBinding;
import com.example.proyectoandroid.databinding.FragmentPagarBinding;

public class PagarFragment extends BaseFragment {
    FragmentPagarBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentPagarBinding.inflate(inflater, container, false)).getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.volverDireccion.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_metodoPagoFragment);
        });
        MutableLiveData<Boolean> finishedLoading = new MutableLiveData<>();


        finishedLoading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                navController.navigate(R.id.action_global_bottom_explorar_fragment2);
            }
        });



        binding.botonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numeroTarjeta = binding.editTextTarjeta.getText().toString();
                String fechaCaducidad = binding.editCaducidad.getText().toString();
                String codigoSeguridad = binding.editTextCodigoSEGURIDAD.getText().toString();
                String nombre = binding.editTextNombre.getText().toString();

                boolean error = false;


                if (numeroTarjeta.isEmpty()) {
                    binding.editTextTarjeta.setError("Este campo no puede estar vacío ");
                    error = true;
                }

                if (fechaCaducidad.isEmpty()) {
                    binding.editCaducidad.setError("Este campo no puede estar vacío ");
                    error = true;
                }

                if (codigoSeguridad.isEmpty()) {
                    binding.editTextCodigoSEGURIDAD.setError("Este campo no puede estar vacío ");
                    error = true;
                }

                if (nombre.isEmpty()) {
                    binding.editTextNombre.setError("Este campo no puede estar vacío ");
                    error = true;
                }

                if (!error) {
                    navController.navigate(R.id.action_global_pagoCorrectoFragment);
                }
            }
        });
    }
}