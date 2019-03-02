/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WRAPERS;

import Estructuras.Nodo;
import INTERFAZ.Template;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Map;
import javax.swing.JButton;

/**
 *
 * @author fernando
 */
public class BotonGenerico extends JButton {

    /*
    Fuente, Tamaño, Color, X, Y,Referencia, valor, Alto, Ancho    
     */
    public String referencia;
    Nodo raiz;

    public BotonGenerico(Nodo raiz) {
        this.raiz = raiz;
    }

    public boolean aplicaStilo(String nombre) {
        switch (nombre.toLowerCase()) {
            case "fuente":
            case "tam":
            case "color":
            case "x":
            case "y":
            case "referencia":
            case "texto"://valor
            case "alto":
            case "ancho":
                return true;
            default:
                return false;
        }
    }

    public void setFuente(String family) {
        try {
            Font ft = new Font(family, this.getFont().getStyle(), this.getFont().getSize());
            Map atributes = ft.getAttributes();
            this.setFont(ft.deriveFont(atributes));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Fuente [" + family + "] en Boton " + this.getName());
        }
    }

    public void setTam(int tam) {
        try {
            Font ft = new Font(this.getFont().getName(), this.getFont().getStyle(), tam);

            Map atributes = ft.getAttributes();
            this.setFont(ft.deriveFont(atributes));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Tamano [" + tam + "] en Boton " + this.getName());
        }
    }

    public void setColor(String hex) {
        try {
            this.setForeground(Color.decode(hex));
        } catch (NumberFormatException e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Color [" + hex + "] en Boton " + this.getName());
        }
    }

    public void setX(int x) {
        try {
            this.setLocation(x, this.getLocation().y);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en X [" + x + "] en Boton " + this.getName());
        }
    }

    public void setY(int y) {
        try {
            this.setLocation(this.getLocation().x, y);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en Y [" + y + "] en Boton " + this.getName());
        }
    }

    public void setReferencia(String referencia) {
        try {
            this.referencia = referencia;
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Ruta [" + referencia + "] en Boton " + this.getName());
        }
        updateUI();
    }

    //Valor
    public void setTexto(String txt) {
        try {

            this.setText(txt);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Texto [" + txt + "] en Boton " + this.getName());
        }
        updateUI();
    }

    public void setAncho(int ancho) {
        try {
            setPreferredSize(new Dimension(ancho, getPreferredSize().height));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Ancho [" + ancho + "] en Boton " + this.getName());
        }
        updateUI();
    }

    public void setAlto(int alto) {
        try {
            setPreferredSize(new Dimension(getPreferredSize().width, alto));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Alto [" + alto + "] en Boton " + this.getName());
        }
        updateUI();
    }

    public void setId(String id) {
        try {
            this.setName(id);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Id/Name [" + id + "] en Boton " + this.getName());
        }
    }

}
