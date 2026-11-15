import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio6_MensajesSecretos {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        String nombreArchivo = "secreto.txt";

        try {
            // Escritura con FileWriter + BufferedWriter en modo agregado (append = true),
            // para no sobrescribir los mensajes anteriores
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
                System.out.println("Escribe los mensajes secretos (escribe 'fin' para terminar):");
                String mensaje = teclado.nextLine();

                while (!mensaje.equalsIgnoreCase("fin")) {
                    escritor.write(mensaje);
                    escritor.newLine();
                    System.out.println("Mensaje guardado correctamente.");
                    mensaje = teclado.nextLine();
                }
            }

            // Lectura con FileReader + BufferedReader
            File archivo = new File(nombreArchivo);
            int contadorMensajes = 0;

            try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
                String linea = lector.readLine();

                System.out.println("\n=== MENSAJES REGISTRADOS ===");

                while (linea != null) {
                    contadorMensajes++;
                    System.out.println("Mensaje " + contadorMensajes + ": " + linea);
                    linea = lector.readLine();
                }
            }

            System.out.println("\nTotal de mensajes registrados: " + contadorMensajes);
        } catch (IOException e) {
            System.out.println("Error de entrada/salida: " + e.getMessage());
        } finally {
            teclado.close();
        }
    }
}
