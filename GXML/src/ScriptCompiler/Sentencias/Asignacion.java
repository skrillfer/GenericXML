/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptCompiler.Sentencias;

import Estructuras.Nodo;
import INTERFAZ.Template;
import ScriptCompiler.Archivo;
import ScriptCompiler.Arreglo;
import ScriptCompiler.Clase;
import ScriptCompiler.Compilador;
import ScriptCompiler.Metodo;
import ScriptCompiler.OperacionesARL.OperacionesARL;
import ScriptCompiler.Resultado;
import ScriptCompiler.Simbolo;
import ScriptCompiler.TablaSimbolo;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import sun.font.Script;

/**
 *
 * @author fernando
 */
public class Asignacion extends Compilador {

    Nodo valorIndice;
    TablaSimbolo tabla;
    TablaSimbolo global;
    //private ArrayList<Archivo> archivos = Script.archivos;

    public Asignacion(Nodo raiz, TablaSimbolo global, TablaSimbolo tabla, Template template) {
        this.raiz = raiz;
        this.global = global;//tabla de sibolos de una clase
        this.tabla = tabla;//tabla de simbolos de un metodo
        opL = new OperacionesARL(global, tabla, miTemplate);
        asignar();
    }

    public Asignacion(Nodo raiz, TablaSimbolo global, TablaSimbolo tabla, String tipo) {
        this.raiz = raiz;
        this.global = global;//tabla de sibolos de una clase
        this.tabla = tabla;//tabla de simbolos de un metodo
        opL = new OperacionesARL(global, tabla, miTemplate);

    }

    public Simbolo asignar() {
        switch (raiz.nombre) {
            case "asignacion"://asignacion de objetos,variables y arreglos
                return asignacion();
        }

        return null;
    }

    private Simbolo asignacion() {
        valorIndice = raiz.get(2);
        Simbolo simbolo = acceso(raiz.get(0));
        Resultado resultado = opL.ejecutar(raiz.get(2));

        if (!esNulo(resultado)) {
            if (simbolo != null) {

                if (resultado.valor.getClass().getSimpleName().equalsIgnoreCase("clase") && resultado.simbolo == null) {
                    Clase clase = (Clase) resultado.valor;
                    clase.nombre = simbolo.nombre;
                    clase.ejecutar(miTemplate);

                    simbolo.valor = resultado.valor;
                    simbolo.inicializado = true;
                    simbolo.tipo = resultado.tipo;
                } else if (resultado.valor.getClass().getSimpleName().equalsIgnoreCase("arreglo") && resultado.simbolo == null) {
                    Arreglo arreglo2 = (Arreglo) resultado.valor;

                    simbolo.valor = arreglo2;
                    simbolo.esArreglo = true;
                    simbolo.inicializado = true;

                } else {

                    try {
                        simbolo.valor = resultado.valor;
                        simbolo.tipo = resultado.tipo;
                        simbolo.inicializado = true;
                    } catch (Exception e) {
                    }
                }
                return simbolo;

            }
        } else {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "El Valor de la Expresion a asignar es Nulo");
        }
        return null;
    }

    public Simbolo acceso(Nodo raiz) {
        JOptionPane.showMessageDialog(null, "dame desde asignacion");
        Clase aux = ScriptCompiler.Script.claseActual;
        TablaSimbolo tablaAux = tabla;

        Simbolo sim = null;
        //----------------------------------------------------------------------
        int nivel = 0;
        for (Nodo acceso : raiz.hijos) {
            String nombre;
            Simbolo simbolo;
            switch (acceso.nombre) {
                case "accesoar":
                    aux.tabla = tabla;
                    tabla = tablaAux;

                    break;
                case "id":
                    nombre = acceso.valor;
                    simbolo = tabla.getSimbolo(nombre, aux);

                    if (simbolo != null) {
                        switch (simbolo.tipo) {
                            case "Integer":
                            case "Double":
                            case "String":
                            case "Boolean":
                                sim = simbolo;
                                break;

                            default:
                                nivel++;
                                if (!simbolo.esArreglo) {
                                    try {
                                        nivel++;
                                        aux = (Clase) simbolo.valor;
                                        tabla = aux.tabla;
                                        sim = simbolo;
                                    } catch (Exception e) {
                                        sim = simbolo;
                                    }

                                }
                                break;
                        }
                        /*if (simbolo.esArreglo) {
                                retorno.valor = simbolo.valor;
                                retorno.tipo = "Arreglo";
                                retorno.simbolo = simbolo;
                            }*/

                    } else {
                        Template.reporteError_CJS.agregar("Semantico", acceso.linea, acceso.columna, "La variable " + nombre + " no existe en el ambito donde fue invocada");
                        return null;
                    }
                    break;

                case "llamada":
                    LlamadaMetodo llamada = new LlamadaMetodo(aux, nivel, acceso);
                    Metodo metodo = llamada.ejecutar(acceso);

                    if (metodo != null) {
                        if (metodo.retorno != null) {
                            sim = metodo.retorno.simbolo;
                            if (!sim.tipo.equalsIgnoreCase("String") && !sim.tipo.equalsIgnoreCase("Integer") && !sim.tipo.equalsIgnoreCase("Double") && !sim.tipo.equalsIgnoreCase("Boolean")) {
                                try {
                                    aux = (Clase) sim.valor;
                                    tabla = aux.tabla;
                                } catch (Exception e) {
                                }
                            }
                        }
                    } else {
                        sim = null;
                    }

                    break;
            }
        }

        tabla = tablaAux;
        return sim;
    }

}
