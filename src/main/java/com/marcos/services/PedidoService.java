package com.marcos.services;

import com.marcos.exceptions.NotFoundException;
import com.marcos.exceptions.StockInsuficienteException;
import com.marcos.models.LineaPedido;
import com.marcos.models.Pedido;
import com.marcos.models.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PedidoService {
    private final List<Pedido> pedidos = new ArrayList<>();
    private final ProductoService productoService;

    public PedidoService(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * Flujo:
     * 1) Validar entrada, existencia de productos y cantidades > 0.
     * 2) Verificar stock suficiente para TODOS los ítems (sin descontar aún).
     * 3) Crear Pedido y sus Lineas (congela precioUnitario).
     * 4) Descontar stock de cada producto.
     * 5) Guardar y devolver el Pedido.
     */
    public Pedido crearPedido(Map<Integer, Integer> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("El pedido debe contener al menos un ítem");
        }

        List<Producto> productos = new ArrayList<>();
        List<Integer> cantidades = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
            Integer idProducto = entry.getKey();
            Integer cantidad = entry.getValue();

            if (idProducto == null) {
                throw new IllegalArgumentException("Se encontró un ítem con idProducto nulo");
            }
            if (cantidad == null || cantidad <= 0) {
                throw new IllegalArgumentException("Cantidad inválida para el producto con id " + idProducto + ". Debe ser mayor a 0");
            }

            // puede lanzar NotFoundException si no existe
            Producto p = productoService.obtenerProductoPorId(idProducto);
            productos.add(p);
            cantidades.add(cantidad);
        }

        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            int cant = cantidades.get(i);
            if (p.getCantidadStock() < cant) {
                throw new StockInsuficienteException(
                        "Producto \"" + p.getNombre() + "\": stock disponible " + p.getCantidadStock() + ", solicitado " + cant
                );
            }
        }

        Pedido pedido = new Pedido();
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            int cant = cantidades.get(i);
            pedido.agregarLinea(LineaPedido.crearLineaPedido(p, cant));
        }

        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            int cant = cantidades.get(i);
            p.setCantidadStock(p.getCantidadStock() - cant);
        }

        pedidos.add(pedido);
        return pedido;
    }

    public List<Pedido> listarPedidos() {
        return List.copyOf(pedidos);
    }

    public Pedido obtenerPedidoPorId(int id) {
        return pedidos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No se encontró el pedido con id " + id));
    }

    public boolean eliminarPedidoPorId(int id) {
        return pedidos.removeIf(p -> p.getId() == id);
    }
}
