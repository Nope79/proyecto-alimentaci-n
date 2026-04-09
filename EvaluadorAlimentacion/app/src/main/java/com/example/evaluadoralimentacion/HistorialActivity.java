package com.example.evaluadoralimentacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HistorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        ListView lvHistorial = findViewById(R.id.lvHistorial);
        EncuestaStorage storage = new EncuestaStorage(this);
        List<Encuesta> encuestas = storage.obtenerEncuestas();

        List<String> items = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        for (Encuesta e : encuestas) {
            items.add(sdf.format(e.getFecha()) + "\n⭐ Puntaje: " + e.getPuntajeTotal() +
                    "/" + e.getMaxPuntaje() + " - " + e.getClasificacion());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);
        lvHistorial.setAdapter(adapter);

        // Listener para clicks en items - CORREGIDO
        lvHistorial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Encuesta encuestaSeleccionada = encuestas.get(position);
                Intent intent = new Intent(HistorialActivity.this, DetalleEncuestaActivity.class);
                intent.putExtra("encuesta", encuestaSeleccionada);
                startActivity(intent);
            }
        });
    }
}