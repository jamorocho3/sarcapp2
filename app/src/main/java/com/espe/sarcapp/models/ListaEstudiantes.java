package com.espe.sarcapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ListaEstudiantes extends ArrayList<Estudiante> implements Parcelable {
    public ListaEstudiantes() {
    }
    public void writeToParcel(Parcel dest, int flags) {
        int size = this.size();
        dest.writeInt(size);
        for (int i = 0; i < size; i++) {
            Estudiante oEstudiante = this.get(i);
            dest.writeString(oEstudiante.getId());
            dest.writeString(oEstudiante.getCedula());
            dest.writeString(oEstudiante.getNombres());
        }
    }
    private ListaEstudiantes(Parcel in) {
        readfromParcel(in);
    }
    private void readfromParcel(Parcel in) {
        this.clear();
        int size = in.readInt();
        //Leemos el tamaño del array int size = in.readInt();
        for (int i = 0; i < size; i++) {
            //el orden de los atributos SI importa
            Estudiante oEstudiante = new Estudiante();
            oEstudiante.setId(in.readString());
            oEstudiante.setCedula(in.readString());
            oEstudiante.setNombres(in.readString());
            this.add(oEstudiante);
        }
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ListaEstudiantes createFromParcel(Parcel in) {
            return new ListaEstudiantes(in);
        }
        public Object[] newArray(int arg0) {
            return null;
        }
    };
    public int describeContents() {
        return 0;
    }
}
