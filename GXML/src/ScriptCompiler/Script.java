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

/**
 *
 * @author fernando
 */
public class Script extends Compilador{
     ArrayList<Clase> lista_Clases = new ArrayList<>();
     
     public void ejecucionCJS(Nodo raiz,String metodoInicio,String archivo, Template template1)
     {
          //aqui habra una lista de archivos cjs que pertenecen a el html
        //como hay una lista de archivos cjs debe haber una lista de CLASES (JAVASCRIPT)
        //##############Se crear una nueva CLASE
        System.out.println("\n\n al menos si llego aqui");
        miTemplate=template1;
        raiz.valor=archivo;
        Clase n_clase = new Clase(raiz);
        n_clase.archivo=archivo;
        
        
        
        claseActual=n_clase;
        global=n_clase.global;
        tabla = n_clase.tabla;
        
        TablaSimbolo tmpGlobal = n_clase.global;
        TablaSimbolo tmpLocal = n_clase.tabla;
        
        //n_clase.ejecutar(); //se ejecutan las declaraciones globales 
        pilaNivelCiclo = new Stack<>();
        pilaClases = new Stack<>();
        pilaMetodos = new Stack<>();
        pilaTablas = new Stack<>();
        
        
        for (Nodo atributo : n_clase.atributos) {
            if (atributo.nombre.equalsIgnoreCase("declara_var") || atributo.nombre.equalsIgnoreCase("declara_vecF1")
                    || atributo.nombre.equalsIgnoreCase("declara_vecF2") || atributo.nombre.equalsIgnoreCase("asigna_vecGlbF1")
                    || atributo.nombre.equalsIgnoreCase("asigna_vecGlbF2") || atributo.nombre.equalsIgnoreCase("asignacionGlb")) {
                new Declaracion(atributo, global, tabla,template1);
            } else {
                Nodo padre = new Nodo("Sentencias", "", 0, 0, 898);
                padre.hijos.add(atributo);
                ejecutarSentencias(padre);
            }
        }
        
        
        metodoActual = getInicio(metodoInicio);
        global = claseActual.global;
        tabla = claseActual.tabla;
        
        /*if(metodoActual!=null){
            ejecutarSentencias(metodoActual.sentencias);// se ejecutan las sentencias del metodo que inicia la compilacion
        }*/
        
        // despues de ejecutar se guarda los valores iniciales que tenia
        n_clase.global=tmpGlobal;
        n_clase.tabla=tmpLocal;
        lista_Clases.add(n_clase);
        System.out.println(" \n\n y acabo aqui");
     }
     
    public Metodo getInicio(String nombre){
        for (Metodo metodo : claseActual.metodos) {
            if (metodo.nombre.equalsIgnoreCase(nombre)) {
                return metodo;
            }
        }
        return null;
    }
    
    
}
