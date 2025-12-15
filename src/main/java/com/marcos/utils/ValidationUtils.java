package com.marcos.utils;

public class ValidationUtils {

    private ValidationUtils() {
    }

    public static void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        String n = nombre.trim();
        if (n.length() < 2 || n.length() > 60) {
            throw new IllegalArgumentException("El nombre debe tener entre 2 y 60 caracteres.");
        }
    }

    public static void validarPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
    }

    public static void validarStock(int cantidadStock) {
        if (cantidadStock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
    }

    public static String normalizar(String valor) {
        return valor == null ? "" : valor.trim().toLowerCase();
    }
}
