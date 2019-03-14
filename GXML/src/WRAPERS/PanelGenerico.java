/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WRAPERS;

import Estructuras.Nodo;
import INTERFAZ.Template;
import ScriptCompiler.Clase;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author fernando
 */
public class PanelGenerico extends JPanel {
    public Clase classe;

    Nodo raiz;

    public PanelGenerico(Nodo raiz) {
        this.raiz = raiz;

    }

    public void setearClasse(Clase classe) {
        this.classe = classe;
    }

    public void setAncho(Object ancho) {
        if(ancho.toString().equals("nulo"))
            return;
        try {
            setPreferredSize(new Dimension(castToInt(ancho), getPreferredSize().height));
            updateUI();
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Color [" + ancho.toString() + "] en PanelGenerico " + this.getName());

        }
        updateUI();
    }

    public void setAlto(Object alto) {
        if(alto.toString().equals("nulo"))
            return;
        try {
            setPreferredSize(new Dimension(getPreferredSize().width, castToInt(alto)));
            updateUI();
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Color [" + alto.toString() + "] en PanelGenerico " + this.getName());

        }
        updateUI();
    }

    public void setColor(String hex) {
        if(hex.equals("nulo"))
            return;
        try {
            this.setBackground(Color.decode(hex));
        } catch (NumberFormatException e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Color [" + hex + "] en PanelGenerico " + this.getName());
        }
    }

    public void setBorde(Object borde) {
        if(borde.toString().equals("nulo"))
            return;
        try {
            if (castToBoolean(borde)) {
                this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            } else {
                this.setBorder(BorderFactory.createEmptyBorder());
            }
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Borde [" + borde.toString() + "] en PanelGenerico " + this.getName());
        }
    }

    public void setX(Object x) {
        if(x.toString().equals("nulo"))
            return;
        try {
            this.setLocation(castToInt(x), this.getLocation().y);
            updateUI();
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en X [" + x.toString() + "] en PanelGenerico " + this.getName());
        }
    }

    public void setY(Object y) {
        if(y.toString().equals("nulo"))
            return;
        try {
            this.setLocation(this.getLocation().x, castToInt(y));
            updateUI();
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en Y [" + y.toString() + "] en PanelGenerico " + this.getName());
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

    //Nombre o id
    public void setId(String id) {
        try {
            this.setName(id);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Id/Name [" + id + "] en TextoGenerico " + this.getName());
        }
    }
    /*
    public void setBorder() {
        Border blackline, raisedetched, loweredetched,
                raisedbevel, loweredbevel, empty;

        //A border that puts 10 extra pixels at the sides and
        //bottom of each pane.
        blackline = BorderFactory.createLineBorder(Color.black);
        raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        raisedbevel = BorderFactory.createRaisedBevelBorder();
        loweredbevel = BorderFactory.createLoweredBevelBorder();
        empty = BorderFactory.createEmptyBorder();

        Border compound;
        compound = BorderFactory.createCompoundBorder(
                raisedbevel, loweredbevel);
        this.setBorder(compound);
    }
     */
}
