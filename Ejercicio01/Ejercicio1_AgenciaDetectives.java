import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Ejercicio1_AgenciaDetectives {
    public static void main(String[] args) {
        String nombreArchivo = "pistas.txt";
        int contadorPistas = 0;

        File archivo = new File(nombreArchivo);

        if (!archivo.exists()) {
            System.out.println("El archivo " + nombreArchivo + " no existe.");
            return;
        }

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea = lector.readLine();

            System.out.println("=== PISTAS DE LA INVESTIGACION ===");

            while (linea != null) {
                contadorPistas++;
                System.out.println("Pista " + contadorPistas + ": " + linea);
                linea = lector.readLine();
            }

            System.out.println("\nTotal de pistas encontradas: " + contadorPistas);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
