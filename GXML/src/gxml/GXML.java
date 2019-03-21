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
import IDE.IDE_Ventana;
import WRAPERS.PanelGenerico;
import WRAPERS.Reproductor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author fernando
 */
public class GXML {

    /**
     * @param args the command line arguments
     */
    public void mxxxx() {
        String num1 = "10";
        //System.out.println((num1++)*(num1--)*(num1++));
        /*switch (num1) {
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
        }*/

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

    

    
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IDE_Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IDE_Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IDE_Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IDE_Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        try (BufferedReader br = new BufferedReader(new FileReader("dir_root.txt"))) {
            String sCurrentLine;
            if ((sCurrentLine = br.readLine()) != null) {

                File fi = new File(sCurrentLine);
                if (fi.exists() && fi.isDirectory()) {
                    IDE_Ventana.JTREE_DIR_ROOT = fi.getAbsolutePath();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new IDE_Ventana().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(IDE_Ventana.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

}
