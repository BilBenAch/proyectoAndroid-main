package com.example.proyectoandroid.model;

import androidx.room.TypeConverters;

import java.util.List;

public class ProductoFavorito {
    public int id;
    public String nombre;
    public boolean esFavorito;
    @TypeConverters(ListIntegerConverter.class)
    public List<Integer> imagenes;

}
