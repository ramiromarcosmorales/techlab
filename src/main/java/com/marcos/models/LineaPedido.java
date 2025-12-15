package com.marcos.models;

public class LineaPedido {
    private final Producto producto;
    private final int cantidad;
    private final double precioUnitario;

    private LineaPedido(Producto producto, int cantidad) {
        if (producto == null) throw new IllegalArgumentException("El producto no puede ser nulo");

        if (cantidad < 1) throw new IllegalArgumentException("La cantidad debe ser mayor a 0");

        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
    }

    // metodo factory
    public static LineaPedido crearLineaPedido(Producto producto, int cantidad) {
        return new LineaPedido(producto, cantidad);
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getSubtotal() {
        return cantidad * precioUnitario;
    }

    @Override
    public String toString() {
        return "LineaPedido{" +
                "producto=" + producto +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                '}';
    }
}
