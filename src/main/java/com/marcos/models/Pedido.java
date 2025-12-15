package com.marcos.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static int contadorId = 1;
    private final int id;
    private final List<LineaPedido> lineas = new ArrayList<>();
    private final LocalDateTime fecha;

    public Pedido() {
        this.id = contadorId++;
        this.fecha = LocalDateTime.now();
    }

    public void agregarLinea(LineaPedido linea) {
        if (linea == null) throw new IllegalArgumentException("La línea del pedido no puede ser nula.");
        lineas.add(linea);
    }

    public int getId() {
        return id;
    }

    public List<LineaPedido> getLineas() {
        return List.copyOf(lineas);
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double getTotal() {
        return lineas.stream().mapToDouble(LineaPedido::getSubtotal).sum();
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", lineas=" + lineas +
                ", fecha=" + fecha +
                ", total=" + getTotal() +
                '}';
    }
}
