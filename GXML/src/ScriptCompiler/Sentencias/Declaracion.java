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
import ScriptCompiler.OperacionesARL.OperacionesARL;
import ScriptCompiler.Resultado;
import ScriptCompiler.Simbolo;
import ScriptCompiler.TablaSimbolo;

/**
 *
 * @author fernando
 */
public class Declaracion extends Compilador {

    TablaSimbolo tabla;
    TablaSimbolo global;

    /*  Constructor utilizado cuando quiero crear una estructura desde Operaciones ARL    */
    public Declaracion(TablaSimbolo global, TablaSimbolo tabla, Template template1) {
        this.miTemplate = template1;
        this.raiz = raiz;
        this.tabla = global;//tabla de atributos
        this.global = new TablaSimbolo();//tabla de variables locales
        opL = new OperacionesARL(global, global, miTemplate);
    }

    public Declaracion(Nodo raiz, TablaSimbolo global, Template template1) {
        this.miTemplate = template1;
        this.raiz = raiz;
        this.tabla = global;//tabla de atributos
        this.global = new TablaSimbolo();//tabla de variables locales
        opL = new OperacionesARL(global, global, miTemplate);
        declarar();
    }

    //* cuando DECLARO PARAMETROS USO ESTE CONSTRUCTOR
    public Declaracion(Nodo raiz, Resultado resultado, TablaSimbolo global, TablaSimbolo tabla, Template template1) {
        this.miTemplate = template1;
        this.raiz = raiz;
        this.global = global;
        this.tabla = tabla;
        declararParametro(resultado);
    }

    //* cuando hago una declaracion simple USO este CONSTRUCTOR
    public Declaracion(Nodo raiz, TablaSimbolo global, TablaSimbolo tabla, Template template1) {
        this.miTemplate = template1;
        this.raiz = raiz;
        this.global = global;//tabla de variables locales
        this.tabla = tabla;//tabla de variables globales
        opL = new OperacionesARL(global, tabla, miTemplate);
        declarar();
    }

    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    private Simbolo declararParametro(Resultado resultado) {

        if (esArreglo(resultado)) {
            return parametroAr(resultado);
        } else {
            return parametro(resultado);
        }
    }

