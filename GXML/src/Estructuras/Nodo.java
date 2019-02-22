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
     public String nombre;
    public String valor;
    public int linea;
    public int columna;
    public int index;
    public ArrayList<Nodo> hijos;

    public Nodo(String nombre, String valor, int linea, int columna, int index) {
        this.nombre = nombre;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
        this.index = index;
        this.hijos = new ArrayList<>();
    }

    public Nodo() {
        this.hijos = new ArrayList<>();
    }
    
    
    public void add(Nodo h){
        this.hijos.add(h);
    }
    
    public Nodo get(int index)
    {
        try {
            return this.hijos.get(index);
        } catch (Exception e) {
            return null;
        }
    }
    public int size()
    {
        return this.hijos.size();
    }
}
