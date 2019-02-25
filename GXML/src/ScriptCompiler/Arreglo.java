/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptCompiler;

import Estructuras.Nodo;
import INTERFAZ.Template;
import ScriptCompiler.OperacionesARL.OperacionesARL;
import java.util.ArrayList;

/**
 *
 * @author fernando
 */
public class Arreglo {

    public ArrayList<Integer> dimensiones;
    private ArrayList<Object> datos;
    private TablaSimbolo global;
    private TablaSimbolo tabla;
    public boolean estado = true;
    private OperacionesARL opL;
    public Template miTemplate;

    public Arreglo() {
        dimensiones = new ArrayList<>();
        datos = new ArrayList<>();
    }

    public Arreglo(Nodo raiz, TablaSimbolo global, TablaSimbolo tabla, ArrayList<Integer> dimensiones, Template template1, int num) {
        this.miTemplate = template1;
        this.dimensiones = dimensiones;
        datos = new ArrayList<>();
        this.global = global;
        this.tabla = tabla;
        opL = new OperacionesARL(global, tabla, miTemplate);

        for (int i = 0; i < dimensiones.size(); i++) {
            int tam = dimensiones.get(i);
            for (int j = 0; j < tam; j++) {
                datos.add(null);
            }
        }

        guardarValores2(raiz);
    }

    //****asignando valores a un vector EXISTENTE
    public Arreglo(Nodo raiz, TablaSimbolo global, TablaSimbolo tabla, ArrayList<Integer> dimensiones, Template template1) {
        this.miTemplate = template1;
        this.dimensiones = dimensiones;
        datos = new ArrayList<>();
        this.global = global;
        this.tabla = tabla;
        opL = new OperacionesARL(global, tabla, miTemplate);
        guardarValores2(raiz);
    }

    //*****creando VECTOR con una lista de VALORES
    public Arreglo(Nodo raiz, TablaSimbolo global, TablaSimbolo tabla, Template template1) {
        this.miTemplate = template1;
        dimensiones = new ArrayList<>();
        datos = new ArrayList<>();
        this.global = global;
        this.tabla = tabla;
        opL = new OperacionesARL(global, tabla, miTemplate);
        guardarValores(raiz);
    }

    //*****creando un VECTOR sin valores pero con TAM establecido
    public Arreglo(Nodo raiz, TablaSimbolo global, TablaSimbolo tabla, int n, Template template1) {
        this.miTemplate = template1;
        dimensiones = new ArrayList<>();
        datos = new ArrayList<>();
        this.global = global;
        this.tabla = tabla;
        opL = new OperacionesARL(global, tabla, miTemplate);
        guardarDimensiones(raiz);
        for (int i = 0; i < dimensiones.size(); i++) {
            int tam = dimensiones.get(i);
            for (int j = 0; j < tam; j++) {
                datos.add(null);
            }
        }
    }

    private void guardarDimensiones(Nodo raiz) {
        ArrayList<Nodo> dim = raiz.hijos.get(1).hijos;
        int total = 1;
        for (Nodo hijo : dim) {
            Resultado dimension = opL.ejecutar(hijo);

            if (dimension.tipo.equalsIgnoreCase("number")) {
                total = total * ((Double) dimension.valor).intValue();
                dimensiones.add(((Double) dimension.valor).intValue());
            } else {
                //Inicio.reporteError.agregar("Semantico", hijo.linea, hijo.columna, "Solo se permiten valores enteros para los indices de un arreglo");
                estado = false;
            }
        }
    }

    public void guardarValores(Nodo raiz) {
        ArrayList<Nodo> dim = null;
        ArrayList<Nodo> val = null;
        String tipo = "";
        switch (raiz.hijos.size()) {

            case 2:
                val = raiz.hijos.get(1).hijos;//modifique para la nueva version
                break;
            default:
                break;
        }

        int total = 0;
        for (Nodo hijo : val) {
            Resultado resultado = opL.ejecutar(hijo);
            if (resultado != null) {
                total++;
                datos.add(resultado);
            }
        }
        if (dimensiones.isEmpty()) {
            dimensiones.add(total);
        }

    }

    public void guardarValores2(Nodo raiz) {
        ArrayList<Nodo> val = raiz.hijos.get(1).hijos;// (LISTA_NODO)valores        
        if (val.size() <= dimensiones.get(0)) {
            for (int i = 0; i < val.size(); i++) {
                Nodo hijo = val.get(i);
                Resultado resultado = opL.ejecutar(hijo);
                if (resultado != null) {
                    datos.set(i, resultado);
                }
            }
        }
    }

    public boolean setValor(Nodo indice, Resultado dato) {
        Resultado res = opL.ejecutar(indice);
        if (res.tipo.equals("number")) {
            int posicion = ((Double) res.valor).intValue();

            if (posicion <= (datos.size() - 1) && posicion >= 0) {
                datos.set(posicion, dato);
                return true;
            }
        }
        return false;
    }

    public void setDatos(ArrayList<Object> datos) {
        this.datos = datos;
    }

    public ArrayList<Object> getDatos() {
        return datos;
    }

    public Object getValor(ArrayList<Integer> indices) {
        int indice = indices.get(0);
        if ((indice + 1) <= datos.size() && indice >= 0) {
            return datos.get(indice);
        } else {
            //indice incorrecto
            return null;
        }
    }

}
