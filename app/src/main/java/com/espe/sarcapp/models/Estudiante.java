package com.espe.sarcapp.models;

public class Estudiante {
    private String id, cedula, nombres;

    Estudiante() {
    }

    public Estudiante(String id, String cedula, String nombres) {
        setId(id);
        setCedula(cedula);
        setNombres(nombres);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
}
