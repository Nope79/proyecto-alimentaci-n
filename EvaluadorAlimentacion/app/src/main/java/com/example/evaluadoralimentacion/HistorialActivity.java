package com.example.evaluadoralimentacion;

import android.os.Bundle;
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
            items.add(sdf.format(e.getFecha()) + " - Puntaje: " + e.getPuntajeTotal() +
                    " - " + e.getClasificacion());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);
        lvHistorial.setAdapter(adapter);
    }
}