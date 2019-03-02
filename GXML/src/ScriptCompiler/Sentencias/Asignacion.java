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

                //if (!simbolo.vieneReferido) {
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
                        TipoAsignacion tipoAsig = new TipoAsignacion();
                        resultado = tipoAsig.aplicarAsignacion(simbolo, raiz.get(1), resultado);

                        simbolo.valor = resultado.valor;
                        simbolo.tipo = resultado.tipo;
                        simbolo.inicializado = true;
                        if (resultado.simbolo != null) {
                            simbolo.esArreglo = resultado.simbolo.esArreglo;
                        }
                    } catch (Exception e) {
                    }
                }
                /*} else {
                    System.out.println("-=-=-=-=-=");
                    Resultado res = (Resultado) simbolo.valor;
                    res.valor = resultado.valor;
                    res.tipo = resultado.tipo;
                }*/

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
                    nombre = acceso.valor;
                    aux.tabla = tabla;
                    tabla = tablaAux;
                    simbolo = accesoAr(acceso, aux);

                    if (simbolo != null) {
                        switch (simbolo.tipo) {
                            case "Integer":
                            case "Double":
                            case "String":
                            case "Boolean":
                                sim = simbolo;
                                break;

                            default:

                                if (!simbolo.esArreglo) {
                                    try {
                                        nivel++;
                                        aux = (Clase) simbolo.valor;
                                        tabla = aux.tabla;
                                        sim = simbolo;
                                    } catch (Exception e) {
                                        nivel++;
                                        sim = simbolo;
                                    }

                                }

                                break;
                        }

                    } else {
                        Template.reporteError_CJS.agregar("Semantico", acceso.linea, acceso.columna, "La variable " + nombre + " no existe en el ambito donde fue invocada");
                        return null;
                    }
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
                                if (!simbolo.esArreglo) {
                                    try {
                                        nivel++;
                                        aux = (Clase) simbolo.valor;
                                        tabla = aux.tabla;
                                        sim = simbolo;
                                    } catch (Exception e) {
                                        nivel++;
                                        sim = simbolo;
                                    }

                                }
                                break;
                        }

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

    private Simbolo accesoAr(Nodo raiz, Clase aux) {
        Simbolo simbolo;
        simbolo = aux.tabla.getSimbolo((String) raiz.valor, aux);

        if (simbolo != null) {
            if (simbolo.inicializado) {
                if (simbolo.esArreglo) {
                    Arreglo arreglo = (Arreglo) simbolo.valor;
                    ArrayList<Integer> indices = new ArrayList<>();

                    if (raiz.get(0).size() > 1) {
                        Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Todos los arreglos son de una dimension, por lo tanto ha sobrepasado el acceso de una dimension al arreglo:" + simbolo.nombre);
                        return null;
                    }
                    Nodo nodo = raiz.get(0).get(0);
                    opL = new OperacionesARL(global, tabla, miTemplate);
                    Resultado indice = opL.ejecutar(nodo);
                    if (indice.tipo.equalsIgnoreCase("Integer")) {
                        indices.add((Integer) indice.valor);
                    } else {
                        Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Solo se permiten valores enteros al acceder a un indce de un arreglo");
                        return null;
                    }

                    //Obtengo el Resultado de la posicion en el arreglo
                    Object obtenido = arreglo.getValor(indices);
                    if (obtenido != null) {
                        Resultado res_obtenido = (Resultado) obtenido;
                        if (!esNulo(res_obtenido)) {
                            Simbolo nuevoSim = new Simbolo(res_obtenido.tipo, "", "", res_obtenido);
                            nuevoSim.valor = res_obtenido.valor;
                            nuevoSim.tipo = res_obtenido.tipo;
                            nuevoSim.inicializado = true;
                            nuevoSim.vieneReferido = true;
                            return nuevoSim;
                        } else {
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "El indice obtenido [" + indices.get(0) + "] para el arreglo:" + raiz.valor + " es nulo");
                            return null;
                        }
                    } else {
                        Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "El indice fuera del alcance [" + indices.get(0) + "] para el arreglo:" + raiz.valor + " no es arreglo");
                        return null;
                    }

                } else {
                    Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + raiz.valor + " no es arreglo");
                    return null;
                }
            } else {
                Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + raiz.valor + " no ha sido inicializada");
                return null;
            }
        } else {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + raiz.valor + " no existe");
            return null;
        }
    }

    private Simbolo asignacionAr() {
        Simbolo simbolo = acceso(raiz.hijos.get(0));
        if (simbolo != null) {
            if (simbolo.valor.getClass().getSimpleName().equalsIgnoreCase("arreglo")) {
                Arreglo arregloId = (Arreglo) simbolo.valor;

            } else {
                Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La variable no es de tipo arreglo");
            }
        } else {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La variable no existe");
        }
        return null;
    }
}
