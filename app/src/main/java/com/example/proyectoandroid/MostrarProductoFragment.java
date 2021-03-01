package com.example.proyectoandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.proyectoandroid.databinding.FragmentMostrarProductoBinding;
import com.example.proyectoandroid.model.ProductoFavorito;
import com.example.proyectoandroid.model.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;


public class MostrarProductoFragment extends BaseFragment {
    private FragmentMostrarProductoBinding binding;
    private Usuario usuario;
    private int userId;
    private Integer fav;
    private boolean comprobar = false;
    String selected, spinner_item;
    private int id;
    Snackbar snackbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return (binding = FragmentMostrarProductoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation);
        //preguntar a Gerard si es lo mismo ponerlo asi o no
        usuario = appViewModel.usuarioAutenticado.getValue();
        userId = usuario.id;
//        appViewModel.usuarioAutenticado.observe(getViewLifecycleOwner(), usuario -> {
//            userId = usuario.id;
//        });

        binding.buscarProducto.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_bottom_explorar_fragment2);
        });

        appViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<ProductoFavorito>() {

            @Override
            public void onChanged(ProductoFavorito productoFavorito) {
                binding.imagen1.setImageResource(productoFavorito.imagenes.get(0));
                binding.imagen2.setImageResource(productoFavorito.imagenes.get(1));
                binding.imagen3.setImageResource(productoFavorito.imagenes.get(2));
                binding.imagenabrigodani.setImageResource(productoFavorito.imagenes.get(0));
                binding.nombreProducto.setText(productoFavorito.nombre);
                binding.PrecioProducto.setText(String.valueOf(productoFavorito.precioProducto) + "€");
                binding.tipoProducto.setText(productoFavorito.tipoProducto);
                binding.ColorProducto.setText(productoFavorito.colorProducto);

                id = productoFavorito.id;
                binding.favorito.setChecked(productoFavorito.esFavorito);

                appViewModel.isFavorite(userId, productoFavorito.id).observe(getViewLifecycleOwner(), integer3 -> {
                    fav = integer3;

                    binding.favorito.setOnClickListener(v -> {
                        appViewModel.invertirFavorito(userId, productoFavorito.id);

                        if (fav == 1) {
                            Snackbar snackbar = Snackbar.make(view, "Producto quitado de favoritos", Snackbar.LENGTH_SHORT);
                            snackbar.setAnchorView(navBar);
                            snackbar.show();

                        } else {
                            //Snackbar snackbar = Snackbar.make(view, "Producto añadido a favoritos", Snackbar.LENGTH_SHORT);
                            showToast();
                        }

                    });

                });
                binding.textOpiniones.setOnClickListener(v -> {
                    navController.navigate(R.id.action_global_opinionesFragment);
                });

                binding.notificacionesHome.setOnClickListener(v -> {
                    navController.navigate(R.id.action_global_bandeja_notificaciones);
                });

                binding.favoritosHome.setOnClickListener(v -> {
                    navController.navigate(R.id.action_global_bottom_favoritos_fragment2);
                });
                //descomentar luego
                MaterialSpinner spinner = (MaterialSpinner) binding.spinner;
                spinner.setItems("Selecciona tu talla ", "S", "M", "L", "XL Actualmente no disponible");
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        if (position == 0) {
                            Snackbar snackbar = Snackbar.make(view, "Debes de seleccionar una talla valida", Snackbar.LENGTH_SHORT);
                            snackbar.setAnchorView(navBar);
                            snackbar.show();
                        } else {
                            if (position == 4) {
                                //navController.navigate(R.id.action_global_bandeja_notificaciones);
                                Snackbar snackbar = Snackbar.make(view, "Talla " + item, Snackbar.LENGTH_SHORT);
                                snackbar.setAnchorView(navBar);
                                snackbar.show();

                                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                        .setTitle("Actualmente no tenemos stock. ¿Deseas recibir un email notificativo?")
//                .setMessage("Note that nuking planet Jupiter will destroy everything in there.")
                                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                navController.navigate(R.id.action_global_enviarMensajeStockFragment);
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                Window window = alertDialog.getWindow();
                                lp.copyFrom(window.getAttributes());
                                //This makes the dialog take up the full width
                                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                window.setAttributes(lp);
                            } else {
                                comprobar = true;
                                comprobarTalla(comprobar);
                                Snackbar snackbar = Snackbar.make(view, "Talla " + item + " Seleccionada", Snackbar.LENGTH_SHORT);
                                snackbar.setAnchorView(navBar);
                                snackbar.show();
                            }
                        }
                    }

                    public void onNothingSelected(MaterialSpinner spinner) {
                        //Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                    }

                });


                binding.comprar.setOnClickListener(v -> {
                    if (comprobarTalla(comprobar)) {
                        appViewModel.insertarCarritoUpdate(userId, productoFavorito.id);
                        Snackbar snackbar = Snackbar.make(view, "Producto añadido a la cesta", Snackbar.LENGTH_SHORT);
                        snackbar.setAnchorView(navBar);
                        snackbar.show();
                    } else {
                        Snackbar snackbar = Snackbar.make(view, "Debes de seleccionar una talla correcta", Snackbar.LENGTH_SHORT);
                        snackbar.setAnchorView(navBar);
                        snackbar.show();
                    }
                });
            }


        });


    }

    public boolean comprobarTalla(boolean comprobar) {

        return comprobar;
    }


}
