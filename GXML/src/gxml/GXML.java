/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gxml;

import Estructuras.Nodo;
import WRAPERS.CajaTextoGenerica;
import WRAPERS.TextoGenerico;
import java.text.ParseException;
import javax.swing.JFrame;

/**
 *
 * @author fernando
 */
public class GXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
        JFrame v = new JFrame();
        v.setSize(300, 303);
        
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        TextoGenerico txt = new TextoGenerico(new Nodo());
        txt.setTexto("Este es mi mensaje ");
        
        txt.setCurvisa(true);
        txt.setTam(13);
        txt.setColor("#8b4513");
        
        v.add(txt);
        v.setVisible(true);
    }

}
