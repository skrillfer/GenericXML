/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WRAPERS;

import Estructuras.Nodo;
import INTERFAZ.Template;
import ScriptCompiler.Arreglo;
import java.awt.Dimension;
import javax.swing.JComboBox;

/**
 *
 * @author fernando
 */
public class DesplegableGenerico extends JComboBox {

    Arreglo lista_datos;
    /*
    Alto, Ancho, lista, X, Y, Defecto, nombre    
     */
    Nodo raiz;

    public DesplegableGenerico(Nodo raiz) {
        this.raiz = raiz;
    }

    public void setAncho(int ancho) {
        try {
            setPreferredSize(new Dimension(ancho, getPreferredSize().height));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Ancho [" + ancho + "] en Desplegable " + this.getName());
        }
        updateUI();
    }

    public void setAlto(int alto) {
        try {
            setPreferredSize(new Dimension(getPreferredSize().width, alto));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Alto [" + alto + "] en Desplegable " + this.getName());
        }
        updateUI();
    }

    public void setLista(Arreglo arr) {
        try {
            this.lista_datos = arr;
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Lista de Datos  en Desplegable " + this.getName());
        }
    }
    
    public void setX(int x) {
        try {
            this.setLocation(x, this.getLocation().y);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en X [" + x + "] en Desplegable " + this.getName());
        }
    }

    public void setY(int y) {
        try {
            this.setLocation(this.getLocation().x, y);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en Y [" + y + "] en Desplegable " + this.getName());
        }
    }
    
    //Defecto !!IMPLEMENTAR ESTO
    public void setTexto(String txt) {
        try {

            //this.setText(txt);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Texto [" + txt + "] en AreaTexto " + this.getName());
        }
        updateUI();
    }

    //Nombre o id
    public void setId(String id) {
        try {
            this.setName(id);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Id/Name [" + id + "] en AreaTexto " + this.getName());
        }
    }
}