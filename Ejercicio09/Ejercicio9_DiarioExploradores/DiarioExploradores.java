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
        Scanner teclado = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\n=== DIARIO DE EXPLORADORES ===");
            System.out.println("1. Registrar nuevo descubrimiento");
            System.out.println("2. Leer todos los descubrimientos y contar");
            System.out.println("3. Buscar descubrimiento por nombre");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            if (teclado.hasNextInt()) {
                opcion = teclado.nextInt();
                teclado.nextLine(); // Limpiar el buffer
            } else {
                System.out.println("Entrada inválida. Por favor ingrese un número.");
                teclado.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nombre o descripción del descubrimiento: ");
                    String nuevoRegistro = teclado.nextLine();
                    registrarDescubrimiento(nuevoRegistro);
                    break;
                case 2:
                    leerYContarDescubrimientos();
                    break;
                case 3:
                    System.out.print("Ingrese el nombre del descubrimiento a buscar: ");
                    String terminoBusqueda = teclado.nextLine();
                    buscarDescubrimiento(terminoBusqueda);
                    break;
                case 4:
                    System.out.println("Cerrando el diario de exploradores...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 4);

        teclado.close();
    }

    // Escribir y agregar al final del archivo (modo append = true)
    private static void registrarDescubrimiento(String descubrimiento) {
        if (descubrimiento.trim().isEmpty()) {
            System.out.println("No se puede guardar un registro vacío.");
            return;
        }

        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, true);
             BufferedWriter escritor = new BufferedWriter(fw)) {
            escritor.write(descubrimiento);
            escritor.newLine();
            System.out.println("Descubrimiento guardado exitosamente en '" + NOMBRE_ARCHIVO + "'.");
        } catch (IOException e) {
            System.out.println("Error de escritura al guardar el descubrimiento: " + e.getMessage());
        }
    }

    // Validar existencia y contenido
    private static boolean validarArchivo() {
        File archivo = new File(NOMBRE_ARCHIVO);
        if (!archivo.exists()) {
            System.out.println("Aviso: El archivo '" + NOMBRE_ARCHIVO + "' aún no existe. Registre un descubrimiento primero.");
            return false;
        }
        if (archivo.length() == 0) {
            System.out.println("Aviso: El archivo '" + NOMBRE_ARCHIVO + "' está vacío.");
            return false;
        }
        return true;
    }

    // Leer línea por línea y contar registros
    private static void leerYContarDescubrimientos() {
        if (!validarArchivo()) return;

        int contador = 0;
        System.out.println("\n--- LISTA DE DESCUBRIMIENTOS ---");
        try (FileReader fr = new FileReader(NOMBRE_ARCHIVO);
             BufferedReader lector = new BufferedReader(fr)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    contador++;
                    System.out.println(contador + ". " + linea);
                }
            }
            System.out.println("--------------------------------");
            System.out.println("Total de descubrimientos registrados: " + contador);
        } catch (IOException e) {
            System.out.println("Error de lectura en el archivo: " + e.getMessage());
        }
    }

    // Buscar un descubrimiento específico por nombre
    private static void buscarDescubrimiento(String busqueda) {
        if (!validarArchivo()) return;

        boolean encontrado = false;
        System.out.println("\n--- RESULTADOS DE BÚSQUEDA ---");
        try (FileReader fr = new FileReader(NOMBRE_ARCHIVO);
             BufferedReader lector = new BufferedReader(fr)) {
            String linea;
            int numeroLinea = 0;
            while ((linea = lector.readLine()) != null) {
                numeroLinea++;
                if (linea.toLowerCase().contains(busqueda.toLowerCase())) {
                    System.out.println("[Registro #" + numeroLinea + "] " + linea);
                    encontrado = true;
                }
            }
            if (!encontrado) {
                System.out.println("No se encontró ningún descubrimiento que coincida con: '" + busqueda + "'.");
            }
        } catch (IOException e) {
            System.out.println("Error al buscar en el archivo: " + e.getMessage());
        }
    }
}
