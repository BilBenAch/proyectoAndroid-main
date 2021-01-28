package com.example.proyectoandroid.model;

import androidx.room.TypeConverters;

import java.util.List;

public class ProductoFavorito {
    public int id;
    public String nombre;
    public boolean esFavorito;
    public String tipoProducto;
    public String colorProducto;
    public Double precioProducto;
    public  String etiqueta;
    @TypeConverters(ListIntegerConverter.class)
    public List<Integer> imagenes;

}
