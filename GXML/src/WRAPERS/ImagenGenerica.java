/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WRAPERS;

import Estructuras.Nodo;
import INTERFAZ.Template;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author fernando
 */
public class ImagenGenerica extends JLabel {

    /*
    Ruta, X, Y, Auto-reproductor, Alto, Ancho    
     */
    Nodo raiz;

    boolean auto = false;
    String ruta = "";

    public ImagenGenerica(Nodo raiz) {
        this.raiz = raiz;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public void renderizarImagen() {
        ImageIcon img = null;

        File f = new File(ruta);
        if (ruta.equals("") || !f.exists() || f.isDirectory()) {
            f = new File(ruta);
        }
        if (ruta.equals("") || !f.exists() || f.isDirectory()) {
            ruta = "src/Recursos/image_notfound.png";
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al cargar la IMAGEN no existe imagen en ruta " + ruta + " en ImagenGenerica " + this.getName());
        }

        img = new ImageIcon(new ImageIcon(ruta).getImage().getScaledInstance(getPreferredSize().width, getPreferredSize().height, Image.SCALE_DEFAULT));
        setIcon(img);
    }

    public void setX(int x) {
        try {
            this.setLocation(x, this.getLocation().y);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en X [" + x + "] en ImagenGenerica " + this.getName());
        }
    }

    public void setY(int y) {
        try {
            this.setLocation(this.getLocation().x, y);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en Y [" + y + "] en ImagenGenerica " + this.getName());
        }
    }

    public void setAutoReproduccion(boolean check) {
        try {
            this.auto = check;
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear AutoReproduccion  en ImagenGenerica " + this.getName());
        }
    }

    public void setAncho(int ancho) {
        try {
            setPreferredSize(new Dimension(ancho, getPreferredSize().height));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Ancho [" + ancho + "] en ImagenGenerica " + this.getName());
        }
        updateUI();
    }

    public void setAlto(int alto) {
        try {
            setPreferredSize(new Dimension(getPreferredSize().width, alto));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Alto [" + alto + "] en ImagenGenerica " + this.getName());
        }
        updateUI();
    }

    public void setId(String id) {
        try {
            this.setName(id);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Id/Name [" + id + "] en ImagenGenerica " + this.getName());
        }
    }
}
