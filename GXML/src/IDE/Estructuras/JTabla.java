/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE.Estructuras;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author fernando
 */
public class JTabla extends JTable {

    //JTable j;
    public JTabla() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Linea");
        model.addColumn("Columna");
        model.addColumn("Descripcion");

        setModel(model);
        setLocation(10, 50);
    }

    public void addRow(String tipo, int linea, int columna, String descripcion) {
        try {
            DefaultTableModel model = (DefaultTableModel) this.getModel();
            model.addRow(new Object[]{String.valueOf(linea), String.valueOf(columna), descripcion});
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void RemoveAllRows() {
        DefaultTableModel dm = (DefaultTableModel) this.getModel();
        int rowCount = dm.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            dm.removeRow(i);
        }
    }

}
