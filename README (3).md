# Ejercicio 4: Parque de aventuras - resultados de participantes

## Enunciado

Un parque de aventuras almacena los nombres y puntajes de sus participantes en un archivo llamado `resultados.txt`. Desarrolla un programa que lea la información utilizando `BufferedReader`, muestre los participantes, calcule el puntaje total e identifique el puntaje más alto.

## Archivos

| Archivo | Descripción |
|---|---|
| `Ejercicio4_ParqueAventuras.java` | Solución del ejercicio |
| `resultados.txt` | Datos de entrada (formato `nombre,puntaje`) |
| `README.md` | Descripción del ejercicio |

## Conceptos utilizados

- Lectura de archivos con `BufferedReader` y `FileReader`
- `try-with-resources` para cerrar el archivo automáticamente
- Manejo de excepciones (`IOException`, `NumberFormatException`)
- Procesamiento de cadenas con `split` y `trim`
- Acumulador (total) y búsqueda del máximo

## Cómo ejecutar

Desde la carpeta `Ejercicio04`:

```bash
javac Ejercicio4_ParqueAventuras.java
java Ejercicio4_ParqueAventuras
```

> El archivo `resultados.txt` debe estar en la misma carpeta desde donde se ejecuta el programa.

## Ejemplo de salida

```
=== PARQUE DE AVENTURAS: RESULTADOS ===

PARTICIPANTE         PUNTAJE
-----------------------------
Carlos Mendoza       85
Lucia Fernandez      92
Andres Paredes       78
Valentina Rojas      95
Mateo Salazar        88
Camila Vargas        70
Sebastian Torres     91
-----------------------------
Total de participantes: 7
Puntaje total: 599
Puntaje más alto: 95 (Valentina Rojas)
```
