/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptCompiler;

import java.util.Hashtable;

/**
 *
 * @author fernando
 */
public class TablaSimbolo {
    public Hashtable<String, Simbolo> tabla;

    public TablaSimbolo() {
        tabla = new Hashtable<>();
    }

    //verifica si existe el simbolo en la tabla de simbolos
    public Boolean existe(String nombre) {
        nombre = nombre.toLowerCase();
        return tabla.containsKey(nombre);
    }
    
    public boolean setSimbolo(Simbolo simbolo) {
        simbolo.nombre = simbolo.nombre.toLowerCase();
        if (!existe(simbolo.nombre)) {
            tabla.put(simbolo.nombre, simbolo);
            /*if (CJS.metodoActual != null) {
                simbolo.ambito = CJS.claseActual.nombre + "_" + CJS.metodoActual.nombre;
            } else {
                simbolo.ambito = CJS.claseActual.nombre;
            }*/
            simbolo.rol = "Variable";
            //Graphik.reporteSimbolos.add(simbolo);
            //System.out.println("AMBITO-> "+simbolo.ambito);

            return true;//se agrego correctamente
        } else {
            return false;//no se agrego a la global
        }
    }
    
     public Simbolo getSimbolo(String nombre, Clase claseActual) {
        Simbolo buscado = null;
        TablaSimbolo principal = claseActual.global;
        nombre = nombre.toLowerCase();
        if (existe(nombre)) {
            
            return tabla.get(nombre);
        } else{//si no existe en el ambito LOCAL se busca en el ambito GLOBAL
            if (principal.existe(nombre)) {
                return principal.tabla.get(nombre);
            }
        }
        return buscado;
    }
     
    public void cambiarAmbito(TablaSimbolo actual) {
        for (Simbolo simbolo : actual.tabla.values()) {
            tabla.put(simbolo.nombre, simbolo);
        }
    }
    
    
}
