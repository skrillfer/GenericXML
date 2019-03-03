/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE.Estructuras;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;

/**
 *
 * @author fernando
 */
public class Pantalla_Pintar extends JFrame{
    public Pantalla_Pintar(){
        super( "Generic GXML Compiladores 2" );
        setSize(1000,1000);
        setBackground(Color.WHITE);
    }
    public void paint( Graphics g ){
        super.paint( g );  // llamar al método paint de la superclase
        for(int i=0;i<Pintar.listapintar.size();i++){
            if(Pintar.listapintar.get(i).getTipo().equals("point")){
                g.setColor(Color.decode(Pintar.listapintar.get(i).getColor()));
                int radio=Pintar.listapintar.get(i).getDiametro()/2;
                g.fillOval(Pintar.listapintar.get(i).getX(),Pintar.listapintar.get(i).getY(),radio,radio);
            }else if(Pintar.listapintar.get(i).getTipo().equals("quadrate")){
                g.setColor(Color.decode(Pintar.listapintar.get(i).getColor()));
                g.fillRect(Pintar.listapintar.get(i).getX(), Pintar.listapintar.get(i).getY(),(Pintar.listapintar.get(i).getAncho()),(Pintar.listapintar.get(i).getAlto()));
            }else if(Pintar.listapintar.get(i).getTipo().equals("oval")){
                g.setColor(Color.decode(Pintar.listapintar.get(i).getColor()));
                g.fillOval(Pintar.listapintar.get(i).getX(), Pintar.listapintar.get(i).getY(),(Pintar.listapintar.get(i).getX()+Pintar.listapintar.get(i).getAncho()),(Pintar.listapintar.get(i).getAlto()+Pintar.listapintar.get(i).getAlto()));
            }else if(Pintar.listapintar.get(i).getTipo().equals("string")){
                g.setColor(Color.decode(Pintar.listapintar.get(i).getColor()));
                int fontSize = 30;
                g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
                g.drawString(Pintar.listapintar.get(i).getCadena(),Pintar.listapintar.get(i).getX(),Pintar.listapintar.get(i).getY());
            }else if(Pintar.listapintar.get(i).getTipo().equals("line")){
                g.setColor(Color.decode(Pintar.listapintar.get(i).getColor()));
                g.drawLine(Pintar.listapintar.get(i).getX(),Pintar.listapintar.get(i).getY(),Pintar.listapintar.get(i).getXf(),Pintar.listapintar.get(i).getYf());
            }else{
                System.out.println("Esto no deberia de aparecer");
            }
        }
    } 
}
