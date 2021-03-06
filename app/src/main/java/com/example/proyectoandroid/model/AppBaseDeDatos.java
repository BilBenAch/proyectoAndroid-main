package com.example.proyectoandroid.model;

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

//import com.example.proyectoandroid.model.ProductoCarritoFavorito;

import java.util.List;

@Database(entities = {Usuario.class, Producto.class, Favorito.class, Carrito.class, Direccion.class, Pedido.class}, version = 6, exportSchema = false)
public abstract class AppBaseDeDatos extends RoomDatabase {

    //public abstract AppDao obtenerDao();

    public abstract UsuariosDao usuariosDao();

    public abstract ProductosDao productosDao();

    private static volatile AppBaseDeDatos INSTANCE;

    public static AppBaseDeDatos getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppBaseDeDatos.class) {
                if (INSTANCE == null) {
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
        LiveData<Usuario> getUser(int userID);

        @Query("SELECT * FROM Usuario WHERE username = :nombre AND password = :contrasenya")
        Usuario autenticar(String nombre, String contrasenya);

        @Query("SELECT * FROM Usuario WHERE username = :nombre")
        Usuario comprobarNombreDisponible(String nombre);

        @Query("SELECT * FROM Usuario WHERE username = :nombre AND email = :email AND password = :password")
        Usuario comprobarContraseniaCorrecta(String nombre, String email, String password);

        ///cambiar nombre y apellido
        @Query("Update Usuario SET nombre = :nombre, apellido = :apellido WHERE id = :userId")
        void cambiarNombreApellido(String nombre, String apellido, int userId);

        ///cambiar email
        @Query("Update Usuario SET email = :email  WHERE id = :userId")
        void cambiarEmail(String email, int userId);

        ///cambiar Telefono
        @Query("Update Usuario SET numeroTelefono = :telefono  WHERE id = :userId")
        void cambiarTelefono(String telefono, int userId);

        //cambiar contraseña
        @Query("Update Usuario SET password = :contrasenia, password2 = :contrasenia  WHERE id = :userId")
        void cambiarContrasenia(String contrasenia, int userId);

        //no esta del todo bien, borrar después
        @Query("Update Usuario SET password = :newPassword, password2 = :newPassword  WHERE username = :userName AND email = :email")
        void cambiarContraUsuarioEmail(String userName, String email, String newPassword);


        //obtener todo de un usuario según su email
        @Query("SELECT * FROM USUARIO where email = :email")
        LiveData<Usuario> obtenerContenidoSegunEmail(String email);

        //Direccion

        //insertar direccion
        @Insert
        void insertarDireccion(Direccion direccion);

        //obtener direcciones del usuario
        @Query("SELECT * FROM direccion WHERE userId = :userId")
        LiveData<List<Direccion>> obtenerDireciones(int userId);

        @Delete
        void eliminarDireccion(Direccion direccion);

        //actualizar dirección
        @Query("Update Direccion SET direccion = :direccion, telefono = :telefono WHERE userId = :userId")
        void updateDireccion(int userId, String direccion, String telefono);


        //Comprobar direccion no repetida no está del todo bien
        @Query("SELECT EXISTS ( SELECT 1 FROM Direccion WHERE direccion = :direccion AND userId = :userId)")
        int comprobarDireccionRepetida(String direccion, int userId);

        //compruebo que existe el usuario en la bdd
        @Query("SELECT * FROM Direccion WHERE userId = :userId")
        Direccion comprobarDireccionUsuario(int userId);


        @Query("SELECT * FROM Usuario WHERE username = :nombre AND email = :email AND password = :password")
        Usuario comprobarCambioContra(String nombre, String email, String password);


    }
    //Producto

    @Dao
    public interface ProductosDao {
        @Insert
        void insertar(Producto producto);

        @Query("SELECT Producto.id, Producto.nombre, Producto.tipoProducto, Producto.colorProducto, Producto.precioProducto , Producto.imagenes, CASE WHEN userId IS NOT NULL THEN 1 ELSE 0 END as esFavorito FROM Producto LEFT JOIN (SELECT * FROM Favorito WHERE userId = :userId) AS Fav ON Producto.id = Fav.productoId")
        LiveData<List<ProductoFavorito>> obtenerProductos(int userId);

        @Query("SELECT * FROM Favorito WHERE userId = :userId AND productoId = :productoId")
        Favorito obtenerFavorito(int userId, int productoId);

        //No carga el mostrarPoructo con esta query
        @Query("SELECT * FROM Producto AS p JOIN Favorito as fav ON p.id = fav.productoId WHERE fav.userId = :userId")
        LiveData<List<Producto>> obtenerProductosFavoritos(int userId);


        //OBTENER PRODUCTOS FAVORITOS correcta
        @Query("SELECT *, CASE WHEN userId IS NOT NULL THEN 1 ELSE 0 END as esFavorito FROM Producto AS p LEFT JOIN FAVORITO AS Fav ON p.id = Fav.productoId WHERE Fav.userId = :userId")
        LiveData<List<ProductoFavorito>> productosFavoritos(int userId);


        @Insert
        void insertarFavorito(Favorito favorito);

        @Delete
        void eliminarFavorito(Favorito favorito);


        //buscar
        @Query("SELECT Producto.id, Producto.nombre, Producto.tipoProducto, Producto.colorProducto, Producto.precioProducto , Producto.imagenes, CASE WHEN userId IS NOT NULL THEN 1 ELSE 0 END as esFavorito FROM Producto Producto LEFT JOIN (SELECT * FROM Favorito WHERE userId = :userId) AS Fav ON Producto.id = Fav.productoId WHERE tipoProducto LIKE '%' || :d || '%' OR nombre LIKE '%' || :d || '%' OR etiqueta LIKE '%' || :d || '%'")
        LiveData<List<ProductoFavorito>> buscar(String d, int userId);


        //carrito compra

        @Delete
        void eliminarCarrito(Carrito carrito);

        @Query("DELETE FROM Carrito WHERE userId = :userId ")
        void eliminarCarritoUserID(int userId);


        @Query("SELECT * FROM Carrito WHERE userId = :userId AND productoId = :productoId")
        Carrito obtenerCarrito(int userId, int productoId);


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

        //aqui
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


        //Devuelve Suma de la cantidad total de productos en carrito
        @Query("SELECT SUM (precioProducto * cantidad) FROM producto AS PROD JOIN Carrito AS CARR ON PROD.id = CARR.productoId WHERE CARR.userId = :userId")
        LiveData<Integer> precioTotal(int userId);

        //obtener elementos carrito por user id
        @Query("Select * FROM CARRITO WHERE userId = :userId")
        List<Carrito> obtenerElementosCarritoUserId(int userId);


        //Pedidos
        @Insert
        void insertarPedido(Pedido pedido);


        @Query("SELECT carritoList FROM Pedido AS p WHERE p.referencia = :referencia")
        LiveData<List<PedidoCarrito>> obtenerElementosPedidoReferencia(String referencia);


        @Query("SELECT * FROM PEDIDO WHERE userId = :userId")
        LiveData<List<Pedido>> obtenerPedidoUserId(int userId);


        //Precio de cada producto para pedido
        @Query("SELECT   precioProducto  FROM Producto where id = :productoId")
        LiveData<Double> consultarPrecioProductoId(int productoId);


    }
}


