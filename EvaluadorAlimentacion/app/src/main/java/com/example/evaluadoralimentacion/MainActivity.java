package com.example.evaluadoralimentacion;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    private List<Pregunta> preguntas;
    private List<Integer> respuestas; // almacena índice seleccionado para cada pregunta
    private int preguntaActual = 0;

    private TextView tvProgreso, tvPregunta, tvResultadoFinal;
    private ProgressBar progressBar;
    private RadioGroup rgOpciones;
    private Button btnAnterior, btnSiguiente, btnVerHistorial;
    private EncuestaStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preguntas = PreguntasData.getPreguntas();
        respuestas = new ArrayList<>(Collections.nCopies(preguntas.size(), -1));
        storage = new EncuestaStorage(this);

        initViews();
        cargarPregunta();

        btnVerHistorial.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistorialActivity.class);
            startActivity(intent);
        });
    }

    private void initViews() {
        tvProgreso = findViewById(R.id.tvProgreso);
        progressBar = findViewById(R.id.progressBar);
        tvPregunta = findViewById(R.id.tvPregunta);
        rgOpciones = findViewById(R.id.rgOpciones);
        btnAnterior = findViewById(R.id.btnAnterior);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnVerHistorial = findViewById(R.id.btnVerHistorial);
        tvResultadoFinal = findViewById(R.id.tvResultadoFinal);

        btnAnterior.setOnClickListener(v -> {
            guardarRespuestaActual();
            preguntaActual--;
            cargarPregunta();
        });

        btnSiguiente.setOnClickListener(v -> {
            guardarRespuestaActual();

            if (preguntaActual == preguntas.size() - 1) {
                finalizarEncuesta();
            } else {
                preguntaActual++;
                cargarPregunta();
            }
        });
    }

    private void guardarRespuestaActual() {
        int selectedId = rgOpciones.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selected = findViewById(selectedId);
            int index = rgOpciones.indexOfChild(selected);
            respuestas.set(preguntaActual, index);
        }
    }

    private void cargarPregunta() {
        Pregunta p = preguntas.get(preguntaActual);
        tvPregunta.setText(p.getTexto());
        tvProgreso.setText("Pregunta " + (preguntaActual + 1) + " de " + preguntas.size());
        progressBar.setProgress(preguntaActual + 1);

        // Limpiar y crear opciones
        rgOpciones.removeAllViews();
        String[] opciones = p.getOpciones();
        for (int i = 0; i < opciones.length; i++) {
            RadioButton rb = new RadioButton(this);
            rb.setText(opciones[i]);
            rb.setId(View.generateViewId());
            rgOpciones.addView(rb);

            // Restaurar respuesta guardada
            if (respuestas.get(preguntaActual) == i) {
                rb.setChecked(true);
            }
        }

        btnAnterior.setEnabled(preguntaActual > 0);

        if (preguntaActual == preguntas.size() - 1) {
            btnSiguiente.setText("Finalizar");
        } else {
            btnSiguiente.setText("Siguiente");
        }

        tvResultadoFinal.setVisibility(View.GONE);
    }

    private void finalizarEncuesta() {
        // Calcular puntaje total
        int puntajeTotal = 0;
        int maxPuntaje = 0;

        for (int i = 0; i < preguntas.size(); i++) {
            int respuestaIdx = respuestas.get(i);
            if (respuestaIdx != -1) {
                int[] puntajes = preguntas.get(i).getPuntajes();
                if (respuestaIdx < puntajes.length) {
                    puntajeTotal += puntajes[respuestaIdx];
                }
                maxPuntaje += puntajes[0]; // asumiendo orden ascendente
            }
        }

        String clasificacion = PreguntasData.clasificarAlimentacion(puntajeTotal, maxPuntaje);

        // Guardar encuesta
        Encuesta encuesta = new Encuesta(
                System.currentTimeMillis(),
                new Date(),
                new ArrayList<>(respuestas),
                puntajeTotal,
                clasificacion
        );
        storage.agregarEncuesta(encuesta);

        // Mostrar resultado
        String resultado = "Puntaje: " + puntajeTotal + "/" + maxPuntaje + "\n\n" + clasificacion;
        tvResultadoFinal.setText(resultado);
        tvResultadoFinal.setVisibility(View.VISIBLE);

        // Diálogo para reiniciar o ver historial
        new AlertDialog.Builder(this)
                .setTitle("Encuesta completada")
                .setMessage(resultado + "\n\n¿Qué deseas hacer?")
                .setPositiveButton("Nueva encuesta", (dialog, which) -> reiniciarEncuesta())
                .setNegativeButton("Ver historial", (dialog, which) -> {
                    // Abrir historial
                    Toast.makeText(this, "Próximamente", Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    private void reiniciarEncuesta() {
        respuestas = new ArrayList<>(Collections.nCopies(preguntas.size(), -1));
        preguntaActual = 0;
        cargarPregunta();
        tvResultadoFinal.setVisibility(View.GONE);
    }
}