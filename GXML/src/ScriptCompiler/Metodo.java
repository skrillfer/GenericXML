/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptCompiler;

import Estructuras.Nodo;
import java.util.ArrayList;

/**
 *
 * @author fernando
 */
public class Metodo {
    public String nombre;
    public String id;
    public String tipo;
    public String visibilidad;
    public Nodo sentencias;
    public ArrayList<Nodo> parametros;
    public Resultado retorno;

    public boolean estadoRetorno = false;
    public boolean estadoTerminar = false;
    public boolean estadoContinuar = false;
    
    public Metodo() {

    }

    public Metodo(Nodo raiz) {
            
            this.tipo = "";
            this.nombre = raiz.hijos.get(0).valor;
            this.parametros = raiz.hijos.get(1).hijos;
            this.sentencias = raiz.hijos.get(2);
            this.id = getId();
        
    }
    
     private String getId() {
        String id = nombre;
        
        id+="_"+parametros.size();
        /*for (Nodo parametro : parametros) {
            if (parametro.nombre.equals("parametro")) {
                id += "_1p";
            } else {
                
                //si el parametro es un vector
                //id += parametro.hijos.get(0).valor + parametro.hijos.get(1).hijos.size();
            }
        }*/
        return id;
    }
}
