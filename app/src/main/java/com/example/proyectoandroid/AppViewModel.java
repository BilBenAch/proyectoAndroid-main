package com.example.proyectoandroid;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.proyectoandroid.model.Direccion;
import com.example.proyectoandroid.model.Producto;
import com.example.proyectoandroid.model.ProductoFavorito;
import com.example.proyectoandroid.model.productosRepositorio;
import com.example.proyectoandroid.modelLogin.AutenticacionManager;
import com.example.proyectoandroid.modelLogin.Usuario;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class AppViewModel extends AndroidViewModel {
    Executor executor = Executors.newSingleThreadExecutor();

    enum EstadoDeLaAutenticacion {
        NO_AUTENTICADO,
        AUTENTICADO,
        AUTENTICACION_INVALIDA
    }

    enum EstadoDelRegistro {
        INICIO_DEL_REGISTRO,
        NOMBRE_NO_DISPONIBLE,
        REGISTRO_COMPLETADO
    }

    enum EstadoDelCambioDePassword {
        INICIO_DEL_CAMBIO,
        USUARIO_INCORRECTO,
        CONTRASENIA_CAMBIADA
    }

   /* enum EstadoDelCambioDeNombre {
        INICIO_DEL_CAMBIO,
        NOMBRE_NO_DISPONIBLE,
        CAMBIO_COMPLETADO
    }

    */


//    enum EstadoDelCambioDePassword {
//        INICIO_DEL_CAMBIO,
//        USUARIO_INCORRECTO,
//        CONTRASENIA_CAMBIADA
//    }

        enum EstadoDireccion {
        INSERTANDO_DIRECCION,
        DIRECCION_REPETIDA,
        DIRECCION_ACTUALIZADA
    }

    MutableLiveData<EstadoDeLaAutenticacion> estadoDeLaAutenticacion = new MutableLiveData<>(EstadoDeLaAutenticacion.NO_AUTENTICADO);
    MutableLiveData<Usuario> usuarioAutenticado = new MutableLiveData<>();
    MutableLiveData<EstadoDelRegistro> estadoDelRegistro = new MutableLiveData<>(EstadoDelRegistro.INICIO_DEL_REGISTRO);
    MutableLiveData<EstadoDelRegistro> usuarioRegistrado = new MutableLiveData<>();
    //cambio contrasenia
    MutableLiveData<EstadoDelCambioDePassword> estadoUsuarioContrasenia = new MutableLiveData<>();

    MutableLiveData<EstadoDireccion> comprobarDireccion = new MutableLiveData<>();

    //estado cambio y apellido
    //MutableLiveData<EstadoDelCambioDeNombre> estadoDelCambiodeNombre = new MutableLiveData<>(EstadoDelCambioDeNombre.INICIO_DEL_CAMBIO);


    AutenticacionManager autenticacionManager;
    private final com.example.proyectoandroid.model.productosRepositorio productosRepositorio;

    public AppViewModel(@NonNull Application application) {
        super(application);
        autenticacionManager = new AutenticacionManager(application);
        productosRepositorio = new productosRepositorio(application);
    }


    void iniciarSesion(String username, String password) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                autenticacionManager.iniciarSesion(username, password, new AutenticacionManager.IniciarSesionCallback() {

                    @Override

                    public void cuandoUsuarioAutenticado(Usuario usuario) {
                        usuarioAutenticado.postValue(usuario);
                        estadoDeLaAutenticacion.postValue(EstadoDeLaAutenticacion.AUTENTICADO);
                    }

                    @Override
                    public void cuandoAutenticacionNoValida() {
                        estadoDeLaAutenticacion.postValue(EstadoDeLaAutenticacion.AUTENTICACION_INVALIDA);
                    }
                });
            }
        });
    }

    void iniciarRegistro() {
        estadoDelRegistro.postValue(EstadoDelRegistro.INICIO_DEL_REGISTRO);

    }


