/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Errores;

/**
 *
 * @author fernando
 */
public class ErrorP {

    public String tipo;
    public int linea;
    public int columna;
    public String descripcion;
    public String archivo;

    public ErrorP(String tipo, int linea, int columna, String archivo, String descripcion) {
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
        this.descripcion = descripcion;
        this.archivo = archivo;

    }
}
