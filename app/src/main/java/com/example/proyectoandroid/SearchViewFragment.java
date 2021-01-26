package com.example.proyectoandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proyectoandroid.databinding.FragmentSearchViewBinding;
import com.example.proyectoandroid.databinding.ViewholderProductoBinding;
import com.example.proyectoandroid.model.ProductoFavorito;

import java.util.List;

public class SearchViewFragment extends BaseFragment {
    FragmentSearchViewBinding binding;
    // AppViewModel appViewModel;
    int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return (binding = FragmentSearchViewBinding.inflate(inflater, container, false)).getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //querytextchange !!!!!!!!!!!!
        binding.buscarProducto.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                appViewModel.buscar(newText);
                return false;
            }
        });

        // navegar atras:
       // navController.popBackStack();

        SearchAdapter productosAdapter = new SearchAdapter();
        binding.listaProductos.setAdapter(productosAdapter);


        appViewModel.usuarioAutenticado.observe(getViewLifecycleOwner(), usuario -> {
            userId = usuario.id;

            appViewModel.resultadoBusqueda.observe(getViewLifecycleOwner(), productos -> {
                if(productos == null || productos.size() == 0) {
                   // Log.e("ZERO", "RESULTS");
                    binding.listaProductos.setVisibility(View.GONE);
                    binding.zeroresults.setVisibility(View.VISIBLE);
                    binding.productosEncontrados.setVisibility(View.GONE);
                    binding.VolverExplorador.setOnClickListener(v ->
                            navController.navigate(R.id.action_global_bottom_explorar_fragment2));
                } else {
                    binding.listaProductos.setVisibility(View.VISIBLE);
                    binding.productosEncontrados.setText(productos.size()+" Productos encontrados");
                    binding.productosEncontrados.setVisibility(View.VISIBLE);
                    binding.zeroresults.setVisibility(View.GONE);
                }
                productosAdapter.establecerProductosList(productos);
//                 if(productos.size() == 0){
//                     navController.navigate(R.id.action_global_productosFragment);
//                 }
            });
        });
        //LiveData<List<ProductoFavorito>> obtenerElementos(){
        //  return elementosViewModel.obtener();

    }
    //mirar esto o enviarle a gerard
    @Override
    public void onStop() {
        super.onStop();
        appViewModel.buscar("");
    }

    @Override
    public void onPause() {
        super.onPause();
        appViewModel.buscar("");
    }

    class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {
        List<ProductoFavorito> productoList;

        @NonNull
        @Override
        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SearchViewHolder(ViewholderProductoBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
            ProductoFavorito producto = productoList.get(position);

            holder.binding.nombre.setText(producto.nombre);

            Glide.with(holder.itemView).load(producto.imagenes.get(0)).into(holder.binding.imagen);

            if (producto.esFavorito) {
                holder.binding.favoritosProductos.setImageResource(R.drawable.favoritostodorojo);
            } else
                holder.binding.favoritosProductos.setImageResource(R.drawable.logofavoritosprueba);

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


    static class SearchViewHolder extends RecyclerView.ViewHolder {
        ViewholderProductoBinding binding;

        public SearchViewHolder(@NonNull ViewholderProductoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}