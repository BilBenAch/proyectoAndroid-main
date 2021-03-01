package com.example.proyectoandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoandroid.databinding.FragmentPedidoBinding;
import com.example.proyectoandroid.databinding.ViewholderPedidosBinding;
import com.example.proyectoandroid.model.Carrito;
import com.example.proyectoandroid.model.Pedido;
import com.example.proyectoandroid.model.PedidoCarrito;
import com.example.proyectoandroid.model.Producto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class PedidoFragment extends BaseFragment {
    FragmentPedidoBinding binding;
    List<Carrito> carritoList;
    int suma, suma2, contador;
    String referencia;
    double precioTotal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentPedidoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PedidoAdapter pedidoAdapter = new PedidoAdapter();
        binding.listaPedidos.setAdapter(pedidoAdapter);
        binding.volverMiCuenta.setOnClickListener(v -> {
            navController.navigate(R.id.action_global_bottom_perfil_fragment);
        });


//        binding.flexboxDetallesPedido.setOnClickListener(v -> {
//            navController.navigate(R.id.action_global_detallaesPedidoFragment);
//        });

        appViewModel.usuarioAutenticado.observe(getViewLifecycleOwner(), usuario -> {
            userId = usuario.id;
            appViewModel.obtenerPedidoUserId(usuario.id).observe(getViewLifecycleOwner(), pedidoList -> {
                //pedidoCarritoList2.add(pedidoList.get(0));
                //convertidoJson();
                pedidoAdapter.establecerPedidoList(pedidoList);
                Log.e("ABC", String.valueOf(pedidoList.size()));
            });

        });
        carritoList = new ArrayList<>();
    }

    class PedidoAdapter extends RecyclerView.Adapter<PedidoViewHolder> {
        List<Pedido> pedidoList;


        @NonNull
        @Override
        public PedidoFragment.PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PedidoFragment.PedidoViewHolder(ViewholderPedidosBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
            Pedido pedido = pedidoList.get(position);

//            for(int i = 0; i<pedidoList.get(position).getCarritoID().size(); i++){
//
//            }
            //Log.e("ABC", String.valueOf(pedidoList.get(position)));

            //holder.binding.estadoPedidoActual.setText("En reparto");

            for (int i = 0; i < pedidoList.get(position).carritoList.size(); ++i) {
                suma = suma + pedidoList.get(position).carritoList.get(i).cantidad;
                //carritoList.add(pedidoList.get(position).carritoList.get(i));

                int finalI1 = i;

                referencia = pedidoList.get(position).getReferencia();
                appViewModel.consultarPrecioProductoId(pedidoList.get(position).carritoList.get(i).productoId).observe(getViewLifecycleOwner(), precioUnitario -> {
                    // Log.e("ABC", "contador --> " + String.valueOf(contador));

                    Log.e("ABC", "cantidad --> " + String.valueOf(pedidoList.get(position).carritoList.get(finalI1).cantidad));
                    // Log.e("ABC", "POSICION LISTA --> " + String.valueOf(pedidoList.get(position).getReferencia()));
                    // Log.e("ABC", String.valueOf(precioUnitario) + "pedido Id --> " + String.valueOf(pedidoList.get(position).carritoList.get(finalI).productoId) + " {cantidad--> " + String.valueOf(pedidoList.get(position).carritoList.get(finalI).cantidad));

                    precioTotal += (precioUnitario * pedidoList.get(position).carritoList.get(finalI1).cantidad);

                    holder.binding.precioTotal.setText(String.valueOf(precioTotal) + " €");
                });
                precioTotal = 0;
            }


            holder.binding.numeroPedidosRealizados.setText(String.valueOf(suma) + " Pedidos realizados");
            suma = 0;
            precioTotal = 0;

            holder.binding.fechaPedido.setText(" Pedido con fecha : " + String.valueOf(pedido.fechaCompra));
            holder.binding.NumeoPedido.setText(String.valueOf(pedido.referencia));

        }

        @Override
        public int getItemCount() {
            return pedidoList == null ? 0 : pedidoList.size();
        }


        void establecerPedidoList(List<Pedido> pedidoList) {
            this.pedidoList = pedidoList;
            notifyDataSetChanged();
        }


        public Pedido obtenerPedidoCarrito(int posicion) {

            return pedidoList.get(posicion);
        }
    }


    class PedidoViewHolder extends RecyclerView.ViewHolder {
        ViewholderPedidosBinding binding;

        public PedidoViewHolder(@NonNull ViewholderPedidosBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}