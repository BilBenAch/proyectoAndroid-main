package com.example.proyectoandroid.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

    @Entity
    public class Pedido {
        @PrimaryKey(autoGenerate = true)
        public int id;
        public int userId;
        public String fechaCompra;
        public String referencia;
        public boolean pendiente; // true


    @TypeConverters(Converters.class)
    public List<Carrito> carritoList;

    public Pedido(int userId, String referencia, String fechaCompra,List<Carrito> carritoList) {
        this.userId = userId;
        this.fechaCompra = fechaCompra;
        this.referencia = referencia;
        this.carritoList = carritoList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public List<Carrito> getCarritoID() {
        return carritoList;
    }

    public void setPedidosID(List<Carrito> carritoList) {
        this.carritoList = carritoList;
    }
}


//No implementado
/*

CREATE TABLE PEDIDO (idPedido integer, fecha Data, userId int)

CREATE TABLE LINEA_PEDIDO (idlinea integer; idPedido integer; idproducto integer; cantidad integer)

Pedido
67 23/23/223 user4
68 1/2/1223 user5

LineaPedido
4 67 Champu 7
5 67 Esponja 9

SELECT SUM(cantidad*precio), COUNT(*) FROM Pedido JOIN LineaPedido ON idPedido JOIN Producto ON idProducto WHERE userId = :userId GROUP BY idPedido

 */