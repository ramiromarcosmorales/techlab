package com.marcos.services;

import com.marcos.models.Pedido;
import com.marcos.models.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoServiceTest {
    ProductoService productoService;
    PedidoService pedidoService;
    Producto mate, cafe;

    @BeforeEach
    void setUp() {
        productoService = new ProductoService();
        pedidoService = new PedidoService(productoService);
        mate = productoService.crearProducto("mate", 100, 8);
        cafe = productoService.crearProducto("cafe", 50, 10);
    }

    @Test
    void crearPedido() {
        Map<Integer,Integer> items = new LinkedHashMap<>();
        items.put(cafe.getId(), 2);
        items.put(mate.getId(), 3);

        Pedido pedido = pedidoService.crearPedido(items);

        assertNotNull(pedido);
        assertEquals(50*2 + 100*3, pedido.getTotal(), 0.0001);
        assertEquals(8, cafe.getCantidadStock());
        assertEquals(5, mate.getCantidadStock());
        assertEquals(1, pedidoService.listarPedidos().size());
    }

    @Test
    void eliminarPedido() {
        Map<Integer,Integer> items = Map.of(cafe.getId(), 2);
        Pedido pedido = pedidoService.crearPedido(items);

        assertTrue(pedidoService.eliminarPedidoPorId(pedido.getId()));
        assertEquals(0, pedidoService.listarPedidos().size());
    }
}
