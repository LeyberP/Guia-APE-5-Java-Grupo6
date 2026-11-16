import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DiarioExploradores {

    private static final String NOMBRE_ARCHIVO = "descubrimientos.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\n=== DIARIO DE EXPLORADORES ===");
            System.out.println("1. Escribir nuevos descubrimientos (sobrescribir archivo)");
            System.out.println("2. Agregar descubrimiento al final del archivo");
            System.out.println("3. Leer todos los descubrimientos");
            System.out.println("4. Contar total de descubrimientos");
            System.out.println("5. Buscar un descubrimiento por nombre");
            System.out.println("6. Salir");
            System.out.print("Selecciona una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingresa un número válido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    escribirNuevosDescubrimientos(scanner);
                    break;
                case 2:
                    agregarDescubrimiento(scanner);
                    break;
                case 3:
                    leerRegistros();
                    break;
                case 4:
                    contarDescubrimientos();
                    break;
                case 5:
                    buscarDescubrimiento(scanner);
                    break;
                case 6:
                    System.out.println("Cerrando el diario de exploradores...");
                    break;
                default:
                    System.out.println("Opción no válida. Intenta nuevamente.");
            }
        } while (opcion != 6);

        scanner.close();
    }

    // 1. Escribir nuevos descubrimientos (crea el archivo o reemplaza su contenido)
    private static void escribirNuevosDescubrimientos(Scanner scanner) {
        System.out.println("\n--- ESCRIBIR NUEVOS DESCUBRIMIENTOS ---");
        System.out.println("Ingresa cada descubrimiento (escribe 'FIN' para terminar):");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, false))) {
            while (true) {
                System.out.print("> ");
                String entrada = scanner.nextLine().trim();

                if (entrada.equalsIgnoreCase("FIN")) {
                    break;
                }
                if (!entrada.isEmpty()) {
                    bw.write(entrada);
                    bw.newLine();
                }
            }
            System.out.println("Registros guardados correctamente en '" + NOMBRE_ARCHIVO + "'.");
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    // 2. Agregar información al final del archivo existente (modo append = true)
    private static void agregarDescubrimiento(Scanner scanner) {
        System.out.println("\n--- AGREGAR DESCUBRIMIENTO ---");
        System.out.print("Ingresa el nuevo descubrimiento: ");
        String nuevoRegistro = scanner.nextLine().trim();

        if (nuevoRegistro.isEmpty()) {
            System.out.println("No se puede agregar un registro vacío.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            bw.write(nuevoRegistro);
            bw.newLine();
            System.out.println("Descubrimiento agregado al final de '" + NOMBRE_ARCHIVO + "'.");
        } catch (IOException e) {
            System.err.println("Error al agregar información al archivo: " + e.getMessage());
        }
    }

    // 3. Leer los registros línea por línea
    private static void leerRegistros() {
        File archivo = new File(NOMBRE_ARCHIVO);
        if (!validarArchivo(archivo)) {
            return;
        }

        System.out.println("\n--- LISTA DE DESCUBRIMIENTOS ---");
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int numeroLinea = 1;

            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    System.out.println(numeroLinea + ". " + linea);
                    numeroLinea++;
                }
            }

            if (numeroLinea == 1) {
                System.out.println("El archivo no contiene registros válidos.");
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // 4. Contar los descubrimientos registrados
    private static void contarDescubrimientos() {
        File archivo = new File(NOMBRE_ARCHIVO);
        if (!validarArchivo(archivo)) {
            return;
        }

        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    contador++;
                }
            }
            System.out.println("\nTotal de descubrimientos registrados: " + contador);
        } catch (IOException e) {
            System.err.println("Error al contar los registros: " + e.getMessage());
        }
    }

    // 5. Buscar un descubrimiento específico por su nombre
    private static void buscarDescubrimiento(Scanner scanner) {
        File archivo = new File(NOMBRE_ARCHIVO);
        if (!validarArchivo(archivo)) {
            return;
        }

        System.out.print("\nIngresa el nombre del descubrimiento a buscar: ");
        String nombreBuscado = scanner.nextLine().trim();

        if (nombreBuscado.isEmpty()) {
            System.out.println("Debes ingresar un término de búsqueda.");
            return;
        }

        boolean encontrado = false;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int numeroLinea = 1;

            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    if (linea.toLowerCase().contains(nombreBuscado.toLowerCase())) {
                        System.out.println("Coincidencia en línea " + numeroLinea + ": " + linea);
                        encontrado = true;
                    }
                    numeroLinea++;
                }
            }

            if (!encontrado) {
                System.out.println("No se encontró ningún descubrimiento con el nombre '" + nombreBuscado + "'.");
            }
        } catch (IOException e) {
            System.err.println("Error durante la búsqueda en el archivo: " + e.getMessage());
        }
    }

    // 6. Validar que el archivo exista y contenga información antes de procesarlo
    private static boolean validarArchivo(File archivo) {
        if (!archivo.exists()) {
            System.out.println("\nError: El archivo '" + NOMBRE_ARCHIVO + "' no existe.");
            return false;
        }
        if (archivo.length() == 0) {
            System.out.println("\nAviso: El archivo '" + NOMBRE_ARCHIVO + "' está vacío (no contiene información).");
            return false;
        }
        return true;
    }
}
