package com.example.proyectoandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.proyectoandroid.databinding.FragmentBottomCarritoBinding;
import com.example.proyectoandroid.databinding.ViewholderProductosCarritoBinding;
import com.example.proyectoandroid.model.Favorito;
import com.example.proyectoandroid.model.Producto;
import com.example.proyectoandroid.model.ProductoFavorito;

import java.util.List;


public class bottom_carrito_fragment extends Fragment {
    private FragmentBottomCarritoBinding binding;
    //private ProductosViewModel productosViewModel;
    AppViewModel appViewModel;
    private com.example.proyectoandroid.model.productosRepositorio productosRepositorio;
    private int contadorProducto;
    private int userId;
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

        appViewModel.usuarioAutenticado.observe(getViewLifecycleOwner(), usuario -> {
            userId = usuario.id;
            appViewModel.getRowCount(userId).observe(getViewLifecycleOwner(), integer -> {
                if(integer != null) {
                    contadorProducto = integer;
                    appViewModel.obtenerProductosCarrito(usuario.id).observe(getViewLifecycleOwner(), productosList -> {
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

            // holder.binding.favoritosProductos.setImageResource(R.drawable.favoritostodorojo);

            Glide.with(holder.itemView).load(producto.imagenes.get(0)).into(holder.binding.imagen);

            //System.out.println("para");
            /*if(productoFavoritosList.get(position).esFavorito){
                holder.binding.favoritosProductos.setImageResource(R.drawable.favoritostodorojo);
            }
            else holder.binding.favoritosProductos.setImageResource(R.drawable.logofavoritosprueba);
            System.out.println("para");

             */

            holder.binding.nombre.setOnClickListener(v -> {
                appViewModel.eliminarCarritoUpdate(userId, producto.id);
                //System.out.println("para");
            });


            holder.binding.imagen.setOnClickListener(v -> {
                // productosViewModel.invertirFavorito(userId, producto.id);
                appViewModel.incrementarCarrito(userId, producto.id);
                //appViewModel.getincremento(userId, producto.id);
                // System.out.println("para");

            });
            //appViewModel.getincremento(userId, producto.id).getValue();
           // holder.binding.cantidad.setText(String.valueOf();
           // holder.binding.cantidad.setText(String.valueOf(appViewModel.getincremento(userId, producto.id).getValue()));
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