//    void noAutenticado() {
//        estadoDeLaAutenticacion.postValue(EstadoDeLaAutenticacion.NO_AUTENTICADO);
//
//    }

    //Seguir aqui
    void crearCuenta(String username, String email, String password, String password2) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                autenticacionManager.crearCuenta(username, email, password, password2, new AutenticacionManager.RegistrarCallback() {
                    @Override
                    public void cuandoRegistroCompletado() {
                        if (estadoDelRegistro.equals(EstadoDelRegistro.REGISTRO_COMPLETADO)) {
                            estadoDelRegistro.postValue(EstadoDelRegistro.INICIO_DEL_REGISTRO);
                        } else {
                            estadoDelRegistro.postValue(EstadoDelRegistro.REGISTRO_COMPLETADO);

                        }
                    }

                    @Override
                    public void cuandoNombreNoDisponible() {
                        estadoDelRegistro.postValue(EstadoDelRegistro.NOMBRE_NO_DISPONIBLE);
                    }
                });
            }
        });
    }


    void cerrarSesion() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                usuarioAutenticado.postValue(null);
                estadoDeLaAutenticacion.postValue(EstadoDeLaAutenticacion.NO_AUTENTICADO);
            }
        });
    }

    //seguir aqui
    void actualizarPassword(String usuario, String email, String password) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                autenticacionManager.cambiarContrasenia(usuario, email, password, new AutenticacionManager.CambiarContraseniaCallback() {

                    @Override
                    public void cuandoContraseniaCambiada() {
                        if (estadoUsuarioContrasenia.equals(EstadoDelCambioDePassword.CONTRASENIA_CAMBIADA)) {
                            //actualizarPassword(usuario, email, password);
                            estadoUsuarioContrasenia.postValue(EstadoDelCambioDePassword.INICIO_DEL_CAMBIO);
                        }
                        else {
                            estadoUsuarioContrasenia.postValue(EstadoDelCambioDePassword.CONTRASENIA_CAMBIADA);
                        }
                    }

                    @Override
                    public void cuandoUsuarioNoExiste() {
                        estadoUsuarioContrasenia.postValue(EstadoDelCambioDePassword.USUARIO_INCORRECTO);
                    }

                });
            }
        });
    }


    MutableLiveData<ProductoFavorito> elementoSeleccionado = new MutableLiveData<>();

    public void insertar(List<Producto> productos) {
        productosRepositorio.insertar(productos);
    }

    LiveData<List<ProductoFavorito>> obtenerProductos(int userId) {
        return productosRepositorio.obtenerProductos(userId);
    }

    public void invertirFavorito(int userId, int productoId) {
        productosRepositorio.invertirFavorito(userId, productoId);
    }

    LiveData<List<ProductoFavorito>> productosFavoritos(int userId) {
        return productosRepositorio.productosFavoritos(userId);
    }


    //selecionar un producto para mostrarlo
    void seleccionar(ProductoFavorito productoFavorito) {
        elementoSeleccionado.setValue(productoFavorito);
    }

    MutableLiveData<ProductoFavorito> seleccionado() {
        return elementoSeleccionado;
    }

    public void insertarCarritoUpdate(int userId, int prductoId) {
        productosRepositorio.insertarCarritoUpdate(userId, prductoId);
    }

    public void eliminarCarritoUpdate(int userId, int prductoId) {
        productosRepositorio.eliminarCarritoUpdate(userId, prductoId);
    }

    //update carrito
    public void incrementarCarrito(int userId, int prductoId) {
        productosRepositorio.insertarCarritoUpdate(userId, prductoId);
    }

    //metodo te devuelve los productos en el carrito
    public LiveData<List<Producto>> obtenerProductosCarrito(int userId) {
        return productosRepositorio.obtenerProductosCarrito(userId);
    }


    //devuelve la cantidad de productos en carrito
    public LiveData<Integer> getRowCount(int userId) {
        return productosRepositorio.getRowCount(userId);
    }

    public LiveData<Integer> getincremento(int userId, int productoId) {
        return productosRepositorio.getincremento(userId, productoId);
    }

    //eliminar elementos del carrito
    public void eliminarDelCarrito(int userId, int productoId) {
        productosRepositorio.eliminarDelCarrito(userId, productoId);
    }


    //buscar
    MutableLiveData<String> terminoBusqueda = new MutableLiveData<>();

    LiveData<List<ProductoFavorito>> resultadoBusqueda = Transformations.switchMap(terminoBusqueda, new Function<String, LiveData<List<ProductoFavorito>>>() {
        @Override
        public LiveData<List<ProductoFavorito>> apply(String input) {
            //Log.e("TERMINO B", "["+input+"]");
            return productosRepositorio.buscar(input, usuarioAutenticado.getValue().id);
        }
    });


    void buscar(String s) {
        terminoBusqueda.setValue(s);
    }

    public LiveData<Integer> isFavorite(int userId, int productId) {
        return productosRepositorio.isFavorite(userId, productId);
    }


    //devuelve precio total de los productos
    public LiveData<Integer> precioTotal(int userId) {
        return productosRepositorio.precioTotal(userId);
    }


    //cambiar nombre y apellido
    public void cambiarNombreApellido(String nombre, String apellido, int userId) {
        autenticacionManager.cambiarNombreApellido(nombre, apellido, userId);
    }

    //cambiar email
    public void cambiarEmail(String email, int userId) {
        autenticacionManager.cambiarEmail(email, userId);
    }

    //cambiar telefono
    public void cambiarTelefono(String telefono, int userId) {
        autenticacionManager.cambiarTelefono(telefono, userId);
    }

    //cambiar contraseña fragment perfil
    public void cambiarContra(String contra, int userId) {
        autenticacionManager.cambiarContra(contra, userId);
    }

    //obtener contenido usuario para ver cambio nombre irl
    LiveData<Usuario> obtenerUsuario(int userId) {
        return autenticacionManager.obtenerUsuario(userId);
    }


    //obtener contenido usuario para ver cambio nombre irl
    LiveData<List<Direccion>> obtenerDirecciones(int userId) {
        return autenticacionManager.obtenerDirecciones(userId);
    }

//    //cambiar datos direccion
//    public void updateDireccion(int userId, String direccion, String telefono){
//        autenticacionManager.updateDireccion(direccion, telefono, userId);
//    }

    //actualiza una direccion para ese user
    public void insertUpdateDireccion(int userId, String direccion, String telefono) {
        autenticacionManager.insertUpdateDireccion(direccion, telefono, userId);
    }

    //eliminar una nueva direccion para ese user
    public void eliminarDireccion(int userId, String direccion, String telefono) {
        autenticacionManager.eliminarDireccion(direccion, telefono, userId);
    }

//no respeta programación reactiva, borrar después o modificar

    public void cambiarContraUsuarioEmail(String userName, String email, String newPassword) {
        autenticacionManager.cambiarContraUsuarioEmail(userName, email, newPassword);
    }

    //Obtener contenidoUser segun Email
    public LiveData<Usuario> obtenerContenidoUsuarioEmail(String email) {
        return autenticacionManager.obtenerContenidoUsuarioEmail(email);
    }


}