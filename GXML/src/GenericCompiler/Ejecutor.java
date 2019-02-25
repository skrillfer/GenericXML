/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GenericCompiler;

import Analizadores.Gxml.LexGxml;
import Analizadores.Gxml.SintacticoGxml;
import Analizadores.Script.LexScript;
import Analizadores.Script.SintacticoScript;
import Ast_Generator.AST_Script;
import Estructuras.Nodo;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fernando
 */
public class Ejecutor {
    public static void main(String[] args) {
        String str="";
        Double db1=1.4;
        Integer    gr1=5;
        
        Boolean flag=true;
        
        String cadena = "abc";
        int suma = 0;
        for (int i = 0; i < cadena.length(); i++) {
            suma+= cadena.codePointAt(i);
        }
        System.out.println(suma);
        
        Object v1 = Integer.valueOf("1");
        Object v2 = Double.valueOf("2.0");
        
        if( ((Integer)v1).doubleValue() < (Double)v2)
        {
            System.out.println("XI");
        }
        
        /*try {
            new Ejecutor().compilar();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ejecutor.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
    public void compilar() throws FileNotFoundException
    {
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
        
               
        LexGxml lex = new LexGxml(new FileReader("EntradaGxml.txt"));
        SintacticoGxml sin = new SintacticoGxml(lex);
        try {
            sin.parse();
            TraduccionGxml_Script tra = new TraduccionGxml_Script();
            tra.IniciarTraduccion(sin.getRoot());
            /*Nodo raiz = sin.getRoot();
            AST_Script   genTcjs = new AST_Script();
            genTcjs.generacion_arbolScript(raiz);*/
        } catch (Exception e) {
            System.err.println("error al compilar:"+e.getMessage());
        }
    }
}
