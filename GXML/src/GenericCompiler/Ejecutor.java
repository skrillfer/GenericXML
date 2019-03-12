/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GenericCompiler;

import Analizadores.Gxml.LexGxml;
import Analizadores.Gxml.SintacticoGxml;
import Estructuras.Nodo;
import ScriptCompiler.Script;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author fernando
 */
public class Ejecutor {
    //public  Script principal_Script = new Script();

    Hashtable<String, String> lista_cjs = null;

    public static void main(String[] args) throws FileNotFoundException {

        //new Ejecutor().MAIN();
        try {
            new Ejecutor().compilar();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ejecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void MAIN() {

        Script.archivos = new ArrayList();

        File padre = new File("/home/fernando/NetBeansProjects/GXMLProyecto/GenericXML/GXML/Entradas_Script/pruebitas.txt");
        File file = new File(padre.getParent());
        File files[] = file.listFiles();
        //Script graphik = new Script(files, "pruebitas.txt");

        /*
        
         if (pestaniaActiva != null) {
            if (pestaniaActiva.file != null) {
                File file = new File(pestaniaActiva.file.getParent());
                File files[] = file.listFiles();
                Graphik graphik = new Graphik(files, pestaniaActiva.nombre);
            }
        }
        
         */
 /*lista_cjs = new Hashtable<>();

        addCjs("/home/fernando/NetBeansProjects/GXMLProyecto/GenericXML/GXML/Entradas_Script/pruebitas.txt");

        try {
            ejecutarArchivosCjs();
        } catch (Exception e) {
            System.out.println("Error Ejecucion: " + "Ejecutando CJS ->" + e.getMessage());
        }
         */
    }

    public void addCjs(String ruta) {
        try {
            if (!lista_cjs.containsKey(ruta)) {
                lista_cjs.put(ruta, ruta);
            }
        } catch (Exception e) {
        }
    }

    public void ejecutarArchivosCjs() throws FileNotFoundException {
        ArrayList<String> listacjs = obtenerListaArchivosCJS();
        if (listacjs != null) {
            for (String ruta : listacjs) {
                try {
                    Nodo root = new ParserMethods().CompilarScriptJS(leerArchivo(ruta));
                    //principal_Script.ejecucionCJS(root, "", ruta,null);
                } catch (Exception e) {
                }

            }
        }
    }

    public ArrayList<String> obtenerListaArchivosCJS() {
        ArrayList<String> lista = new ArrayList<>();
        Enumeration<String> llaves = lista_cjs.keys();
        while (llaves.hasMoreElements()) {
            String llave = llaves.nextElement();
            String vruta = lista_cjs.get(llave).trim();
            lista.add(vruta);
        }
        return lista;
    }

    public String leerArchivo(String path) {
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String str = new String(data, "UTF-8");
            return str;
        } catch (Exception e) {
            return "";
        }
    }

    public void compilar() throws FileNotFoundException {
        /*
        LexScript lex = new LexScript(new FileReader("EntradaScript.txt"));
        SintacticoScript sin = new SintacticoScript(lex);
        try {
            sin.parse();
            System.out.println("TODO BIEN");
            Nodo raiz = sin.getRoot();
            AST_Script   genTcjs = new AST_Script();
            genTcjs.generacion_arbolScript(raiz);
        } catch (Exception e) {
            System.err.println("error al compilar:"+e.getMessage());
        }
         */

        File file = new File("EntradasGXML/Inicio.gxml");
        LexGxml lex = new LexGxml(new FileReader("EntradasGXML/Inicio.gxml"));
        SintacticoGxml sin = new SintacticoGxml(lex);
        TraduccionGxml_Script tra = new TraduccionGxml_Script();
        try {
            sin.parse();

            tra.IniciarTraduccion(sin.getRoot(), file.getParent());
            /*Nodo raiz = sin.getRoot();
            AST_Script   genTcjs = new AST_Script();
            genTcjs.generacion_arbolScript(raiz);*/
        } catch (Exception e) {
            System.err.println("error al compilar:" + e.getMessage());
        }
        tra.codigoScript = tra.codigoImports+tra.codigoScript;
        JTextArea ar = new JTextArea(tra.codigoScript);
        JScrollPane scroll = new JScrollPane(ar);
        scroll.setBackground(Color.red);
        scroll.setPreferredSize(new Dimension(700, 700));
        //JOptionPane.showMessageDialog(null, scroll);
        System.out.println(tra.codigoScript);

    }

}
