package com.example.evaluadoralimentacion; // Cambia por tu paquete

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HistorialActivity extends AppCompatActivity {

    private ListView lvHistorial;
    private TextView tvMensajeVacio;
    private Button btnEliminarHistorial;
    private EncuestaStorage storage;
    private List<Encuesta> encuestas;
    private ArrayAdapter<String> adapter;
    private List<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        lvHistorial = findViewById(R.id.lvHistorial);
        tvMensajeVacio = findViewById(R.id.tvMensajeVacio);
        btnEliminarHistorial = findViewById(R.id.btnEliminarHistorial);
        storage = new EncuestaStorage(this);

        // Botón para eliminar todo el historial
        btnEliminarHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoConfirmacion();
            }
        });

        cargarHistorial();

        // Listener para clicks en items
        lvHistorial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!encuestas.isEmpty()) {
                    Encuesta encuestaSeleccionada = encuestas.get(position);
                    Intent intent = new Intent(HistorialActivity.this, DetalleEncuestaActivity.class);
                    intent.putExtra("encuesta", encuestaSeleccionada);
                    startActivity(intent);
                }
            }
        });
    }

    private void cargarHistorial() {
        encuestas = storage.obtenerEncuestas();
        items = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        if (encuestas.isEmpty()) {
            // Mostrar mensaje visual y ocultar lista
            lvHistorial.setVisibility(View.GONE);
            tvMensajeVacio.setVisibility(View.VISIBLE);
            btnEliminarHistorial.setEnabled(false); // Deshabilitar botón eliminar
        } else {
            // Mostrar lista y ocultar mensaje
            lvHistorial.setVisibility(View.VISIBLE);
            tvMensajeVacio.setVisibility(View.GONE);
            btnEliminarHistorial.setEnabled(true); // Habilitar botón eliminar

            for (Encuesta e : encuestas) {
                items.add(sdf.format(e.getFecha()) + "\n⭐ Puntaje: " + e.getPuntajeTotal() +
                        "/" + e.getMaxPuntaje() + " - " + e.getClasificacion());
            }

            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, items);
            lvHistorial.setAdapter(adapter);
        }
    }

    private void mostrarDialogoConfirmacion() {
        new AlertDialog.Builder(this)
                .setTitle("🗑️ Eliminar historial")
                .setMessage("¿Estás seguro de que quieres eliminar TODAS las encuestas guardadas?\n\nEsta acción no se puede deshacer.")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Sí, eliminar", (dialog, which) -> eliminarHistorial())
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    Toast.makeText(this, "Eliminación cancelada", Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    private void eliminarHistorial() {
        // Limpiar el almacenamiento
        storage.limpiarHistorial();

        // Recargar la lista
        cargarHistorial();

        // Mostrar mensaje de éxito
        Toast.makeText(this, "✅ Historial eliminado correctamente", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recargar el historial cada vez que se vuelve a esta actividad
        cargarHistorial();
    }
}