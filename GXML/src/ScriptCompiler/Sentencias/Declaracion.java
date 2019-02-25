/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptCompiler.Sentencias;

import Estructuras.Nodo;
import INTERFAZ.Template;
import ScriptCompiler.Compilador;
import ScriptCompiler.OperacionesARL.OperacionesARL;
import ScriptCompiler.Resultado;
import ScriptCompiler.Simbolo;
import ScriptCompiler.TablaSimbolo;

/**
 *
 * @author fernando
 */
public class Declaracion extends Compilador{
    TablaSimbolo tabla;
    TablaSimbolo global;

    //* cuando DECLARO PARAMETROS USO ESTE CONSTRUCTOR
    public Declaracion(Nodo raiz, Resultado resultado, TablaSimbolo global, TablaSimbolo tabla,Template template1) {
        this.miTemplate=template1;
        this.raiz = raiz;
        this.global = global;
        this.tabla = tabla;
        //declararParametro(resultado);
    }
    
    //* cuando hago una declaracion simple USO este CONSTRUCTOR
    public Declaracion(Nodo raiz, TablaSimbolo global, TablaSimbolo tabla,Template template1) {
        this.miTemplate=template1;
        this.raiz = raiz;
        this.global = global;//tabla de variables locales
        this.tabla = tabla;//tabla de variables globales
        opL = new OperacionesARL(global, tabla,miTemplate);
        declarar();
    }
    
     public Object declarar() {
        //System.out.println(raiz.nombre);
        switch (raiz.nombre) {
            //********************  AMBITO GLOBAL ******************************
            case "declara_var":
                try {
                    declara_var();
                } catch (Exception e) {
                }
                break;
            default:
                System.out.println(raiz.nombre);
                break;
        }

        return null;
    }
     
      public void declara_var(){
        String tipo = "";//el tipo de la  variable depende del valor que tenga
        String nombre= raiz.hijos.get(0).valor;//se obtiene el nombre de la variable a declarar
        
        
        if(raiz.hijos.get(1).hijos.size()>0){
            Nodo exp = raiz.hijos.get(1).hijos.get(0);//se obtiene el nodo de la expresion
            //----------ejecuto la parte de la expresion
            System.out.println("ASIGNO a " + nombre);
            Resultado resultado = opL.ejecutar(exp);
            
            if(resultado!=null ){
                // la variable toma el tipo del valor que le es asignado
                tipo=resultado.tipo;
                Simbolo simbolo = new Simbolo(tipo, nombre, "", resultado.valor);
                simbolo.inicializado = true;
                if (!global.setSimbolo(simbolo)) {
                    Template.reporteError_CJS.agregar("Error Semantico",raiz.linea, raiz.columna,"La variable " + nombre + " ya existe");
                }
            }else{
                Simbolo simbolo = new Simbolo("", nombre, "", null);
                if (!global.setSimbolo(simbolo)) {
                    Template.reporteError_CJS.agregar("Error Semantico",raiz.linea, raiz.columna,"La variable " + nombre + " ya existe");
                }/*else{
                    System.out.println("se agrego correctamente  "+simbolo.nombre);
                }*/
            }
             
        }else{
            Simbolo simbolo = new Simbolo(tipo, nombre, "", null);
            if (!global.setSimbolo(simbolo)) {
                Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
            }
        }
    }
    
}
