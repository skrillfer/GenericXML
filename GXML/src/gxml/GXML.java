/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gxml;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author fernando
 */
public class GXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Object v1 = Double.valueOf("-14.5");
        Object v2 = Double.valueOf("14.5");
        
        if(!Objects.equals((Double)v1, (Double)v2))
        {
            System.out.println("es diferente");
        }else
        {
            System.out.println("IGUALES");
        }
        /*ArrayList <String> lista = new ArrayList<>();
        
        String nombre = "";
        
        Scanner scan = new Scanner(System.in);
        while (!"".equals(nombre = scan.nextLine())) {
            lista.add(nombre);
        }
        String salida1="";
        String salida2="";
        for (String string : lista) {
            salida2 += string+",";
            salida1 += "\n<YYINITIAL> \"" + string + "\"         {return new Symbol(sym." + string + ", new token(yycolumn, yyline, yytext()));}";
        }
        System.out.println(salida1);
        System.out.println(salida2);*/
        
    }

}
