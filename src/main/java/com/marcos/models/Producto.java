package com.marcos.models;

public class Producto {
    private static int contadorId = 1;
    private int id;
    private String nombre;
    private double precio;
    private int cantidadStock;

    private Producto(String nombre, double precio, int cantidadStock) {
        this.id = contadorId++;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
    }

    // metodo factory
    public static Producto crearProducto(String nombre, double precio, int cantidadStock) {
        return new Producto(nombre, precio, cantidadStock);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", cantidadStock=" + cantidadStock +
                '}';
    }
}
