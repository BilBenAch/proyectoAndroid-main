package com.example.proyectoandroid.modelLogin;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import com.example.proyectoandroid.model.Carrito;
import com.example.proyectoandroid.model.Favorito;
import com.example.proyectoandroid.model.Producto;
//import com.example.proyectoandroid.model.ProductoCarritoFavorito;
import com.example.proyectoandroid.model.ProductoCarritoFavorito;
import com.example.proyectoandroid.model.ProductoFavorito;

import java.util.List;

@Database(entities = {Usuario.class, Producto.class, Favorito.class, Carrito.class}, version =  3, exportSchema = false)
public abstract class AppBaseDeDatos extends RoomDatabase {

    //public abstract AppDao obtenerDao();

    public abstract UsuariosDao usuariosDao();
    public abstract ProductosDao productosDao();

    private static volatile AppBaseDeDatos INSTANCE;

    public static AppBaseDeDatos getInstance(final Context context){
        if (INSTANCE == null){
            synchronized (AppBaseDeDatos.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, AppBaseDeDatos.class, "app.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }



    @Dao
    public interface UsuariosDao {
        //usuario
        @Insert
        void insertarUsuario(Usuario usuario);

        @Update
        void updateContrasenia(Usuario usuario);

        @Query("SELECT * FROM Usuario WHERE id =:userID")
        Usuario getUser(int userID);

        @Query("SELECT * FROM Usuario WHERE username = :nombre AND password = :contrasenya")
        Usuario autenticar(String nombre, String contrasenya);

        @Query("SELECT * FROM Usuario WHERE username = :nombre")
        Usuario comprobarNombreDisponible(String nombre);

        @Query("SELECT * FROM Usuario WHERE username = :nombre AND email = :email AND password = :password")
        Usuario comprobarContraseniaCorrecta(String nombre, String email, String password);
    }
        //Producto

    @Dao
    public interface ProductosDao {
        @Insert
        void insertar(Producto producto);

        @Query("SELECT Producto.id, Producto.nombre, Producto.imagenes, CASE WHEN userId IS NOT NULL THEN 1 ELSE 0 END as esFavorito FROM Producto LEFT JOIN (SELECT * FROM Favorito WHERE userId = :userId) AS Fav ON Producto.id = Fav.productoId")
        LiveData<List<ProductoFavorito>> obtenerProductos(int userId);

        @Query("SELECT * FROM Favorito WHERE userId = :userId AND productoId = :productoId")
        Favorito obtenerFavorito(int userId, int productoId);

        @Query("SELECT * FROM Producto AS p JOIN Favorito as fav ON p.id = fav.productoId WHERE fav.userId = :userId")
        LiveData<List<Producto>> obtenerProductosFavoritos(int userId);

        @Insert
        void insertarFavorito(Favorito favorito);

        @Delete
        void eliminarFavorito(Favorito favorito);


        //buscar
        @Query("SELECT Producto.id, Producto.nombre, Producto.imagenes, CASE WHEN userId IS NOT NULL THEN 1 ELSE 0 END as esFavorito FROM Producto Producto LEFT JOIN (SELECT * FROM Favorito WHERE userId = :userId) AS Fav ON Producto.id = Fav.productoId WHERE nombre LIKE '%' || :d || '%'")
        LiveData<List<ProductoFavorito>> buscar(String d, int userId);


        //carrito compra

        @Delete
        void eliminarCarrito(Carrito carrito);


        @Query("SELECT * FROM Carrito WHERE userId = :userId AND productoId = :productoId")
        Carrito obtenerCarrito(int userId, int productoId);


        //@Query("SELECT * FROM Carrito AS ca JOIN Producto AS p WHERE userId = :userId AND p.id = :productoId")
        //Carrito obtenerCarritoprueba(int userId, int productoId);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertarCarrito(Carrito carrito);

        //Incrementa la cantidad individual del producto en carrito
        @Query("UPDATE Carrito SET cantidad = cantidad + 1 WHERE userId = :userId AND productoId = :productoId")
        void incrementarCarrito(int userId, int productoId);

        //Obtiene la cantidad individual del carrito, si lo pongo como live data no funciona
        @Query("SELECT cantidad From Carrito WHERE userId = :userId AND productoId = :productoId")
        int obtenerCantidad(int userId, int productoId);

        //Resta uno a la cantidad del carrito
        @Query("UPDATE carrito  SET cantidad = cantidad - 1  WHERE userId = :userId AND productoId = :productoId")
        void updateCantidadDecrementar(int userId, int productoId);

        //@Query("SELECT Producto.id, Producto.nombre, Producto.imagenes, CASE WHEN userId IS NOT NULL THEN 1 ELSE 0 END as isFavorito FROM Producto LEFT JOIN (SELECT * FROM Favorito WHERE userId = :userId) AS Fav ON Producto.id = Fav.productoId")
        //LiveData<List<ProductoCarritoFavorito>> obtenerProductosCarrito(int userId);


        @Query("SELECT * FROM Producto AS p JOIN Carrito as carr  ON p.id = carr.productoId WHERE carr.userId = :userId")
        LiveData<List<Producto>> obtenerProductosCarrito(int userId);

        //Devuelve cantidad total de productos añadidos a la cesta en carrito
        @Query("SELECT SUM (cantidad) FROM carrito WHERE userId = :userId")
        LiveData<Integer> getRowCount(int userId);


        //Devuelve cantidad de cada producto del carrito
        @Query("SELECT cantidad FROM carrito WHERE userId = :userId AND productoId = :productoId")
        LiveData<Integer> getincremento(int userId, int productoId);


        //comprobar si un producto es favorito en carrito
        @Query("SELECT EXISTS (SELECT 1 FROM Favorito WHERE userId=:userId AND productoId = :productId)")
        LiveData<Integer> isFavorite(int userId, int productId);
    }


}
