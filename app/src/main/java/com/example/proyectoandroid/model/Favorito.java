package com.example.proyectoandroid.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

@Entity(primaryKeys = {"userId", "productoId"})
public class Favorito {
    public int userId;
    public int productoId;

    public Favorito(int userId, int productoId) {
        this.userId = userId;
        this.productoId = productoId;
    }
}


