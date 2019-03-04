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
import javax.swing.JTextField;

/**
 *
 * @author fernando
 */
public class CajaTextoGenerica extends JTextField {

    /*
    Alto, Ancho, Fuente, Tamaño, Color, X, Y, Negrilla, Cursiva, defecto, nombre 
     */
    Nodo raiz;

    public CajaTextoGenerica(Nodo raiz) {
        this.raiz = raiz;
        //Estilo por defecto
        setNegrilla(false);
    }

    public void setAncho(Object ancho) {
        try {
            setPreferredSize(new Dimension(castToInt(ancho), getPreferredSize().height));
            updateUI();

        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Ancho [" + ancho.toString() + "] en CajaTexto " + this.getName());
        }
        updateUI();
    }

    public void setAlto(Object alto) {
        try {
            setPreferredSize(new Dimension(getPreferredSize().width, castToInt(alto)));
            updateUI();

        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Alto [" + alto.toString() + "] en CajaTexto " + this.getName());
        }
        updateUI();
    }

    public void setFuente(String family) {
        try {
            Font ft = new Font(family, this.getFont().getStyle(), this.getFont().getSize());

            Map atributes = ft.getAttributes();
            this.setFont(ft.deriveFont(atributes));
            updateUI();

        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Fuente [" + family + "] en CajaTexto " + this.getName());
        }
    }

    public void setTam(Object tam) {
        try {
            Font ft = new Font(this.getFont().getName(), this.getFont().getStyle(), castToInt(tam));

            Map atributes = ft.getAttributes();
            this.setFont(ft.deriveFont(atributes));
            updateUI();

        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Tamano [" + tam.toString() + "] en CajaTexto " + this.getName());
        }
    }

    public void setColor(String hex) {
        try {
            this.setForeground(Color.decode(hex));
        } catch (NumberFormatException e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Color [" + hex + "] en CajaTexto " + this.getName());
        }
    }

    public void setX(Object x) {
        try {
            this.setLocation(castToInt(x), this.getLocation().y);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en X [" + x.toString() + "] en CajaTexto " + this.getName());
        }
    }

    public void setY(Object y) {
        try {
            this.setLocation(this.getLocation().x, castToInt(y));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en Y [" + y.toString() + "] en CajaTexto " + this.getName());
        }
    }

    public void setNegrilla(Object check) {
        try {

            Font ft = null;
            if (castToBoolean(check)) {
                if (this.getFont().isItalic()) {
                    ft = new Font(this.getFont().getName(), Font.ITALIC + Font.BOLD, this.getFont().getSize());
                } else {
                    ft = new Font(this.getFont().getName(), Font.BOLD, this.getFont().getSize());
                }
            } else {
                if (this.getFont().isItalic()) {
                    ft = new Font(this.getFont().getName(), Font.ITALIC, this.getFont().getSize());
                } else {
                    ft = new Font(this.getFont().getName(), Font.PLAIN, this.getFont().getSize());
                }
            }

            Map atributes = ft.getAttributes();
            this.setFont(ft.deriveFont(atributes));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Negrilla  en CajaTexto " + this.getName());
        }
    }

    public void setCurvisa(Object check) {
        try {

            Font ft = null;
            if (castToBoolean(check)) {
                if (this.getFont().isBold()) {
                    ft = new Font(this.getFont().getName(), Font.ITALIC + Font.BOLD, this.getFont().getSize());
                } else {
                    ft = new Font(this.getFont().getName(), Font.ITALIC, this.getFont().getSize());
                }
            } else {
                if (this.getFont().isBold()) {
                    ft = new Font(this.getFont().getName(), Font.BOLD, this.getFont().getSize());
                } else {
                    ft = new Font(this.getFont().getName(), Font.PLAIN, this.getFont().getSize());
                }
            }

            Map atributes = ft.getAttributes();
            this.setFont(ft.deriveFont(atributes));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Cursiva  en CajaTexto " + this.getName());
        }
    }

    //Defecto
    public void setTexto(String txt) {
        try {

            this.setText(txt);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Texto [" + txt + "] en CajaTexto " + this.getName());
        }
        repaint();
    }

    //Nombre o id
    public void setId(String id) {
        try {
            this.setName(id);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Id/Name [" + id + "] en CajaTexto " + this.getName());
        }
    }

    public Integer castToInt(Object nm) {
        try {
            return Integer.valueOf(nm.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Boolean castToBoolean(Object nm) {
        try {
            return Boolean.valueOf(nm.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
