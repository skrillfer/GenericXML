/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GenericCompiler;

import Analizadores.Gxml.LexGxml;
import Analizadores.Gxml.SintacticoGxml;
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
        LexGxml lex = new LexGxml(new FileReader("EntradaGxml.txt"));
        SintacticoGxml sin = new SintacticoGxml(lex);
        try {
            sin.parse();
        } catch (Exception e) {
            System.err.println("error al compilar:"+e.getMessage());
        }
    }
}
