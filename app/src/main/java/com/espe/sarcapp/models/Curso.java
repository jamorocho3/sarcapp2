package com.espe.sarcapp.models;

import java.io.Serializable;

public class Curso implements Serializable {
    private String periodo, codigo_materia, nrc, materia, campus, docente;
    private int dias_semana;

    public Curso(String periodo, String codigo_materia, String nrc, int dias_semana, String materia, String campus, String docente) {
        this.periodo = periodo;
        this.codigo_materia = codigo_materia;
        this.nrc = nrc;
        this.materia = materia;
        this.campus = campus;
        this.docente = docente;
        this.dias_semana = dias_semana;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getCodigo_materia() {
        return codigo_materia;
    }

    public void setCodigo_materia(String codigo_materia) {
        this.codigo_materia = codigo_materia;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public int getDias_semana() {
        return dias_semana;
    }

    public void setDias_semana(int dias_semana) {
        this.dias_semana = dias_semana;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }
}
