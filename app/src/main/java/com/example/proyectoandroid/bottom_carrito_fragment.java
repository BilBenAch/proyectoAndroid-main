package com.example.proyectoandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proyectoandroid.databinding.FragmentBottomCarritoBinding;
import com.example.proyectoandroid.databinding.ViewholderProductosCarritoBinding;
import com.example.proyectoandroid.model.Favorito;
import com.example.proyectoandroid.model.Producto;
import com.example.proyectoandroid.model.ProductoFavorito;

import java.util.List;


public class bottom_carrito_fragment extends BaseFragment {
    private FragmentBottomCarritoBinding binding;
    //private ProductosViewModel productosViewModel;
    AppViewModel appViewModel;
    private com.example.proyectoandroid.model.productosRepositorio productosRepositorio;
    private int contadorProducto;
    private int userId;
    private int fav;
    Integer precioTotal;
    Integer cantidadIndividual;
    Favorito favorito;

    List<ProductoFavorito> productoFavoritosList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentBottomCarritoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //productosViewModel = new ViewModelProvider(requireActivity()).get(ProductosViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        CarritoAdapter carritoAdaper = new CarritoAdapter();

        binding.listaProductos.setAdapter(carritoAdaper);
        binding.notificacionesHome.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_bandeja_notificaciones);
        });
        binding.favoritosHome.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_bottom_favoritos_fragment2);
        });
        appViewModel.usuarioAutenticado.observe(getViewLifecycleOwner(), usuario -> {
            userId = usuario.id;
            appViewModel.getRowCount(userId).observe(getViewLifecycleOwner(), integer -> {
                if (integer != null) {
                    contadorProducto = integer;

                    appViewModel.obtenerProductosCarrito(usuario.id).observe(getViewLifecycleOwner(), productosList -> {
                        if (productosList == null || productosList.size() == 0) {
                            // Log.e("ZERO", "RESULTS");
                            binding.listaProductos.setVisibility(View.GONE);
                            binding.zeroresults.setVisibility(View.VISIBLE);
                            binding.productosEncontrados.setVisibility(View.GONE);
                            binding.flexboxCarrito.setVisibility(View.VISIBLE);
                            binding.flexboxCarrito.setVisibility(View.GONE);
                            binding.VolverExplorador.setOnClickListener(v ->
                                    navController.navigate(R.id.action_global_bottom_explorar_fragment2));
                        } else {
                            binding.listaProductos.setVisibility(View.VISIBLE);
                            binding.productosEncontrados.setText(productosList.size() + " Productos en carrito");
                            binding.productosEncontrados.setVisibility(View.VISIBLE);
                            binding.zeroresults.setVisibility(View.GONE);
                            binding.cantidadTotal.setText(String.valueOf(contadorProducto));
                        }
                        carritoAdaper.establecerCarritoList(productosList);

                    });
                } else {
                    appViewModel.obtenerProductosCarrito(usuario.id).observe(getViewLifecycleOwner(), productosList -> {
                        if (productosList == null || productosList.size() == 0) {
                            // Log.e("ZERO", "RESULTS");
                            binding.listaProductos.setVisibility(View.GONE);
                            binding.zeroresults.setVisibility(View.VISIBLE);
                            binding.productosEncontrados.setVisibility(View.GONE);
                            binding.flexboxCarrito.setVisibility(View.GONE);
                            binding.VolverExplorador.setOnClickListener(v ->
                                    navController.navigate(R.id.action_global_bottom_explorar_fragment2));
                        } else {
                            binding.listaProductos.setVisibility(View.VISIBLE);
                            binding.productosEncontrados.setText(productosList.size() + " Productos en carrito");
                            binding.productosEncontrados.setVisibility(View.VISIBLE);
                            binding.flexboxCarrito.setVisibility(View.VISIBLE);
                            binding.zeroresults.setVisibility(View.GONE);
                            binding.cantidadTotal.setText(String.valueOf(contadorProducto));
                        }
                        carritoAdaper.establecerCarritoList(productosList);

                    });

                }
            });
        });

    }

    class CarritoAdapter extends RecyclerView.Adapter<CarritoViewHolder> {
        List<Producto> listaProductos;


        @NonNull
        @Override
        public bottom_carrito_fragment.CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new bottom_carrito_fragment.CarritoViewHolder(ViewholderProductosCarritoBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
            Producto producto = listaProductos.get(position);

            holder.binding.nombre.setText(producto.nombre);

            holder.binding.precio.setText(String.valueOf(producto.precioProducto) + " €");
            holder.binding.productoColor.setText(producto.colorProducto);

            Glide.with(holder.itemView).load(producto.imagenes.get(0)).into(holder.binding.imagen);


            //comprobar si es favorito
            appViewModel.isFavorite(userId, producto.id).observe(getViewLifecycleOwner(), integer3 -> {
                fav = integer3;
                if (fav == 1) {
                    holder.binding.favoritosProductos.setImageResource(R.drawable.favoritostodorojo);
                    //Toast.makeText(getContext(), "Producto añadido de favs", Toast.LENGTH_SHORT).show();

                } else {
                    holder.binding.favoritosProductos.setImageResource(R.drawable.logofavoritosprueba);
                    //Toast.makeText(getContext(), "Producto quitado de favs", Toast.LENGTH_SHORT).show();
                }
            });

            holder.binding.favoritosProductos.setOnClickListener(v -> {
                //comprobar si es favorito
                appViewModel.invertirFavorito(userId, producto.id);
                appViewModel.isFavorite(userId, producto.id).observe(getViewLifecycleOwner(), integer5 -> {
                    fav = integer5;
                    if (fav == 1) {
                        showToast();
                    }
                    else {
                        Toast.makeText(getContext(), "Producto quitado de favs", Toast.LENGTH_SHORT).show();
                    }
                });
            });

            holder.binding.botonDecrementar.setOnClickListener(v -> {
                appViewModel.eliminarCarritoUpdate(userId, producto.id);

            });


            holder.binding.botonIncrementar.setOnClickListener(v -> {
                appViewModel.incrementarCarrito(userId, producto.id);


            });

//            holder.binding.eliminarProducto.setOnClickListener(v -> {
//                appViewModel.eliminarDelCarrito(userId, producto.id);
//            });

            appViewModel.getincremento(userId, producto.id).observe(getViewLifecycleOwner(), integer2 -> {
                cantidadIndividual = integer2;
                holder.binding.cantidad.setText(String.valueOf(cantidadIndividual));
            });

            appViewModel.precioTotal(userId).observe(getViewLifecycleOwner(), integer4 -> {
                precioTotal = integer4;
                binding.precioTotal.setText(String.valueOf(precioTotal) + " €");
                binding.cantidadPrecioTotal.setText(String.valueOf(precioTotal) + " €");
            });

            binding.botonPagar.setOnClickListener(v -> {
                navController.navigate(R.id.action_global_direccionEnvioFragment);
            });


            holder.binding.eliminarProducto.setOnClickListener(v -> {
                new AlertDialog.Builder(getContext())
                        .setTitle("¿Estás seguro que desas eliminar el producto del carrito?")
//                .setMessage("Note that nuking planet Jupiter will destroy everything in there.")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                appViewModel.eliminarDelCarrito(userId, producto.id);
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
            return listaProductos == null ? 0 : listaProductos.size();
        }

        void establecerCarritoList(List<Producto> producto) {
            this.listaProductos = producto;
            notifyDataSetChanged();
        }

        public Producto obtenerProductos(int posicion) {

            return listaProductos.get(posicion);
        }
    }

    class CarritoViewHolder extends RecyclerView.ViewHolder {
        ViewholderProductosCarritoBinding binding;

        public CarritoViewHolder(@NonNull ViewholderProductosCarritoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}