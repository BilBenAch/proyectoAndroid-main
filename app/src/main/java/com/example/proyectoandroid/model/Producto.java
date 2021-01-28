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
    public String tipoProducto;
    public String colorProducto;
    public String etiqueta;
    public Double precioProducto;

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

    public String getTipoProducto() {
        return tipoProducto;
    }

    public String getColorProducto() {
        return colorProducto;
    }

    public Double getPrecioProducto() {
        return precioProducto;
    }

    public Producto(String nombre, String tipoProducto, String colorProducto, Double precioProducto , String etiqueta ,List<Integer> imagenes) {
        this.nombre = nombre;
        this.tipoProducto = tipoProducto;
        this.colorProducto = colorProducto;
        this.precioProducto = precioProducto;
        this.etiqueta = etiqueta;
        this.imagenes = imagenes;
        this.valoracion = 0;
    }


}
