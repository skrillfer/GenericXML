/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WRAPERS;

import Estructuras.Nodo;
import INTERFAZ.Template;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

/**
 *
 * @author fernando
 */
public class Reproductor extends JPanel {
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
                        mediaPlayer.playMedia(ruta);
                        yainicio=true;
                    } else {
                        mediaPlayer.play(); 
                    }

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

            }
        });
    }

    public void iniciarReproduccion() {

        if (auto) {
            yainicio = true;
            mediaPlayer.playMedia(this.ruta);
        }
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public void setX(int x) {
        try {
            this.setLocation(x, this.getLocation().y);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en X [" + x + "] en Reproductor " + this.getName());
        }
    }

    public void setY(int y) {
        try {
            this.setLocation(this.getLocation().x, y);
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Location en Y [" + y + "] en Reproductor " + this.getName());
        }
    }
    
    
    public void setAutoReproduccion(boolean check)
    {
        try {
            this.auto = check;
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear AutoReproduccion  en Reproductor " + this.getName());
        }
    }
    
    public void setAncho(int ancho) {
        try {
            setPreferredSize(new Dimension(ancho, getPreferredSize().height));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Ancho [" + ancho + "] en Reproductor " + this.getName());
        }
        updateUI();
    }

    public void setAlto(int alto) {
        try {
            setPreferredSize(new Dimension(getPreferredSize().width, alto));
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al setear Alto [" + alto + "] en Reproductor " + this.getName());
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
}
