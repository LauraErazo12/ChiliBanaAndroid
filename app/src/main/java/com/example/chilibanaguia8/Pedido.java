package com.example.chilibanaguia8;

public class Pedido {

    private int id;
    private String cliente;
    private String tipoPedido;
    private String producto;
    private int cantidad;

    public Pedido(int id, String cliente, String tipoPedido, String producto, int cantidad) {
        this.id = id;
        this.cliente = cliente;
        this.tipoPedido = tipoPedido;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public String getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}