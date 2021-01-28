package com.example.proyectoandroid.modelLogin;

import android.app.Application;

import androidx.lifecycle.LiveData;

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
        void cuandoEmailYUsuarioNoCoinciden();
        void cuandoUsuariooEsVacio();
        void cuandoEmailEsVacio();
        void cuandoConstraseniaEsVacio();
    }

    AppBaseDeDatos.UsuariosDao dao;

    public AutenticacionManager(Application application){
        dao = AppBaseDeDatos.getInstance(application).usuariosDao();
    }

    public void iniciarSesion(String username, String password, IniciarSesionCallback callback){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Usuario usuario = dao.autenticar(username, password);

                if (usuario != null){
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
                if (usuario == null ){
                    dao.insertarUsuario(new Usuario(username, email, password, password2));
                    callback.cuandoRegistroCompletado();
                } else {
                    callback.cuandoNombreNoDisponible();
                }
            }
        });
    }

    //cambiar contraseña incompleto
    public void cambiarContrasenia(String username, String email,String password, CambiarContraseniaCallback callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Usuario usuario = dao.comprobarContraseniaCorrecta(username,email,password);
                Usuario usuario1 = dao.autenticar(username, password);
                if (usuario == null){
                    callback.cuandoUsuariooEsVacio();
                } else if(email == null){
                    callback.cuandoEmailEsVacio();
                }
                else if(password == null){
                    callback.cuandoConstraseniaEsVacio();
                }
                //preguntar a Gerard
                else if(!usuario.email.equals(email)){
                    callback.cuandoEmailYUsuarioNoCoinciden();
                }
                else{
                    dao.updateContrasenia(usuario);
                    callback.cuandoContraseniaCambiada();
                }

            }
        });
    }




    public void cambiarNombreApellido(String nombre,String apellido, int userId) {
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


    public LiveData<Usuario> obtenerUsuario(int userId){
        return dao.getUser(userId);
    }

}

