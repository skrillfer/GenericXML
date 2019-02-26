/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GenericCompiler;

import Analizadores.Script.LexScript;
import Analizadores.Script.SintacticoScript;
import Ast_Generator.AST_Script;
import Estructuras.Nodo;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author fernando
 */
public class ParserMethods {
    
    public Nodo CompilarScriptJS(String texto) throws FileNotFoundException{
        escribir("EntradaScript.txt",texto);
        
        LexScript lex = new LexScript(new FileReader("EntradaScript.txt"));
        SintacticoScript parser = new SintacticoScript(lex);
        
        
        Nodo retorno=null;
        try {
            parser.parse();
            Nodo raiz = parser.getRoot();
            retorno=raiz;
        } catch (Exception e) {
            System.out.println("Clase ParserMethods:"+e.getMessage());
        }
        return retorno;
    }
    
    public void escribir(String direccion,String texto) {
        //metodo que guarda lo que esta escrito en un archivo de texto
        try {
            FileWriter writer = new FileWriter(direccion);
            PrintWriter print = new PrintWriter(writer);
            print.print(texto);
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
