/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptCompiler;

import Estructuras.Nodo;
import INTERFAZ.Template;
import ScriptCompiler.Sentencias.Declaracion;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JComponent;

/**
 *
 * @author fernando
 */
public class Clase {
    
    public Object Componente =null;
    public boolean Inicializada = false;
    
    public Stack<TablaSimbolo> pilaTablas;
         //--------------------------------------
    public String archivo;
    public TablaSimbolo global;
    public TablaSimbolo tabla;
    public String nombre;
    public String visibilidad;
    public ArrayList<Metodo> metodos;
    public ArrayList<Nodo> atributos;
    
    /*  CONSTRUCTOR usado cuando crea una nueva estructura desde Operaciones ARL */
    public Clase(Nodo raiz) {
        nombre = "";
        global = new TablaSimbolo();   //La tabla de simbolos global
        tabla = new TablaSimbolo();    
        atributos = getAtributos(raiz);
        metodos = new ArrayList<>();
        pilaTablas = new Stack<>();
    }
     public Clase(String nombre) {
        global = new TablaSimbolo();   //La tabla de simbolos global
        tabla = new TablaSimbolo();    
        atributos = new ArrayList<>();
        metodos = new ArrayList<>();
        pilaTablas = new Stack<>();
    }
    
     /*
        ** Constructor usado para cuando se esta importando un archivo
     */
     
     public Clase(Nodo raiz,ArrayList<Metodo> metodos, ArrayList<Nodo> atributos)
     {
         this.metodos = metodos;
         this.atributos = atributos;
         getMetodos(raiz);
         getAtributosImport(raiz);
     }
     
    /*  CONSTRUCTOR utilizado por la clase Archivo*/
    public Clase(Nodo raiz,String nombre) {
        archivo = nombre;
        global = new TablaSimbolo();
        tabla = new TablaSimbolo();
        atributos = new ArrayList<>();
        metodos = new ArrayList<>();
        pilaTablas = new Stack<>();
     
        //----------------------------------------------------------------------
        this.nombre = raiz.valor;
        getMetodos(raiz);
        this.atributos = getAtributos(raiz);
        //----------------------------------------------------------------------
    } 

     private void getMetodos(Nodo raiz) {
        for (Nodo hijo : raiz.hijos) {
            if (hijo.nombre.equalsIgnoreCase("funcion")) {
                Metodo metodo = new Metodo(hijo);
                if (!existeMetodo(metodo.id, hijo)) {
                    metodos.add(metodo);
                    Simbolo simbolo = new Simbolo("funcion", metodo.nombre, "metodo");
                    simbolo.rol = "Metodo";
                    simbolo.ambito = nombre;
                    Script.reporteSimbolos.add(simbolo);
                }
            }
        }
    }

    private Boolean existeMetodo(String id, Nodo raiz) {
        for (Metodo metodo : metodos) {
            if (metodo.id.equalsIgnoreCase(id)) {
                Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "El metodo " + metodo.nombre + " ya existe en el archivo " + this.archivo);
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
            if (!hijo.nombre.equalsIgnoreCase("funcion") ){
                atributos.add(hijo);
            }
        }
        return atributos;
    }
    
    private void getAtributosImport(Nodo raiz) {
        
        for (Nodo hijo : raiz.hijos) {
            if (!hijo.nombre.equalsIgnoreCase("funcion") ){
                this.atributos.add(hijo);
            }
        }
        
    }

    
    
    public void ejecutar(Template template) {
        
        Compilador.pilaClases.push(Compilador.claseActual);
        Compilador.claseActual = this;
        for (Nodo atributo : atributos) {
            new Declaracion(atributo, global, tabla,template);
        }
        Compilador.claseActual = Compilador.pilaClases.pop();
        
    }
    
    
}
