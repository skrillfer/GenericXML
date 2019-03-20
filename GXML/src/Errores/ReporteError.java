/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Errores;

/**
 *
 * @author fernando
 */
public class ReporteError {

    public ReporteError() {
    }

    public void agregar(String tipo, int linea, int columna, String descripcion) {
        
        switch (tipo.toLowerCase()) {
            case "semantico":
                try {
                    IDE.IDE_Ventana.tablaSemantico.addRow(tipo, linea, columna, descripcion);
                } catch (Exception e) {
                }
                break;
            case "sintactico":
            case "lexico":    
                try {
                    IDE.IDE_Ventana.tablaSintactico.addRow(tipo, linea, columna, descripcion);
                } catch (Exception e) {
                }
                break;
            default:
                try {
                    IDE.IDE_Ventana.tablaEjecucion.addRow(tipo, linea, columna, descripcion);
                } catch (Exception e) {
                }
                break;
        }

    }

    


}
