/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Errores;

import ScriptCompiler.Compilador;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author fernando
 */
public class ReporteError {

    //private ArrayList<ErrorP> listaErrores;
    public ReporteError() {
        //listaErrores = new ArrayList<>();
    }

    public void agregar(String tipo, int linea, int columna, String descripcion) {
        /*String archivo = "";
        if (Compilador.claseActual != null) {
            archivo = Compilador.claseActual.archivo;
        } else {
            archivo = "archivoActual";
        }
        ErrorP error = new ErrorP(tipo, linea, columna, archivo, descripcion);
        String strError = tipo + "  linea:" + linea + "  columna:" + columna + " archivo:" + archivo + "   " + descripcion;
        System.err.println(strError);
        //listaErrores.add(error);*/
        switch (tipo.toLowerCase()) {
            case "semantico":
                try {
                    IDE.IDE_Ventana.tablaSemantico.addRow(tipo, linea, columna, descripcion);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                break;
            case "sintactico":
                try {
                    IDE.IDE_Ventana.tablaSintactico.addRow(tipo, linea, columna, descripcion);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                break;
            default:
                try {
                    IDE.IDE_Ventana.tablaEjecucion.addRow(tipo, linea, columna, descripcion);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                break;
        }

    }

    public void agregar(String tipo, int linea, int columna, String descripcion, String archivo) {

        ErrorP error = new ErrorP(tipo, linea, columna, archivo, descripcion);
        String strError = tipo + "  linea:" + linea + "  columna:" + columna + " archivo:" + archivo + "   " + descripcion;
        System.err.println(strError);
        //listaErrores.add(error);
    }

    public void generarHtml(String titulo, String lenguaje) {

    }

}
