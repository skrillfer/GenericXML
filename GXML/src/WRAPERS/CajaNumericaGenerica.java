/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WRAPERS;

import Estructuras.Nodo;
import INTERFAZ.Template;
import ScriptCompiler.Clase;
import ScriptCompiler.Simbolo;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 *
 * @author fernando
 */
public class CajaNumericaGenerica extends JPanel {
    public Clase classe;

    JTextField caja = new JTextField();
    /*
    Alto, Ancho, Maximo, Minimo, X, Y, defecto, nombre 
     */
    String lastText = "";
    Double maximo = null;
    Double minimo = null;
    Nodo raiz;

    JButton subir = new JButton("");
    JButton bajar = new JButton("");

    public CajaNumericaGenerica(Nodo raiz) {
        this.raiz = raiz;
        //Estilo por defecto
        setControlNumerico();

        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        subir.setPreferredSize(new Dimension(10, 10));
        bajar.setPreferredSize(new Dimension(10, 10));

        subir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                INCREMENTAR();
            }
        });

        bajar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DECREMENTAR();
            }
        });
        JPanel btns = new JPanel();
        btns.setLayout(new BoxLayout(btns, BoxLayout.Y_AXIS));
        btns.add(subir);
        btns.add(bajar);
        
        
        this.addAncestorListener(new AncestorListener() {

            @Override
            public void ancestorAdded(AncestorEvent event) {
                reset();
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {

            }

            @Override
            public void ancestorMoved(AncestorEvent event) {

            }
        });

        this.add(caja);
        this.add(btns);
        this.add(caja);
        this.add(btns);
    }
    
    public void setearClasse(Clase classe)
    {
        this.classe = classe;
    }

    public void reset()
    {
        if(classe!=null)
        {
            Simbolo defecto  = classe.tabla.getSimbolo("defecto", classe);
            try {
                setTexto(defecto.valor.toString());
            } catch (Exception e) {
                Template.reporteError_CJS.agregar("Ejecucion",raiz.linea, raiz.columna, "Error al reset defecti de CajaNumerica "+e.getMessage());
            }
        }
    }
    
    public void setControlNumerico() {
        caja.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        INCREMENTAR();
                        break;
                    case KeyEvent.VK_DOWN:
                        DECREMENTAR();
                        break;
                    default:
                        if (!caja.getText().equalsIgnoreCase(lastText)) {
                            if (!caja.getText().equals("") && !caja.getText().equals("-")) {
                                if (!MATCH()) {
                                    caja.setText(lastText);
                                } else {
                                    lastText = caja.getText();
                                }
                            }
                        }

                        break;
                }

            }
        });
    }

    public void INCREMENTAR() {
        try {
            Double num = Double.valueOf(caja.getText());
            num++;
            caja.setText(num.toString());
            if (!MATCH()) {
                caja.setText(lastText);
            } else {
                lastText = caja.getText();
            }
        } catch (Exception xe) {
        }
    }

    public void DECREMENTAR() {
        try {
            Double num = Double.valueOf(caja.getText());
            num--;
            caja.setText(num.toString());
            if (!MATCH()) {
                caja.setText(lastText);
            } else {
                lastText = caja.getText();
            }
        } catch (Exception xe) {
        }
    }

    public boolean MATCH() {
        // String to be scanned to find the pattern.

        String pattern = "^[-]?(\\d+(\\.\\d*)?)?";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(caja.getText());
        if (m.matches()) {

            try {

                if (maximo != null) {
                    if (Double.valueOf(caja.getText()) <= this.maximo) {
                        if (minimo != null) {
                            if (Double.valueOf(caja.getText()) >= this.minimo) {
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return true;
                        }
                    } else {
                        return false;
                    }
                } else {
                    if (minimo != null) {
                        if (Double.valueOf(caja.getText()) >= this.minimo) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return true;
                    }
                }

            } catch (Exception e) {
                return false;
            }

        } else {
            return false;
        }
    }

    public void setAlto(Object alto) {
        try {
            setPreferredSize(new Dimension(getPreferredSize().width, castToInt(alto)));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Alto [" + alto.toString() + "] en CajaNumerica " + this.getName());

        }
        updateUI();
    }

    public void setAncho(Object ancho) {
        try {
            setPreferredSize(new Dimension(castToInt(ancho), getPreferredSize().height));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Ancho [" + ancho.toString() + "] en CajaNumerica " + this.getName());

        }
        updateUI();
    }

    public void setMaximo(Object maximo) {
        try {
            this.maximo = castToDouble(maximo);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Maximo [" + maximo.toString() + "] en CajaNumerica " + this.getName());

        }
        updateUI();
    }

    public void setMinimo(Object minimo) {
        try {
            this.minimo = castToDouble(minimo);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Minimo [" + minimo.toString() + "] en CajaNumerica " + this.getName());

        }
        updateUI();
    }

    public void setX(Object x) {
        try {
            this.setLocation(castToInt(x), this.getLocation().y);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en X [" + x.toString() + "] en CajaNumerica " + this.getName());
        }
    }

    public void setY(Object y) {
        try {
            this.setLocation(this.getLocation().x, castToInt(y));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en Y [" + y.toString() + "] en CajaNumerica " + this.getName());
        }
    }

    //Valor por Defecto
    public void setTexto(String txt) {
        try {

            caja.setText(txt);
            lastText = txt;
        } catch (Exception ex) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Texto por Defecto [" + txt + "] en CajaNumerica " + this.getName());

        }
        updateUI();
    }

    public void setId(String id) {
        try {
            this.setName(id);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Id/Name [" + id + "] en CajaNumerica " + this.getName());
        }
    }

    public Integer castToInt(Object nm) {
        try {
            return Integer.valueOf(nm.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Double castToDouble(Object nm) {
        try {
            return Double.valueOf(nm.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