    private Simbolo parametro(Resultado resultado) {
        String nombre = raiz.valor;
        if (!esNulo(resultado)) {
            String tipo = resultado.tipo;
            Simbolo simbolo = new Simbolo(tipo, nombre, resultado.valor);
            simbolo.inicializado = true;
            if (!tabla.setSimbolo(simbolo)) {
                Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error en declaracion de parametro: La variable " + nombre + " ya existe");
            }
        } else {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "El valor a asignar al parametro [" + nombre + "] es nulo");
        }
        return null;
    }

    private Simbolo parametroAr(Resultado resultado) {
        String nombre = raiz.valor;
        if (!esNulo(resultado)) {
            Arreglo arreglo = (Arreglo) resultado.valor;
            String tipo = arreglo.type;
            Simbolo simbolo = new Simbolo(tipo, nombre, arreglo);
            simbolo.inicializado = true;
            simbolo.esArreglo = true;
            if (!tabla.setSimbolo(simbolo)) {
                Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " ya existe");
            }

        } else {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "El valor a asignar al parametroArr [" + nombre + "] es nulo");
        }
        return null;
    }

    /*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    public Object declarar() {
        //System.out.println(raiz.nombre);
        switch (raiz.nombre) {
            //********************  AMBITO GLOBAL ******************************
            case "declaracionvarG":
                try {
                    declaracionvarG();
                } catch (Exception e) {
                }
                break;
            //********************  AMBITO  ******************************    
            case "declaracionvar":
                try {
                    declaracionvar();
                } catch (Exception e) {
                }
                break;
            default:
                System.out.println(raiz.nombre);
                break;
        }

        return null;
    }

    public void declaracionvarG() {
        String tipo = "";//el tipo de la  variable depende del valor que tenga

        Nodo LISTA_ID = raiz.get(0);
        Nodo ASIGN = raiz.get(1);

        for (Nodo nodoID : LISTA_ID.hijos) {
            String nombre = nodoID.valor;//se obtiene el nombre de la variable a declarar

            if (ASIGN.size() > 0) {
                //Se obtiene la expresion de asignacion y entonces se trata de obtener el resultado
                Nodo EXP = ASIGN.get(0);
                
                Resultado resultado = null;
                if(claseActual.Componente==null)
                {
                    resultado = opL.ejecutar(EXP);
                }else
                {
                    if(!nombre.equals("referencia"))
                    {
                        resultado = opL.ejecutar(EXP);
                    }else
                    {
                        resultado = new Resultado("String", EXP);
                    }
                }
                

                if (!esNulo(resultado)) {

                    // la variable toma el tipo del valor que le es asignado
                    tipo = resultado.tipo;

                    Object RESULTADO = null;
                    boolean INICIALIZADO = false;

                    if (LISTA_ID.get(LISTA_ID.size() - 1).valor.equalsIgnoreCase(nodoID.valor)) {
                        RESULTADO = resultado.valor;
                        INICIALIZADO = true;

                        if (esArreglo(resultado.valor)) {
                            Arreglo arr = (Arreglo) resultado.valor;
                            if (!arr.estado) {
                                RESULTADO = null;
                                INICIALIZADO = false;
                            } else {
                                tipo = arr.type;
                            }
                        }

                        if (esClase(resultado.valor)) {
                            Clase clase = (Clase) resultado.valor;
                            if(!clase.Inicializada)
                            {
                                clase.nombre = nombre;
                                clase.ejecutar(miTemplate);
                                clase.Inicializada = true;
                            }
                            
                        }

                    }

                    Simbolo simbolo = new Simbolo(tipo, nombre, "", RESULTADO);
                    simbolo.inicializado = INICIALIZADO;
                    if (!global.setSimbolo(simbolo)) {
                        Template.reporteError_CJS.agregar("Error Semantico", nodoID.linea, nodoID.columna, "La variable " + nombre + " ya existe");
                    }
                } else {
                    Simbolo simbolo = new Simbolo(tipo, nombre, "", null);
                    if (!global.setSimbolo(simbolo)) {
                        Template.reporteError_CJS.agregar("Error Semantico", nodoID.linea, nodoID.columna, "La variable " + nombre + " ya existe");
                    }
                }
            } else {
                Simbolo simbolo = new Simbolo(tipo, nombre, "", null);
                if (!global.setSimbolo(simbolo)) {
                    Template.reporteError_CJS.agregar("Semantico", nodoID.linea, nodoID.columna, "La variable " + nombre + " ya existe");
                }
            }
        }
    }
    
    public void declaracionvar() {
        String tipo = "";//el tipo de la  variable depende del valor que tenga

        Nodo LISTA_ID = raiz.get(0);
        Nodo ASIGN = raiz.get(1);

        for (Nodo nodoID : LISTA_ID.hijos) {
            String nombre = nodoID.valor;//se obtiene el nombre de la variable a declarar

            if (ASIGN.size() > 0) {
                //Se obtiene la expresion de asignacion y entonces se trata de obtener el resultado
                Nodo EXP = ASIGN.get(0);
                
                Resultado resultado = null;
                if(claseActual.Componente==null)
                {
                    resultado = opL.ejecutar(EXP);
                }else
                {
                    if(!nombre.equals("referencia"))
                    {
                        resultado = opL.ejecutar(EXP);
                    }else
                    {
                        resultado = new Resultado("String", EXP);
                    }
                }
                

                if (!esNulo(resultado)) {

                    // la variable toma el tipo del valor que le es asignado
                    tipo = resultado.tipo;

                    Object RESULTADO = null;
                    boolean INICIALIZADO = false;

                    if (LISTA_ID.get(LISTA_ID.size() - 1).valor.equalsIgnoreCase(nodoID.valor)) {
                        RESULTADO = resultado.valor;
                        INICIALIZADO = true;

                        if (esArreglo(resultado.valor)) {
                            Arreglo arr = (Arreglo) resultado.valor;
                            if (!arr.estado) {
                                RESULTADO = null;
                                INICIALIZADO = false;
                            } else {
                                tipo = arr.type;
                            }
                        }

                        if (esClase(resultado.valor)) {
                            Clase clase = (Clase) resultado.valor;
                            if(!clase.Inicializada)
                            {
                                clase.nombre = nombre;
                                clase.ejecutar(miTemplate);
                                clase.Inicializada = true;
                            }
                            
                        }

                    }

                    Simbolo simbolo = new Simbolo(tipo, nombre, "", RESULTADO);
                    simbolo.inicializado = INICIALIZADO;
                    if (!tabla.setSimbolo(simbolo)) {
                        Template.reporteError_CJS.agregar("Error Semantico", nodoID.linea, nodoID.columna, "La variable LOCAL " + nombre + " ya existe");
                    }
                } else {
                    Simbolo simbolo = new Simbolo(tipo, nombre, "", null);
                    if (!tabla.setSimbolo(simbolo)) {
                        Template.reporteError_CJS.agregar("Error Semantico", nodoID.linea, nodoID.columna, "La variable LOCAL " + nombre + " ya existe");
                    }
                }
            } else {
                Simbolo simbolo = new Simbolo(tipo, nombre, "", null);
                if (!tabla.setSimbolo(simbolo)) {
                    Template.reporteError_CJS.agregar("Semantico", nodoID.linea, nodoID.columna, "La variable LOCAL " + nombre + " ya existe");
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

    public Clase crearInstanciaEstructura(Nodo raiz) {
        Clase clase = new Clase(raiz);
        return clase;
    }

}
