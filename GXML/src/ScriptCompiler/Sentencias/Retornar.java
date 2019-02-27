/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptCompiler.Sentencias;

import Estructuras.Nodo;
import ScriptCompiler.Compilador;
import ScriptCompiler.Metodo;
import ScriptCompiler.OperacionesARL.OperacionesARL;
import ScriptCompiler.Resultado;

/**
 *
 * @author fernando
 */
public class Retornar extends Compilador{
    public Metodo ejecutar(Nodo raiz) {
        if (raiz.hijos.size() > 0) {
            opL = new OperacionesARL(global, tabla,miTemplate);
            Resultado retorno = opL.ejecutar(raiz.hijos.get(0));
            metodoActual.retorno = retorno;
            metodoActual.estadoRetorno = true;
        } else {
            metodoActual.retorno = null;
            metodoActual.estadoRetorno = true;
        }
        return metodoActual;
    }
}
