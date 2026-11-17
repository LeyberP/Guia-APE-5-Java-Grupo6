import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class EstacionEspacial {
    public static void main(String[] args) {
        File archivo = new File("mensaje.txt");

        if (!archivo.exists()) {
            System.out.println("Error: El archivo 'mensaje.txt' no existe en la ruta del proyecto.");
            return;
        }

        int contadorCaracteres = 0;

        System.out.println("=== MENSAJE DE EMERGENCIA RECIBIDO ===");
        try (FileReader lector = new FileReader(archivo)) {
            int caracter;
            // read() devuelve el código ASCII del carácter o -1 al llegar al final del archivo
            while ((caracter = lector.read()) != -1) {
                System.out.print((char) caracter);
                contadorCaracteres++;
            }
            System.out.println("\n======================================");
            System.out.println("Total de caracteres leídos: " + contadorCaracteres);

        } catch (IOException e) {
            System.out.println("Ocurrió un error de lectura: " + e.getMessage());
        }
    }
}
