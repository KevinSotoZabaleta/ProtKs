package com.protks.ProtKs.model;

public class Material {

    public String  id;
    public String nombre;
    public int precio;

    public Material(String id, String nombre, int precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
