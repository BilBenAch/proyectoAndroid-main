package com.example.proyectoandroid.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.proyectoandroid.model.Favorito;
import com.example.proyectoandroid.model.Producto;
import com.example.proyectoandroid.modelLogin.AppBaseDeDatos;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FavoritoRepositorio {
    /*AppBaseDeDatos.AppDao FavoritosDao;
    Executor executor = Executors.newSingleThreadExecutor();

    FavoritoRepositorio(Application application){
        FavoritosDao = AppBaseDeDatos.getInstance(application).obtenerDao();
    }

    public void anadirFavorito(Favorito favorito){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                FavoritosDao.anadirFavoritos(favorito);
            }
        });
    }


    public LiveData<List<Favorito>> obtenerFavoritos(Integer IDusuario) {
        return FavoritosDao.obtenerFavoritos(IDusuario);
    }
    public LiveData<List<Producto>> obtenerProductosFavoritos(List<Integer> favoritosId){

        return FavoritosDao.obtenerListadoProductos(favoritosId);
    }


    public void eliminar(int productoId, int userId){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                FavoritosDao.delete(productoId, userId);

            }
        });
    }


     */
}
