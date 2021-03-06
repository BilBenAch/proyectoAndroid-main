package com.example.proyectoandroid.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class productosRepositorio {
    AppBaseDeDatos.ProductosDao ProductosDao;
    Executor executor = Executors.newSingleThreadExecutor();

    public productosRepositorio(Application application) {
        ProductosDao = AppBaseDeDatos.getInstance(application).productosDao();
    }

    public void insertar(List<Producto> productos) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (Producto producto : productos) {
                    ProductosDao.insertar(producto);
                }
            }
        });
    }

    public LiveData<List<ProductoFavorito>> obtenerProductos(int userId) {
        return ProductosDao.obtenerProductos(userId);
    }

    public void invertirFavorito(int userId, int productoId) {
        executor.execute(() -> {
            if (ProductosDao.obtenerFavorito(userId, productoId) == null) {
                ProductosDao.insertarFavorito(new Favorito(userId, productoId));
            } else {
                ProductosDao.eliminarFavorito(new Favorito(userId, productoId));
            }
        });
    }


    public LiveData<List<ProductoFavorito>> productosFavoritos(int userId) {
        return ProductosDao.productosFavoritos(userId);
    }


    //metodo para incrementar o decrementar un producto en carrito
    public void insertarCarritoUpdate(int userId, int productoId) {
        executor.execute(() -> {
            if (ProductosDao.obtenerCarrito(userId, productoId) == null) {
                ProductosDao.insertarCarrito(new Carrito(userId, productoId));
            } else {
                ProductosDao.incrementarCarrito(userId, productoId);
            }
        });
    }

    public void eliminarCarritoUpdate(int userId, int productoId) {
        executor.execute(() -> {
            if (ProductosDao.obtenerCarrito(userId, productoId) != null) {
                ProductosDao.updateCantidadDecrementar(userId, productoId);
                //me devuelve la cantidad de un elemento en carrito
                if (ProductosDao.obtenerCantidad(userId, productoId) <= 0) {
                    ProductosDao.eliminarCarrito(new Carrito(userId, productoId));
                }
            }
        });
    }


    //productos carrito
    public LiveData<List<Producto>> obtenerProductosCarrito(int userId) {
        return ProductosDao.obtenerProductosCarrito(userId);
    }

    //devuelve la cantidad de productos totales en carrito
    public LiveData<Integer> getRowCount(int userId) {
        return ProductosDao.getRowCount(userId);
    }


    public LiveData<Integer> getincremento(int userId, int productoId) {
        return ProductosDao.getincremento(userId, productoId);
    }


    //   buscar
    public LiveData<List<ProductoFavorito>> buscar(String d, int userId) {
        if (d.isEmpty()) {
            return null;
        } else {
            return ProductosDao.buscar(d, userId);
        }
    }

    //comprobar si es favorito un producto en carrito o en cualquier otro fragment sin necesidad de joins
    public LiveData<Integer> isFavorite(int userId, int productId) {
        return ProductosDao.isFavorite(userId, productId);
    }

    public void eliminarDelCarrito(int userId, int productoId) {
        executor.execute(() -> {
            ProductosDao.eliminarCarrito(new Carrito(userId, productoId));
        });
    }

    //devuelve precio total de los productos
    public LiveData<Integer> precioTotal(int userId) {
        return ProductosDao.precioTotal(userId);
    }


    public void insertPedido(int userId, String referencia, String fecha) {
        executor.execute(() -> {
            ProductosDao.obtenerElementosCarritoUserId(userId);
            List<Carrito> carritos = new ArrayList<>(ProductosDao.obtenerElementosCarritoUserId(userId));
            ProductosDao.insertarPedido(new Pedido(userId, referencia, fecha, carritos));
            ProductosDao.eliminarCarritoUserID(userId);

        });
    }

    public LiveData<List<Pedido>> obtenerPedidoUserId(int userId) {
        return ProductosDao.obtenerPedidoUserId(userId);
    }


    public LiveData<List<PedidoCarrito>> obtenerElementosPedidoReferencia(String referencia) {
        return ProductosDao.obtenerElementosPedidoReferencia(referencia);
    }


    public LiveData<Double> consultarPrecioProductoId(int productoId) {
        return ProductosDao.consultarPrecioProductoId(productoId);
    }

}
