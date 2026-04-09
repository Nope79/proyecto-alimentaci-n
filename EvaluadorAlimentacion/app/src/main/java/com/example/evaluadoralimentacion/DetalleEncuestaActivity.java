package com.example.evaluadoralimentacion;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DetalleEncuestaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_encuesta);

        // Obtener la encuesta seleccionada
        Encuesta encuesta = (Encuesta) getIntent().getSerializableExtra("encuesta");

        if (encuesta != null) {
            mostrarDetalle(encuesta);
        }
    }

    private void mostrarDetalle(Encuesta encuesta) {
        TextView tvFecha = findViewById(R.id.tvFechaDetalle);
        TextView tvPuntaje = findViewById(R.id.tvPuntajeDetalle);
        LinearLayout llDetalle = findViewById(R.id.llDetalleRespuestas);

        // Formatear fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        tvFecha.setText("📅 Fecha: " + sdf.format(encuesta.getFecha()));
        tvPuntaje.setText("⭐ Puntaje total: " + encuesta.getPuntajeTotal() + "/" + encuesta.getMaxPuntaje() +
                "\n📊 Clasificación: " + encuesta.getClasificacion());

        // Obtener preguntas para mostrar los textos
        List<Pregunta> preguntas = PreguntasData.getPreguntas();
        List<String> textosRespuestas = encuesta.getTextosRespuestas();
        List<Integer> puntajesObtenidos = encuesta.getPuntajesObtenidos();

        // Mostrar cada pregunta con su respuesta y puntaje
        for (int i = 0; i < preguntas.size() && i < textosRespuestas.size(); i++) {
            Pregunta pregunta = preguntas.get(i);
            String respuesta = textosRespuestas.get(i);
            int puntaje = puntajesObtenidos.get(i);
            int[] puntajesPosibles = pregunta.getPuntajes();

            // Crear un contenedor para cada pregunta
            LinearLayout preguntaLayout = new LinearLayout(this);
            preguntaLayout.setOrientation(LinearLayout.VERTICAL);
            preguntaLayout.setPadding(16, 16, 16, 16);

            // Fondo gris claro y márgenes
            preguntaLayout.setBackgroundColor(0xFFF5F5F5);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 16);
            preguntaLayout.setLayoutParams(params);

            // Texto de la pregunta
            TextView tvPregunta = new TextView(this);
            tvPregunta.setText((i + 1) + ". " + pregunta.getTexto());
            tvPregunta.setTextSize(14);
            tvPregunta.setTextColor(0xFF000000);
            tvPregunta.setPadding(8, 4, 8, 4);

            // Texto de la respuesta seleccionada
            TextView tvRespuesta = new TextView(this);
            tvRespuesta.setText("✓ Respuesta: " + respuesta);
            tvRespuesta.setTextSize(13);
            tvRespuesta.setTextColor(0xFF2196F3);
            tvRespuesta.setPadding(8, 4, 8, 4);

            // Texto del puntaje
            TextView tvPuntajePregunta = new TextView(this);
            String posibles = "";
            for (int j = 0; j < puntajesPosibles.length; j++) {
                if (j > 0) posibles += " | ";
                posibles += puntajesPosibles[j];
            }
            tvPuntajePregunta.setText("💰 Puntaje obtenido: " + puntaje + " (Posibles: " + posibles + ")");
            tvPuntajePregunta.setTextSize(12);
            tvPuntajePregunta.setTextColor(0xFF4CAF50);
            tvPuntajePregunta.setPadding(8, 4, 8, 4);

            // Agregar todo al layout de la pregunta
            preguntaLayout.addView(tvPregunta);
            preguntaLayout.addView(tvRespuesta);
            preguntaLayout.addView(tvPuntajePregunta);

            // Agregar separador
            View separator = new View(this);
            separator.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 1));
            separator.setBackgroundColor(0xFFE0E0E0);
            preguntaLayout.addView(separator);

            // Agregar al layout principal
            llDetalle.addView(preguntaLayout);
        }
    }
}