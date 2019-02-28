/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptCompiler;

import Errores.ReporteError;
import Estructuras.Nodo;
import INTERFAZ.Template;
import ScriptCompiler.OperacionesARL.OperacionesARL;
import ScriptCompiler.Sentencias.Asignacion;
import ScriptCompiler.Sentencias.Declaracion;
import ScriptCompiler.Sentencias.Retornar;
import ScriptCompiler.Sentencias.Seleccion;
import ScriptCompiler.Sentencias.Si;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JOptionPane;

/**
 *
 * @author fernando
 */
public abstract class Compilador {

    public static ArrayList<Archivo> archivos;
    public String archivoActual;
    public static ArrayList<Simbolo> reporteSimbolos;
    public static ReporteError reporteError_CJS; // este REPORTE es para CJS

    //--------------------------------------------------------------------------
    public Template miTemplate;

    public static Clase claseActual;
    public static Stack<Clase> pilaClases;
    public static Stack<Metodo> pilaMetodos;
    public static Metodo metodoActual;
    public static Stack<TablaSimbolo> pilaTablas;
    //--------------------------------------------------------------------------

    protected Nodo raiz;
    public static int nivelCiclo = 0;
    public static Stack<Integer> pilaNivelCiclo;
    protected OperacionesARL opL;
    public static TablaSimbolo tabla;//
    public static TablaSimbolo global;//esta es la tabla que se usa a nivel global para todas las clases del compilador

