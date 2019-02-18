/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizadores.Script;

/**
 *
 * @author fernando
 */
public class token {
     
    private int columna;
    private int linea;
    private String cadena;
    
    public token(){
    }
    
    public token(int columna, int linea, String cadena ) {
        this.columna = columna;
        this.linea = linea;
        this.cadena = cadena;
    }
      
    public int getColumna() {
        return (this.columna)+1;
    }
    
    public int getLinea() {
        return (this.linea)+1;
    }
    
    public String getCadena() {
        return this.cadena;
    }
    
    public void setCadena(String c) {
        cadena = c;
    }
}
