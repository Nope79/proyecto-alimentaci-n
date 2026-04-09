package com.example.evaluadoralimentacion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Encuesta implements Serializable {
    private long id;
    private Date fecha;
    private List<Integer> respuestas; // índices de opciones seleccionadas
    private int puntajeTotal;
    private int maxPuntaje;
    private String clasificacion;
    private List<String> textosRespuestas; // Guardar el texto de cada respuesta
    private List<Integer> puntajesObtenidos; // Guardar puntaje de cada pregunta

    public Encuesta(long id, Date fecha, List<Integer> respuestas,
                    int puntajeTotal, int maxPuntaje, String clasificacion,
                    List<String> textosRespuestas, List<Integer> puntajesObtenidos) {
        this.id = id;
        this.fecha = fecha;
        this.respuestas = respuestas;
        this.puntajeTotal = puntajeTotal;
        this.maxPuntaje = maxPuntaje;
        this.clasificacion = clasificacion;
        this.textosRespuestas = textosRespuestas;
        this.puntajesObtenidos = puntajesObtenidos;
    }

    // Getters
    public long getId() { return id; }
    public Date getFecha() { return fecha; }
    public List<Integer> getRespuestas() { return respuestas; }
    public int getPuntajeTotal() { return puntajeTotal; }
    public int getMaxPuntaje() { return maxPuntaje; }
    public String getClasificacion() { return clasificacion; }
    public List<String> getTextosRespuestas() { return textosRespuestas; }
    public List<Integer> getPuntajesObtenidos() { return puntajesObtenidos; }
}