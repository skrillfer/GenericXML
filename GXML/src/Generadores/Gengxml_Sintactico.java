/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Generadores;
import java.io.File;
/**
 *
 * @author fernando
 */

public class Gengxml_Sintactico {
    public static void main(String[] args) {
        String opciones[] = new String[5];
        opciones[0]="-destdir";
        opciones[1]="src"
                +File.separator+
                "Analizadores"
                +File.separator+
                "Gxml";
        opciones[2]="-parser";
        opciones[3]="SintacticoGxml";
        opciones[4]="src"
                +File.separator+
                "Analizadores"
                +File.separator+
                "Gxml"
                +File.separator+
                "sintacticogxml.cup";
        try {
            java_cup.Main.main(opciones);
            }
        catch (Exception e) {
            System.out.print(e.getMessage().toString());
            }
    }
}
