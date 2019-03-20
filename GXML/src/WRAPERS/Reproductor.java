/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WRAPERS;

import Estructuras.Nodo;
import INTERFAZ.Template;
import ScriptCompiler.Clase;
import ScriptCompiler.Compilador;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

/**
 *
 * @author fernando
 */
public class Reproductor extends JPanel {

    public Clase classe;

    /*
    Ruta, X, Y, Auto-reproductor, Alto, Ancho    
     */
    Nodo raiz;

    boolean yainicio = false;
    boolean auto = false;
    String ruta = "";

    Canvas canvas;
    EmbeddedMediaPlayer mediaPlayer;
    JPanel contentPane = new JPanel();

    JPanel bar = new JPanel();
    GridLayout grid = new GridLayout(1, 3);

    JButton pause = new JButton("PAUSE");
    JButton play = new JButton("PLAY");
    JButton stop = new JButton("STOP");

    //APLICA PARA CREAR AUDIO y VIDEO
    public Reproductor(Nodo raiz) {
        this.raiz = raiz;
        // Crea la ventana del reproductor
        canvas = new Canvas();
        canvas.setBackground(Color.black);

        bar.setLayout(grid);

        bar.add(play);
        bar.add(pause);
        bar.add(stop);

        setBackground(Color.black);
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        add(bar, BorderLayout.SOUTH);

        String[] VLC_ARGS = {
            "--no-xlib", // we don't want audio (decoding)
        };
        MediaPlayerFactory factory = new MediaPlayerFactory(VLC_ARGS);
        mediaPlayer = factory.newEmbeddedMediaPlayer();

        CanvasVideoSurface videoSurface = factory.newVideoSurface(canvas);
        mediaPlayer.setVideoSurface(videoSurface);

        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.pause();
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.stop();
            }
        });

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!yainicio) {
                        String rutabuena = existeArchivo(ruta);
                        if (!rutabuena.equals("")) {
                            ruta = rutabuena;
                        }
                        File file = new File(ruta);
                        if (file.exists()) {
                            mediaPlayer.playMedia(ruta);
                            yainicio = true;
                        } else {
                            Template.reporteError_CJS.agregar("Ejecucion", raiz.linea, raiz.columna, "Error al reproducir ruta[" + ruta + "] en Reproductor " + getName());
                        }

                    } else {
                        File file = new File(ruta);
                        if (file.exists()) {
                            mediaPlayer.play();
                        } else {
                            Template.reporteError_CJS.agregar("Ejecucion", raiz.linea, raiz.columna, "Error al reproducir ruta[" + ruta + "] en Reproductor " + getName());
                        }
                    }

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        this.addAncestorListener(new AncestorListener() {

            @Override
            public void ancestorAdded(AncestorEvent event) {
                iniciarReproduccion();
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
                mediaPlayer.stop();
            }

            @Override
            public void ancestorMoved(AncestorEvent event) {

            }
        });
    }

    public void setearClasse(Clase classe) {
        this.classe = classe;
    }

    public void iniciarReproduccion() {

        if (auto) {
            yainicio = true;
            String rutabuena = existeArchivo(this.ruta);
            if (!rutabuena.equals("")) {
                this.ruta = rutabuena;
            }
            File file = new File(this.ruta);
            if (file.exists()) {
                mediaPlayer.playMedia(this.ruta);
            } else {
                Template.reporteError_CJS.agregar("Ejecucion", raiz.linea, raiz.columna, "Error al reproducir ruta[" + this.ruta + "] en Reproductor " + this.getName());
            }
        }
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public void setX(Object x) {
        if (x.toString().equals("nulo")) {
            return;
        }
        try {
            this.setLocation(castToInt(x), this.getLocation().y);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en X [" + x.toString() + "] en Reproductor " + this.getName());
        }
    }

    public void setY(Object y) {
        if (y.toString().equals("nulo")) {
            return;
        }
        try {
            this.setLocation(this.getLocation().x, castToInt(y));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en Y [" + y.toString() + "] en Reproductor " + this.getName());
        }
    }

    public void setAutoReproduccion(Object check) {
        if (check.toString().equals("nulo")) {
            return;
        }
        try {
            this.auto = castToBoolean(check);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear AutoReproduccion " + check.toString() + " en Reproductor " + this.getName());
        }
    }

    public void setAncho(Object ancho) {
        if (ancho.toString().equals("nulo")) {
            return;
        }
        try {
            setPreferredSize(new Dimension(castToInt(ancho), getPreferredSize().height));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Ancho [" + ancho.toString() + "] en Reproductor " + this.getName());
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
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Alto [" + alto.toString() + "] en Reproductor " + this.getName());
        }
        updateUI();
    }

    public void setId(String id) {
        try {
            this.setName(id);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Id/Name [" + id + "] en Reproductor " + this.getName());
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

    public String existeArchivo(String path) {
        String pathPadre = Compilador.PathRoot;

        File file1 = new File(pathPadre + "" + path);
        File file2 = new File(pathPadre + "/" + path);
        File file3 = new File(path);
        File file4 = new File("/" + path);

        if (file1.exists()) {
            return file1.getAbsolutePath();
        }

        if (file2.exists()) {
            return file2.getAbsolutePath();
        }

        if (file3.exists()) {
            return file3.getAbsolutePath();
        }

        if (file4.exists()) {
            return file4.getAbsolutePath();
        }
        return "";
    }
}
