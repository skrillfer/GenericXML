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
        try {
            new Ejecutor().compilar();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ejecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        } catch (Exception e) {
            System.err.println("error al compilar:"+e.getMessage());
        }
    }
}