    public Metodo ejecutarSentencias(Nodo Sentencias) {
        for (Nodo sentencia : Sentencias.hijos) {
            switch (sentencia.nombre) {
                case "declaracionvarG":
                    try {
                        new Declaracion(sentencia, global, tabla, miTemplate);
                    } catch (Exception e) {
                        Template.reporteError_CJS.agregar("Semantico", sentencia.linea, sentencia.columna, "Error al ejecutar Sentencia declaracion de Var Global:" + e.getMessage());
                    }
                    break;
                case "acceso":
                    try {
                        JOptionPane.showMessageDialog(null, "acceso");
                        opL = new OperacionesARL(global, tabla, miTemplate);
                        opL.ejecutar(sentencia);
                    } catch (Exception e) {
                        Template.reporteError_CJS.agregar("Semantico", sentencia.linea, sentencia.columna, "Error al ejecutar Sentencia Acceso:" + e.getMessage());
                    }

                    break;
                case "imprimir":
                    opL = new OperacionesARL(global, tabla, miTemplate);
                    Nodo param = sentencia.get(0);
                    if (param.size() > 0) {
                        Resultado rs = opL.ejecutar(param.get(0));

                        try {
                            //miTemplate.CONSOLA+="\n"+(String)rs.valor;
                            System.out.println(rs.valor.toString());
                        } catch (Exception e) {
                            Template.reporteError_CJS.agregar("Semantico", sentencia.linea, sentencia.columna, "Error al ejecutar Sentencia Imprimir:" + e.getMessage());
                        }
                    }

                    break;
                /*case "declara_vecF1_L":
                case "declara_vecF2_L":
                case "asignacionLocal":
                case "asigna_vecLocalF2":    
                case "asigna_vecLocalF1":        */
 /*try {
                        new Declaracion(sentencia, global, tabla,miTemplate);
                    } catch (Exception e) {
                    }
                    break;*/
 /*case "llamadaFuncion":
                    try {
                        opL = new OperacionesARL(global, tabla,miTemplate);
                        opL.acceso(sentencia);
                    } catch (Exception e) {
                    }
                    break;    
                case "retornar":
                    Retornar retorno = new Retornar();
                    //System.out.println("[ignore]estoy entrando aretornars");
                    metodoActual = retorno.ejecutar(sentencia);
                    //System.out.println(metodoActual.retorno.valor);
                    return metodoActual;
                case "si":
                    try {
                        Si si = new Si();
                        metodoActual = si.ejecutar(sentencia);
                        if (metodoActual.estadoRetorno) {
                            return metodoActual;
                        }
                        if (metodoActual.estadoTerminar) {
                            //metodoActual.estadoTerminar=false;
                            return metodoActual;
                        }

                        if (metodoActual.estadoContinuar) {
                            return metodoActual;
                        }
                    } catch (Exception e) {
                    }
                    break;
                case "mientras":
                    nivelCiclo++;
                    try {
                        Mientras mientras = new Mientras();
                        
                        metodoActual = mientras.ejecutar(sentencia);
                        if (metodoActual.estadoRetorno) {
                            nivelCiclo--;
                            return metodoActual;
                        }
                        
                    } catch (Exception e) {
                    }
                    nivelCiclo--;
                    break;
                 case "selecciona":
                     nivelCiclo++;
                     try {
                         
                         Selecciona seleccion = new Selecciona();
                         metodoActual = seleccion.ejecutar(sentencia);
                         if (metodoActual.estadoRetorno) {
                             nivelCiclo--;
                             return metodoActual;
                         }
                         if (metodoActual.estadoContinuar) {
                             nivelCiclo--;
                             return metodoActual;
                         }
                         
                     } catch (Exception e) {
                     }
                     nivelCiclo--;
                    break;    
                case "para":
                    nivelCiclo++;
                    try {
                        
                        Para para = new Para();
                        metodoActual = para.ejecutar(sentencia);
                        if (metodoActual.estadoRetorno) {
                            nivelCiclo--;
                            return metodoActual;
                        }
                        
                    } catch (Exception e) {
                    }
                    nivelCiclo--;
                    break;    
                case "imprimir":
                    opL = new OperacionesARL(global,tabla,miTemplate);
                    ResultadoG rs = opL.ejecutar(sentencia.hijos.get(0));
                    try {
                        miTemplate.CONSOLA+="\n"+(String)rs.valor;
                        System.out.println((String)rs.valor);
                    } catch (Exception e) {
                    }
                    break;
                case "mensaje":
                    opL = new OperacionesARL(global,tabla,miTemplate);
                    ResultadoG r1s = opL.ejecutar(sentencia.hijos.get(0));
                    try {
                        String texto=r1s.valor.toString();
                        System.out.println(texto);
                        //texto=texto.replaceAll("\\n", Ja);
                        AlertaGenerica alert = new AlertaGenerica(texto);
                        //Template.CONSOLA+="\n"+r1s.valor;
                        //System.out.println(r1s.valor);
                    } catch (Exception e) {
                    }
                    break;    
                case "detener":
                    if (nivelCiclo > 0) {
                        metodoActual.estadoTerminar = true;
                        return metodoActual;
                    } else {
                        Template.reporteError_CJS.agregar("Semantico", sentencia.linea, sentencia.columna, "La sentencia terminar solo puede estar detro de ciclos");
                    }
                    break;    
                case "Accion_Setear":
                    Accion_Setear setear = new Accion_Setear(miTemplate);
                    metodoActual = setear.ejecutar(sentencia);
                    break;   
                case "Accion_Obtener":
                    opL = new OperacionesARL(global,tabla,miTemplate);
                    opL.ejecutar(sentencia);
                    break;
                case "ADD":
                case "SUB":
                    opL = new OperacionesARL(global, global,miTemplate);
                    ResultadoG resp=opL.ejecutar(sentencia);
                    if(resp!=null){
                        if(!sentencia.valor.equals("")){
                            try {
                                Nodo raizz = new Nodo("asignacionLocal","",sentencia.linea,sentencia.columna,11222);
                                raizz.add(new Nodo("id", sentencia.valor, sentencia.linea, sentencia.columna, 8889));
                                raizz.add(new Nodo(resp.tipo,resp.valor.toString(),sentencia.linea,sentencia.columna,666));
                                new Declaracion(raizz, global, tabla,miTemplate);
                            } catch (Exception e) {}
                            
                            //System.out.println(resp.valor.toString());
                        }
                        //System.out.println(resp.valor.toString());
                        //System.out.println("ADD|SUB:"+resp.tipo);
                    }
                    break;
                 */

                case "si":
                    try {
                        Si si = new Si();
                        metodoActual = si.ejecutar(sentencia);
                        if (metodoActual.estadoRetorno) {
                            return metodoActual;
                        }
                        if (metodoActual.estadoTerminar) {
                            return metodoActual;
                        }

                        if (metodoActual.estadoContinuar) {
                            return metodoActual;
                        }
                    } catch (Exception e) {
                        Template.reporteError_CJS.agregar("Semantico", sentencia.linea, sentencia.columna, "Error al ejecutar Sentencia Si:" + e.getMessage());
                    }

                    break;
                case "selecciona":
                    try {
                        nivelCiclo++;
                        Seleccion seleccion = new Seleccion();
                        metodoActual = seleccion.ejecutar(sentencia);
                        if (metodoActual.estadoRetorno) {
                            nivelCiclo--;
                            return metodoActual;
                        }
                        if (metodoActual.estadoContinuar) {
                            nivelCiclo--;
                            return metodoActual;
                        }
                        nivelCiclo--;

                    } catch (Exception e) {
                        Template.reporteError_CJS.agregar("Semantico", sentencia.linea, sentencia.columna, "Error al ejecutar Sentencia Seleccionar:" + e.getMessage());
                    }
                    break;

                case "terminar":
                    if (nivelCiclo > 0) {
                        metodoActual.estadoTerminar = true;
                        return metodoActual;
                    } else {
                        Template.reporteError_CJS.agregar("Semantico", sentencia.linea, sentencia.columna, "La sentencia terminar solo puede estar detro de ciclos");
                    }
                    break;
                case "retorno":
                    Retornar retorno = new Retornar();
                    metodoActual = retorno.ejecutar(sentencia);
                    return metodoActual;
                case "asignacion":
                    new Asignacion(sentencia, global, tabla,miTemplate);
                    break;    
            }
        }
        return metodoActual;
    }

    public boolean esNulo(Resultado r) {
        if (r == null) {
            return true;
        } else {
            if (r.tipo.equals("-1") || r.tipo.equals("0nulo")) {
                return true;
            } else {
                if (r.valor == null) {
                    return true;
                } else {
                    return false;
                }
            }

        }
    }
}
