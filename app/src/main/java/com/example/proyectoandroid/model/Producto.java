package com.example.proyectoandroid.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

@Entity
public class Producto {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String nombre;
    @TypeConverters(ListIntegerConverter.class)
    public List<Integer> imagenes;
    public Integer valoracion;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Integer> getImagenes() {
        return imagenes;
    }

    public Integer getValoracion() {
        return valoracion;
    }

    public Producto(String nombre, List<Integer> imagenes) {
        this.nombre = nombre;
        this.imagenes = imagenes;
        this.valoracion = 0;
    }

}
