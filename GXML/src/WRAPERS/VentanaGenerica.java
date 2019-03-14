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
import ScriptCompiler.OperacionesARL.OperacionesARL;
import ScriptCompiler.Resultado;
import ScriptCompiler.Simbolo;
import ScriptCompiler.TablaSimbolo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;

/**
 *
 * @author fernando
 */
public class VentanaGenerica extends JFrame {
    /*Solo tiene ID */
    public Clase classe;
    
    protected TablaSimbolo tabla;
    protected TablaSimbolo global;
    public Template miTemplate;

    OperacionesARL opL = null;
    Object alCargar = null;
    Object alCerrar = null;

    Nodo raiz;

    public VentanaGenerica(Nodo raiz) throws HeadlessException {
        this.raiz = raiz;
        this.setLayout(null);

        this.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentHidden(ComponentEvent e) {
                if (alCerrar != null) {
                    if (alCerrar.getClass().getSimpleName().equalsIgnoreCase("nodo")) {
                        Nodo llamada = (Nodo) alCerrar;
                        if (llamada.nombre.equalsIgnoreCase("acceso")) {
                            opL = new OperacionesARL(global, tabla, miTemplate);
                            opL.ejecutar(llamada);
                        } else {
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Al cerrar  de la ventana no es una llamada :" + alCargar.toString());
                        }
                    } else {
                        Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Al cerrar  dela ventana es incorrecta:" + alCargar.toString());
                    }
                }
            }

            @Override
            public void componentShown(ComponentEvent e) {
                if (alCargar != null) {
                    if (alCargar.getClass().getSimpleName().equalsIgnoreCase("nodo")) {
                        Nodo llamada = (Nodo) alCargar;
                        if (llamada.nombre.equalsIgnoreCase("acceso")) {
                            opL = new OperacionesARL(global, tabla, miTemplate);
                            opL.ejecutar(llamada);
                        } else {
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Alcargar  dela ventana no es una llamada :" + alCargar.toString());
                        }
                    } else {
                        Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Alcargar  dela ventana es incorrecta:" + alCargar.toString());
                    }
                }
            }
        });
    }

    
    public void setearClasse(Clase classe)
    {
        this.classe = classe;
    }
    
    public void setearCore(TablaSimbolo global, TablaSimbolo tabla, Template template) {
        this.global = global;
        this.tabla = tabla;
        this.miTemplate = template;
    }

    //Al Cargar
    public void setAlCargar(Object alcargar) {
        try {
            this.alCargar = alcargar;
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear AlCargar [" + alcargar.toString() + "] en Ventana Generica " + this.getName());
        }
    }

    //Al Cerrar
    public void setAlCerrar(Object alcerrar) {
        try {
            this.alCerrar = alcerrar;
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear AlCerrar [" + alcerrar.toString() + "] en Ventana Generica " + this.getName());
        }
    }

    public void setTitulo(String titulo) {
        try {
            setTitle(titulo);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Titulo  [" + titulo + "] en Ventana Generica " + this.getName());
        }
    }

    public void setColor(String hex) {
        if(hex.equals("nulo"))
        {
            return;
        }
        try {
            getContentPane().setBackground(Color.decode(hex));

        } catch (NumberFormatException e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Color [" + hex + "] en Ventana Generica " + this.getName());
        }
    }

    public void setAncho(Object ancho) {
        if(ancho.toString().equals("nulo"))
        {
            return;
        }
        try {
            setPreferredSize(new Dimension(castToInt(ancho), getPreferredSize().height));
            repaint();
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Ancho [" + ancho.toString() + "] en Ventana Generica " + this.getName());
        }
    }

    public void setAlto(Object alto) {
        if(alto.toString().equals("nulo"))
        {
            return;
        }
        try {
            setPreferredSize(new Dimension(getPreferredSize().width, castToInt(alto)));
            repaint();
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
