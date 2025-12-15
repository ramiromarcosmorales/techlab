package com.marcos.app;

import com.marcos.exceptions.NotFoundException;
import com.marcos.exceptions.StockInsuficienteException;
import com.marcos.models.Pedido;
import com.marcos.models.Producto;
import com.marcos.services.PedidoService;
import com.marcos.services.ProductoService;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class TechLab {
    private final ProductoService productoService;
    private final PedidoService pedidoService;
    private final Scanner sc = new Scanner(System.in);

    public TechLab() {
        productoService = new ProductoService();
        pedidoService = new PedidoService(productoService);
    }

    public void run() {
        int opcion = -1;
        imprimirMenu();
        do {
            try {
                opcion = leerOpcion();
                switch (opcion) {
                    case 1 -> agregarProducto();
                    case 2 -> listarProductos();
                    case 3 -> buscarProducto();
                    case 4 -> actualizarProducto();
                    case 5 -> eliminarProducto();
                    case 6 -> crearPedido();
                    case 7 -> listarPedidos();
                    case 8 -> eliminarPedido();
                    case 9 -> System.out.println("Salir");
                    default -> System.out.println("Opcion invalida");
                }
            } catch (NotFoundException | StockInsuficienteException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Entrada invalida, vuelve a reintentar:");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println();
        } while (opcion != 9);
    }

    private void imprimirMenu() {
        System.out.println("===================================");
        System.out.println("SISTEMA DE GESTIÓN - TECHLAB");
        System.out.println("===================================");
        System.out.println("1. Agregar Producto");
        System.out.println("2. Listar Productos");
        System.out.println("3. Buscar Producto");
        System.out.println("4. Actualizar Producto");
        System.out.println("5. Eliminar Producto");
        System.out.println("6. Crear pedido");
        System.out.println("7. Listar pedidos");
        System.out.println("8. Eliminar pedido");
        System.out.println("9. Salir");
        System.out.println("===================================");
    }

    private int leerOpcion() {
        int opcion = -1;
        boolean valido = false;

        System.out.println("Ingresa una opción: ");
        while (!valido) {
            try {
                opcion = sc.nextInt();
                valido = true;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("La opción ingresada es incorrecta, vuelve a intentar:");
            }
        }

        return opcion;
    }

    private void agregarProducto() {
        sc.nextLine();

        System.out.println("Ingresa el nombre del producto: ");
        String nombre = sc.nextLine();
        System.out.println("Ingresa el precio del producto: ");
        double precio = sc.nextDouble();
        System.out.println("Ingresa la cantidad del producto: ");
        int cantidad = sc.nextInt();

        productoService.crearProducto(nombre, precio, cantidad);
    }

    private void listarProductos() {
        productoService.obtenerProductos().forEach(System.out::println);
    }

    private void buscarProducto() {
        sc.nextLine();

        System.out.println("Ingresa el ID del producto: ");
        int id = sc.nextInt();

        Producto producto = productoService.obtenerProductoPorId(id);

        System.out.println(producto);
    }

    private void eliminarProducto() {
        sc.nextLine();

        System.out.println("Ingresa el ID del producto: ");
        int id = sc.nextInt();

        productoService.eliminarProductoPorId(id);
    }

    private Producto actualizarProducto() {
        sc.nextLine();

        System.out.println("Ingresa el ID del producto: ");
        int id = sc.nextInt();

        Producto prod = productoService.obtenerProductoPorId(id);
        sc.nextLine();

        if (prod != null) {
            System.out.println("Ingresa el nombre del producto: ");
            String nombre = sc.nextLine();
            System.out.println("Ingresa el precio del producto: ");
            double precio = sc.nextDouble();
            System.out.println("Ingresa la cantidad del producto: ");
            int cantidad = sc.nextInt();

            return productoService.actualizarProducto(prod, nombre, precio, cantidad);
        }

        return null;
    }

    private void crearPedido() {
        sc.nextLine();

        System.out.println("Ingresa la cantidad de lineas del pedido");
        int n = sc.nextInt();

        Map<Integer, Integer> items = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            System.out.println("-- Línea " + i + " --");
            System.out.println("Ingresa el ID del producto: ");
            int id = sc.nextInt();
            System.out.println("Ingresa la cantidad: ");
            int cantidad = sc.nextInt();

            items.merge(id, cantidad, Integer::sum);
        }

        Pedido pedido = pedidoService.crearPedido(items);
        System.out.println("Pedido creado con éxito. ID: " + pedido.getId() + " | Total: " + pedido.getTotal());
    }

    private void listarPedidos() {
        pedidoService.listarPedidos().forEach(System.out::println);
    }

    private void eliminarPedido() {
        sc.nextLine();

        System.out.println("Ingresa el ID del pedido: ");
        int id = sc.nextInt();

        pedidoService.eliminarPedidoPorId(id);
    }
}
