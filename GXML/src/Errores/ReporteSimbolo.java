/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Errores;

import ScriptCompiler.Compilador;
import ScriptCompiler.Simbolo;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author fernando
 */
public class ReporteSimbolo {
    private ArrayList<SimboloR> listaSimbolos;

    public ReporteSimbolo() {
        listaSimbolos = new ArrayList<>();
    }

    public void agregar(String tipo, int linea, int columna, String descripcion) {
        String archivo = "";
        if (Compilador.claseActual != null) {
            archivo = Compilador.claseActual.archivo;
        } else {
            archivo = "archivoActual";
        }
        SimboloR error = new SimboloR(tipo, linea, columna, archivo, descripcion);
        String strError = tipo + "  linea:" + linea + "  columna:" + columna + " archivo:" + archivo + "   " + descripcion;
        System.err.println(strError);
        listaSimbolos.add(error);
    }

    public void agregar(String tipo, int linea, int columna, String descripcion, String archivo) {

        SimboloR error = new SimboloR(tipo, linea, columna, archivo, descripcion);
        String strError = tipo + "  linea:" + linea + "  columna:" + columna + " archivo:" + archivo + "   " + descripcion;
        System.err.println(strError);
        listaSimbolos.add(error);
    }

    public String generarHtml(String titulo, String lenguaje) {
        
        titulo += ".html";
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
        String fecha = hourdateFormat.format(date);
        String html = "";
        html += "<!DOCTYPE html";
        html += "<html>";
        html += "<title> Simbolos </title>";
        html += "<body>";
        html += "<span style=\"color: #ff0000;\">";
        html += "<center> <h1>" + "REPORTE DE ERRORES" + "</h1> </center>";
        html += "</span>";
        html += "<span style=\"color: #0000ff;\">";
        html += "<center> <h2>" + lenguaje + "</h2> </center>";
        html += "</span>";
        html += "<span style=\"color: #0000ff;\">";
        html += "<center> <h4>" + "Fecha: " + fecha + "</h4> </center>";
        html += "</span>";
        html += "<hr/>";
        html += "<center>";
        html += "<table class=\"egt\" border=1>";
        html += "<thead>";
        //aqui van los titulos
        html += "<tr>";
        html += "<td>" + "" + "</td>";
        html += "<td>" + "Tipo" + "</td>";
        html += "<td>" + "Descripcion" + "</td>";
        html += "<td>" + "Archivo" + "</td>";
        html += "<td>" + "Linea" + "</td>";
        html += "<td>" + "columna" + "</td>";
        html += "</tr>";
        html += "</thead>";
        int i = 0;
        for (SimboloR error : listaSimbolos) {
            html += "<tr>";
            html += "<td>" + i + "</td>";
            html += "<td>" + error.tipo + "</td>";
            html += "<td>" + error.descripcion + "</td>";
            html += "<td>" + error.archivo + "</td>";
            html += "<td>" + error.linea + "</td>";
            html += "<td>" + error.columna + "</td>";

            html = html + "</tr>";
            i++;
        }

        html += "</table>";
        html += "</center>";
        html += "</body>";
        html += "</html>";
        return html;
        /*try {
            FileWriter archivo = new FileWriter(titulo);
            PrintWriter escritura = new PrintWriter(archivo);
            escritura.println(html);
            archivo.close();

            File file = new File(titulo);
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
        }*/
    }
}
