/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package INTERFAZ;

import Errores.ReporteError;
import Errores.ReporteSimbolo;
import ScriptCompiler.Script;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author fernando
 */
public class Template {
    //----------------------------------------------------//
    public static ReporteError reporteError_CJS = new ReporteError(); // este REPORTE es para CJS
    public static ReporteSimbolo reporteSimbolos_CJS = new ReporteSimbolo(); // este REPORTE es para CJS
    //**************************************************************************
    
    public void ParsearArchivoFs(String absolutePath , String nombreArchivoActual)
    {
        
        Script.archivos = new ArrayList();

        File padre = new File(absolutePath);
        
        
        //File file = new File(padre.getParent());
        File files[] = {padre};
        Script graphik = new Script(files, nombreArchivoActual,this,padre.getParent());
        
    }
    
}
