package com.marcos.services;

import com.marcos.exceptions.NotFoundException;
import com.marcos.models.Producto;

import java.util.ArrayList;
import java.util.List;

import static com.marcos.utils.ValidationUtils.*;

public class ProductoService {
    private final ArrayList<Producto> productos = new ArrayList<>();

    public Producto crearProducto(String nombre, double precio, int cantidadStock) {
        validarNombre(nombre);
        validarPrecio(precio);
        validarStock(cantidadStock);

        String busqueda = normalizar(nombre);
        boolean duplicado = productos.stream()
                .anyMatch(p -> normalizar(p.getNombre()).equals(busqueda));
        if (duplicado) throw new IllegalArgumentException("Ya existe un producto con ese nombre");

        Producto producto = Producto.crearProducto(nombre.trim(), precio, cantidadStock);
        productos.add(producto);

        return producto;
    }

    public boolean eliminarProductoPorId(int id) {
        boolean eliminado = productos.removeIf(p -> p.getId() == id);

        if (!eliminado) throw new NotFoundException("No existe un producto con ese id");

        return true;
    }

    public boolean eliminarProductoPorNombre(String nombre) {
        validarNombre(nombre);

        String busqueda = normalizar(nombre);
        boolean eliminar = productos.removeIf(p -> normalizar(p.getNombre()).equals(busqueda));

        if (!eliminar) throw new NotFoundException("No existe un producto con ese nombre");

        return true;
    }

    public List<Producto> obtenerProductos() {
        // devolver una lista copia inmutable
        return List.copyOf(productos);
    }

    public Producto obtenerProductoPorId(int id) {
        return productos.stream()
                .filter(producto -> producto.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No existe un producto con ese id"));
    }

    public Producto obtenerProductoPorNombre(String nombre) {
        validarNombre(nombre);
        String busqueda = normalizar(nombre);

        return productos.stream()
                .filter(producto -> normalizar(producto.getNombre()).equals(busqueda))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No existe un producto con ese nombre"));
    }

    public Producto actualizarProducto(Producto producto, String nombre, double precio, int cantidadStock) {
        if (producto == null) throw new IllegalArgumentException("No existe el producto");
        validarNombre(nombre);
        validarPrecio(precio);
        validarStock(cantidadStock);

        String busqueda = normalizar(nombre);
        boolean ocupado = productos.stream()
                        .anyMatch(p -> p.getId() != producto.getId() && normalizar(p.getNombre()).equals(busqueda));

        if (ocupado) throw new IllegalArgumentException("Ya existe un producto con ese nombre");

        producto.setNombre(nombre.trim());
        producto.setPrecio(precio);
        producto.setCantidadStock(cantidadStock);

        return producto;
    }
}
