/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptCompiler;

import Estructuras.Nodo;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author fernando
 */
public class Clase {
    public Stack<TablaSimbolo> pilaTablas;
         //--------------------------------------
    public String archivo;
    public TablaSimbolo global;
    public TablaSimbolo tabla;
    public String nombre;
    public String visibilidad;
    public ArrayList<Metodo> metodos;
    public ArrayList<Nodo> atributos;
    public ArrayList<Nodo> sentenciasGlobales;
    
     public Clase() {
        global = new TablaSimbolo();   //La tabla de simbolos global
        tabla = new TablaSimbolo();    
        atributos = new ArrayList<>();
        sentenciasGlobales = new ArrayList<>();
        metodos = new ArrayList<>();
        pilaTablas = new Stack<>();
    }
     
    public Clase(Nodo raiz) {

        global = new TablaSimbolo();
        tabla = new TablaSimbolo();
        atributos = new ArrayList<>();
        metodos = new ArrayList<>();
        sentenciasGlobales = new ArrayList<>();
        pilaTablas = new Stack<>();
     
        //----------------------------------------------------------------------
        this.nombre = raiz.valor;
        //this.visibilidad = raiz.hijos.get(0).valor;
        this.metodos = getMetodos(raiz);
        this.atributos = getAtributos(raiz);
        //----------------------------------------------------------------------
    } 

     private ArrayList<Metodo> getMetodos(Nodo raiz) {
        ArrayList<Metodo> metodos = new ArrayList<>();//una lista de METODOS
        for (Nodo hijo : raiz.hijos) {
            if (hijo.nombre.equalsIgnoreCase("funcion")) {
                Metodo metodo = new Metodo(hijo);
                if (!existeMetodo(metodo.id, hijo)) {
                    metodos.add(metodo);
                    Simbolo simbolo = new Simbolo("funcion", metodo.nombre, "metodo");
                    simbolo.rol = "Metodo";
                    simbolo.ambito = nombre;
                    //Graphik.reporteSimbolos.add(simbolo);
                }
            }
        }
        return metodos;
    }

    private Boolean existeMetodo(String id, Nodo raiz) {
        for (Metodo metodo : metodos) {
            if (metodo.id.equalsIgnoreCase(id)) {
                //Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "El metodo " + metodo.nombre + " ya existe en la clase " + nombre);
                return true;
            }
        }
        return false;
    }

     public Metodo getMetodo(String id){
        for (Metodo metodo : metodos) {
            if (metodo.id.equalsIgnoreCase(id)) {
                return metodo;
            }
        }
        return null;
    }
    
    private ArrayList<Nodo> getAtributos(Nodo raiz) {
        ArrayList<Nodo> atributos = new ArrayList<>();
        for (Nodo hijo : raiz.hijos) {
            if (!hijo.nombre.equalsIgnoreCase("funcion")  ) {
                atributos.add(hijo);
            }
        }
        return atributos;
    }

    
    
    public void ejecutar() {
        /*
        for (Nodo atributo : atributos) {
            if ( atributo.nombre.equalsIgnoreCase("declara_var")  || atributo.nombre.equalsIgnoreCase("declara_vecF1") 
              || atributo.nombre.equalsIgnoreCase("declara_vecF2") || atributo.nombre.equalsIgnoreCase("asigna_vecGlbF1")
              || atributo.nombre.equalsIgnoreCase("asigna_vecGlbF2") || atributo.nombre.equalsIgnoreCase("asignacionGlb")    )
            {
                new Declaracion(atributo, global, tabla);
            }else{
                
            }
            
        }
        */
    }
    
    
}
