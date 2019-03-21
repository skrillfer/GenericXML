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
import WRAPERS.VentanaGenerica;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JOptionPane;

/**
 *
 * @author fernando
 */
public abstract class Compilador {

    public static VentanaGenerica ULTIMAVT=null;
    public static Clase superClase;
    public static String PathRoot = "";

    public static VentanaGenerica VT_ACTUAL = null;
    public static ArrayList<VentanaGenerica> listaVentanas;

    public static ArrayList<Archivo> archivos;
    public String archivoActual;
    public static ArrayList<Simbolo> reporteSimbolos;
    public static ReporteError reporteError_CJS; // este REPORTE es para CJS

    //--------------------------------------------------------------------------
    public static Template miTemplate;

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
                case "importar":
                    break;
                case "declaracionvarG":
                case "declaracionvar":
                    try {
                        new Declaracion(sentencia, global, tabla, miTemplate);
                    } catch (Exception e) {
                        Template.reporteError_CJS.agregar("Semantico", sentencia.linea, sentencia.columna, "Error al ejecutar Sentencia " + sentencia.nombre + ":" + e.getMessage());
                    }
                    break;
                case "acceso":
                    try {
                        opL = new OperacionesARL(global, tabla, miTemplate);
                        opL.ejecutar(sentencia);
                    } catch (Exception e) {
                        Template.reporteError_CJS.agregar("Semantico", sentencia.linea, sentencia.columna, "Error al ejecutar Sentencia " + sentencia.nombre + ":" + e.getMessage());
                    }
                    break;
                case "imprimir":
                    try {
                        opL = new OperacionesARL(global, tabla, miTemplate);
                        Nodo param = sentencia.get(0);
                        if (param.size() > 0) {
                            Resultado rs = opL.ejecutar(param.get(0));

                            try {
                                if (Template.CONSOLA != null) {
                                    Template.CONSOLA.append(rs.valor.toString() + "\n");
                                } else {
                                    System.out.println(rs.valor.toString());
                                }
                            } catch (Exception e) {
                                Template.reporteError_CJS.agregar("Semantico", sentencia.linea, sentencia.columna, "Error al ejecutar Sentencia Imprimir:" + e.getMessage());
                            }
                        }
                    } catch (Exception e) {
                        Template.reporteError_CJS.agregar("Semantico", sentencia.linea, sentencia.columna, "Error al ejecutar Sentencia " + sentencia.nombre + ":" + e.getMessage());
                    }
                    break;
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
                        Template.reporteError_CJS.agregar("Semantico", sentencia.linea, sentencia.columna, "Error al ejecutar Sentencia " + sentencia.nombre + ":" + e.getMessage());
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
                        Template.reporteError_CJS.agregar("Semantico", sentencia.linea, sentencia.columna, "Error al ejecutar Sentencia " + sentencia.nombre + ":" + e.getMessage());
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

                    try {
                        metodoActual = retorno.ejecutar(sentencia);
                        return metodoActual;

                    } catch (Exception e) {
                        Template.reporteError_CJS.agregar("Semantico", sentencia.linea, sentencia.columna, "Error al ejecutar Sentencia " + sentencia.nombre + ":" + e.getMessage());
                    }

                case "asignacion":
                    try {
                        new Asignacion(sentencia, global, tabla, miTemplate);
                    } catch (Exception e) {
                        Template.reporteError_CJS.agregar("Semantico", sentencia.linea, sentencia.columna, "Error al ejecutar Sentencia " + sentencia.nombre + ":" + e.getMessage());
                    }

                    break;
                case "add":
                case "sub":
                    try {
                        opL = new OperacionesARL(global, tabla, miTemplate);
                        opL.ejecutar(sentencia);
                    } catch (Exception e) {
                        Template.reporteError_CJS.agregar("Semantico", sentencia.linea, sentencia.columna, "Error al ejecutar Sentencia " + sentencia.nombre + ":" + e.getMessage());
                    }

                    break;

            }
        }
        return metodoActual;
    }

    public boolean esNulo(Resultado r) {
        if (r == null) {
            return true;
        } else {
            if (r.tipo.equals("-1")) {
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

    public Nodo crearNodo(String nombre, String valor, int linea, int columna, int index) {
        Nodo nuevo = new Nodo(nombre, valor, linea, columna, index);
        return nuevo;
    }

    public boolean esNumero(String nm) {
        try {
            Integer.valueOf(nm);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String existeArchivo(String path) {
        String pathPadre = Compilador.PathRoot;

        File file1 = new File(pathPadre + "" + path);
        File file2 = new File(pathPadre + "/" + path);
        File file3 = new File(path);
        File file4 = new File("/" + path);

        if (file1.exists()) {
            return file1.getAbsolutePath();
        }

        if (file2.exists()) {
            return file2.getAbsolutePath();
        }

        if (file3.exists()) {
            return file3.getAbsolutePath();
        }

        if (file4.exists()) {
            return file4.getAbsolutePath();
        }
        return "";
    }

    public String obtenerTextoArchivo(File file) {
        String texto = "";
        try {
            BufferedReader bufer = new BufferedReader(
                    new InputStreamReader(new FileInputStream((String) file.getAbsolutePath())));
            String temp = "";
            while (temp != null) {
                temp = bufer.readLine();
                if (temp != null) {
                    texto = texto + temp + "\n";
                    temp = "";
                } else {
                }

            }
            bufer.close();

        } catch (Exception e) {
        }
        return texto;
    }
}
