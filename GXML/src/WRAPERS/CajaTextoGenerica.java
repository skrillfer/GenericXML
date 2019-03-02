/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WRAPERS;

import Estructuras.Nodo;
import INTERFAZ.Template;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextField;

/**
 *
 * @author fernando
 */
public class CajaTextoGenerica extends JTextField {
    String lastText="";
    Nodo raiz;

    public CajaTextoGenerica(Nodo raiz) {
        this.raiz = raiz;
        this.setName("");

        setControlNumerico();
    }
    
    public void setControlNumerico()
    {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(!MATCH())
                {
                    setText(lastText);
                }else
                {
                    lastText = getText();
                }
                System.out.println("presionado");
                /*char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) && (c != '.')) {

                    e.consume(); // consume non-numbers
                }*/
            }
        });
    }

    public boolean MATCH() {
        // String to be scanned to find the pattern.

        String pattern = "^[-]?(\\d+(\\.\\d*)?)?";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(this.getText());
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    //Valor por Defecto
    public void setTexto(String txt) {
        try {
            this.setText(txt);
        } catch (Exception ex) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Texto por Defecto [" + txt + "] en CajaTexto " + this.getName());

        }
        updateUI();
    }

    public void setId(String id) {
        try {
            this.setName(id);
        } catch (Exception ex) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Id/Name [" + id + "] en CajaTexto " + this.getName());
        }
    }

    public void setAncho(int ancho) {
        try {
            setPreferredSize(new Dimension(ancho, getPreferredSize().height));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Ancho [" + ancho + "] en CajaTexto " + this.getName());

        }
        updateUI();
    }

    public void setAlto(int alto) {
        try {
            setPreferredSize(new Dimension(getPreferredSize().width, alto));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Alto [" + alto + "] en CajaTexto " + this.getName());

        }
        updateUI();
    }

}
