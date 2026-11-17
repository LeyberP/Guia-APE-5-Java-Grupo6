import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Ejercicio 10: Sistema de ventas de una cafetería.
 *
 * Las ventas se guardan en ventas.txt con el formato: producto,precio
 * El programa permite:
 *  - Registrar nuevas ventas con BufferedWriter
 *  - Leer los registros con BufferedReader
 *  - Convertir los precios de texto a double
 *  - Calcular el total de ventas
 *  - Identificar el producto de mayor precio
 *  - Filtrar los productos que superan un valor determinado
 *  - Validar que los precios sean correctos
 */
public class Ejercicio10_SistemaVentasCafeteria {

    private static final String ARCHIVO = "ventas.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero(sc, "Elige una opción: ");

            switch (opcion) {
                case 1:
                    registrarVenta(sc);
                    break;
                case 2:
                    mostrarVentasYTotal();
                    break;
                case 3:
                    mostrarProductoMayorPrecio();
                    break;
                case 4:
                    double limite = leerPrecio(sc, "Mostrar productos con precio mayor a: ");
                    filtrarPorPrecio(limite);
                    break;
                case 5:
                    System.out.println("Hasta pronto.");
                    break;
                default:
                    System.out.println("Opción inválida. Elige un número del 1 al 5.");
            }
        } while (opcion != 5);

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("=== SISTEMA DE VENTAS - CAFETERÍA ===");
        System.out.println("1. Registrar nueva venta");
        System.out.println("2. Mostrar ventas y total");
        System.out.println("3. Producto de mayor precio");
        System.out.println("4. Filtrar productos por precio");
        System.out.println("5. Salir");
    }

    // ---------------------------------------------------------------
    // Registro de ventas (BufferedWriter)
    // ---------------------------------------------------------------

    private static void registrarVenta(Scanner sc) {
        System.out.print("Nombre del producto: ");
        String producto = sc.nextLine().trim();

        if (producto.isEmpty() || producto.contains(",")) {
            System.out.println("Nombre inválido: no puede estar vacío ni contener comas.");
            return;
        }

        double precio = leerPrecio(sc, "Precio: ");

        // true = agregar al final del archivo sin borrar lo existente
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(producto + "," + precio);
            bw.newLine();
            System.out.println("Venta registrada: " + producto + " - $" + String.format("%.2f", precio));
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // Lectura de ventas (BufferedReader) y validación
    // ---------------------------------------------------------------

    /** Lee ventas.txt y devuelve las ventas válidas. Reporta las líneas inválidas. */
    private static List<Venta> leerVentas() {
        List<Venta> ventas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            int numeroLinea = 0;

            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                linea = linea.trim();
                if (linea.isEmpty()) {
                    continue;
                }

                String[] partes = linea.split(",");
                if (partes.length != 2) {
                    System.out.println("Línea " + numeroLinea + " con formato inválido (se omite): " + linea);
                    continue;
                }

                String producto = partes[0].trim();
                if (producto.isEmpty()) {
                    System.out.println("Línea " + numeroLinea + " sin nombre de producto (se omite): " + linea);
                    continue;
                }

                try {
                    // Conversión de texto a número decimal
                    double precio = Double.parseDouble(partes[1].trim());
                    if (precio <= 0 || Double.isNaN(precio) || Double.isInfinite(precio)) {
                        System.out.println("Línea " + numeroLinea + " con precio no válido (se omite): " + linea);
                        continue;
                    }
                    ventas.add(new Venta(producto, precio));
                } catch (NumberFormatException e) {
                    System.out.println("Línea " + numeroLinea + " con precio no numérico (se omite): " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer '" + ARCHIVO + "': " + e.getMessage());
        }

        return ventas;
    }

    // ---------------------------------------------------------------
    // Consultas
    // ---------------------------------------------------------------

    private static void mostrarVentasYTotal() {
        List<Venta> ventas = leerVentas();
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        double total = 0;
        System.out.println();
        System.out.printf("%-25s %10s%n", "PRODUCTO", "PRECIO");
        System.out.println("------------------------------------");
        for (Venta v : ventas) {
            System.out.printf("%-25s %10.2f%n", v.producto, v.precio);
            total += v.precio;
        }
        System.out.println("------------------------------------");
        System.out.println("Cantidad de ventas: " + ventas.size());
        System.out.printf("Total de ventas: $%.2f%n", total);
    }

    private static void mostrarProductoMayorPrecio() {
        List<Venta> ventas = leerVentas();
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        Venta mayor = ventas.get(0);
        for (Venta v : ventas) {
            if (v.precio > mayor.precio) {
                mayor = v;
            }
        }
        System.out.printf("Producto de mayor precio: %s ($%.2f)%n", mayor.producto, mayor.precio);
    }

    private static void filtrarPorPrecio(double limite) {
        List<Venta> ventas = leerVentas();
        int encontrados = 0;

        System.out.printf("Productos con precio mayor a $%.2f:%n", limite);
        for (Venta v : ventas) {
            if (v.precio > limite) {
                System.out.printf("  - %-25s $%.2f%n", v.producto, v.precio);
                encontrados++;
            }
        }
        if (encontrados == 0) {
            System.out.println("  (ningún producto supera ese valor)");
        }
    }

    // ---------------------------------------------------------------
    // Lectura segura desde teclado
    // ---------------------------------------------------------------

    private static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (!sc.hasNextLine()) {
                return 5; // fin de la entrada: salir
            }
            String texto = sc.nextLine().trim();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Escribe un número entero.");
            }
        }
    }

    /** Pide un precio y repite hasta que sea un número decimal positivo. */
    private static double leerPrecio(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (!sc.hasNextLine()) {
                return 0;
            }
            String texto = sc.nextLine().trim().replace(',', '.');
            try {
                double precio = Double.parseDouble(texto);
                if (precio > 0 && !Double.isNaN(precio) && !Double.isInfinite(precio)) {
                    return precio;
                }
                System.out.println("El precio debe ser mayor que 0.");
            } catch (NumberFormatException e) {
                System.out.println("Precio inválido. Escribe un número (ejemplo: 2.50).");
            }
        }
    }

    // Clase auxiliar para representar una venta
    private static class Venta {
        final String producto;
        final double precio;

        Venta(String producto, double precio) {
            this.producto = producto;
            this.precio = precio;
        }
    }
}
