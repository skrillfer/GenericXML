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
import ScriptCompiler.Script;
import ScriptCompiler.Simbolo;
import ScriptCompiler.TablaSimbolo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JButton;

/**
 *
 * @author fernando
 */
public class BotonGenerico extends JButton {

    public Clase classe;

    protected TablaSimbolo tabla;
    protected TablaSimbolo global;
    public Template miTemplate;

    OperacionesARL opL = null;

    /*
    Fuente, Tamaño, Color, X, Y,Referencia, valor, Alto, Ancho    
     */
    public Object referencia = null;
    public ArrayList<Object> LTclick = new ArrayList<>();

    Nodo raiz;

    public BotonGenerico(Nodo raiz) {
        this.raiz = raiz;

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Object click : LTclick) {
                    if (click != null) {
                        if (click.getClass().getSimpleName().equalsIgnoreCase("nodo")) {
                            Nodo llamada = (Nodo) click;
                            if (llamada.nombre.equalsIgnoreCase("acceso")) {
                                opL = new OperacionesARL(global, tabla, miTemplate);
                                opL.ejecutar(llamada);
                            } else {
                                Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "El click del boton no es una llamada :" + click.toString());
                            }
                        } else {
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "El click del boton es incorrecta:" + click.toString());
                        }
                    }
                }

                if (referencia != null) {
                    if (referencia.getClass().getSimpleName().equalsIgnoreCase("nodo")) {
                        Nodo llamada = (Nodo) referencia;
                        if (llamada.nombre.equalsIgnoreCase("acceso")) {
                            opL = new OperacionesARL(global, tabla, miTemplate);
                            opL.ejecutar(llamada);
                        } else {
                            if (llamada.nombre.equalsIgnoreCase("string_literal")) {
                                String nombre_ventana = llamada.valor;
                                VentanaGenerica cargar = null;
                                for (VentanaGenerica vtn : ScriptCompiler.Script.listaVentanas) {
                                    if (nombre_ventana.equalsIgnoreCase(vtn.getName())) {
                                        cargar = vtn;
                                        break;
                                    }
                                }
                                if (cargar != null) {
                                    if (cargar.getPreferredSize().width > 50 && cargar.getPreferredSize().height > 50) {
                                        cargar.setBounds(100, 10, cargar.getPreferredSize().width, cargar.getPreferredSize().height);
                                    } else {
                                        int maxWidth = 0;
                                        int maxHeigth = 0;
                                        int x = 0;
                                        int y = 0;
                                        for (Component component : cargar.getContentPane().getComponents()) {
                                            if (component.getLocation().x > x) {
                                                x = component.getLocation().x;
                                                maxWidth = component.getPreferredSize().width + x;
                                            }

                                            if (component.getLocation().y > y) {
                                                y = component.getLocation().y;
                                                maxHeigth = component.getPreferredSize().height + y;
                                            }
                                        }
                                        cargar.setBounds(100, 10, maxWidth, maxHeigth);
                                    }

                                    /*for (Component component : cargar.getContentPane().getComponents()) {
                                        System.out.println(component.getClass().getSimpleName());
                                    }*/
                                    if (Script.VT_ACTUAL != null) {
                                        Script.VT_ACTUAL.dispose();
                                    }
                                    Script.VT_ACTUAL = cargar;
                                    cargar.setLocationRelativeTo(null);
                                    cargar.setVisible(true);

                                } else {
                                    Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Accion Referencia: La ventana con id " + nombre_ventana + " no existe ");
                                }
                            } else {
                                if (!llamada.nombre.equalsIgnoreCase("nulo")) {
                                    Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La refencia del boton no es una llamada :" + referencia.toString());
                                }

                            }
                        }
                    } else {
                        Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La refencia del boton es incorrecta:" + referencia.toString());
                    }
                }
            }
        });
    }

    public void setearClasse(Clase classe) {
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
        if (family.equals("nulo")) {
            return;
        }
        try {
            Font ft = new Font(family, this.getFont().getStyle(), this.getFont().getSize());
            Map atributes = ft.getAttributes();
            this.setFont(ft.deriveFont(atributes));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Fuente [" + family + "] en Boton " + this.getName());
        }
    }

    public void setTam(Object tam) {
        if (tam.toString().equals("nulo")) {
            return;
        }
        try {
            Font ft = new Font(this.getFont().getName(), this.getFont().getStyle(), castToInt(tam));

            Map atributes = ft.getAttributes();
            this.setFont(ft.deriveFont(atributes));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Tamano [" + tam.toString() + "] en Boton " + this.getName());
        }
    }

    public void setColor(String hex) {
        if (hex.equals("nulo")) {
            return;
        }
        try {
            this.setForeground(Color.decode(hex));
        } catch (NumberFormatException e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Color [" + hex + "] en Boton " + this.getName());
        }
    }

    public void setX(Object x) {
        if (x.toString().equals("nulo")) {
            return;
        }
        try {
            this.setLocation(castToInt(x), this.getLocation().y);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en X [" + x.toString() + "] en Boton " + this.getName());
        }
    }

    public void setY(Object y) {
        if (y.toString().equals("nulo")) {
            return;
        }
        try {
            this.setLocation(this.getLocation().x, castToInt(y));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en Y [" + y.toString() + "] en Boton " + this.getName());
        }
    }

    public void setearCore(TablaSimbolo global, TablaSimbolo tabla, Template template) {
        this.global = global;
        this.tabla = tabla;
        this.miTemplate = template;
    }

    public void setReferencia(Object referencia) {
        if (referencia.toString().equals("nulo")) {
            return;
        }
        try {
            this.referencia = referencia;
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Referecia [" + referencia.toString() + "] en Boton " + this.getName());
        }
    }

    //Al clik
    public void setAlClick(Object clik) {
        if (clik.toString().equals("nulo")) {
            return;
        }
        try {
            LTclick.add(clik);
            //this.click = clik;
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear AlClic [" + clik.toString() + "] en Boton " + this.getName());
        }
    }

    //Valor
    public void setTexto(String txt) {
        if (txt.equals("nulo")) {
            return;
        }
        try {

            this.setText(txt);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Texto [" + txt + "] en Boton " + this.getName());
        }
        updateUI();
    }

    public void setAncho(Object ancho) {
        if (ancho.toString().equals("nulo")) {
            return;
        }
        try {
            setPreferredSize(new Dimension(castToInt(ancho), getPreferredSize().height));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Ancho [" + ancho.toString() + "] en Boton " + this.getName());
        }
        updateUI();
    }

    public void setAlto(Object alto) {
        if (alto.toString().equals("nulo")) {
            return;
        }
        try {
            setPreferredSize(new Dimension(getPreferredSize().width, castToInt(alto)));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Alto [" + alto.toString() + "] en Boton " + this.getName());
        }
        updateUI();
    }

    public void setId(String id) {
        if (id.toString().equals("nulo")) {
            return;
        }
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
