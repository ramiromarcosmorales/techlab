package com.marcos.services;

import com.marcos.exceptions.NotFoundException;
import com.marcos.models.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


public class ProductoServiceTest {
    ProductoService service;

    @BeforeEach
    void setup() {
        service = new ProductoService();
    }

    @Test
    void crearProducto() {
        Producto p = service.crearProducto("Cafe Premium", 1000, 15);
        assertNotNull(p);
        assertEquals("Cafe Premium", p.getNombre());
        assertEquals(1000, p.getPrecio());
        assertEquals(15, p.getCantidadStock());
        assertEquals(1, service.obtenerProductos().size());
    }

    @Test
    void crearProductoConNombreDuplicado() {
        service.crearProducto("Cafe Premium", 1000, 15);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.crearProducto("Cafe Premium", 1000, 15));
        assertTrue(ex.getMessage().toLowerCase().contains("ya existe"));
    }

    @Test
    void buscarProductoInexistentePorId() {
        assertThrows(NotFoundException.class, () -> service.obtenerProductoPorId(500));
    }

    @Test
    void eliminarProductoPorId() {
        Producto p = service.crearProducto("Cafe Premium", 1000, 15);
        assertTrue(service.eliminarProductoPorId(p.getId()));
        assertEquals(0, service.obtenerProductos().size());
    }



}
