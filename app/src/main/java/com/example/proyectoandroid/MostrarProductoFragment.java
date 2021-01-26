package com.example.proyectoandroid;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoandroid.databinding.FragmentMostrarProductoBinding;
import com.example.proyectoandroid.model.ProductoFavorito;
import com.example.proyectoandroid.modelLogin.Usuario;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import static android.widget.Toast.LENGTH_SHORT;


public class MostrarProductoFragment extends BaseFragment {
    private FragmentMostrarProductoBinding binding;
    private Usuario usuario;
    private int userId;
    private boolean favorito;
    String selected, spinner_item;
    private int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return (binding = FragmentMostrarProductoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        if(comprobarFavorito()){
//            binding.imagen.setImageResource(R.drawable.favoritostodorojo);
//        }
//        else binding.imagen.setImageResource(R.drawable.logofavoritosprueba);
//            //Preguntar a Gerard porque no me funciona esto y el de debajo si
//       /* appViewModel.usuarioAutenticado.observe(getViewLifecycleOwner(), usuario -> {
//            userId = usuario.id;
//        });
//
//        */

        usuario = appViewModel.usuarioAutenticado.getValue();
        userId = usuario.id;
        //System.out.println("para");

        appViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<ProductoFavorito>() {

            @Override
            public void onChanged(ProductoFavorito productoFavorito) {
                binding.imagen1.setImageResource(productoFavorito.imagenes.get(1));
                binding.imagen2.setImageResource(productoFavorito.imagenes.get(2));
                binding.imagen3.setImageResource(productoFavorito.imagenes.get(3));
                binding.imagenabrigodani.setImageResource(productoFavorito.imagenes.get(1));
                binding.nombreProducto.setText(productoFavorito.nombre);
                id = productoFavorito.id;
                binding.favorito.setChecked(productoFavorito.esFavorito);
                //descometnar luego
                binding.comprar.setOnClickListener(v -> {
                    appViewModel.insertarCarritoUpdate(userId, productoFavorito.id);

                });
                binding.favorito.setOnClickListener(v -> {
                    appViewModel.invertirFavorito(userId, productoFavorito.id);
                });
                binding.textOpiniones.setOnClickListener(v -> {
                    navController.navigate(R.id.action_global_opinionesFragment);
                });

                //descomentar luego
                MaterialSpinner spinner = (MaterialSpinner) binding.spinner;
                spinner.setItems("Selecciona tu talla ", "S", "M", "L", "XL Actualmente no disponible");
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        if (position == 0 ) {
                            Snackbar.make(view, "Debes de seleccionar una talla valida", Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(view, "Talla " + item, Snackbar.LENGTH_LONG).show();
                            if(position==4){
                                //navController.navigate(R.id.action_global_bandeja_notificaciones);
                            }
                        }
                    }

                    public void onNothingSelected(MaterialSpinner spinner) {
                        //Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                    }

                });


            }


        });

    }


}
