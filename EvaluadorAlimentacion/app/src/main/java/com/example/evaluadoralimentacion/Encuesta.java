package com.example.evaluadoralimentacion;

import java.util.Date;
import java.util.List;

public class Encuesta {
    private long id;
    private Date fecha;
    private List<Integer> respuestas; // índices de opciones seleccionadas
    private int puntajeTotal;
    private String clasificacion;

    public Encuesta(long id, Date fecha, List<Integer> respuestas, int puntajeTotal, String clasificacion) {
        this.id = id;
        this.fecha = fecha;
        this.respuestas = respuestas;
        this.puntajeTotal = puntajeTotal;
        this.clasificacion = clasificacion;
    }

    // Getters
    public long getId() { return id; }
    public Date getFecha() { return fecha; }
    public List<Integer> getRespuestas() { return respuestas; }
    public int getPuntajeTotal() { return puntajeTotal; }
    public String getClasificacion() { return clasificacion; }
}