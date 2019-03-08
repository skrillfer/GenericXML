/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WRAPERS;

import Estructuras.Nodo;
import INTERFAZ.Template;
import ScriptCompiler.Clase;
import ScriptCompiler.OperacionesARL.OperacionesARL;
import ScriptCompiler.TablaSimbolo;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JButton;

/**
 *
 * @author fernando
 */
public class BotonGenerico extends JButton {
    protected Clase classe;
    
    protected TablaSimbolo tabla;
    protected TablaSimbolo global;
    public Template miTemplate;
    
    OperacionesARL opL = null;
    
    /*
    Fuente, Tamaño, Color, X, Y,Referencia, valor, Alto, Ancho    
     */
    public Object referencia =  null;
    public Object click      = null;
    Nodo raiz;

    public BotonGenerico(Nodo raiz) {
        this.raiz = raiz;
        
        
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(referencia!=null)
                {
                    if(referencia.getClass().getSimpleName().equalsIgnoreCase("nodo"))
                    {
                        Nodo llamada  = (Nodo)referencia;
                        if(llamada.nombre.equalsIgnoreCase("acceso"))
                        {
                            opL = new OperacionesARL(global, tabla, miTemplate);
                            opL.ejecutar(llamada);
                        }else
                        {
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La refencia del boton no es una llamada :"+referencia.toString());
                        }
                    }else
                    {
                        Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La refencia del boton es incorrecta:"+referencia.toString());
                    }
                }
                if(click!=null)
                {
                    if(click.getClass().getSimpleName().equalsIgnoreCase("nodo"))
                    {
                        Nodo llamada  = (Nodo)click;
                        if(llamada.nombre.equalsIgnoreCase("acceso"))
                        {
                            opL = new OperacionesARL(global, tabla, miTemplate);
                            opL.ejecutar(llamada);
                        }else
                        {
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "El click del boton no es una llamada :"+click.toString());
                        }
                    }else
                    {
                        Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "El click del boton es incorrecta:"+click.toString());
                    }
                }
            }
        });
    }
    
    public void setearClasse(Clase classe)
    {
        this.classe = classe;
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

    public void setTam(Object tam) {
        try {
            Font ft = new Font(this.getFont().getName(), this.getFont().getStyle(), castToInt(tam));

            Map atributes = ft.getAttributes();
            this.setFont(ft.deriveFont(atributes));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Tamano [" + tam.toString() + "] en Boton " + this.getName());
        }
    }

    public void setColor(String hex) {
        try {
            this.setForeground(Color.decode(hex));
        } catch (NumberFormatException e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Color [" + hex + "] en Boton " + this.getName());
        }
    }

    public void setX(Object x) {
        try {
            this.setLocation(castToInt(x), this.getLocation().y);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en X [" + x.toString() + "] en Boton " + this.getName());
        }
    }

    public void setY(Object y) {
        try {
            this.setLocation(this.getLocation().x, castToInt(y));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en Y [" + y.toString() + "] en Boton " + this.getName());
        }
    }

    
    public void setearCore(TablaSimbolo global, TablaSimbolo tabla, Template template)
    {
        this.global = global;
        this.tabla = tabla;
        this.miTemplate = template;
    }
    
    public void setReferencia(Object referencia) {
        try {
            this.referencia = referencia;
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Referecia [" + referencia.toString() + "] en Boton " + this.getName());
        }
    }
    
    //Al clik
    public void setAlClick(Object clik) {
        try {
            this.click = clik;
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear AlClic [" + clik.toString() + "] en Boton " + this.getName());
        }
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

    public void setAncho(Object ancho) {
        try {
            setPreferredSize(new Dimension(castToInt(ancho), getPreferredSize().height));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Ancho [" + ancho.toString() + "] en Boton " + this.getName());
        }
        updateUI();
    }

    public void setAlto(Object alto) {
        try {
            setPreferredSize(new Dimension(getPreferredSize().width, castToInt(alto)));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Alto [" + alto.toString() + "] en Boton " + this.getName());
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
