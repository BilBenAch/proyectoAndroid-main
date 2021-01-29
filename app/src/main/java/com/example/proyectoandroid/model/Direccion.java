package com.example.proyectoandroid.model;

import androidx.room.Entity;


@Entity(primaryKeys = {"userId"})
public class Direccion {
    public int userId;
    public String direccion;
    public String telefono;

    public Direccion(int userId, String direccion, String telefono) {
        this.userId = userId;
        this.direccion = direccion;
        this.telefono = telefono;
    }
}
