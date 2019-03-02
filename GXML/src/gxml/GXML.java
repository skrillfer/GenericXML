/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gxml;

import WRAPERS.Reproductor;
import WRAPERS.VLCPlayer;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.text.ParseException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 *
 * @author fernando
 */
public class GXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /*NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Reproductor re = new Reproductor();
            }
        });*/
        Reproductor reproductor = new Reproductor();
        reproductor.setRuta("/home/fernando/Vídeos/Betrayed.mp3");

        JFrame frame = new JFrame("Capture");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(reproductor);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(50, 50);
        frame.setSize(800, 600);

        frame.setVisible(true);

        // Reproduce el vídeo.
        reproductor.iniciarReproduccion();

    }

}
