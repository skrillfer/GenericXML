/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptCompiler.Sentencias;

import Estructuras.Nodo;
import ScriptCompiler.Compilador;
import ScriptCompiler.Metodo;
import ScriptCompiler.OperacionesARL.OperacionesARL;
import ScriptCompiler.Resultado;
import ScriptCompiler.TablaSimbolo;
import javax.swing.JOptionPane;

/**
 *
 * @author fernando
 */
public class Seleccion extends Compilador {

    public Metodo ejecutar(Nodo raiz) {
        Nodo acceso = raiz.hijos.get(0);
        Nodo casos = raiz.hijos.get(1);
        boolean estado = true;

        Nodo defecto = null;
        if (casos.hijos.size() > 0) {
            for (int i = 0; i < casos.hijos.size(); i++) {
                Nodo caso = casos.hijos.get(i);

                if (caso.nombre.equalsIgnoreCase("defecto")) {
                    defecto = caso;
                } else {
                    Nodo comp = caso.hijos.get(0);
                    Nodo sentencias = caso.hijos.get(1);

                    Nodo exp = new Nodo("ig_ig", "", comp.linea - 1, comp.columna - 1, 1112);
                    exp.add(acceso);
                    exp.add(comp);

                    opL = new OperacionesARL(global, tabla, miTemplate);
                    Resultado condicion = opL.ejecutar(exp);
                    if (condicion.tipo.equals("Boolean")) {
                        if ((Boolean) condicion.valor) {
                            TablaSimbolo tablaTemp = new TablaSimbolo();
                            tablaTemp.cambiarAmbito(tabla);
                            pilaTablas.push(tabla);
                            tabla = tablaTemp;
                            metodoActual = ejecutarSentencias(sentencias);

                            if (metodoActual.estadoRetorno) {
                                tabla = pilaTablas.pop();
                                return metodoActual;
                            }

                            if (metodoActual.estadoTerminar) {
                                estado = false;
                                tabla = pilaTablas.pop();
                                metodoActual.estadoTerminar = false;
                                break;
                            }
                            if (metodoActual.estadoContinuar) {
                                estado = false;
                                tabla = pilaTablas.pop();
                                break;
                            }
                            tabla = pilaTablas.pop();
                        }
                    }
                }

            }
            //defecto
            if (estado && defecto!=null) {
                Nodo sentencias = defecto.hijos.get(0);
                estado = false;
                TablaSimbolo tablaTemp = new TablaSimbolo();
                tablaTemp.cambiarAmbito(tabla);
                pilaTablas.push(tabla);
                tabla = tablaTemp;
                metodoActual = ejecutarSentencias(sentencias);

                if (metodoActual.estadoRetorno) {
                    tabla = pilaTablas.pop();
                    return metodoActual;
                }

                if (metodoActual.estadoTerminar) {
                    tabla = pilaTablas.pop();
                    metodoActual.estadoTerminar = false;
                }
                if (metodoActual.estadoContinuar) {
                    estado = false;
                    tabla = pilaTablas.pop();
                    //metodoActual.estadoContinuar=false;
                } else {
                    tabla = pilaTablas.pop();
                }
            }

        }

        return metodoActual;
    }
}
