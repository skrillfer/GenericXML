/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptCompiler.Sentencias;

import Estructuras.Nodo;
import INTERFAZ.Template;
import ScriptCompiler.Compilador;
import ScriptCompiler.Resultado;
import ScriptCompiler.Simbolo;

/**
 *
 * @author fernando
 */
public class TipoAsignacion extends Compilador {

    public Resultado aplicarAsignacion(Simbolo sim, Nodo raiz, Resultado r1) {
        if (!sim.inicializado && !raiz.nombre.equals("igual")) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Intenta aplicar asignacion tipo: " + raiz.nombre + " a la variable " + sim.nombre + " que no esta inicializada");
            return r1;
        }
        Resultado resultado = new Resultado("-1", null);
        Object valor;
        if (raiz.nombre.equals("asigmas")) {
            switch (sim.tipo) {
                case "Integer":
                    switch (r1.tipo) {
                        case "Integer":
                            valor = (Integer) sim.valor + (Integer) r1.valor;
                            resultado.valor = valor;
                            resultado.tipo = "Integer";
                            break;
                        case "Double":
                            valor = ((Integer) sim.valor).doubleValue() + (Double) r1.valor;
                            resultado.valor = valor;
                            resultado.tipo = "Double";
                            break;
                        case "String":
                            valor = (Integer) sim.valor + "" + (String) r1.valor;
                            resultado.valor = valor;
                            resultado.tipo = "String";
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Tipos de Asignacion no aplica entre tipos [" + sim.tipo + "] y [" + r1.tipo + "]");
                            return r1;
                    }
                    break;
                case "Double":
                    switch (r1.tipo) {
                        case "Integer":
                            valor = (Double) sim.valor + ((Integer) r1.valor).doubleValue();
                            resultado.valor = valor;
                            resultado.tipo = "Double";
                            break;
                        case "Double":
                            valor = (Double) sim.valor + (Double) r1.valor;
                            resultado.valor = valor;
                            resultado.tipo = "Double";
                            break;
                        case "String":
                            valor = (Double) sim.valor + "" + (String) r1.valor;
                            resultado.valor = valor;
                            resultado.tipo = "String";
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Tipos de Asignacion no aplica entre tipos [" + sim.tipo + "] y [" + r1.tipo + "]");
                            return r1;
                    }
                    break;
                case "String":
                    switch (r1.tipo) {
                        case "Integer":
                            valor = (Integer) sim.valor + "" + (String) r1.valor;
                            resultado.valor = valor;
                            resultado.tipo = "String";
                            break;
                        case "Double":
                            valor = (Double) sim.valor + "" + (String) r1.valor;
                            resultado.valor = valor;
                            resultado.tipo = "String";
                            break;
                        case "String":
                            valor = (String) sim.valor + "" + (String) r1.valor;
                            resultado.valor = valor;
                            resultado.tipo = "String";
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Tipos de Asignacion no aplica entre tipos [" + sim.tipo + "] y [" + r1.tipo + "]");
                            return r1;
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Tipos de Asignacion no aplica entre tipos [" + sim.tipo + "] y [" + r1.tipo + "]");
                    return r1;
            }
        } else if (raiz.nombre.equals("asigmen")) {
            switch (sim.tipo) {
                case "Integer":
                    switch (r1.tipo) {
                        case "Integer":
                            valor = (Integer) sim.valor - (Integer) r1.valor;
                            resultado.valor = valor;
                            resultado.tipo = "Integer";
                            break;
                        case "Double":
                            valor = ((Integer) sim.valor).doubleValue() - (Double) r1.valor;
                            resultado.valor = valor;
                            resultado.tipo = "Double";
                            break;
                        
                        default:
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Tipos de Asignacion no aplica entre tipos [" + sim.tipo + "] y [" + r1.tipo + "]");
                            return r1;
                    }
                    break;
                case "Double":
                    switch (r1.tipo) {
                        case "Integer":
                            valor = (Double) sim.valor - ((Integer) r1.valor).doubleValue();
                            resultado.valor = valor;
                            resultado.tipo = "Double";
                            break;
                        case "Double":
                            valor = (Double) sim.valor - (Double) r1.valor;
                            resultado.valor = valor;
                            resultado.tipo = "Double";
                            break;
                        
                        default:
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Tipos de Asignacion no aplica entre tipos [" + sim.tipo + "] y [" + r1.tipo + "]");
                            return r1;
                    }
                    break;
               
                default:
                    Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Tipos de Asignacion no aplica entre tipos [" + sim.tipo + "] y [" + r1.tipo + "]");
                    return r1;
            }
        }else if (raiz.nombre.equals("asigpor")) {
            switch (sim.tipo) {
                case "Integer":
                    switch (r1.tipo) {
                        case "Integer":
                            valor = (Integer) sim.valor * (Integer) r1.valor;
                            resultado.valor = valor;
                            resultado.tipo = "Integer";
                            break;
                        case "Double":
                            valor = ((Integer) sim.valor).doubleValue() * (Double) r1.valor;
                            resultado.valor = valor;
                            resultado.tipo = "Double";
                            break;
                        
                        default:
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Tipos de Asignacion no aplica entre tipos [" + sim.tipo + "] y [" + r1.tipo + "]");
                            return r1;
                    }
                    break;
                case "Double":
                    switch (r1.tipo) {
                        case "Integer":
                            valor = (Double) sim.valor * ((Integer) r1.valor).doubleValue();
                            resultado.valor = valor;
                            resultado.tipo = "Double";
                            break;
                        case "Double":
                            valor = (Double) sim.valor * (Double) r1.valor;
                            resultado.valor = valor;
                            resultado.tipo = "Double";
                            break;
                        
                        default:
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Tipos de Asignacion no aplica entre tipos [" + sim.tipo + "] y [" + r1.tipo + "]");
                            return r1;
                    }
                    break;
               
                default:
                    Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Tipos de Asignacion no aplica entre tipos [" + sim.tipo + "] y [" + r1.tipo + "]");
                    return r1;
            }
        }else if (raiz.nombre.equals("asigdiv")) {
            switch (sim.tipo) {
                case "Integer":
                    switch (r1.tipo) {
                        case "Integer":
                            valor = ((Integer) sim.valor).doubleValue() / ((Integer) r1.valor).doubleValue();
                            resultado.valor = valor;
                            resultado.tipo = "Double";
                            break;
                        case "Double":
                            valor = ((Integer) sim.valor).doubleValue() / (Double) r1.valor;
                            resultado.valor = valor;
                            resultado.tipo = "Double";
                            break;
                        
                        default:
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Tipos de Asignacion no aplica entre tipos [" + sim.tipo + "] y [" + r1.tipo + "]");
                            return r1;
                    }
                    break;
                case "Double":
                    switch (r1.tipo) {
                        case "Integer":
                            valor = (Double) sim.valor / ((Integer) r1.valor).doubleValue();
                            resultado.valor = valor;
                            resultado.tipo = "Double";
                            break;
                        case "Double":
                            valor = (Double) sim.valor / (Double) r1.valor;
                            resultado.valor = valor;
                            resultado.tipo = "Double";
                            break;
                        
                        default:
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Tipos de Asignacion no aplica entre tipos [" + sim.tipo + "] y [" + r1.tipo + "]");
                            return r1;
                    }
                    break;
               
                default:
                    Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Tipos de Asignacion no aplica entre tipos [" + sim.tipo + "] y [" + r1.tipo + "]");
                    return r1;
            }
        }else
        {
            return r1;
        }
        return resultado;
    }
}
