/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gxml;

import Analizadores.Gdato.LexGdato;
import Analizadores.Gdato.SintacticoGdato;
import Analizadores.Gdato.StringMatcher;
import Analizadores.Gxml.LexGxml;
import Analizadores.Gxml.SintacticoGxml;
import Ast_Generator.AST_Script;
import Estructuras.Nodo;
import WRAPERS.PanelGenerico;
import WRAPERS.Reproductor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import javax.swing.JFrame;

/**
 *
 * @author fernando
 */
public class GXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        String num1 = "10";
        //System.out.println((num1++)*(num1--)*(num1++));
        switch (num1) {
            case "1":
                System.out.println("1");
                break;
            case "2":
                System.out.println("1");
                break;
            case "10":
                System.out.println("10");

            case "11":
                System.out.println("11");
                break;
            default:
                System.out.println("defecto");

                break;
        }

        //JFrame frame = new JFrame("Capture");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*PanelGenerico pane=null;*/
        //pane.setBorder();
        /*frame.setContentPane(pane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(50, 50);
        frame.setSize(800, 600);

        frame.setVisible(true);*/
 /*Reproductor reproductor = new Reproductor(new Nodo());
        reproductor.setRuta("/home/fernando/Imágenes/capppp.png");
        reproductor.setAutoReproduccion(true);
        JFrame frame = new JFrame("Capture");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(reproductor);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(50, 50);
        frame.setSize(800, 600);

        frame.setVisible(true);*/
        // Reproduce el vídeo.
        //reproductor.iniciarReproduccion();
        /*
        LexGxml lex = new LexGxml(new FileReader("EntradaGxml.txt"));
        SintacticoGxml parser = new SintacticoGxml(lex);

        Nodo retorno = null;
        try {
            parser.parse();
            Nodo raiz = parser.getRoot();
            retorno = raiz;
            //Ast_Generator.AST_Script ppp = new AST_Script();
            //ppp.generacion_arbolScript(raiz);
        } catch (Exception e) {
            System.out.println("Error al parsear Gdato:" + e.getMessage());
        }*/
        //StringMatcher sss = new StringMatcher();
        //System.out.println(sss.esTipo(".3."));
    }

    public int ackermann(int m, int n) {
        //System.out.println("entro");
        if (m == 0) {
            return (n + 1);
        } else if (m > 0 && n == 0) {
            return ackermann(m - 1, 1);
        } else {
            return ackermann(m - 1, ackermann(m, n - 1));
        }
    }

}
