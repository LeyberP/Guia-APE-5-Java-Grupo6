import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ControlTemperatura {

    private static final String NOMBRE_ARCHIVO = "temperaturas.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\n--- CONTROL DE TEMPERATURA ---");
            System.out.println("1. Registrar nuevas temperaturas");
            System.out.println("2. Analizar registros (Promedio, Máxima, > 30°C)");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        guardarTemperaturas(scanner);
                        break;
                    case 2:
                        analizarTemperaturas();
                        break;
                    case 3:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número entero para la opción.");
            }
        } while (opcion != 3);

        scanner.close();
    }

    // Método para guardar nuevas temperaturas en el archivo sin sobrescribir las anteriores
    public static void guardarTemperaturas(Scanner scanner) {
        System.out.print("¿Cuántas temperaturas desea ingresar?: ");
        int cantidad;

        try {
            cantidad = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Cantidad no válida.");
            return;
        }

        // FileWriter con 'true' permite agregar datos al final del archivo (append)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            for (int i = 1; i <= cantidad; i++) {
                System.out.print("Ingrese la temperatura #" + i + ": ");
                String entrada = scanner.nextLine().trim();

                // Validamos que sea un número antes de guardarlo (opcional, también se valida al leer)
                try {
                    double temp = Double.parseDouble(entrada);
                    writer.write(String.valueOf(temp));
                    writer.newLine();
                } catch (NumberFormatException e) {
                    System.out.println("Advertencia: '" + entrada + "' no es un número válido y no se guardará.");
                    i--; // Repetir este registro
                }
            }
            System.out.println("Registros guardados correctamente en " + NOMBRE_ARCHIVO);
        } catch (IOException e) {
            System.out.println("Error de entrada/salida al escribir en el archivo: " + e.getMessage());
        }
    }

    // Método para leer el archivo, convertir datos y calcular estadísticas
    public static void analizarTemperaturas() {
        File archivo = new File(NOMBRE_ARCHIVO);

        if (!archivo.exists() || archivo.length() == 0) {
            System.out.println("El archivo '" + NOMBRE_ARCHIVO + "' no existe o está vacío.");
            return;
        }

        double suma = 0;
        double maxima = Double.NEGATIVE_INFINITY;
        int contadorValidos = 0;
        int mayoresA30 = 0;
        int numeroLinea = 0;

        System.out.println("\n--- LECTURA DE REGISTROS ---");

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                numeroLinea++;
                linea = linea.trim();

                if (linea.isEmpty()) {
                    continue; // Ignorar líneas en blanco
                }

                try {
                    // Convertir el texto leído a valor numérico
                    double temperatura = Double.parseDouble(linea);

                    suma += temperatura;
                    contadorValidos++;

                    if (temperatura > maxima) {
                        maxima = temperatura;
                    }

                    if (temperatura > 30.0) {
                        mayoresA30++;
                    }

                    System.out.println("Registro #" + numeroLinea + ": " + temperatura + " °C");

                } catch (NumberFormatException e) {
                    // Control de errores si el archivo fue editado manualmente con datos inválidos
                    System.out.println("Error de formato en línea " + numeroLinea + ": '" + linea + "' no es un valor numérico válido.");
                }
            }

            if (contadorValidos > 0) {
                double promedio = suma / contadorValidos;
                System.out.println("\n--- RESULTADOS DEL ANÁLISIS ---");
                System.out.println("Total de registros válidos: " + contadorValidos);
                System.out.printf("Temperatura promedio: %.2f °C%n", promedio);
                System.out.println("Temperatura más alta: " + maxima + " °C");
                System.out.println("Registros que superan los 30 °C: " + mayoresA30);
            } else {
                System.out.println("No se encontraron registros numéricos válidos para analizar.");
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
