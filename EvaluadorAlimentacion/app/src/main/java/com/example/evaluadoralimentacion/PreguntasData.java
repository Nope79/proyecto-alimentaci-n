package com.example.evaluadoralimentacion;

import java.util.ArrayList;
import java.util.List;

public class PreguntasData {

    public static List<Pregunta> getPreguntas() {
        List<Pregunta> preguntas = new ArrayList<>();

        // Ejemplo de primeras preguntas (adaptas según tu formulario)
        // Pregunta 1
        preguntas.add(new Pregunta(
                "1. ¿Cuántas frutas consumes al día?",
                new String[]{"Ninguna", "1 pieza", "2-3 piezas", "4 o más"},
                new int[]{0, 2, 4, 6}
        ));

        // Pregunta 2
        preguntas.add(new Pregunta(
                "2. ¿Cuántas verduras consumes al día?",
                new String[]{"Ninguna", "1 porción", "2 porciones", "3 o más"},
                new int[]{0, 2, 4, 6}
        ));

        // Pregunta 3
        preguntas.add(new Pregunta(
                "3. ¿Consumes comida chatarra a la semana?",
                new String[]{"Todos los días", "3-4 veces", "1-2 veces", "Ninguna"},
                new int[]{0, 2, 4, 6}
        ));

        // Pregunta 4
        preguntas.add(new Pregunta(
                "4. ¿Bebes refrescos o bebidas azucaradas?",
                new String[]{"Diario", "3-4 veces/semana", "1-2 veces/semana", "Nunca"},
                new int[]{0, 1, 3, 6}
        ));

        // Pregunta 5
        preguntas.add(new Pregunta(
                "5. ¿Cuánta agua tomas al día?",
                new String[]{"Menos de 1L", "1-2L", "2-3L", "Más de 3L"},
                new int[]{0, 2, 5, 8}
        ));

        // ... CONTINÚA HASTA PREGUNTA 30
        // Puedes seguir este patrón. Aquí te pongo un ejemplo rápido de cómo llenar:

        for (int i = 6; i <= 30; i++) {
            preguntas.add(new Pregunta(
                    i + ". [Tu pregunta aquí]",
                    new String[]{"Opción A", "Opción B", "Opción C", "Opción D"},
                    new int[]{0, 2, 4, 6}
            ));
        }

        return preguntas;
    }

    // Método para clasificar según puntaje total
    public static String clasificarAlimentacion(int puntajeTotal, int maxPuntaje) {
        double porcentaje = (puntajeTotal * 100.0) / maxPuntaje;

        if (porcentaje >= 80) {
            return "EXCELENTE alimentación 🎉🎉🎉";
        } else if (porcentaje >= 60) {
            return "BUENA alimentación 👍";
        } else if (porcentaje >= 40) {
            return "REGULAR alimentación 😐";
        } else if (porcentaje >= 20) {
            return "MALA alimentación ⚠️";
        } else {
            return "MUY MALA alimentación ❌ ¡Necesitas ayuda profesional!";
        }
    }
}