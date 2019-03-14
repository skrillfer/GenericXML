/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package INTERFAZ;

import Analizadores.Gxml.LexGxml;
import Analizadores.Gxml.SintacticoGxml;
import Errores.ReporteError;
import Errores.ReporteSimbolo;
import GenericCompiler.TraduccionGxml_Script;
import ScriptCompiler.Script;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author fernando
 */
public class Template {

    public static JTextArea CONSOLA;
    //----------------------------------------------------//
    public static ReporteError reporteError_CJS = new ReporteError(); // este REPORTE es para CJS
    public static ReporteSimbolo reporteSimbolos_CJS = new ReporteSimbolo(); // este REPORTE es para CJS
    //**************************************************************************

    public void ParsearArchivoFs(String absolutePath, String nombreArchivoActual) {
        Script.archivos = new ArrayList();
        File padre = new File(absolutePath);
        File files[] = {padre};
        Script graphik = new Script(files, nombreArchivoActual, this, padre.getParent());
    }

    public String ParsearArchivoGxml(String absolutePath, String nombreArchivoActual) throws FileNotFoundException {
        //************************************************************************************
        File padre = new File(absolutePath);
        LexGxml lex = new LexGxml(new FileReader(absolutePath));
        SintacticoGxml sin = new SintacticoGxml(lex);
        TraduccionGxml_Script tra = new TraduccionGxml_Script();
        try {
            sin.parse();
            tra.IniciarTraduccion(sin.getRoot(), padre.getParent());
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Ejecucion", 0, 0, "Error al Parsear GXML a Fs:"+e.getMessage());
        }
        tra.codigoScript = tra.codigoImports + tra.codigoScript;
        
        
        if(!tra.ventanaPrincipal.equals(""))
        {
            tra.codigoScript += "\n"+tra.ventanaPrincipal+".alcargar();\n";
        }
        //************************************************************************************
        return tra.codigoScript;
        
        /*Script.archivos = new ArrayList();
        File padre = new File(absolutePath);
        File files[] = {padre};
        Script graphik = new Script(files, nombreArchivoActual, this, padre.getParent());*/
    }
}
