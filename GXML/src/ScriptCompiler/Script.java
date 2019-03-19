/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptCompiler;

import Analizadores.Script.LexScript;
import Analizadores.Script.SintacticoScript;
import Ast_Generator.AST_Script;
import Errores.ReporteError;
import Estructuras.Nodo;
import INTERFAZ.Template;
import ScriptCompiler.Sentencias.Declaracion;
import WRAPERS.VentanaGenerica;
import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author fernando
 */
public class Script extends Compilador {

    ArrayList<Clase> lista_Clases = new ArrayList<>();

    public Script(File[] files, String archivoActual, Template template, String path_root) {
        //EL ARCHIVO ACTUAL ES MI PRINCIPAL
        miTemplate = template;
        PathRoot=path_root;
        archivos = new ArrayList();
        listaVentanas = new ArrayList<>();
        reporteError_CJS = new ReporteError();
        reporteSimbolos = new ArrayList();
        this.archivoActual = archivoActual;
        for (File file : files) {
            String nombre = file.getName();
            String tipo = nombre.substring(nombre.length() - 2, nombre.length());
            //if (tipo.equalsIgnoreCase("fs")) {
            String cadena = obtenerTextoArchivo(file);
            try {
                LexScript lex = new LexScript(new BufferedReader(new StringReader(cadena)));
                SintacticoScript sin = new SintacticoScript(lex);
                sin.parse();
                Nodo raizz = sin.getRoot();
                if (raizz != null) {
                    Ast_Generator.AST_Script ss = new AST_Script();
                    ss.generacion_arbolScript(raizz);
                    Archivo archivo = new Archivo(nombre, raizz);
                    archivos.add(archivo);
                }
            } catch (Exception ex) {
                System.err.println(ex.toString());
            }
            //}
        }

        pilaNivelCiclo = new Stack<>();
        pilaClases = new Stack<>();
        pilaMetodos = new Stack<>();
        pilaTablas = new Stack<>();
        claseActual = getClasePrincipal();
        superClase = getClasePrincipal();
        if (claseActual == null) {
            Template.reporteError_CJS.agregar("Semantico", 0, 0, "Metodo inicio no encontrado");
            return;
        }
        tabla = claseActual.tabla;
        global = claseActual.global;

        Nodo padre = new Nodo("Sentencias", "", 0, 0, 8918);
        for (Nodo atributo : claseActual.atributos) {
            padre.add(atributo);
        }
        Metodo m = new Metodo();
        metodoActual = m;
        ejecutarSentencias(padre);

        //new Heredar(claseActual);
        for (VentanaGenerica listaVentana : listaVentanas) {
            //System.out.println(listaVentana.getPreferredSize().width + "-" + listaVentana.getPreferredSize().height);
            //listaVentana.getContentPane().setLayout(null);


            //listaVentana.pack();
            //listaVentana.setLayout(null);

            //listaVentana.setBounds(100, 10, listaVentana.getSize().width, listaVentana.getSize().height);
            //listaVentana.setLocationRelativeTo(null);
            //listaVentana.setVisible(true);
        }

    }

    private Clase getClasePrincipal() {
        ArrayList<Clase> clases;
        Archivo archivo = getArchivoPrincipal();
        if (archivo == null) {
            return null;
        }
        //Se retorna la primera clase ya que todo el documento es una sola "clase"
        clases = archivo.clases;
        if (!clases.isEmpty()) {
            return clases.get(0);
        }
        return null;
    }

    private Archivo getArchivoPrincipal() {
        for (Archivo archivo : archivos) {
            if (archivo.nombre.equalsIgnoreCase(archivoActual)) {
                return archivo;
            }
        }
        return null;
    }

    public void ejecucionCJS(Nodo raiz, String metodoInicio, String archivo, Template template1) {
        //aqui habra una lista de archivos cjs que pertenecen a el html
        //como hay una lista de archivos cjs debe haber una lista de CLASES (JAVASCRIPT)
        //##############Se crear una nueva CLASE
        System.out.println("\n\n al menos si llego aqui");
        miTemplate = template1;
        raiz.valor = archivo;
        Clase n_clase = new Clase(raiz, "");
        n_clase.archivo = archivo;

        claseActual = n_clase;
        global = n_clase.global;
        tabla = n_clase.tabla;

        TablaSimbolo tmpGlobal = n_clase.global;
        TablaSimbolo tmpLocal = n_clase.tabla;

        //n_clase.ejecutar(); //se ejecutan las declaraciones globales 
        pilaNivelCiclo = new Stack<>();
        pilaClases = new Stack<>();
        pilaMetodos = new Stack<>();
        pilaTablas = new Stack<>();

        for (Nodo atributo : n_clase.atributos) {
            if (atributo.nombre.equalsIgnoreCase("declaracionvarG") /*|| atributo.nombre.equalsIgnoreCase("declara_vecF1")
                    || atributo.nombre.equalsIgnoreCase("declara_vecF2") || atributo.nombre.equalsIgnoreCase("asigna_vecGlbF1")
                    || atributo.nombre.equalsIgnoreCase("asigna_vecGlbF2") || atributo.nombre.equalsIgnoreCase("asignacionGlb")*/) {
                new Declaracion(atributo, global, tabla, template1);
            } else {
                Nodo padre = new Nodo("Sentencias", "", 0, 0, 898);
                padre.hijos.add(atributo);
                ejecutarSentencias(padre);
            }
        }

        metodoActual = getInicio(metodoInicio);
        global = claseActual.global;
        tabla = claseActual.tabla;

        /*if(metodoActual!=null){
            ejecutarSentencias(metodoActual.sentencias);// se ejecutan las sentencias del metodo que inicia la compilacion
        }*/
        // despues de ejecutar se guarda los valores iniciales que tenia
        n_clase.global = tmpGlobal;
        n_clase.tabla = tmpLocal;
        lista_Clases.add(n_clase);
        System.out.println(" \n\n y acabo aqui");
    }

    public Metodo getInicio(String nombre) {
        for (Metodo metodo : claseActual.metodos) {
            if (metodo.nombre.equalsIgnoreCase(nombre)) {
                return metodo;
            }
        }
        return null;
    }

    
}
