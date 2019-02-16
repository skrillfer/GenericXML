/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;
import java.util.ArrayList;
/**
 *
 * @author fernando
 */
public class Nodo {
     private String nombre;
    private String valor;
    private int linea;
    private int columna;
    private int index;
    private ArrayList<Nodo> Hijos;
    
    

    public Nodo(String nombre, String valor, int linea, int columna, int index) {
        this.nombre = nombre;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
        this.index = index;
        this.Hijos = new ArrayList<>();
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * @return the linea
     */
    public int getLinea() {
        return linea;
    }

    /**
     * @param linea the linea to set
     */
    public void setLinea(int linea) {
        this.linea = linea;
    }

    /**
     * @return the columna
     */
    public int getColumna() {
        return columna;
    }

    /**
     * @param columna the columna to set
     */
    public void setColumna(int columna) {
        this.columna = columna;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return the Hijos
     */
    public ArrayList<Nodo> getHijos() {
        return Hijos;
    }

    /**
     * @param Hijos the Hijos to set
     */
    public void setHijos(ArrayList<Nodo> Hijos) {
        this.Hijos = Hijos;
    }
}
