import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CompetenciaVideojuegos {
    public static void main(String[] args) {
        String archivo = "jugadores.txt";
        String mejorJugador = "";
        double puntajeMayor = Double.NEGATIVE_INFINITY;
        double sumaPuntajes = 0;
        int cantidadJugadores = 0;

        System.out.println("===== COMPETENCIA DE VIDEOJUEGOS =====");

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(",", -1);

                if (datos.length != 2 || datos[0].trim().isEmpty()
                        || datos[1].trim().isEmpty()) {
                    System.out.println("Registro inválido: " + linea);
                    continue;
                }

                String nombre = datos[0].trim();
                double puntaje;

                try {
                    puntaje = Double.parseDouble(datos[1].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Puntaje no numérico, registro omitido: " + linea);
                    continue;
                }

                System.out.println("Jugador: " + nombre + " | Puntaje: " + puntaje);

                sumaPuntajes += puntaje;
                cantidadJugadores++;

                if (puntaje > puntajeMayor) {
                    puntajeMayor = puntaje;
                    mejorJugador = nombre;
                }

                if (puntaje > 70) {
                    System.out.println("  -> Supera los 70 puntos");
                }
            }

            System.out.println("\n===== RESUMEN DE LA COMPETENCIA =====");
            if (cantidadJugadores == 0) {
                System.out.println("No existen registros válidos para calcular resultados.");
            } else {
                double promedio = sumaPuntajes / cantidadJugadores;
                System.out.printf("Promedio de puntuaciones: %.2f%n", promedio);
                System.out.println("Jugador con mayor puntaje: " + mejorJugador);
                System.out.println("Puntaje más alto: " + puntajeMayor);
                System.out.println("Cantidad de jugadores válidos: " + cantidadJugadores);
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo " + archivo + ": " + e.getMessage());
        }
    }
}
