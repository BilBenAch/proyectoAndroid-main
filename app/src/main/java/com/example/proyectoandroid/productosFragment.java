package com.example.proyectoandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectoandroid.databinding.FragmentProductosBinding;
import com.example.proyectoandroid.databinding.ViewholderProductoBinding;
import com.example.proyectoandroid.model.ProductoFavorito;

import java.util.List;

public class productosFragment extends BaseFragment {
    private FragmentProductosBinding binding;
    private int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentProductosBinding.inflate(inflater, container, false)).getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProductosAdapter productosAdapter = new ProductosAdapter();
        binding.listaProductos.setAdapter(productosAdapter);


        appViewModel.usuarioAutenticado.observe(getViewLifecycleOwner(), usuario -> {
            userId = usuario.id;

            appViewModel.resultadoBusqueda.observe(getViewLifecycleOwner(), productos -> {

                binding.volverExplorador.setOnClickListener(v -> {
                    navController.navigate(R.id.action_global_bottom_explorar_fragment2);
                });
                binding.productosEncontrados.setText(productos.size()+" Resultados ");//Preguntar  a Gerard como obtener el nombre del elemento buscado
                productosAdapter.establecerProductosList(productos);
            });
        });
    }


    class ProductosAdapter extends RecyclerView.Adapter<ProductosViewHolder> {
        List<ProductoFavorito> productoList;

        @NonNull
        @Override
        public ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ProductosViewHolder(ViewholderProductoBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ProductosViewHolder holder, int position) {
            ProductoFavorito producto = productoList.get(position);

            //binding.productosEncontrados.setText(producto.);

            holder.binding.nombre.setText(producto.nombre);

            Glide.with(holder.itemView).load(producto.imagenes.get(0)).into(holder.binding.imagen);

            holder.binding.precio.setText(String.valueOf(producto.precioProducto)+"€");
            // holder.binding.favoritosProductos.setChecked(producto.esFavorito);
            if (producto.esFavorito) {
                holder.binding.favoritosProductos.setImageResource(R.drawable.favoritostodorojo);
            } else
                holder.binding.favoritosProductos.setImageResource(R.drawable.logofavoritosprueba);
            //holder.binding.favoritosProductos.setChecked(producto.esFavorito);

            holder.binding.favoritosProductos.setOnClickListener(v -> {
                appViewModel.invertirFavorito(userId, producto.id);
                if (producto.esFavorito) {
                    Toast.makeText(getContext(), "Producto quitado de favs", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getContext(), "Producto añadido a favs", Toast.LENGTH_SHORT).show();
            });

            holder.binding.nombre.setOnClickListener(v -> {
                appViewModel.seleccionar(producto);
                navController.navigate(R.id.action_global_mostrarProducto);

            });


        }

        @Override
        public int getItemCount() {
            return productoList == null ? 0 : productoList.size();
        }

        void establecerProductosList(List<ProductoFavorito> productoList) {
            this.productoList = productoList;
            notifyDataSetChanged();
        }

        public ProductoFavorito obtenerProductos(int posicion) {
            return productoList.get(posicion);
        }

    }


    static class ProductosViewHolder extends RecyclerView.ViewHolder {
        ViewholderProductoBinding binding;

        public ProductosViewHolder(@NonNull ViewholderProductoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}