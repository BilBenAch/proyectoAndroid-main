package com.example.proyectoandroid.modelLogin;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String username;
    public String email;
    public String password;
    public String password2;

    public Usuario(String username, String email, String password, String password2) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.password2 = password2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    @NonNull
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", password2='" + password2 + '\'' +
                '}';
    }
}
