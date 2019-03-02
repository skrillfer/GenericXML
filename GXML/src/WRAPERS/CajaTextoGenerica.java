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
    boolean esnumerico;
    String lastText="";
    Double maximo=12.0;
    Double minimo=0.0;
    Nodo raiz;

    public CajaTextoGenerica(Nodo raiz) {
        this.raiz = raiz;
        this.setName("");
        esnumerico = false;
        //setControlNumerico();
    }
    
    
    
    public void setControlNumerico()
    {
        esnumerico = true;
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(!getText().equals("") && !getText().equals("-"))
                {
                    if(!MATCH())
                    {
                        setText(lastText);
                    }else
                    {
                        lastText = getText();
                    }
                }
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
            if(this.maximo!=this.minimo)
            {
                if(Double.valueOf(this.getText())<=this.maximo && this.maximo>this.minimo)
                {
                    if(Double.valueOf(this.getText())>=this.minimo && this.minimo<this.maximo)
                    {
                        return true;
                    }else
                    {
                        return false;
                    }
                }else
                {
                    return false;
                }
            }else
            {
                return true;
            }
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

        public void setX(int x) {
            try {
                this.setLocation(x, this.getLocation().y);
            } catch (Exception e) {
                Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en X [" + x + "] en CajaTexto " + this.getName());
            }
        }

        public void setY(int y) {
            try {
                this.setLocation(this.getLocation().x, y);
            } catch (Exception e) {
                Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en Y [" + y + "] en CajaTexto " + this.getName());
            }
        }

        public void setId(String id) {
            try {
                this.setName(id);
            } catch (Exception e) {
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
    
    
    
    public void setMaximo(Double minimo) {
        try {
            this.minimo= minimo;
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Minimo [" + minimo + "] en CajaNumerica " + this.getName());

        }
        updateUI();
    }

    public void setMinimo(Double maximo) {
        try {
            this.maximo = maximo;
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Maximo [" + maximo + "] en CajaNumerica " + this.getName());

        }
        updateUI();
    }

}
