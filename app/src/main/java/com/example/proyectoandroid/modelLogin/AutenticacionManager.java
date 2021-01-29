package com.example.proyectoandroid.modelLogin;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.proyectoandroid.model.Direccion;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AutenticacionManager {

    Executor executor = Executors.newSingleThreadExecutor();

    public interface IniciarSesionCallback {
        void cuandoUsuarioAutenticado(Usuario usuario);

        void cuandoAutenticacionNoValida();
    }

    public interface RegistrarCallback {
        void cuandoRegistroCompletado();

        void cuandoNombreNoDisponible();


    }

    //cambiar contrasenia aqui añado los errores
    public interface CambiarContraseniaCallback {
        void cuandoContraseniaCambiada();
        void cuandoUsuarioNoExiste();

    }

    AppBaseDeDatos.UsuariosDao dao;

    public AutenticacionManager(Application application) {
        dao = AppBaseDeDatos.getInstance(application).usuariosDao();
    }

    public void iniciarSesion(String username, String password, IniciarSesionCallback callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Usuario usuario = dao.autenticar(username, password);

                if (usuario != null) {
                    callback.cuandoUsuarioAutenticado(usuario);
                } else {
                    callback.cuandoAutenticacionNoValida();
                }
            }
        });
    }

    public void crearCuenta(String username, String email, String password, String password2, RegistrarCallback callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Usuario usuario = dao.comprobarNombreDisponible(username);
                if (usuario == null) {
                    dao.insertarUsuario(new Usuario(username, email, password, password2));
                    callback.cuandoRegistroCompletado();
                } else {
                    callback.cuandoNombreNoDisponible();
                }
            }
        });
    }

    //cambiar contraseña testeo
    public void cambiarContrasenia(String username, String email, String password, CambiarContraseniaCallback callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Usuario usuario = dao.comprobarContraseniaCorrecta(username, email, password);
                Usuario usuario1 = dao.comprobarNombreDisponible(username);
                if (usuario1 == null) {
                    callback.cuandoUsuarioNoExiste();
                } else {
                    dao.cambiarContraUsuarioEmail(username, email, password);
                    callback.cuandoContraseniaCambiada();
                }

            }
        });
    }


    public void cambiarNombreApellido(String nombre, String apellido, int userId) {
        {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    dao.cambiarNombreApellido(nombre, apellido, userId);
                }
            });
        }
    }

    public void cambiarEmail(String email, int userId) {
        {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    dao.cambiarEmail(email, userId);
                }
            });
        }
    }


    public void cambiarTelefono(String telefono, int userId) {
        {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    dao.cambiarTelefono(telefono, userId);
                }
            });
        }
    }


    public void cambiarContra(String contra, int userId) {
        {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    dao.cambiarContrasenia(contra, userId);
                }
            });
        }
    }


    public LiveData<Usuario> obtenerUsuario(int userId) {
        return dao.getUser(userId);
    }


    public LiveData<List<Direccion>> obtenerDirecciones(int userId) {
        return dao.obtenerDireciones(userId);
    }

    public void updateDireccion(String direccion, String telefono, int userId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updateDireccion(userId, direccion, telefono);
            }
        });
    }

    public void eliminarDireccion(String direccion, String telefono, int userId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dao.eliminarDireccion(new Direccion(userId, direccion, telefono));
            }
        });
    }

    //no está del todo bien, mdodificar después de la entrega de Dani
    public void insertUpdateDireccion(String direccion, String telefono, int userId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Direccion direccion1 = dao.comprobarDireccionUsuario(userId);
                if(direccion1 == null){
                    dao.insertarDireccion(new Direccion(userId, direccion, telefono));
                }
                else {
                    dao.updateDireccion(userId, direccion, telefono);
                }
            }
        });
    }

    //Cambiar contra no respeta programación reactiva, modificar después entrega Dani
    public void cambiarContraUsuarioEmail(String userName, String email, String newPassword) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dao.cambiarContraUsuarioEmail(userName, email, newPassword);
            }
        });
    }


    //Obtener contenidoUser segun Email
    public LiveData<Usuario> obtenerContenidoUsuarioEmail(String email) {
        return dao.obtenerContenidoSegunEmail(email);
    }

}

