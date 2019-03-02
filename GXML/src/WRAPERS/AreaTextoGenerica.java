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
import javax.swing.JTextArea;

/**
 *
 * @author fernando
 */
public class AreaTextoGenerica extends JTextArea {

    Nodo raiz;

    public AreaTextoGenerica(Nodo raiz) {
        this.raiz = raiz;
    }

    /*
    Alto, ancho, Fuente, Tamaño, Color, X, Y, Negrilla, Cursiva, defecto, nombre
     */
    public void setAncho(int ancho) {
        try {
            setPreferredSize(new Dimension(ancho, getPreferredSize().height));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Ancho [" + ancho + "] en AreaTexto " + this.getName());
        }
        updateUI();
    }

    public void setAlto(int alto) {
        try {
            setPreferredSize(new Dimension(getPreferredSize().width, alto));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Alto [" + alto + "] en AreaTexto " + this.getName());
        }
        updateUI();
    }

    public void setFuente(String family) {
        try {
            Font ft = new Font(family, this.getFont().getStyle(), this.getFont().getSize());

            Map atributes = ft.getAttributes();
            this.setFont(ft.deriveFont(atributes));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Fuente [" + family + "] en AreaTexto " + this.getName());
        }
    }

    public void setTam(int tam) {
        try {
            Font ft = new Font(this.getFont().getName(), this.getFont().getStyle(), tam);

            Map atributes = ft.getAttributes();
            this.setFont(ft.deriveFont(atributes));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Tamano [" + tam + "] en AreaTexto " + this.getName());
        }
    }

    public void setColor(String hex) {
        try {
            this.setForeground(Color.decode(hex));
        } catch (NumberFormatException e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Color [" + hex + "] en AreaTexto " + this.getName());
        }
    }

    public void setX(int x) {
        try {
            this.setLocation(x, this.getLocation().y);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en X [" + x + "] en AreaTexto " + this.getName());
        }
    }

    public void setY(int y) {
        try {
            this.setLocation(this.getLocation().x, y);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en Y [" + y + "] en AreaTexto " + this.getName());
        }
    }

    public void setNegrilla(boolean check) {
        try {

            Font ft = null;
            if (check) {
                if (this.getFont().isItalic()) {
                    ft = new Font(this.getFont().getName(), Font.ITALIC + Font.BOLD, this.getFont().getSize());
                } else {
                    ft = new Font(this.getFont().getName(), Font.BOLD, this.getFont().getSize());
                }
            } else {
                if (this.getFont().isItalic()) {
                    ft = new Font(this.getFont().getName(), Font.ITALIC, this.getFont().getSize());
                } else {
                    ft = new Font(this.getFont().getName(), this.getFont().getStyle(), this.getFont().getSize());
                }
            }

            Map atributes = ft.getAttributes();
            this.setFont(ft.deriveFont(atributes));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Negrilla  en AreaTexto " + this.getName());
        }
    }

    public void setCurvisa(boolean check) {
        try {

            Font ft = null;
            if (check) {
                if (this.getFont().isBold()) {
                    ft = new Font(this.getFont().getName(), Font.ITALIC + Font.BOLD, this.getFont().getSize());
                } else {
                    ft = new Font(this.getFont().getName(), Font.ITALIC, this.getFont().getSize());
                }
            } else {
                if (this.getFont().isBold()) {
                    ft = new Font(this.getFont().getName(), Font.BOLD, this.getFont().getSize());
                } else {
                    ft = new Font(this.getFont().getName(), this.getFont().getStyle(), this.getFont().getSize());
                }
            }

            Map atributes = ft.getAttributes();
            this.setFont(ft.deriveFont(atributes));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Cursiva  en AreaTexto " + this.getName());
        }
    }

    //Defecto
    public void setTexto(String txt) {
        try {

            this.setText(txt);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Texto [" + txt + "] en AreaTexto " + this.getName());
        }
        updateUI();
    }

    //Nombre
    public void setId(String id) {
        try {
            this.setName(id);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Id/Name [" + id + "] en AreaTexto " + this.getName());
        }
    }
}
