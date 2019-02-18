/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COMPONENTES;

import javax.swing.JPanel;

/**
 *
 * @author fernando
 */
public class PanelGenerico extends JPanel{

    public PanelGenerico() {
        super();
    }
      
    
    public void setSIZE(int alto, int ancho)
    {
        try {
            this.setSize(alto,ancho);
        } catch (Exception e) {
        }
    }
    
    public void setALTO(int alto, int ancho)
    {
        try {
            this.setSize(alto,this.getSize().height);
        } catch (Exception e) {
        }
    }
    
    public void setANCHO(int alto, int ancho)
    {
        try {
            this.setSize(this.getSize().width,ancho);
        } catch (Exception e) {
        }
    }
}
