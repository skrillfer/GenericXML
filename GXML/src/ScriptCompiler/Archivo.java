/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptCompiler;

import Analizadores.Script.LexScript;
import Analizadores.Script.SintacticoScript;
import Estructuras.Nodo;
import INTERFAZ.Template;
import static ScriptCompiler.Compilador.archivos;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 *
 * @author fernando
 */
public class Archivo {

    public String nombre;
    Nodo raiz;
    public ArrayList<Clase> clases;
    public ArrayList<Nodo> archivosImportados;
    //***************************************************************************
    public ArrayList<Metodo> metodos = null;
    public ArrayList<Nodo> atributos = null;

    /*
        ** Para cuando el archivo a crear ya es de import mas no el principal
     */
    public Archivo(String nombre, Nodo raiz, ArrayList<Metodo> metodos, ArrayList<Nodo> atributos) {
        clases = new ArrayList();
        archivosImportados = new ArrayList<>();
        this.nombre = nombre;
        this.raiz = raiz;
        this.metodos = metodos;
        this.atributos = atributos;
        guardarImports(raiz.hijos.get(0));
        guardarClases(raiz.hijos.get(1));

        
    }

    /*
       ** Utilizado por Clase Script siendo el archivo principal el que crea esta instancia
     */
    public Archivo(String nombre, Nodo raiz) {
        clases = new ArrayList();
        archivosImportados = new ArrayList<>();
        this.nombre = nombre;
        this.raiz = raiz;
        guardarImports(raiz.hijos.get(0));
        guardarClases(raiz.hijos.get(1));
    }

    private void guardarImports(Nodo raiz) {
        for (Nodo imp : raiz.hijos) {
            if (imp.nombre.equalsIgnoreCase("importar")) {
                archivosImportados.add(imp);
            }
        }
    }

    private void guardarClases(Nodo raiz) {
        Clase clase = null;
        if (metodos != null && atributos != null) {
            clase = new Clase(raiz, metodos, atributos);
        } else {
            clase = new Clase(raiz, nombre);
            clases.add(clase);
        }

        for (Nodo nodoImportado : archivosImportados) {
            String rutaBuena = existeArchivo(nodoImportado.valor);
            if (!rutaBuena.equals("")) {
                File file = new File(rutaBuena);
                String nombre_ = file.getName();
                String tipo = nombre_.substring(nombre_.length() - 2, nombre_.length());
                if (tipo.equals("fs")) {
                    String cadena = obtenerTextoArchivo(file);
                    try {
                        LexScript lex = new LexScript(new BufferedReader(new StringReader(cadena)));
                        SintacticoScript sin = new SintacticoScript(lex);
                        sin.parse();
                        Nodo raizz = sin.getRoot();
                        if (raizz != null) {
                            Archivo archivo = new Archivo(nombre, raizz,clase.metodos,clase.atributos);
                            //archivos.add(archivo);
                        }
                    } catch (Exception ex) {
                        Template.reporteError_CJS.agregar("Sintactico", nodoImportado.linea, nodoImportado.columna, "El archivo [" + nombre_ + "] no pudo ser parseado");
                    }
                } else {
                    Template.reporteError_CJS.agregar("Semantico", nodoImportado.linea, nodoImportado.columna, "El archivo [" + nombre_ + "] a importar no es de extension .fs");
                }
            } else {
                Template.reporteError_CJS.agregar("Semantico", nodoImportado.linea, nodoImportado.columna, "El archivo [" + nodoImportado.valor + "] a importar no existe en la ruta especificada");
            }
        }
        /*
        Creo una instancia para cada estructura que encuentre dentro de los atributos 
         */

    }
    
    public String existeArchivo(String path)
    {
        String pathPadre=Compilador.PathRoot;
        
        File file1 = new File(pathPadre+""+path);
        File file2 = new File(pathPadre+"/"+path);
        File file3 = new File(path);
        File file4 = new File("/"+path);
        
        if(file1.exists())
        {
            return file1.getAbsolutePath();
        }
        
        if(file2.exists())
        {
            return file2.getAbsolutePath();
        }
        
        if(file3.exists())
        {
            return file3.getAbsolutePath();
        }
        
        if(file4.exists())
        {
            return file4.getAbsolutePath();
        }
        return "";
    }

    String obtenerTextoArchivo(File file) {
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
