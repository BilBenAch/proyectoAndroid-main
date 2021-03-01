package com.example.proyectoandroid.model;

import androidx.room.TypeConverters;

import java.util.List;

public class PedidoCarrito {
    @TypeConverters(Converters.class)
    public List<Carrito> carritoList;
}
