import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Ejercicio 4: Parque de aventuras - resultados de participantes.
 *
 * Lee el archivo resultados.txt (formato: nombre,puntaje) usando BufferedReader,
 * muestra los participantes, calcula el puntaje total e identifica el puntaje más alto.
 */
public class Ejercicio4_ParqueAventuras {

    public static void main(String[] args) {
        String archivo = "resultados.txt";

        int total = 0;
        int maximo = Integer.MIN_VALUE;
        String ganador = "";
        int cantidad = 0;

        System.out.println("=== PARQUE DE AVENTURAS: RESULTADOS ===");
        System.out.println();
        System.out.printf("%-20s %s%n", "PARTICIPANTE", "PUNTAJE");
        System.out.println("-----------------------------");

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) {
                    continue; // ignora líneas vacías
                }

                String[] partes = linea.split(",");
                if (partes.length != 2) {
                    System.out.println("Línea con formato inválido (se omite): " + linea);
                    continue;
                }

                String nombre = partes[0].trim();
                int puntaje;
                try {
                    puntaje = Integer.parseInt(partes[1].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Puntaje inválido (se omite): " + linea);
                    continue;
                }

                System.out.printf("%-20s %d%n", nombre, puntaje);

                total += puntaje;
                cantidad++;

                if (puntaje > maximo) {
                    maximo = puntaje;
                    ganador = nombre;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo '" + archivo + "': " + e.getMessage());
            return;
        }

        System.out.println("-----------------------------");

        if (cantidad == 0) {
            System.out.println("No se encontraron participantes en el archivo.");
            return;
        }

        System.out.println("Total de participantes: " + cantidad);
        System.out.println("Puntaje total: " + total);
        System.out.println("Puntaje más alto: " + maximo + " (" + ganador + ")");
    }
}
