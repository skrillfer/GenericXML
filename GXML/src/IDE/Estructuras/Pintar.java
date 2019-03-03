/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE.Estructuras;

import java.util.LinkedList;

/**
 *
 * @author fernando
 */
public class Pintar {

    public static LinkedList<Elemento> listapintar = new LinkedList<Elemento>();
    Pantalla_Pintar nuevo = new Pantalla_Pintar();

    public Pintar() {
    }

    public void agregarPoint(int x, int y, String color, int diametro) {
        Elemento point = new Elemento();
        point.setX(x);
        point.setY(y);
        point.setColor(color);
        point.setDiametro(diametro);
        point.setTipo("point");
        listapintar.add(point);
        crearNavegador();
    }

    public void agregarQuadrate(int x, int y, String color, int ancho, int alto) {
        Elemento quadrate = new Elemento();
        quadrate.setX(x);
        quadrate.setY(y);
        quadrate.setColor(color);
        quadrate.setAncho(ancho);
        quadrate.setAlto(alto);
        quadrate.setTipo("quadrate");
        listapintar.add(quadrate);
        crearNavegador();
    }

    public void agregarOval(int x, int y, String color, int ancho, int alto) {
        Elemento oval = new Elemento();
        oval.setX(x);
        oval.setY(y);
        oval.setColor(color);
        oval.setAncho(ancho);
        oval.setAlto(alto);
        oval.setTipo("oval");
        listapintar.add(oval);
        crearNavegador();
    }

    public void agregarString(int x, int y, String color, String cadena) {
        Elemento texto = new Elemento();
        texto.setX(x);
        texto.setY(y);
        texto.setColor(color);
        texto.setCadena(cadena);
        texto.setTipo("string");
        listapintar.add(texto);
        crearNavegador();
    }

    public void agregarLine(int x, int y, int xf, int yf, String color, int grosor) {
        Elemento line = new Elemento();
        line.setX(x);
        line.setY(y);
        line.setXf(xf);
        line.setYf(yf);
        line.setColor(color);
        line.setGrosor(grosor);
        line.setTipo("line");
        listapintar.add(line);
        crearNavegador();
    }

    public void crearNavegador() {
        nuevo.dispose();
        nuevo.repaint();
        nuevo.setVisible(true);
    }
}
