/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WRAPERS;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

/**
 *
 * @author fernando
 */
public class Reproductor extends JPanel {

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

    public Reproductor() {
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

}
