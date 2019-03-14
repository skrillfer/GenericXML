/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WRAPERS;

import Estructuras.Nodo;
import INTERFAZ.Template;
import ScriptCompiler.Arreglo;
import ScriptCompiler.Clase;
import ScriptCompiler.Resultado;
import java.awt.Dimension;
import javax.swing.JComboBox;

/**
 *
 * @author fernando
 */
public class DesplegableGenerico extends JComboBox {

    public Clase classe;

    Object defecto = null;
    Arreglo lista_datos;
    /*
    Alto, Ancho, lista, X, Y, Defecto, nombre    
     */
    Nodo raiz;

    public DesplegableGenerico(Nodo raiz) {
        this.raiz = raiz;
    }
    
    public void setearClasse(Clase classe)
    {
        this.classe = classe;
    }

    public void setAncho(Object ancho) {
        if(ancho.toString().equals("nulo"))
            return;
        try {
            setPreferredSize(new Dimension(castToInt(ancho), getPreferredSize().height));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Ancho [" + ancho.toString() + "] en Desplegable " + this.getName());
        }
        updateUI();
    }

    public void setAlto(Object alto) {
        if(alto.toString().equals("nulo"))
            return;
        try {
            setPreferredSize(new Dimension(getPreferredSize().width, castToInt(alto)));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Alto [" + alto.toString() + "] en Desplegable " + this.getName());
        }
        updateUI();
    }

    public void setLista(Object arr) {
        try {
            Arreglo miarr = (Arreglo) arr;
            this.lista_datos = miarr;
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Lista de Datos, no es una lista,  en Desplegable " + this.getName());
        }
    }

    public void setDatos() {
        for (Object dato : lista_datos.getDatos()) {
            try {
                Resultado res = (Resultado) dato;

                switch (res.tipo) {
                    case "String":
                    case "Double":
                    case "Integer":
                    case "Boolean":
                        addItem(res.valor.toString());
                        break;
                    default:
                        Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Un dato de desplegable no puede ser de tipo: " + res.tipo + " en desplegable " + this.getName());
                }

            } catch (Exception e) {
            }
        }
        try {
            cargarDefecto();
        } catch (Exception e) {
        }
    }

    public void cargarDefecto() {
        try {
            if (lista_datos != null) {
                boolean existe = lista_datos.existeOpcion(defecto.toString());
                if (existe) {
                    this.setSelectedItem(defecto.toString());
                } else {
                    Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "El valor por defecto [" + defecto.toString() + "] no existe en la lista de Desplegable " + this.getName());
                }
            }
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Texto [" + defecto.toString() + "] en Desplegable " + this.getName());
        }
        updateUI();
    }

    public void setX(Object x) {
        if(x.toString().equals("nulo"))
            return;
        try {
            this.setLocation(castToInt(x), this.getLocation().y);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en X [" + x.toString() + "] en Desplegable " + this.getName());
        }
    }

    public void setY(Object y) {
        if(y.toString().equals("nulo"))
            return;
        try {
            this.setLocation(this.getLocation().x, castToInt(y));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en Y [" + y.toString() + "] en Desplegable " + this.getName());
        }
    }

    //Defecto
    public void setDefecto(Object txt) {
        try {
            this.defecto = txt;
            if (lista_datos != null) {
                if (lista_datos.getDatos().size() > 0) {
                    cargarDefecto();
                }
            }
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Texto [" + txt + "] en Desplegable " + this.getName());
        }
    }

    //Nombre o id
    public void setId(String id) {
        try {
            this.setName(id);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Id/Name [" + id + "] en Desplegable " + this.getName());
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
