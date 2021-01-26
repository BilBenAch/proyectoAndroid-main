package com.example.proyectoandroid.model;

import androidx.room.TypeConverters;

import java.util.List;

public class ProductoCarritoFavorito {
    public int id;
    public String nombre;
    public boolean isFavorito;
    @TypeConverters(ListIntegerConverter.class)
    public List<Integer> imagenes;

}
