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
import java.awt.HeadlessException;
import javax.swing.JFrame;

/**
 *
 * @author fernando
 */
public class VentanaGenerica extends JFrame {

    Nodo raiz;

    public VentanaGenerica(Nodo raiz) throws HeadlessException {
        this.raiz = raiz;
    }

    public void setTitulo(String titulo) {
        try {
            setTitle(titulo);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Titulo  [" + titulo + "] en Ventana Generica " + this.getName());
        }
    }

    public void setColor(String hex) {
        try {
            this.setBackground(Color.decode(hex));
        } catch (NumberFormatException e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Color [" + hex + "] en Ventana Generica " + this.getName());
        }
    }

    public void setAncho(Object ancho) {
        try {
            setPreferredSize(new Dimension(castToInt(ancho), getPreferredSize().height));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Ancho [" + ancho.toString() + "] en Ventana Generica " + this.getName());
        }
    }

    public void setAlto(Object alto) {
        try {
            setPreferredSize(new Dimension(getPreferredSize().width, castToInt(alto)));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Alto [" + alto.toString() + "] en Ventana Generica " + this.getName());
        }
    }

    public void setId(String id) {
        try {
            this.setName(id);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Id/Name [" + id + "] en Ventana Generica " + this.getName());
        }
    }

    public Integer castToInt(Object nm) {
        try {
            return Integer.valueOf(nm.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
