package com.example.proyectoandroid.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"userId", "productoId"})
public class Carrito {
    public int userId;
    public int productoId;
    public int cantidad = 1;
     //private String tallaEscogida;

 //   public Carrito(){
        
   // }

    public Carrito(int userId, int productoId) {
        this.userId = userId;
        this.productoId = productoId;
        //  this.tallaEscogida = tallaEscogida;
    }

}