/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptCompiler;

/**
 *
 * @author fernando
 */
public class Resultado {
    public String tipo;
    public Object valor;
    public Simbolo simbolo;

    public Resultado() {

    }

    public Resultado(String tipo) {
        this.tipo = tipo;
    }

    public Resultado(Object valor) {
        this.valor = valor;
    }

    public Resultado(String tipo, Object valor) {
        this.tipo = tipo;
        this.valor = valor;
    }
}
