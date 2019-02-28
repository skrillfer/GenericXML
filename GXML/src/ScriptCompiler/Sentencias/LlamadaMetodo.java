/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptCompiler.Sentencias;

import Estructuras.Nodo;
import INTERFAZ.Template;
import ScriptCompiler.Arreglo;
import ScriptCompiler.Clase;
import ScriptCompiler.Compilador;
import ScriptCompiler.Metodo;
import ScriptCompiler.OperacionesARL.OperacionesARL;
import ScriptCompiler.Resultado;
import ScriptCompiler.TablaSimbolo;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author fernando
 */
public class LlamadaMetodo extends Compilador {

    public Resultado res_nativas = null;
    private Nodo raiz;
    private Resultado actualResultado;

    private Clase actual;
    private int nivel = 0;
    public boolean proceder = true;

    public LlamadaMetodo(Clase actual) {
        this.actual = actual;
    }

    public LlamadaMetodo(Clase actual, int nivel, Nodo raiz) {
        this.actual = actual;
        this.nivel = nivel;
        this.raiz = raiz;
    }

    public LlamadaMetodo(Clase actual, int nivel, Resultado resultado, Nodo raiz) {
        this.actual = actual;
        this.nivel = nivel;
        this.actualResultado = resultado;
        this.raiz = raiz;
        FuncionesNativasScript();
    }

    public Metodo ejecutar(Nodo raiz) {
        String nombre = raiz.valor;
        ArrayList<Resultado> parametros = getParametros(raiz);
        String id = getId(nombre, parametros);
        Metodo metodoTemp = getMetodo(id);
        //----------------------------------------------------------------------
        if (metodoTemp != null) {
            pilaNivelCiclo.push(nivelCiclo);
            nivelCiclo = 0;

            pilaTablas.push(tabla);
            TablaSimbolo tablaTemp = new TablaSimbolo();
            tabla = tablaTemp;
            for (int i = 0; i < metodoTemp.parametros.size(); i++) {
                Nodo parametro = metodoTemp.parametros.get(i);
                Resultado valor = parametros.get(i);
                new Declaracion(parametro, valor, actual.global, tabla, miTemplate);
            }

            pilaMetodos.push(metodoActual);
            metodoActual = metodoTemp;

            global = actual.global;
            pilaClases.push(claseActual);
            claseActual = actual;

            metodoTemp = ejecutarSentencias(metodoActual.sentencias);
            metodoTemp.estadoRetorno = false;
            metodoTemp.estadoContinuar = false;
            metodoTemp.estadoTerminar = false;
            metodoActual = pilaMetodos.pop();
            claseActual = pilaClases.pop();
            global = claseActual.global;
            tabla = pilaTablas.pop();
            nivelCiclo = pilaNivelCiclo.pop();
        } else {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "El metodo " + nombre + " no existe en el ambito donde fue invocado");
        }
        return metodoTemp;
    }

    private String getId(String nombre, ArrayList<Resultado> parametros) {
        /*for (Resultado resultado : parametros) {
            if (resultado.valor != null) {
                if (resultado.valor.getClass().getSimpleName().equalsIgnoreCase("arreglo")) {
                    Arreglo arr = (Arreglo) resultado.valor;
                    nombre += resultado.tipo + arr.dimensiones.size();
                } else {
                    nombre += resultado.tipo;
                }
            }
        }*/
        nombre += "_" + parametros.size();
        return nombre;
    }

    private ArrayList<Resultado> getParametros(Nodo raiz) {
        ArrayList<Resultado> parametros = new ArrayList<>();
        Nodo nodoParametros = raiz.hijos.get(0);
        for (Nodo hijo : nodoParametros.hijos) {
            opL = new OperacionesARL(global, tabla, miTemplate);
            Resultado resultado = opL.ejecutar(hijo);
            parametros.add(resultado);
        }
        return parametros;
    }

    private Metodo getMetodo(String id) {
        Metodo metodo = buscarMetodo(id, actual);
        if (metodo == null) {
            //NOTA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //aqui deberia de buscarlo en los importados
            /*if (actual.herencia != null) {
                actual = actual.herencia;
                metodo = getMetodo(id);
            }*/
        }

        return metodo;
    }

    private Metodo buscarMetodo(String id, Clase actual) {
        for (Metodo metodo : actual.metodos) {
            if (metodo.id.equalsIgnoreCase(id)) {
                return metodo;
            }
        }
        return null;
    }

    /*------------------------------------------------------------------------*/
    public void FuncionesNativasScript() {

        if (!esNulo(actualResultado)) {
            //El resultado anterior fu un arreglo
            if (esArreglo(actualResultado.valor)) {
                Arreglo arr;
                switch (raiz.valor.toLowerCase()) {
                    case "ascendente":
                        proceder = false;
                        arr = (Arreglo) actualResultado.valor;
                        try {
                            arr.ascendente();
                        } catch (Exception e) {
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al ejecutar ascendente:" + e.getMessage());
                        }

                        break;
                    case "descendente":
                        proceder = false;
                        arr = (Arreglo) actualResultado.valor;
                        try {
                            arr.descendente();
                        } catch (Exception e) {
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al ejecutar descendente:" + e.getMessage());
                        }
                        break;
                    case "maximo":
                        proceder = false;
                        arr = (Arreglo) actualResultado.valor;
                        try {
                            res_nativas = arr.maximo();
                        } catch (Exception e) {
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al ejecutar maximo:" + e.getMessage());
                        }
                        break;
                    case "minimo":
                        proceder = false;
                        arr = (Arreglo) actualResultado.valor;
                        try {
                            res_nativas = arr.minimo();
                        } catch (Exception e) {
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al ejecutar minimo:" + e.getMessage());
                        }
                        break;
                    case "invertir":
                        proceder = false;
                        arr = (Arreglo) actualResultado.valor;
                        try {
                            arr.invertir();
                        } catch (Exception e) {
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al ejecutar invertir:" + e.getMessage());
                        }
                        break;

                }
            }
        }
    }

    public boolean esArreglo(Object valor) {
        try {
            Arreglo ar = (Arreglo) valor;
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean esClase(Object valor) {
        try {
            Clase ar = (Clase) valor;
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
