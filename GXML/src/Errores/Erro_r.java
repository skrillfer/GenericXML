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
public class Erro_r {
    private String valor;
    private int columna;
    private int fila;
    private String Detalle;
    private String pertenece;
    
    public Erro_r(int fila, int columna, String valor, String Detalle) {
        this.valor=valor;
        this.columna=columna;
        this.fila=fila;
        this.Detalle=Detalle;
    }

    public Erro_r(int fila,int columna,String valor, String Detalle, String pertenece) {
        this.valor = valor;
        this.columna = columna;
        this.fila = fila;
        this.Detalle = Detalle;
        this.pertenece = pertenece;
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
     * @return the fila
     */
    public int getFila() {
        return fila;
    }
 
    /**
     * @param fila the fila to set
     */
    public void setFila(int fila) {
        this.fila = fila;
    }

    /**
     * @return the Detalle
     */
    public String getDetalle() {
        return Detalle;
    }

    /**
     * @param Detalle the Detalle to set
     */
    public void setDetalle(String Detalle) {
        this.Detalle = Detalle;
    }
    
}
