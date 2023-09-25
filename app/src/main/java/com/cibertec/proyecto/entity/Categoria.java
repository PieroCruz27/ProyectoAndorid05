package com.cibertec.proyecto.entity;

public class Categoria {

    private int idCategoria;
    private String  descripcion;
    private int tipo;


    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTipo() { return tipo; }

    public void setTipo(int tipo) {this.tipo = tipo;}
}
