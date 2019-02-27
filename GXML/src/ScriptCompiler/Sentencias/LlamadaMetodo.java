/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptCompiler.Sentencias;

import Estructuras.Nodo;
import ScriptCompiler.Arreglo;
import ScriptCompiler.Clase;
import ScriptCompiler.Compilador;
import ScriptCompiler.Metodo;
import ScriptCompiler.Resultado;
import javax.swing.JOptionPane;

/**
 *
 * @author fernando
 */
public class LlamadaMetodo extends Compilador {
    public Resultado res_nativas = null;
    private Nodo raiz ;
    private Resultado actualResultado;
    
    private Clase actual;
    private int nivel = 0;
    public boolean proceder = true;
    
    public LlamadaMetodo(Clase actual) {
        this.actual = actual;
    }

    public LlamadaMetodo(Clase actual, int nivel,Resultado resultado,Nodo raiz) {
        this.actual = actual;
        this.nivel = nivel;
        this.actualResultado = resultado;
        this.raiz =  raiz;
        FuncionesNativasScript();
    }

    public Metodo ejecutar(Nodo raiz) {
        
        return metodoActual;
    }
    
    public void FuncionesNativasScript()
    {
        
        if(!esNulo(actualResultado))
        {
            //El resultado anterior fu un arreglo
            if(esArreglo(actualResultado.valor))
            {
                Arreglo arr ;
                switch(raiz.valor.toLowerCase())
                {
                    case "ascendente":
                        proceder= false;
                        arr = (Arreglo)actualResultado.valor;
                        arr.ascendente();
                        break;
                    case "descendente":
                        proceder= false;
                        arr = (Arreglo)actualResultado.valor;
                        arr.descendente();
                        break;
                    case "maximo":
                        proceder= false;
                        arr = (Arreglo)actualResultado.valor;
                        res_nativas = arr.maximo();
                        break;    
                    case "minimo":
                        proceder= false;
                        arr = (Arreglo)actualResultado.valor;
                        res_nativas = arr.minimo();
                        break;    
                    case "invertir":
                        proceder= false;
                        arr = (Arreglo)actualResultado.valor;
                        arr.invertir();
                        break;        
                    
                }
            }
        }
    }
    
    public boolean esArreglo(Object valor) {
        try {
            Arreglo ar = (Arreglo) valor;
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    public boolean esClase(Object valor) {
        try {
            Clase ar = (Clase) valor;
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
