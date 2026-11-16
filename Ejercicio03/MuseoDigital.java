import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class MuseoDigital {
    public static void main(String[] args) {
        String archivo = "obras.txt";
        Map<String, Integer> categorias = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        int totalObras = 0;

        System.out.println("===== MUSEO DIGITAL: REGISTRO DE OBRAS =====");

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(",", 2);

                if (datos.length != 2 || datos[0].trim().isEmpty()
                        || datos[1].trim().isEmpty()) {
                    System.out.println("Registro inválido: " + linea);
                    continue;
                }

                String nombre = datos[0].trim();
                String categoria = datos[1].trim();

                System.out.println("Obra: " + nombre + " | Categoría: " + categoria);

                categorias.put(categoria, categorias.getOrDefault(categoria, 0) + 1);
                totalObras++;
            }

            System.out.println("\n===== CLASIFICACIÓN POR CATEGORÍA =====");
            if (totalObras == 0) {
                System.out.println("No se encontraron obras válidas.");
            } else {
                for (Map.Entry<String, Integer> entrada : categorias.entrySet()) {
                    System.out.println(entrada.getKey() + ": " + entrada.getValue() + " obra(s)");
                }
                System.out.println("Total de obras registradas: " + totalObras);
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo " + archivo + ": " + e.getMessage());
        }
    }
}
