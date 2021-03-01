package com.example.proyectoandroid.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters {
    @TypeConverter
    public String fromCarritoList(List<Carrito> carrito) {
        if (carrito == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Carrito>>() {}.getType();
        String json = gson.toJson(carrito, type);
        return json;
    }

    @TypeConverter
    public List<Carrito> toCarritoList(String carritoString) {
        if (carritoString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Carrito>>() {}.getType();
        List<Carrito> carritoList = gson.fromJson(carritoString, type);
        return carritoList;
    }
}