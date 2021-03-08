package com.example.proyectoandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoandroid.databinding.FragmentDireccionEnvioBinding;
import com.example.proyectoandroid.databinding.FragmentSearchViewBinding;
import com.example.proyectoandroid.databinding.ViewholderDireccionBinding;
import com.example.proyectoandroid.databinding.ViewholderProductoFavoritosBinding;
import com.example.proyectoandroid.model.Direccion;
import com.example.proyectoandroid.model.Producto;

import java.util.List;


public class DireccionEnvioFragment extends BaseFragment {
    FragmentDireccionEnvioBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentDireccionEnvioBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DireccionAdapter direccionAdapter = new DireccionAdapter();
        binding.listaDirecciones.setAdapter(direccionAdapter);

        appViewModel.usuarioAutenticado.observe(getViewLifecycleOwner(), usuario -> {
            userId = usuario.id;

            appViewModel.obtenerDirecciones(usuario.id).observe(getViewLifecycleOwner(), direccionList -> {
                if (direccionList == null || direccionList.size() == 0) {
                    // Log.e("ZERO", "RESULTS");
                    binding.listaDirecciones.setVisibility(View.GONE);
                    binding.zeroresults.setVisibility(View.VISIBLE);
                    binding.botonPagar.setVisibility(View.GONE);
                    binding.anadirDireccion.setOnClickListener(v -> {
                        navController.navigate(R.id.action_global_insertarDireccionFragment);
                    });
                    //binding..setVisibility(View.GONE);
                } else {
                    binding.listaDirecciones.setVisibility(View.VISIBLE);
                    binding.botonPagar.setVisibility(View.VISIBLE);
                    binding.botonPagar.setOnClickListener(v -> {
                        navController.navigate(R.id.action_global_metodoPagoFragment);
                    });
                    binding.zeroresults.setVisibility(View.GONE);
                }

                direccionAdapter.establecerDireccionList(direccionList);
            });
        });
        binding.volverPerfil.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_bottom_carrito_fragment);
        });

    }

    class DireccionAdapter extends RecyclerView.Adapter<DireccionesViewHolder> {
        List<Direccion> direcciones;

        @NonNull
        @Override
        public DireccionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DireccionesViewHolder(ViewholderDireccionBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull DireccionesViewHolder holder, int position) {
            Direccion direccion = direcciones.get(position);

            appViewModel.obtenerUsuario(userId).observe(getViewLifecycleOwner(), usuario -> {
                holder.binding.nombre.setText(usuario.getUsername());
            });
            holder.binding.direccion.setText(direccion.direccion);

            holder.binding.numeroTelefono.setText(direccion.telefono);

            holder.binding.editarDireccion.setOnClickListener(v -> {
                navController.navigate(R.id.action_global_insertarDireccionFragment);
            });
            holder.binding.desechar.setOnClickListener(v -> {
                new AlertDialog.Builder(getContext())
                        .setTitle("¿Seguro que deseas eliminar la dirección?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Log.d("MainActivity", "Sending atomic bombs to Jupiter");
                                appViewModel.eliminarDireccion(userId, direccion.direccion, direccion.telefono);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            });

        }

        @Override
        public int getItemCount() {
            return direcciones == null ? 0 : direcciones.size();
        }

        void establecerDireccionList(List<Direccion> direcciones) {
            this.direcciones = direcciones;
            notifyDataSetChanged();
        }

        public Direccion obtenerDirecciones(int posicion) {

            return direcciones.get(posicion);
        }
    }


    class DireccionesViewHolder extends RecyclerView.ViewHolder {
        ViewholderDireccionBinding binding;

        public DireccionesViewHolder(@NonNull ViewholderDireccionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}