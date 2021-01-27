package com.example.proyectoandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectoandroid.databinding.FragmentBottomFavoritosBinding;
import com.example.proyectoandroid.databinding.ViewholderProductoFavoritosBinding;
import com.example.proyectoandroid.model.Producto;
import com.example.proyectoandroid.modelLogin.Usuario;

import java.util.List;


public class bottom_favoritos_fragment extends Fragment {
    private FragmentBottomFavoritosBinding binding;
    private Usuario usuario;
   // private ProductosViewModel productosViewModel;
    com.example.proyectoandroid.model.productosRepositorio productosRepositorio;
    AppViewModel appViewModel;
    private int userId;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentBottomFavoritosBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        navController = Navigation.findNavController(view);
        FavoritosAdapter favoritosAdapter = new FavoritosAdapter();
        binding.listaProductos.setAdapter(favoritosAdapter);
        appViewModel.usuarioAutenticado.observe(getViewLifecycleOwner(), usuario -> {
            userId = usuario.id;

            appViewModel.obtenerProductosFavoritos(usuario.id).observe(getViewLifecycleOwner(), productosList -> {
                if(productosList == null || productosList.size() == 0) {
                    // Log.e("ZERO", "RESULTS");
                    binding.listaProductos.setVisibility(View.GONE);
                    binding.zeroresults.setVisibility(View.VISIBLE);
                    binding.productosEncontrados.setVisibility(View.GONE);
                } else {
                    binding.listaProductos.setVisibility(View.VISIBLE);
                    binding.productosEncontrados.setText(productosList.size()+" Productos encontrados");
                    binding.productosEncontrados.setVisibility(View.VISIBLE);
                    binding.zeroresults.setVisibility(View.GONE);
                }
                favoritosAdapter.establecerFavoritoList(productosList);
//                if(productosList.size() == 0){
//                    //aqui funciona
//                   // navController.navigate(R.id.action_global_mostrarProducto);
//                }
            });
        });
    }

    class FavoritosAdapter extends RecyclerView.Adapter<FavoritosViewHolder> {
        List<Producto> listaProductos;

        @NonNull
        @Override
        public FavoritosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FavoritosViewHolder(ViewholderProductoFavoritosBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull FavoritosViewHolder holder, int position) {
            Producto producto = listaProductos.get(position);

            //modificar esto
            //binding.numeroApariciones.setText(getItemCount() + "productos encontrados");

            holder.binding.nombre.setText(producto.nombre);

            Glide.with(holder.itemView).load(producto.imagenes.get(0)).into(holder.binding.imagen);


            holder.binding.imagen.setOnClickListener(v -> {
                appViewModel.invertirFavorito(userId, producto.id);

            });
        }

        @Override
        public int getItemCount() {
            return listaProductos == null ? 0 : listaProductos.size();
        }

        void establecerFavoritoList(List<Producto> producto) {
            this.listaProductos = producto;
            notifyDataSetChanged();
        }

        public Producto obtenerProductos(int posicion) {

            return listaProductos.get(posicion);
        }
    }

    class FavoritosViewHolder extends RecyclerView.ViewHolder {
        ViewholderProductoFavoritosBinding binding;

        public FavoritosViewHolder(@NonNull ViewholderProductoFavoritosBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
