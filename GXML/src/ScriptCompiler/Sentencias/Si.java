/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptCompiler.Sentencias;

import Estructuras.Nodo;
import INTERFAZ.Template;
import ScriptCompiler.Compilador;
import ScriptCompiler.Metodo;
import ScriptCompiler.OperacionesARL.OperacionesARL;
import ScriptCompiler.Resultado;
import ScriptCompiler.TablaSimbolo;

/**
 *
 * @author fernando
 */
public class Si extends Compilador {

    public Metodo ejecutar(Nodo raiz) {
        Nodo exp = raiz.hijos.get(0);
        Nodo sentenciasSi = raiz.hijos.get(1);
        Nodo sentenciasSino_Sino = raiz.hijos.get(2);

        opL = new OperacionesARL(global, tabla, miTemplate);
        Resultado condicion = opL.ejecutar(exp);

        //---------     CAMBIO DE AMBITO    --------------  ----------  --------
        TablaSimbolo tablaTemp = new TablaSimbolo();
        tablaTemp.cambiarAmbito(tabla);
        pilaTablas.push(tabla);
        tabla = tablaTemp;
        //--------      -------------   --------------  -----------------   ----

        condicion = Si_Condicion_Num(condicion);

        if (condicion.tipo.equalsIgnoreCase("Boolean")) {
            if ((Boolean) condicion.valor) {
                metodoActual = ejecutarSentencias(sentenciasSi);

                if (metodoActual.estadoRetorno) {
                    tabla = pilaTablas.pop();
                    return metodoActual;
                }

            } else {
                Nodo sino = null;

                if (sentenciasSino_Sino.size() == 1)//Solo Sino
                {
                    sino = sentenciasSino_Sino.get(0);
                    if (sino.size() > 0) {
                        /*Sentencia Sino*/
                        return Ejecutar_Sino(sino);
                    }
                } else if (sentenciasSino_Sino.size() > 1) {
                    sino = sentenciasSino_Sino.get(sentenciasSino_Sino.size() - 1);
                    for (int x = 0; x < sentenciasSino_Sino.size() - 1; x++) {
                        /*Sentencia Sino Si*/
                        Metodo m = Ejecutar_Sino_Si(sentenciasSino_Sino.get(x));
                        if (m != null) {
                            if(!m.estadoRetorno)
                            {
                                tabla = pilaTablas.pop();
                            }
                            
                            return m;
                        }
                    }
                    if (sino.size() > 0) {
                        /*Sentencia Sino*/
                        return Ejecutar_Sino(sino);
                    }

                }
            }
        } else {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Solo se permiten valores booleanos en la condicion de la sentencia si o enteros 0 o 1");
        }

        //regreso al ambito 
        tabla = pilaTablas.pop();
        return metodoActual;
    }

    public Metodo Ejecutar_Sino_Si(Nodo sino_si) {
        Nodo exp = sino_si.get(0);
        Nodo sentencias = sino_si.get(1);

        opL = new OperacionesARL(global, tabla, miTemplate);
        Resultado condicion = opL.ejecutar(exp);

        condicion = Si_Condicion_Num(condicion);
        if (condicion.tipo.equalsIgnoreCase("Boolean")) {
            if ((Boolean) condicion.valor) {
                metodoActual = ejecutarSentencias(sentencias);

                if (metodoActual.estadoRetorno) {
                    tabla = pilaTablas.pop();
                    return metodoActual;
                }
                return metodoActual;
            }
        } else {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Solo se permiten valores booleanos en la condicion de la sentencia si o enteros 0 o 1");
        }
        return null;
    }

    public Metodo Ejecutar_Sino(Nodo final_sino) {

        Nodo sentencias = final_sino.get(0);

        metodoActual = ejecutarSentencias(sentencias);
        if (metodoActual.estadoRetorno) {
            tabla = pilaTablas.pop();
            return metodoActual;
        }
        //regreso al ambito 
        tabla = pilaTablas.pop();
        return metodoActual;
    }

    public Resultado Si_Condicion_Num(Resultado condicion) {
        if (condicion.tipo.equalsIgnoreCase("Integer")) {
            if ((Integer) condicion.valor == 1) {
                return new Resultado("Boolean", true);
            } else if ((Integer) condicion.valor == 0) {
                return new Resultado("Boolean", false);
            }
        }
        return condicion;
    }
}
