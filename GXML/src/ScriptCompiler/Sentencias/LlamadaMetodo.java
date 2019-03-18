/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptCompiler.Sentencias;

import Analizadores.Gdato.LexGdato;
import Analizadores.Gdato.SintacticoGdato;
import Analizadores.Gxml.LexGxml;
import Analizadores.Gxml.SintacticoGxml;
import Estructuras.Nodo;
import INTERFAZ.Template;
import ScriptCompiler.Arreglo;
import ScriptCompiler.Clase;
import ScriptCompiler.Compilador;
import ScriptCompiler.Metodo;
import ScriptCompiler.OperacionesARL.OperacionesARL;
import ScriptCompiler.Resultado;
import ScriptCompiler.Simbolo;
import ScriptCompiler.TablaSimbolo;
import WRAPERS.AreaTextoGenerica;
import WRAPERS.BotonGenerico;
import WRAPERS.CajaNumericaGenerica;
import WRAPERS.CajaTextoGenerica;
import WRAPERS.DesplegableGenerico;
import WRAPERS.PanelGenerico;
import WRAPERS.Reproductor;
import WRAPERS.TextoGenerico;
import WRAPERS.VentanaGenerica;
import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 *
 * @author fernando
 */
public class LlamadaMetodo extends Compilador {

    public Resultado ComponenteRes = null;

    public Resultado res_nativas = null;

    private Nodo raiz;
    private Resultado actualResultado;

    private Clase actual;
    private int nivel = 0;
    public boolean proceder = true;

    public LlamadaMetodo(Clase actual) {
        this.actual = actual;
    }

    public LlamadaMetodo(Clase actual, int nivel, Nodo raiz) {
        this.actual = actual;
        this.nivel = nivel;
        this.raiz = raiz;
    }

    public LlamadaMetodo(Clase actual, int nivel, Resultado resultado, Nodo raiz) {
        this.actual = actual;
        this.nivel = nivel;
        this.actualResultado = resultado;
        this.raiz = raiz;
        FuncionesNativasScript();
    }

    public Metodo ejecutar(Nodo raiz) {
        String nombre = raiz.valor;
        ArrayList<Resultado> parametros = getParametros(raiz);
        String id = getId(nombre, parametros);
        Metodo metodoTemp = getMetodo(id);
        //----------------------------------------------------------------------
        if (metodoTemp != null) {
            pilaNivelCiclo.push(nivelCiclo);
            nivelCiclo = 0;

            pilaTablas.push(tabla);

            //---------     CAMBIO DE AMBITO    --------------  ----------  --------
            TablaSimbolo tablaTemp = new TablaSimbolo();
            tablaTemp.cambiarAmbito(superClase.global);
            tabla = tablaTemp;
            //--------      -------------   --------------  -----------------   ----
            //TablaSimbolo tablaTemp = new TablaSimbolo();
            //tabla = tablaTemp;

            for (int i = 0; i < metodoTemp.parametros.size(); i++) {
                Nodo parametro = metodoTemp.parametros.get(i);
                Resultado valor = parametros.get(i);
                new Declaracion(parametro, valor, actual.global, tabla, miTemplate);
            }

            pilaMetodos.push(metodoActual);
            metodoActual = metodoTemp;

            global = actual.global;
            pilaClases.push(claseActual);
            claseActual = actual;

            metodoTemp = ejecutarSentencias(metodoActual.sentencias);
            metodoTemp.estadoRetorno = false;
            metodoTemp.estadoContinuar = false;
            metodoTemp.estadoTerminar = false;
            metodoActual = pilaMetodos.pop();
            claseActual = pilaClases.pop();
            global = claseActual.global;
            tabla = pilaTablas.pop();
            nivelCiclo = pilaNivelCiclo.pop();
        } else {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "El metodo " + nombre + " no existe en el ambito donde fue invocado");
        }
        return metodoTemp;
    }

    public Metodo ejecutarFuncion_Arreglo(ArrayList<Resultado> parametros, String nombre) {
        String id = getId(nombre, parametros);
        Metodo metodoTemp = getMetodo(id);
        //----------------------------------------------------------------------
        if (metodoTemp != null) {
            pilaNivelCiclo.push(nivelCiclo);
            nivelCiclo = 0;

            pilaTablas.push(tabla);

            //---------     CAMBIO DE AMBITO    --------------  ----------  --------
            TablaSimbolo tablaTemp = new TablaSimbolo();
            tablaTemp.cambiarAmbito(superClase.global);
            tabla = tablaTemp;
            //--------      -------------   --------------  -----------------   ----

            for (int i = 0; i < metodoTemp.parametros.size(); i++) {
                Nodo parametro = metodoTemp.parametros.get(i);
                Resultado valor = parametros.get(i);
                new Declaracion(parametro, valor, actual.global, tabla, miTemplate);
            }

            pilaMetodos.push(metodoActual);
            metodoActual = metodoTemp;

            global = actual.global;
            pilaClases.push(claseActual);
            claseActual = actual;

            metodoTemp = ejecutarSentencias(metodoActual.sentencias);
            metodoTemp.estadoRetorno = false;
            metodoTemp.estadoContinuar = false;
            metodoTemp.estadoTerminar = false;
            metodoActual = pilaMetodos.pop();
            claseActual = pilaClases.pop();
            global = claseActual.global;
            tabla = pilaTablas.pop();
            nivelCiclo = pilaNivelCiclo.pop();
        } else {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "El metodo " + nombre + " no existe en el ambito donde fue invocado");
        }
        return metodoTemp;
    }

    private String getId(String nombre, ArrayList<Resultado> parametros) {
        nombre += "_" + parametros.size();
        return nombre;
    }

    private ArrayList<Resultado> getParametros(Nodo raiz) {
        ArrayList<Resultado> parametros = new ArrayList<>();
        Nodo nodoParametros = raiz.hijos.get(0);
        for (Nodo hijo : nodoParametros.hijos) {
            opL = new OperacionesARL(global, tabla, miTemplate);
            Resultado resultado = opL.ejecutar(hijo);
            if (!esNulo(resultado)) {
                parametros.add(resultado);
            }
        }
        return parametros;
    }

    private ArrayList<Resultado> getParametrosConRefencia(Nodo raiz, int pos) {
        ArrayList<Resultado> parametros = new ArrayList<>();
        Nodo nodoParametros = raiz.hijos.get(0);
        int index = 0;
        for (Nodo hijo : nodoParametros.hijos) {
            if (pos == index) {
                parametros.add(new Resultado("String", hijo));
            } else {
                opL = new OperacionesARL(global, tabla, miTemplate);
                Resultado resultado = opL.ejecutar(hijo);
                parametros.add(resultado);
            }
            index++;
        }
        return parametros;
    }

    private Metodo getMetodo(String id) {
        Metodo metodo = buscarMetodo(id, actual);
        if (metodo == null) {
            metodo = buscarMetodo(id, superClase);
        }
        return metodo;
    }

    private Metodo buscarMetodo(String id, Clase actual) {
        for (Metodo metodo : actual.metodos) {
            if (metodo.id.equalsIgnoreCase(id)) {
                return metodo;
            }
        }
        return null;
    }

    /*------------------------------------------------------------------------*/
    public void FuncionesNativasScript() {
        String idxTMP = "";

        if (!esNulo(actualResultado)) {
            //El resultado anterior fu un arreglo
            if (esArreglo(actualResultado.valor)) {
                Arreglo arr;
                switch (raiz.valor.toLowerCase()) {
                    case "map":
                    case "filtrar":
                    case "buscar":
                    case "reduce":
                        proceder = false;
                        boolean todosFlag = true;
                        boolean algunoFlag = false;
                        Resultado resReduce = null;
                        int contReduce = 0;
                        Arreglo ARR = new Arreglo();
                        ArrayList<Resultado> params;
                        Nodo LTExp1 = raiz.get(0);
                        if (LTExp1.size() > 0) {
                            params = new ArrayList<>();
                            Nodo EXP = LTExp1.get(0);
                            if (EXP.size() > 0) {
                                Nodo subEXP = EXP.get(0);
                                if (subEXP.nombre.equalsIgnoreCase("id")) {

                                    arr = (Arreglo) actualResultado.valor;

                                    for (Object dato : arr.getDatos()) {
                                        Resultado resFilter;
                                        Resultado resTodos;
                                        Resultado resAlguno;

                                        Resultado res = (Resultado) dato;
                                        try {
                                            if (raiz.valor.toLowerCase().equals("reduce")) {
                                                //JOptionPane.showMessageDialog(null, "soy reduce");

                                                if (contReduce == 0) {
                                                    params.add(res);
                                                    if ((contReduce + 1) < arr.getDatos().size()) {
                                                        params.add((Resultado) arr.getDatos().get(contReduce + 1));
                                                    } else {
                                                        //Solo existe un elemento
                                                        res_nativas = res;
                                                        return;
                                                    }
                                                } else {
                                                    if ((contReduce + 1) < arr.getDatos().size()) {
                                                        params.add(resReduce);
                                                        params.add((Resultado) arr.getDatos().get(contReduce + 1));
                                                    } else {
                                                        res_nativas = resReduce;
                                                        return;
                                                    }
                                                }
                                            } else {
                                                params.add(res);
                                            }

                                            LlamadaMetodo llamada = new LlamadaMetodo(this.actual, 0, subEXP);
                                            Metodo metodo = llamada.ejecutarFuncion_Arreglo(params, subEXP.valor);
                                            //---------------------------------------------------
                                            if (metodo != null) {
                                                metodo.estadoRetorno = false;
                                                if (!esNulo(metodo.retorno)) {

                                                    if (raiz.valor.toLowerCase().equals("map")) {
                                                        ARR.AGREGAR(metodo.retorno);
                                                    } else if (raiz.valor.toLowerCase().equals("filtrar")) {
                                                        resFilter = metodo.retorno;
                                                        if (resFilter.tipo.equals("Boolean")) {
                                                            if ((Boolean) resFilter.valor) {
                                                                ARR.AGREGAR(res);
                                                            }
                                                        }
                                                    } else if (raiz.valor.toLowerCase().equals("buscar")) {
                                                        resFilter = metodo.retorno;
                                                        if (resFilter.tipo.equals("Boolean")) {
                                                            if ((Boolean) resFilter.valor) {
                                                                res_nativas = res;
                                                                return;
                                                            }
                                                        }
                                                    } else if (raiz.valor.toLowerCase().equals("todos")) {
                                                        resTodos = metodo.retorno;
                                                        if (resTodos.tipo.equals("Boolean")) {
                                                            if (!(Boolean) resTodos.valor) {
                                                                todosFlag = false;
                                                                break;
                                                            }
                                                        } else {
                                                            todosFlag = false;
                                                            break;
                                                        }
                                                    } else if (raiz.valor.toLowerCase().equals("alguno")) {
                                                        resAlguno = metodo.retorno;
                                                        if (resAlguno.tipo.equals("Boolean")) {
                                                            if ((Boolean) resAlguno.valor) {
                                                                algunoFlag = true;
                                                                break;
                                                            }
                                                        }
                                                    } else if (raiz.valor.toLowerCase().equals("reduce")) {
                                                        resReduce = metodo.retorno;
                                                    }
                                                }
                                            } else {
                                                if (llamada.res_nativas != null) {

                                                    if (raiz.valor.toLowerCase().equals("map")) {
                                                        ARR.AGREGAR(llamada.res_nativas);
                                                    } else if (raiz.valor.toLowerCase().equals("filtrar")) {
                                                        resFilter = llamada.res_nativas;
                                                        if (resFilter.tipo.equals("Boolean")) {
                                                            if ((Boolean) resFilter.valor) {
                                                                ARR.AGREGAR(res);
                                                            }
                                                        }
                                                    } else if (raiz.valor.toLowerCase().equals("buscar")) {
                                                        resFilter = llamada.res_nativas;
                                                        if (resFilter.tipo.equals("Boolean")) {
                                                            if ((Boolean) resFilter.valor) {
                                                                res_nativas = res;
                                                                return;
                                                            }
                                                        }
                                                    } else if (raiz.valor.toLowerCase().equals("todos")) {
                                                        resTodos = llamada.res_nativas;
                                                        if (resTodos.tipo.equals("Boolean")) {
                                                            if (!(Boolean) resTodos.valor) {
                                                                todosFlag = false;
                                                                break;
                                                            }
                                                        } else {
                                                            todosFlag = false;
                                                            break;
                                                        }
                                                    } else if (raiz.valor.toLowerCase().equals("alguno")) {
                                                        resAlguno = llamada.res_nativas;
                                                        if (resAlguno.tipo.equals("Boolean")) {
                                                            if ((Boolean) resAlguno.valor) {
                                                                algunoFlag = true;
                                                                break;
                                                            }
                                                        }
                                                    } else if (raiz.valor.toLowerCase().equals("reduce")) {
                                                        resReduce = llamada.res_nativas;
                                                    }

                                                    //ARR.AGREGAR(llamada.res_nativas);
                                                }
                                                if (llamada.ComponenteRes != null) {
                                                    if (raiz.valor.toLowerCase().equals("map")) {
                                                        ARR.AGREGAR(llamada.ComponenteRes);
                                                    } else if (raiz.valor.toLowerCase().equals("filtrar")) {
                                                        resFilter = llamada.ComponenteRes;
                                                        if (resFilter.tipo.equals("Boolean")) {
                                                            if ((Boolean) resFilter.valor) {
                                                                ARR.AGREGAR(res);
                                                            }
                                                        }
                                                    } else if (raiz.valor.toLowerCase().equals("buscar")) {
                                                        resFilter = llamada.ComponenteRes;
                                                        if (resFilter.tipo.equals("Boolean")) {
                                                            if ((Boolean) resFilter.valor) {
                                                                res_nativas = res;
                                                                return;
                                                            }
                                                        }
                                                    } else if (raiz.valor.toLowerCase().equals("todos")) {
                                                        resTodos = llamada.ComponenteRes;
                                                        if (resTodos.tipo.equals("Boolean")) {
                                                            if (!(Boolean) resTodos.valor) {
                                                                todosFlag = false;
                                                                break;
                                                            }
                                                        } else {
                                                            todosFlag = false;
                                                            break;
                                                        }
                                                    } else if (raiz.valor.toLowerCase().equals("alguno")) {
                                                        resAlguno = llamada.ComponenteRes;
                                                        if (resAlguno.tipo.equals("Boolean")) {
                                                            if ((Boolean) resAlguno.valor) {
                                                                algunoFlag = true;
                                                                break;
                                                            }
                                                        }
                                                    } else if (raiz.valor.toLowerCase().equals("reduce")) {
                                                        resReduce = llamada.ComponenteRes;
                                                    }
                                                    //ARR.AGREGAR(llamada.ComponenteRes);
                                                }
                                            }
                                            //---------------------------------------------------

                                            params.clear();
                                        } catch (Exception e) {
                                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al ejecutar funcion " + raiz.valor.toLowerCase() + " con:" + subEXP.valor + e.getMessage());
                                        }
                                        contReduce++;
                                    }
                                    //Fin Foreach
                                    if (raiz.valor.toLowerCase().equals("todos")) {
                                        if (todosFlag) {
                                            res_nativas = new Resultado("Boolean", true);
                                            return;
                                        } else {
                                            res_nativas = new Resultado("Boolean", false);
                                            return;
                                        }
                                    }

                                    if (raiz.valor.toLowerCase().equals("alguno")) {
                                        if (algunoFlag) {
                                            res_nativas = new Resultado("Boolean", true);
                                            return;
                                        } else {
                                            res_nativas = new Resultado("Boolean", false);
                                            return;
                                        }
                                    }

                                    ARR.SETDIM();
                                }
                            }
                        }
                        res_nativas = new Resultado("", ARR);

                        break;
                    case "ascendente":
                        proceder = false;
                        arr = (Arreglo) actualResultado.valor;
                        try {
                            arr.ascendente();
                        } catch (Exception e) {
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al ejecutar ascendente:" + e.getMessage());
                        }

                        break;
                    case "descendente":
                        proceder = false;
                        arr = (Arreglo) actualResultado.valor;
                        try {
                            arr.descendente();
                        } catch (Exception e) {
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al ejecutar descendente:" + e.getMessage());
                        }
                        break;
                    case "maximo":
                        proceder = false;
                        arr = (Arreglo) actualResultado.valor;
                        try {
                            res_nativas = arr.maximo();
                        } catch (Exception e) {
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al ejecutar maximo:" + e.getMessage());
                        }
                        break;
                    case "minimo":
                        proceder = false;
                        arr = (Arreglo) actualResultado.valor;
                        try {
                            res_nativas = arr.minimo();
                        } catch (Exception e) {
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al ejecutar minimo:" + e.getMessage());
                        }
                        break;
                    case "invertir":
                        proceder = false;
                        arr = (Arreglo) actualResultado.valor;
                        try {
                            arr.invertir();
                        } catch (Exception e) {
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Error al ejecutar invertir:" + e.getMessage());
                        }
                        break;
                    case "obtenerporetiqueta":
                        Arreglo arr1 = new Arreglo();
                        arr = (Arreglo) actualResultado.valor;

                        proceder = false;

                        for (Object dato : arr.getDatos()) {
                            try {
                                Resultado dato1 = (Resultado) dato;
                                if (esClase(dato1.valor)) {
                                    Clase clss = (Clase) dato1.valor;
                                    try {
                                        if (clss.raiz_GXML != null) {
                                            Nodo LTExp = raiz.get(0);
                                            if (LTExp.size() > 0) {

                                                Nodo EXP = LTExp.get(0);
                                                opL = new OperacionesARL(global, tabla, miTemplate);
                                                Resultado resultado = opL.ejecutar(EXP);

                                                if (!esNulo(resultado)) {
                                                    if (resultado.tipo.equals("String")) {
                                                        obtenerPorEtiqueta_NODO(clss.raiz_GXML, resultado.valor.toString(), arr1);
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        JOptionPane.showMessageDialog(null, "obtener por etiqueta error:" + e.getMessage());
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }

                        arr1.SETDIM();
                        ComponenteRes = new Resultado("", arr1);
                        JOptionPane.showMessageDialog(null, arr1.getDatos().size());
                        break;
                    case "obtenerporid":
                        Arreglo arrx1 = new Arreglo();
                        arr = (Arreglo) actualResultado.valor;

                        proceder = false;
                        for (Object dato : arr.getDatos()) {
                            try {
                                Resultado dato1 = (Resultado) dato;
                                if (esClase(dato1.valor)) {
                                    Clase clss = (Clase) dato1.valor;
                                    try {
                                        if (clss.raiz_GXML != null) {
                                            Nodo LTExp = raiz.get(0);
                                            if (LTExp.size() > 0) {

                                                Nodo EXP = LTExp.get(0);
                                                opL = new OperacionesARL(global, tabla, miTemplate);
                                                Resultado resultado = opL.ejecutar(EXP);

                                                if (!esNulo(resultado)) {
                                                    if (resultado.tipo.equals("String")) {
                                                        idxTMP = resultado.valor.toString();
                                                        obtenerPorID_NODO(clss.raiz_GXML, resultado.valor.toString(), arrx1, "id");
                                                        arrx1.SETDIM();
                                                        if (arrx1.getDatos().size() == 1) {
                                                            ComponenteRes = (Resultado) arrx1.getDatos().get(0);
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        JOptionPane.showMessageDialog(null, "obtener por id error:" + e.getMessage());
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        if (arrx1.getDatos().isEmpty()) {
                            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "obtenerporId No existe un componente con id=" + idxTMP);
                        }
                        break;
                    case "obtenerpornombre":
                        arr = (Arreglo) actualResultado.valor;

                        proceder = false;
                        for (Object dato : arr.getDatos()) {
                            try {
                                Resultado dato1 = (Resultado) dato;
                                if (esClase(dato1.valor)) {
                                    Clase clss = (Clase) dato1.valor;

                                    try {
                                        if (clss.raiz_GXML != null) {

                                            Nodo LTExp = raiz.get(0);

                                            if (LTExp.size() >= 2) {

                                                Nodo EXP1 = LTExp.get(0);
                                                Nodo EXP2 = LTExp.get(1);
                                                opL = new OperacionesARL(global, tabla, miTemplate);
                                                Resultado nombreHijo = opL.ejecutar(EXP1);
                                                Resultado idPadre = opL.ejecutar(EXP2);

                                                if (!esNulo(idPadre) && !esNulo(nombreHijo)) {
                                                    if (idPadre.tipo.equals("String")) {

                                                        Simbolo sim = clss.tabla.getSimbolo("id", clss);
                                                        if (sim != null) {
                                                            try {
                                                                if (sim.valor.toString().equalsIgnoreCase(idPadre.valor.toString())) {

                                                                    if (clss.raiz_GXML != null) {
                                                                        Arreglo nuevoArr = new Arreglo();
                                                                        obtenerPorID_NODO(clss.raiz_GXML, nombreHijo.valor.toString(), nuevoArr, "nombre");
                                                                        nuevoArr.SETDIM();
                                                                        if (nuevoArr.getDatos().size() > 0) {
                                                                            ComponenteRes = (Resultado) nuevoArr.getDatos().get(0);
                                                                        } else {
                                                                            Template.reporteError_CJS.agregar("Semantico", EXP1.linea, EXP1.columna, "obtenerporNombre No existe un componente con nombre : " + nombreHijo.valor.toString());
                                                                        }
                                                                        break;
                                                                    }
                                                                }
                                                            } catch (Exception e) {
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
                                                Template.reporteError_CJS.agregar("Semantico", LTExp.linea, LTExp.columna, "obtenerporNombre necesita al menos dos parametros ");
                                            }

                                        }
                                    } catch (Exception e) {
                                        JOptionPane.showMessageDialog(null, "obtener por id error:" + e.getMessage());
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }

                        break;
                }
            } else if (esClase(actualResultado.valor)) {
                //Creacion de componentes GRAFICOS
                switch (raiz.valor.toLowerCase()) {
                    case "crearcontenedor":
                        try {
                            crearContenedor();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "crearContenedor error:" + e.getMessage());
                        }
                        break;
                    case "creartexto":
                        try {
                            crearTexto();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "crearTexto error:" + e.getMessage());
                        }
                        break;
                    case "crearcajatexto":
                        try {
                            crearCajaTexto();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "crearCajaTexto error:" + e.getMessage());
                        }
                        break;
                    case "crearareatexto":
                        try {
                            crearAreaTexto();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "crearAreaTexto error:" + e.getMessage());
                        }
                        break;
                    case "crearcontrolnumerico":
                        try {
                            crearControlNumerico();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "crearControlNumerico error:" + e.getMessage());
                        }
                        break;
                    case "crearboton":
                        try {
                            crearBoton();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "crearBoton error:" + e.getMessage());
                        }
                        break;
                    case "crearreproductor":
                    case "crearaudio":
                    case "crearvideo":
                    case "crearimagen":
                        try {
                            crearReproductor();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "crearReproductor error:" + e.getMessage());
                        }
                        break;
                    case "creardesplegable":
                        try {
                            crearDesplegable();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "crearDesplegable error:" + e.getMessage());
                        }
                        break;

                    case "alclic":
                        proceder = false;

                        try {
                            Clase clas = (Clase) actualResultado.valor;
                            if (clas.Componente != null) {
                                Nodo LTExp = raiz.get(0);
                                switch (clas.nombre.toLowerCase()) {
                                    case "boton":
                                        ((BotonGenerico) clas.Componente).setAlClick(LTExp.get(0));
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "alclic error:" + e.getMessage());
                        }
                        break;
                    case "alcargar":
                        try {
                            Clase clas = (Clase) actualResultado.valor;
                            if (clas.Componente != null) {
                                proceder = false;
                                Nodo LTExp = raiz.get(0);
                                if (LTExp.size() > 0) {
                                    switch (clas.nombre.toLowerCase()) {
                                        case "ventana":
                                            ((VentanaGenerica) clas.Componente).setAlCargar(LTExp.get(0));
                                            break;
                                    }
                                } else {
                                    if (clas.nombre.equalsIgnoreCase("ventana")) {
                                        VentanaGenerica vt = ((VentanaGenerica) clas.Componente);

                                        if (vt.getPreferredSize().width > 50 && vt.getPreferredSize().height > 50) {
                                            vt.setBounds(100, 10, vt.getPreferredSize().width, vt.getPreferredSize().height);
                                        } else {
                                            int maxWidth = 0;
                                            int maxHeigth = 0;
                                            int x = 0;
                                            int y = 0;
                                            for (Component component : vt.getContentPane().getComponents()) {
                                                if (component.getLocation().x > x) {
                                                    x = component.getLocation().x;
                                                    maxWidth = component.getPreferredSize().width + x;
                                                }

                                                if (component.getLocation().y > y) {
                                                    y = component.getLocation().y;
                                                    maxHeigth = component.getPreferredSize().height + y;
                                                }
                                            }
                                            vt.setBounds(100, 10, maxWidth+50, maxHeigth+50);
                                        }
                                        /*for (Component component : vt.getContentPane().getComponents()) {
                                            System.out.println(component.getClass().getSimpleName());
                                        }*/

                                        vt.setLocationRelativeTo(null);
                                        vt.setVisible(true);
                                        VT_ACTUAL = vt;

                                    }
                                }

                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "alcargar error:" + e.getMessage());
                        }
                        break;
                    case "alcerrar":
                        try {
                            Clase clas = (Clase) actualResultado.valor;
                            if (clas.Componente != null) {
                                proceder = false;
                                Nodo LTExp = raiz.get(0);
                                if (LTExp.size() > 0) {
                                    switch (clas.nombre.toLowerCase()) {
                                        case "ventana":
                                            ((VentanaGenerica) clas.Componente).setAlCerrar(LTExp.get(0));
                                            break;
                                    }
                                } else {
                                    if (clas.nombre.equalsIgnoreCase("ventana")) {
                                        ((VentanaGenerica) clas.Componente).dispose();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "alcerrar error:" + e.getMessage());
                        }
                        break;

                    case "obtenerporetiqueta":
                        Arreglo arr1 = new Arreglo();
                        proceder = false;
                        try {
                            Clase clas = (Clase) actualResultado.valor;
                            if (clas.raiz_GXML != null) {
                                Nodo LTExp = raiz.get(0);
                                if (LTExp.size() > 0) {

                                    Nodo EXP = LTExp.get(0);
                                    opL = new OperacionesARL(global, tabla, miTemplate);
                                    Resultado resultado = opL.ejecutar(EXP);

                                    if (!esNulo(resultado)) {
                                        if (resultado.tipo.equals("String")) {
                                            obtenerPorEtiqueta_NODO(clas.raiz_GXML, resultado.valor.toString(), arr1);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "obtener por etiqueta error:" + e.getMessage());
                        }
                        arr1.SETDIM();
                        ComponenteRes = new Resultado("", arr1);
                        break;

                    case "obtenerporid":
                        Arreglo arr2 = new Arreglo();
                        proceder = false;
                        try {
                            Clase clas = (Clase) actualResultado.valor;
                            if (clas.raiz_GXML != null) {
                                Nodo LTExp = raiz.get(0);
                                if (LTExp.size() > 0) {

                                    Nodo EXP = LTExp.get(0);
                                    opL = new OperacionesARL(global, tabla, miTemplate);
                                    Resultado resultado = opL.ejecutar(EXP);

                                    if (!esNulo(resultado)) {
                                        if (resultado.tipo.equals("String")) {
                                            obtenerPorID_NODO(clas.raiz_GXML, resultado.valor.toString(), arr2, "id");
                                            arr2.SETDIM();
                                            if (arr2.getDatos().size() == 1) {
                                                ComponenteRes = (Resultado) arr2.getDatos().get(0);
                                            } else {
                                                Template.reporteError_CJS.agregar("Semantico", EXP.linea, EXP.columna, "obtenerporId No existe un componente con id=" + resultado.valor.toString());
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "obtener por etiqueta error:" + e.getMessage());
                        }

                        break;
                    case "obtenerpornombre":
                        Arreglo arr3 = new Arreglo();
                        proceder = false;
                        try {
                            Clase clas = (Clase) actualResultado.valor;
                            if (clas.raiz_GXML != null) {
                                Nodo LTExp = raiz.get(0);
                                if (LTExp.size() >= 2) {

                                    Nodo EXP1 = LTExp.get(0);
                                    Nodo EXP2 = LTExp.get(1);
                                    opL = new OperacionesARL(global, tabla, miTemplate);
                                    Resultado nombreHijo = opL.ejecutar(EXP1);
                                    Resultado idPadre = opL.ejecutar(EXP2);

                                    if (!esNulo(idPadre) && !esNulo(nombreHijo)) {
                                        if (idPadre.tipo.equals("String")) {
                                            obtenerPorID_NODO(clas.raiz_GXML, idPadre.valor.toString(), arr3, "id");
                                            arr3.SETDIM();

                                            if (arr3.getDatos().size() > 0) {
                                                try {
                                                    Clase clas2 = (Clase) ((Resultado) arr3.getDatos().get(0)).valor;
                                                    if (clas2.raiz_GXML != null) {
                                                        Arreglo nuevoArr = new Arreglo();
                                                        obtenerPorID_NODO(clas2.raiz_GXML, nombreHijo.valor.toString(), nuevoArr, "nombre");
                                                        nuevoArr.SETDIM();
                                                        if (nuevoArr.getDatos().size() > 0) {
                                                            ComponenteRes = (Resultado) nuevoArr.getDatos().get(0);
                                                        } else {
                                                            Template.reporteError_CJS.agregar("Semantico", EXP1.linea, EXP1.columna, "obtenerporNombre No existe un componente con nombre : " + nombreHijo.valor.toString());
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                }
                                            } else {
                                                Template.reporteError_CJS.agregar("Semantico", EXP2.linea, EXP2.columna, "obtenerporNombre No existe un componente con id : " + idPadre.valor.toString());
                                            }
                                        }
                                    }
                                } else {
                                    Template.reporteError_CJS.agregar("Semantico", LTExp.linea, LTExp.columna, "obtenerporNombre necesita al menos dos parametros ");
                                }
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "obtener por nombre error:" + e.getMessage());
                        }
                        break;
                }
            }
        } else {
            //Creacion de componentes GRAFICOS
            Nodo EXP;
            Resultado resultado;
            String cad_ruta;
            String rutaBuena = "";
            switch (raiz.valor.toLowerCase()) {
                case "crearventana":
                    try {
                        crearVentana();
                    } catch (Exception e) {
                        Template.reporteError_CJS.agregar("Ejecucion", raiz.linea, raiz.columna, "crearVentana Error:"+e.getMessage());
                    }

                    break;
                case "leergxml":
                    proceder = false;
                    Arreglo arr = new Arreglo();
                    Nodo LTExp = raiz.get(0);
                    if (LTExp.size() > 0) {
                        EXP = LTExp.get(0);
                        opL = new OperacionesARL(global, tabla, miTemplate);
                        resultado = opL.ejecutar(EXP);
                        if (!esNulo(resultado)) {
                            if (resultado.tipo.equals("String")) {
                                cad_ruta = (String) resultado.valor;
                                rutaBuena = existeArchivo(cad_ruta);
                                if (!rutaBuena.equals("")) {
                                    File file = new File(rutaBuena);
                                    String nombre_ = file.getName();
                                    String ext = Arrays.stream(nombre_.split("\\.")).reduce((a, b) -> b).orElse(null);
                                    if (ext.equals("gxml")) {
                                        Nodo r_GXML = null;
                                        try {
                                            LexGxml lex = new LexGxml(new FileReader(rutaBuena));
                                            SintacticoGxml sin = new SintacticoGxml(lex);
                                            try {
                                                sin.parse();
                                                r_GXML = sin.getRoot();
                                            } catch (Exception e) {
                                                Template.reporteError_CJS.agregar("Semantico", EXP.linea, EXP.columna, "Erro al compilar archivo " + nombre_);
                                            }
                                        } catch (FileNotFoundException ex) {
                                            Logger.getLogger(LlamadaMetodo.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        if (r_GXML != null) {
                                            //mandar a realizar el arreglo de ventanas
                                            crearArrayDesdeArchivo(r_GXML, arr);
                                            arr.raiz_GXML = r_GXML;
                                        }
                                    } else {
                                        Template.reporteError_CJS.agregar("Semantico", EXP.linea, EXP.columna, "El parametro de funcion leerGxml tiene extension ." + ext + " solo se soporta .gxml");
                                    }
                                }
                            } else {
                                Template.reporteError_CJS.agregar("Semantico", EXP.linea, EXP.columna, "El parametro de funcion leerGxml tiene que ser un string");
                            }
                        }
                    } else {
                        //es para guardar en gdato
                    }
                    arr.SETDIM();
                    res_nativas = new Resultado("", arr);
                    JOptionPane.showMessageDialog(null, "termine perro");
                    break;
                case "creararraydesdearchivo":
                    proceder = false;
                    Arreglo arrX = new Arreglo();
                    Nodo LTExpC = raiz.get(0);
                    if (LTExpC.size() > 0) {
                        EXP = LTExpC.get(0);
                        opL = new OperacionesARL(global, tabla, miTemplate);
                        resultado = opL.ejecutar(EXP);
                        if (!esNulo(resultado)) {
                            if (resultado.tipo.equals("String")) {
                                cad_ruta = (String) resultado.valor;
                                rutaBuena = existeArchivo(cad_ruta);
                                if (!rutaBuena.equals("")) {
                                    File file = new File(rutaBuena);
                                    String nombre_ = file.getName();
                                    String ext = Arrays.stream(nombre_.split("\\.")).reduce((a, b) -> b).orElse(null);
                                    if (ext.equals("gdato")) {
                                        Nodo r_GXML = null;
                                        try {
                                            
                                            LexGdato lex = new LexGdato(new FileReader(rutaBuena));
                                            SintacticoGdato sin = new SintacticoGdato(lex);
                                            try {
                                                sin.parse();
                                                r_GXML = sin.getRoot();
                                            } catch (Exception e) {
                                                Template.reporteError_CJS.agregar("Semantico", EXP.linea, EXP.columna, "Erro al compilar archivo " + nombre_);
                                            }
                                        } catch (FileNotFoundException ex) {
                                            Logger.getLogger(LlamadaMetodo.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        if (r_GXML != null) {
                                            //mandar a realizar el arreglo de ventanas
                                            crearArrayDesdeArchivoGdato(r_GXML, arrX);
                                            arrX.raiz_GXML = r_GXML;
                                        }
                                    } else {
                                        Template.reporteError_CJS.agregar("Semantico", EXP.linea, EXP.columna, "El parametro de funcion leerGxml tiene extension ." + ext + " solo se soporta .gxml");
                                    }
                                }
                            } else {
                                Template.reporteError_CJS.agregar("Semantico", EXP.linea, EXP.columna, "El parametro de funcion leerGxml tiene que ser un string");
                            }
                        }
                    } else {
                        //es para guardar en gdato
                    }
                    arrX.SETDIM();
                    res_nativas = new Resultado("", arrX);
                    JOptionPane.showMessageDialog(null, "termine perro");
                    break;    
            }
        }
    }

    /*  ====    ====            CREAR VENTANA            ====    ====    */
    public void crearVentana() {
        String[] stilos = {"color", "alto", "ancho", "id"};
        proceder = false;
        ArrayList<Resultado> parametros = getParametros(raiz);

        VentanaGenerica nuevaVentana = new VentanaGenerica(raiz);
        nuevaVentana.setearCore(global, tabla, miTemplate);
        /*----------------###############################---------------------*/
        Resultado color = null, alto = null, ancho = null, id = null;
        try {
            color = parametros.get(0);
        } catch (Exception e) {
            color = new Resultado("$nulo", "nulo");
        }

        try {
            alto = parametros.get(1);
        } catch (Exception e) {
            alto = new Resultado("$nulo", "nulo");
        }

        try {
            ancho = parametros.get(2);
        } catch (Exception e) {
            ancho = new Resultado("$nulo", "nulo");
        }

        try {
            id = parametros.get(3);
        } catch (Exception e) {
        }
        /*----------------###############################---------------------*/

        //Se crea el Arbol que corresponde a cntObj
        Nodo cntobj = crearNodoObj(raiz, stilos, 3);

        opL = new OperacionesARL(global, tabla, miTemplate);
        Resultado resultado = opL.ejecutar(cntobj);

        if (!esNulo(resultado)) {
            ComponenteRes = resultado;
            if (esClase(resultado.valor)) {
                Clase clase = (Clase) resultado.valor;
                clase.Componente = nuevaVentana;
                clase.nombre = "Ventana";
                clase.ejecutar(miTemplate);
                clase.Inicializada = true;

                nuevaVentana.setearClasse(clase);
            }

            /*---------------------------------------------------------------------*/
            if (!esNulo(id)) {
                if(!id.tipo.equals("$nulo"))
                {
                    nuevaVentana.setId(id.valor.toString());
                }else {
                    nuevaVentana.setId("");
                }
            } 

            if (!esNulo(color)) {
                nuevaVentana.setColor(color.valor.toString());
            }

            if (!esNulo(alto)) {
                nuevaVentana.setAlto(alto.valor.toString());
            }

            if (!esNulo(ancho)) {
                nuevaVentana.setAncho(ancho.valor.toString());
            }
            /*---------------------------------------------------------------------*/

            listaVentanas.add(nuevaVentana);//Si se usara para cuando se haga la referencia en el boton
        }

    }

    public void crearContenedor() {
        String[] stilos = {"alto", "ancho", "color", "borde", "x", "y"};
        proceder = false;
        ArrayList<Resultado> parametros = getParametros(raiz);

        PanelGenerico nuevoPanel = new PanelGenerico(raiz);

        /*----------------###############################---------------------*/
        Resultado alto = null, ancho = null, color = null, borde = null, x = null, y = null;
        try {
            alto = parametros.get(0);
        } catch (Exception e) {
            alto = new Resultado("$nulo", "nulo");
        }

        try {
            ancho = parametros.get(1);
        } catch (Exception e) {
            ancho = new Resultado("$nulo", "nulo");
        }

        try {
            color = parametros.get(2);
        } catch (Exception e) {
            color = new Resultado("$nulo", "nulo");
        }

        try {
            borde = parametros.get(3);
        } catch (Exception e) {
            borde = new Resultado("$nulo", "nulo");
        }

        try {
            x = parametros.get(4);
        } catch (Exception e) {
            x = new Resultado("$nulo", "nulo");
        }

        try {
            y = parametros.get(5);
        } catch (Exception e) {
            y = new Resultado("$nulo", "nulo");
        }


        /*----------------###############################---------------------*/
        //Se crea el Arbol que corresponde a cntObj
        Nodo cntobj = crearNodoObj(raiz, stilos, 5);

        opL = new OperacionesARL(global, tabla, miTemplate);
        Resultado resultado = opL.ejecutar(cntobj);

        if (!esNulo(resultado)) {
            ComponenteRes = resultado;
            if (esClase(resultado.valor)) {
                Clase clase = (Clase) resultado.valor;
                clase.Componente = nuevoPanel;
                clase.nombre = "Panel";
                clase.ejecutar(miTemplate);
                clase.Inicializada = true;

                nuevoPanel.setearClasse(clase);
            }

            /*---------------------------------------------------------------------*/
            if (!esNulo(alto)) {
                nuevoPanel.setAlto(alto.valor.toString());
            }

            if (!esNulo(ancho)) {
                nuevoPanel.setAncho(ancho.valor.toString());
            }

            if (!esNulo(color)) {
                nuevoPanel.setColor(color.valor.toString());
            }

            if (!esNulo(borde)) {
                nuevoPanel.setBorde(borde.valor.toString());
            }

            if (!esNulo(x)) {
                nuevoPanel.setX(x.valor.toString());
            }

            if (!esNulo(y)) {
                nuevoPanel.setY(y.valor.toString());
            }
            /*---------------------------------------------------------------------*/

            if (!esNulo(actualResultado)) {
                if (esClase(actualResultado.valor)) {
                    Clase clase = (Clase) actualResultado.valor;
                    if (clase.Componente != null) {

                        switch (clase.nombre.toLowerCase()) {
                            case "ventana":
                                nuevoPanel.setLayout(null);
                                nuevoPanel.setBounds(nuevoPanel.getLocation().x, nuevoPanel.getLocation().y, nuevoPanel.getPreferredSize().width, nuevoPanel.getPreferredSize().height);
                                ((VentanaGenerica) clase.Componente).getContentPane().add(nuevoPanel);

                                break;
                            case "panel":
                                nuevoPanel.setLayout(null);
                                nuevoPanel.setBounds(nuevoPanel.getLocation().x, nuevoPanel.getLocation().y, nuevoPanel.getPreferredSize().width, nuevoPanel.getPreferredSize().height);
                                ((PanelGenerico) clase.Componente).add(nuevoPanel);
                                break;
                        }

                    }
                }
            }
        }

    }

    public void crearBoton() {
        //(Fuente, Tamaño, Color, X, Y,Referencia, valor, Alto, Ancho) la referencia es una llamada a metodo

        String[] stilos = {"fuente", "tam", "color", "x", "y", "referencia", "valor", "alto", "ancho"};
        proceder = false;
        ArrayList<Resultado> parametros = getParametrosConRefencia(raiz, 5);

        BotonGenerico nuevoBoton = new BotonGenerico(raiz);
        /*----------------###############################---------------------*/
        Resultado fuente = null, tam = null, color = null, x = null, y = null, referencia = null, valor = null, alto = null, ancho = null;
        try {
            fuente = parametros.get(0);
        } catch (Exception e) {
            fuente = new Resultado("$nulo", "nulo");
        }

        try {
            tam = parametros.get(1);
        } catch (Exception e) {
            tam = new Resultado("$nulo", "nulo");
        }

        try {
            color = parametros.get(2);
        } catch (Exception e) {
            color = new Resultado("$nulo", "nulo");
        }

        try {
            x = parametros.get(3);
        } catch (Exception e) {
            x = new Resultado("$nulo", "nulo");
        }

        try {
            y = parametros.get(4);
        } catch (Exception e) {
            y = new Resultado("$nulo", "nulo");
        }

        try {
            referencia = parametros.get(5);
        } catch (Exception e) {
            referencia = new Resultado("$nulo", "nulo");
        }

        try {
            valor = parametros.get(6);
        } catch (Exception e) {
            valor = new Resultado("$nulo", "nulo");
        }

        try {
            alto = parametros.get(7);
        } catch (Exception e) {
        }

        try {
            ancho = parametros.get(8);
        } catch (Exception e) {
        }

        /*----------------###############################---------------------*/
        //Se crea el Arbol que corresponde a cntObj
        Nodo cntobj = crearNodoObj(raiz, stilos, 5);

        opL = new OperacionesARL(global, tabla, miTemplate);
        Resultado resultado = opL.ejecutar(cntobj);

        //======================================================================
        if (!esNulo(resultado)) {
            ComponenteRes = resultado;
            if (esClase(resultado.valor)) {
                Clase clase = (Clase) resultado.valor;
                clase.Componente = nuevoBoton;
                clase.nombre = "Boton";
                clase.ejecutar(miTemplate);
                clase.Inicializada = true;

                nuevoBoton.setearClasse(clase);
            }

            /*  al boton le voy a setear la tabla,global y template actual*/
            nuevoBoton.setearCore(global, tabla, miTemplate);
            /*---------------------------------------------------------------------*/
            //(Fuente, Tamaño, Color, X, Y,Referencia, valor, Alto, Ancho) la referencia es una llamada a metodo
            if (!esNulo(fuente)) {
                nuevoBoton.setFuente(fuente.valor.toString());
            }

            if (!esNulo(tam)) {
                nuevoBoton.setTam(tam.valor.toString());
            }

            if (!esNulo(color)) {
                nuevoBoton.setColor(color.valor.toString());
            }

            if (!esNulo(x)) {
                nuevoBoton.setX(x.valor.toString());
            }

            if (!esNulo(y)) {
                nuevoBoton.setY(y.valor.toString());
            }

            if (!esNulo(referencia)) {
                nuevoBoton.setReferencia(referencia.valor);
            }

            if (!esNulo(valor)) {
                nuevoBoton.setTexto(valor.valor.toString());
            }

            if (!esNulo(alto)) {
                nuevoBoton.setAlto(alto.valor.toString());
            }

            if (!esNulo(ancho)) {
                nuevoBoton.setAncho(ancho.valor.toString());
            }
            /*---------------------------------------------------------------------*/

            if (!esNulo(actualResultado)) {
                if (esClase(actualResultado.valor)) {
                    Clase clase = (Clase) actualResultado.valor;
                    if (clase.Componente != null) {

                        switch (clase.nombre.toLowerCase()) {
                            case "ventana":
                                nuevoBoton.setLayout(null);
                                nuevoBoton.setBounds(nuevoBoton.getLocation().x, nuevoBoton.getLocation().y, nuevoBoton.getPreferredSize().width, nuevoBoton.getPreferredSize().height);
                                ((VentanaGenerica) clase.Componente).getContentPane().add(nuevoBoton);

                                break;
                            case "panel":
                                nuevoBoton.setLayout(null);
                                nuevoBoton.setBounds(nuevoBoton.getLocation().x, nuevoBoton.getLocation().y, nuevoBoton.getPreferredSize().width, nuevoBoton.getPreferredSize().height);
                                ((PanelGenerico) clase.Componente).add(nuevoBoton);
                                break;
                        }

                    }
                }
            }
        }
        //======================================================================
    }

    public void crearTexto() {
        //Fuente, Tamaño, Color, X, Y, Negrilla, Cursiva, valo
        String[] stilos = {"fuente", "tam", "color", "x", "y", "negrilla", "cursiva", "valor"};

        proceder = false;
        ArrayList<Resultado> parametros = getParametros(raiz);

        TextoGenerico nuevaTexto = new TextoGenerico(raiz);

        /*----------------###############################---------------------*/
        Resultado fuente = null, tam = null, color = null, x = null, y = null, negrilla = null, cursiva = null, valor = null;

        try {
            fuente = parametros.get(0);
        } catch (Exception e) {
            fuente = new Resultado("$nulo", "nulo");
        }

        try {
            tam = parametros.get(1);
        } catch (Exception e) {
            tam = new Resultado("$nulo", "nulo");
        }

        try {
            color = parametros.get(2);
        } catch (Exception e) {
            color = new Resultado("$nulo", "nulo");
        }

        try {
            x = parametros.get(3);
        } catch (Exception e) {
            x = new Resultado("$nulo", "nulo");
        }

        try {
            y = parametros.get(4);
        } catch (Exception e) {
            y = new Resultado("$nulo", "nulo");
        }

        try {
            negrilla = parametros.get(5);
        } catch (Exception e) {
            negrilla = new Resultado("$nulo", "nulo");
        }

        try {
            cursiva = parametros.get(6);
        } catch (Exception e) {
            cursiva = new Resultado("$nulo", "nulo");
        }

        try {
            valor = parametros.get(7);
        } catch (Exception e) {
            valor = new Resultado("$nulo", "nulo");
        }

        /*----------------###############################---------------------*/
        //Se crea el Arbol que corresponde a cntObj
        Nodo cntobj = crearNodoObj(raiz, stilos, 7);

        opL = new OperacionesARL(global, tabla, miTemplate);
        Resultado resultado = opL.ejecutar(cntobj);

        if (!esNulo(resultado)) {
            ComponenteRes = resultado;
            if (esClase(resultado.valor)) {
                Clase clase = (Clase) resultado.valor;
                clase.Componente = nuevaTexto;
                clase.nombre = "Texto";
                clase.ejecutar(miTemplate);
                clase.Inicializada = true;

                nuevaTexto.setearClasse(clase);
            }

            /*---------------------------------------------------------------------*/
            if (!esNulo(fuente)) {
                nuevaTexto.setFuente(fuente.valor.toString());
            }

            if (!esNulo(tam)) {
                nuevaTexto.setTam(tam.valor.toString());
            }

            if (!esNulo(color)) {
                nuevaTexto.setColor(color.valor.toString());
            }

            if (!esNulo(x)) {
                nuevaTexto.setX(x.valor.toString());
            }

            if (!esNulo(y)) {
                nuevaTexto.setY(y.valor.toString());
            }

            if (!esNulo(negrilla)) {
                nuevaTexto.setNegrilla(negrilla.valor.toString());
            }

            if (!esNulo(cursiva)) {
                nuevaTexto.setCurvisa(cursiva.valor.toString());
            }

            if (!esNulo(valor)) {
                if(!valor.tipo.equals("$nulo"))
                {
                    nuevaTexto.setTexto(valor.valor.toString());
                }
            }
            /*---------------------------------------------------------------------*/

            if (!esNulo(actualResultado)) {
                if (esClase(actualResultado.valor)) {
                    Clase clase = (Clase) actualResultado.valor;
                    if (clase.Componente != null) {

                        switch (clase.nombre.toLowerCase()) {
                            case "ventana":
                                nuevaTexto.setBounds(nuevaTexto.getLocation().x, nuevaTexto.getLocation().y, nuevaTexto.getPreferredSize().width, nuevaTexto.getPreferredSize().height);
                                ((VentanaGenerica) clase.Componente).getContentPane().add(nuevaTexto);

                                break;
                            case "panel":
                                nuevaTexto.setBounds(nuevaTexto.getLocation().x, nuevaTexto.getLocation().y, nuevaTexto.getPreferredSize().width, nuevaTexto.getPreferredSize().height);
                                ((PanelGenerico) clase.Componente).add(nuevaTexto);
                                break;
                            case "boton":
                                BotonGenerico btn=((BotonGenerico) clase.Componente);
                                btn.setFont(nuevaTexto.getFont());
                                btn.setForeground(nuevaTexto.getForeground());
                                btn.setText(nuevaTexto.getText());
                                btn.repaint();
                                break;
                                
                        }

                    }
                }
            }

        }
    }

    public void crearReproductor() {
        //Ruta, X, Y, Auto-reproductor, Alto, Ancho
        String[] stilos = {"ruta", "x", "y", "auto_reproductor", "alto", "ancho"};

        proceder = false;
        ArrayList<Resultado> parametros = getParametros(raiz);

        Reproductor nuevoReproductor = new Reproductor(raiz);

        /*----------------###############################---------------------*/
        Resultado ruta = null, x = null, y = null, auto_reproductor = null, alto = null, ancho = null;

        try {
            ruta = parametros.get(0);
        } catch (Exception e) {
            ruta = new Resultado("$nulo", "nulo");
        }

        try {
            x = parametros.get(1);
        } catch (Exception e) {
            x = new Resultado("$nulo", "nulo");
        }

        try {
            y = parametros.get(2);
        } catch (Exception e) {
            y = new Resultado("$nulo", "nulo");
        }

        try {
            auto_reproductor = parametros.get(3);
        } catch (Exception e) {
            auto_reproductor = new Resultado("$nulo", "nulo");
        }

        try {
            alto = parametros.get(4);
        } catch (Exception e) {
            alto = new Resultado("$nulo", "nulo");
        }

        try {
            ancho = parametros.get(5);
        } catch (Exception e) {
            ancho = new Resultado("$nulo", "nulo");
        }

        /*----------------###############################---------------------*/
        //Se crea el Arbol que corresponde a cntObj
        Nodo cntobj = crearNodoObj(raiz, stilos, 5);

        opL = new OperacionesARL(global, tabla, miTemplate);
        Resultado resultado = opL.ejecutar(cntobj);

        if (!esNulo(resultado)) {
            ComponenteRes = resultado;
            if (esClase(resultado.valor)) {
                Clase clase = (Clase) resultado.valor;
                clase.Componente = nuevoReproductor;
                clase.nombre = "Reproductor";
                clase.ejecutar(miTemplate);
                clase.Inicializada = true;

                nuevoReproductor.setearClasse(clase);
            }

            /*---------------------------------------------------------------------*/
            if (!esNulo(ruta)) {
                if(!ruta.tipo.equals("$nulo"))
                {
                    nuevoReproductor.setRuta(ruta.valor.toString());
                }
            }

            if (!esNulo(x)) {
                nuevoReproductor.setX(x.valor.toString());
            }

            if (!esNulo(y)) {
                nuevoReproductor.setY(y.valor.toString());
            }

            if (!esNulo(auto_reproductor)) {
                nuevoReproductor.setAutoReproduccion(auto_reproductor.valor.toString());
            }

            if (!esNulo(alto)) {
                nuevoReproductor.setAlto(alto.valor.toString());
            }

            if (!esNulo(ancho)) {
                nuevoReproductor.setAncho(ancho.valor.toString());
            }

            /*---------------------------------------------------------------------*/
            if (!esNulo(actualResultado)) {
                if (esClase(actualResultado.valor)) {
                    Clase clase = (Clase) actualResultado.valor;
                    if (clase.Componente != null) {

                        switch (clase.nombre.toLowerCase()) {
                            case "ventana":
                                nuevoReproductor.setBounds(nuevoReproductor.getLocation().x, nuevoReproductor.getLocation().y, nuevoReproductor.getPreferredSize().width, nuevoReproductor.getPreferredSize().height);
                                ((VentanaGenerica) clase.Componente).getContentPane().add(nuevoReproductor);

                                break;
                            case "panel":
                                nuevoReproductor.setBounds(nuevoReproductor.getLocation().x, nuevoReproductor.getLocation().y, nuevoReproductor.getPreferredSize().width, nuevoReproductor.getPreferredSize().height);
                                ((PanelGenerico) clase.Componente).add(nuevoReproductor);
                                break;
                        }

                    }
                }
            }

        }
    }

    public void crearDesplegable() {
        //(Alto, Ancho, lista, X, Y, Defecto, nombre)
        String[] stilos = {"alto", "ancho", "lista", "x", "y", "defecto", "nombre"};

        proceder = false;
        ArrayList<Resultado> parametros = getParametros(raiz);

        DesplegableGenerico nuevoDesple = new DesplegableGenerico(raiz);
        /*----------------###############################---------------------*/
        Resultado alto = null, ancho = null, lista = null, x = null, y = null, defecto = null, nombre = null;

        try {
            alto = parametros.get(0);
        } catch (Exception e) {
            alto = new Resultado("$nulo", "nulo");
        }

        try {
            ancho = parametros.get(1);
        } catch (Exception e) {
            ancho = new Resultado("$nulo", "nulo");
        }

        try {
            lista = parametros.get(2);
        } catch (Exception e) {
            lista = new Resultado("$nulo", "nulo");
        }

        try {
            x = parametros.get(3);
        } catch (Exception e) {
            x = new Resultado("$nulo", "nulo");
        }

        try {
            y = parametros.get(4);
        } catch (Exception e) {
            y = new Resultado("$nulo", "nulo");
        }

        try {
            defecto = parametros.get(5);
        } catch (Exception e) {
            defecto = new Resultado("$nulo", "nulo");
        }

        try {
            nombre = parametros.get(6);
        } catch (Exception e) {
            nombre = new Resultado("$nulo", "nulo");
        }

        /*----------------###############################---------------------*/
        //Se crea el Arbol que corresponde a cntObj
        Nodo cntobj = crearNodoObj(raiz, stilos, 6);

        opL = new OperacionesARL(global, tabla, miTemplate);
        Resultado resultado = opL.ejecutar(cntobj);

        if (!esNulo(resultado)) {
            ComponenteRes = resultado;
            if (esClase(resultado.valor)) {
                Clase clase = (Clase) resultado.valor;
                clase.Componente = nuevoDesple;
                clase.nombre = "Desplegable";
                clase.ejecutar(miTemplate);
                clase.Inicializada = true;

                nuevoDesple.setearClasse(clase);
            }

            /*---------------------------------------------------------------------*/
            if (!esNulo(defecto)) {
                if(!defecto.tipo.equals("$nulo"))
                {
                    nuevoDesple.setDefecto(defecto.valor.toString());
                }
                
            }

            if (!esNulo(nombre)) {
                if(!nombre.tipo.equals("$nulo"))
                {
                    nuevoDesple.setId(nombre.valor.toString());
                }else
                {
                    nuevoDesple.setId("");
                }
            }

            if (!esNulo(x)) {
                nuevoDesple.setX(x.valor.toString());
            }

            if (!esNulo(y)) {
                nuevoDesple.setY(y.valor.toString());
            }

            if (!esNulo(lista)) {
                nuevoDesple.setLista(lista.valor);
            }

            if (!esNulo(alto)) {
                nuevoDesple.setAlto(alto.valor.toString());
            }

            if (!esNulo(ancho)) {
                nuevoDesple.setAncho(ancho.valor.toString());
            }

            /*---------------------------------------------------------------------*/
            if (!esNulo(actualResultado)) {
                if (esClase(actualResultado.valor)) {
                    Clase clase = (Clase) actualResultado.valor;
                    if (clase.Componente != null) {

                        switch (clase.nombre.toLowerCase()) {
                            case "ventana":
                                nuevoDesple.setDatos();
                                nuevoDesple.setBounds(nuevoDesple.getLocation().x, nuevoDesple.getLocation().y, nuevoDesple.getPreferredSize().width, nuevoDesple.getPreferredSize().height);
                                ((VentanaGenerica) clase.Componente).getContentPane().add(nuevoDesple);

                                break;
                            case "panel":
                                nuevoDesple.setDatos();
                                nuevoDesple.setBounds(nuevoDesple.getLocation().x, nuevoDesple.getLocation().y, nuevoDesple.getPreferredSize().width, nuevoDesple.getPreferredSize().height);
                                ((PanelGenerico) clase.Componente).add(nuevoDesple);
                                break;
                        }

                    }
                }
            }

        }

    }

    public void crearControlNumerico() {
        //Alto, Ancho, Maximo, Minimo, X, Y, defecto, nombre
        String[] stilos = {"alto", "ancho", "maximo", "minimo", "x", "y", "defecto", "nombre"};

        proceder = false;
        ArrayList<Resultado> parametros = getParametros(raiz);

        CajaNumericaGenerica nuevaNumerica = new CajaNumericaGenerica(raiz);

        /*----------------###############################---------------------*/
        Resultado alto = null, ancho = null, maximo = null, minimo = null, x = null, y = null, nombre = null, defecto = null;

        try {
            alto = parametros.get(0);
        } catch (Exception e) {
            alto = new Resultado("$nulo", "nulo");
        }

        try {
            ancho = parametros.get(1);
        } catch (Exception e) {
            ancho = new Resultado("$nulo", "nulo");
        }

        try {
            maximo = parametros.get(2);
        } catch (Exception e) {
        }

        try {
            minimo = parametros.get(3);
        } catch (Exception e) {
            minimo = new Resultado("$nulo", "nulo");
        }

        try {
            x = parametros.get(4);
        } catch (Exception e) {
            x = new Resultado("$nulo", "nulo");
        }

        try {
            y = parametros.get(5);
        } catch (Exception e) {
            y = new Resultado("$nulo", "nulo");
        }

        try {
            defecto = parametros.get(6);
        } catch (Exception e) {
            defecto = new Resultado("$nulo", "nulo");
        }

        try {
            nombre = parametros.get(7);
        } catch (Exception e) {
            nombre = new Resultado("$nulo", "nulo");
        }


        /*----------------###############################---------------------*/
        //Se crea el Arbol que corresponde a cntObj
        Nodo cntobj = crearNodoObj(raiz, stilos, 7);

        opL = new OperacionesARL(global, tabla, miTemplate);
        Resultado resultado = opL.ejecutar(cntobj);

        if (!esNulo(resultado)) {
            ComponenteRes = resultado;
            if (esClase(resultado.valor)) {
                Clase clase = (Clase) resultado.valor;
                clase.Componente = nuevaNumerica;
                clase.nombre = "CajaNumerica";
                clase.ejecutar(miTemplate);
                clase.Inicializada = true;

                nuevaNumerica.setearClasse(clase);
            }

            /*---------------------------------------------------------------------*/
            if (!esNulo(alto)) {
                nuevaNumerica.setAlto(alto.valor.toString());
            }

            if (!esNulo(ancho)) {
                nuevaNumerica.setAncho(ancho.valor.toString());
            }

            if (!esNulo(maximo)) {
                nuevaNumerica.setMaximo(maximo.valor.toString());
            }

            if (!esNulo(minimo)) {
                nuevaNumerica.setMinimo(minimo.valor.toString());
            }

            if (!esNulo(x)) {
                nuevaNumerica.setX(x.valor.toString());
            }

            if (!esNulo(y)) {
                nuevaNumerica.setY(y.valor.toString());
            }

            if (!esNulo(defecto)) {
                if(!defecto.tipo.equals("$nulo"))
                {
                    nuevaNumerica.setTexto(defecto.valor.toString());
                }
                
            }

            if (!esNulo(nombre)) {
                if(!nombre.tipo.equals("$nulo"))
                {
                    nuevaNumerica.setId(nombre.valor.toString());
                }else
                {
                    nuevaNumerica.setId("");
                }
                
            }


            /*---------------------------------------------------------------------*/
            if (!esNulo(actualResultado)) {
                if (esClase(actualResultado.valor)) {
                    Clase clase = (Clase) actualResultado.valor;
                    if (clase.Componente != null) {

                        switch (clase.nombre.toLowerCase()) {
                            case "ventana":
                                nuevaNumerica.setBounds(nuevaNumerica.getLocation().x, nuevaNumerica.getLocation().y, nuevaNumerica.getPreferredSize().width, nuevaNumerica.getPreferredSize().height);
                                ((VentanaGenerica) clase.Componente).getContentPane().add(nuevaNumerica);

                                break;
                            case "panel":
                                nuevaNumerica.setBounds(nuevaNumerica.getLocation().x, nuevaNumerica.getLocation().y, nuevaNumerica.getPreferredSize().width, nuevaNumerica.getPreferredSize().height);
                                ((PanelGenerico) clase.Componente).add(nuevaNumerica);
                                break;
                        }

                    }
                }
            }

        }

    }

    public void crearCajaTexto() {
        //Fuente, Tamaño, Color, X, Y, Negrilla, Cursiva, valo
        String[] stilos = {"alto", "ancho", "fuente", "tam", "color", "x", "y", "negrilla", "cursiva", "defecto", "nombre"};

        proceder = false;
        ArrayList<Resultado> parametros = getParametros(raiz);

        CajaTextoGenerica nuevaCajaText = new CajaTextoGenerica(raiz);

        /*----------------###############################---------------------*/
        Resultado alto = null, ancho = null, fuente = null, tam = null, color = null, x = null, y = null, negrilla = null, cursiva = null, nombre = null, defecto = null;

        try {
            alto = parametros.get(0);
        } catch (Exception e) {
            alto = new Resultado("$nulo", "nulo");
        }

        try {
            ancho = parametros.get(1);
        } catch (Exception e) {
            ancho = new Resultado("$nulo", "nulo");
        }

        try {
            fuente = parametros.get(2);
        } catch (Exception e) {
            fuente = new Resultado("$nulo", "nulo");
        }

        try {
            tam = parametros.get(3);
        } catch (Exception e) {
            tam = new Resultado("$nulo", "nulo");
        }

        try {
            color = parametros.get(4);
        } catch (Exception e) {
            color = new Resultado("$nulo", "nulo");
        }

        try {
            x = parametros.get(5);
        } catch (Exception e) {
            x = new Resultado("$nulo", "nulo");
        }

        try {
            y = parametros.get(6);
        } catch (Exception e) {
            y = new Resultado("$nulo", "nulo");
        }

        try {
            negrilla = parametros.get(7);
        } catch (Exception e) {
            negrilla = new Resultado("$nulo", "nulo");
        }

        try {
            cursiva = parametros.get(8);
        } catch (Exception e) {
            cursiva = new Resultado("$nulo", "nulo");
        }

        try {
            defecto = parametros.get(9);
        } catch (Exception e) {
            defecto = new Resultado("$nulo", "nulo");
        }

        try {
            nombre = parametros.get(10);
        } catch (Exception e) {
            nombre = new Resultado("$nulo", "nulo");
        }

        /*----------------###############################---------------------*/
        //Se crea el Arbol que corresponde a cntObj
        Nodo cntobj = crearNodoObj(raiz, stilos, 10);

        opL = new OperacionesARL(global, tabla, miTemplate);
        Resultado resultado = opL.ejecutar(cntobj);

        if (!esNulo(resultado)) {
            ComponenteRes = resultado;
            if (esClase(resultado.valor)) {
                Clase clase = (Clase) resultado.valor;
                clase.Componente = nuevaCajaText;
                clase.nombre = "CajaTexto";
                clase.ejecutar(miTemplate);
                clase.Inicializada = true;

                nuevaCajaText.setearClasse(clase);

            }

            /*---------------------------------------------------------------------*/
            if (!esNulo(alto)) {
                nuevaCajaText.setAlto(alto.valor.toString());
            }

            if (!esNulo(ancho)) {
                nuevaCajaText.setAncho(ancho.valor.toString());
            }

            if (!esNulo(fuente)) {
                nuevaCajaText.setFuente(fuente.valor.toString());
            }

            if (!esNulo(tam)) {
                nuevaCajaText.setTam(tam.valor.toString());
            }

            if (!esNulo(color)) {
                nuevaCajaText.setColor(color.valor.toString());
            }

            if (!esNulo(x)) {
                nuevaCajaText.setX(x.valor.toString());
            }

            if (!esNulo(y)) {
                nuevaCajaText.setY(y.valor.toString());
            }

            if (!esNulo(negrilla)) {
                nuevaCajaText.setNegrilla(negrilla.valor.toString());
            }

            if (!esNulo(cursiva)) {
                nuevaCajaText.setCurvisa(cursiva.valor.toString());
            }

            if (!esNulo(defecto)) {
                if(!defecto.tipo.equals("$nulo"))
                {
                    nuevaCajaText.setTexto(defecto.valor.toString());
                }   
            }

            if (!esNulo(nombre)) {
                if(!nombre.tipo.equals("$nulo"))
                {
                    nuevaCajaText.setId(nombre.valor.toString());
                }else
                {
                    nuevaCajaText.setId("");
                }
            }


            /*---------------------------------------------------------------------*/
            if (!esNulo(actualResultado)) {
                if (esClase(actualResultado.valor)) {
                    Clase clase = (Clase) actualResultado.valor;
                    if (clase.Componente != null) {

                        switch (clase.nombre.toLowerCase()) {
                            case "ventana":
                                nuevaCajaText.setBounds(nuevaCajaText.getLocation().x, nuevaCajaText.getLocation().y, nuevaCajaText.getPreferredSize().width, nuevaCajaText.getPreferredSize().height);
                                ((VentanaGenerica) clase.Componente).getContentPane().add(nuevaCajaText);

                                break;
                            case "panel":
                                nuevaCajaText.setBounds(nuevaCajaText.getLocation().x, nuevaCajaText.getLocation().y, nuevaCajaText.getPreferredSize().width, nuevaCajaText.getPreferredSize().height);
                                ((PanelGenerico) clase.Componente).add(nuevaCajaText);
                                break;
                        }

                    }
                }
            }

        }
    }

    public void crearAreaTexto() {
        //Fuente, Tamaño, Color, X, Y, Negrilla, Cursiva, valo
        String[] stilos = {"alto", "ancho", "fuente", "tam", "color", "x", "y", "negrilla", "cursiva", "defecto", "nombre"};

        proceder = false;
        ArrayList<Resultado> parametros = getParametros(raiz);

        AreaTextoGenerica nuevaAreaText = new AreaTextoGenerica(raiz);

        /*----------------###############################---------------------*/
        Resultado alto = null, ancho = null, fuente = null, tam = null, color = null, x = null, y = null, negrilla = null, cursiva = null, nombre = null, defecto = null;

        try {
            alto = parametros.get(0);
        } catch (Exception e) {
            alto = new Resultado("$nulo", "nulo");
        }

        try {
            ancho = parametros.get(1);
        } catch (Exception e) {
            ancho = new Resultado("$nulo", "nulo");
        }

        try {
            fuente = parametros.get(2);
        } catch (Exception e) {
            fuente = new Resultado("$nulo", "nulo");
        }

        try {
            tam = parametros.get(3);
        } catch (Exception e) {
            tam = new Resultado("$nulo", "nulo");
        }

        try {
            color = parametros.get(4);
        } catch (Exception e) {
            color = new Resultado("$nulo", "nulo");
        }

        try {
            x = parametros.get(5);
        } catch (Exception e) {
            x = new Resultado("$nulo", "nulo");
        }

        try {
            y = parametros.get(6);
        } catch (Exception e) {
            y = new Resultado("$nulo", "nulo");
        }

        try {
            negrilla = parametros.get(7);
        } catch (Exception e) {
            negrilla = new Resultado("$nulo", "nulo");
        }

        try {
            cursiva = parametros.get(8);
        } catch (Exception e) {
            cursiva = new Resultado("$nulo", "nulo");
        }

        try {
            defecto = parametros.get(9);
        } catch (Exception e) {
            defecto = new Resultado("$nulo", "nulo");
        }

        try {
            nombre = parametros.get(10);
        } catch (Exception e) {
            nombre = new Resultado("$nulo", "nulo");
        }

        /*----------------###############################---------------------*/
        //Se crea el Arbol que corresponde a cntObj
        Nodo cntobj = crearNodoObj(raiz, stilos, 10);

        opL = new OperacionesARL(global, tabla, miTemplate);
        Resultado resultado = opL.ejecutar(cntobj);

        if (!esNulo(resultado)) {
            ComponenteRes = resultado;
            if (esClase(resultado.valor)) {
                Clase clase = (Clase) resultado.valor;
                clase.Componente = nuevaAreaText;
                clase.nombre = "AreaTexto";
                clase.ejecutar(miTemplate);
                clase.Inicializada = true;

                nuevaAreaText.setearClasse(clase);
            }

            /*---------------------------------------------------------------------*/
            if (!esNulo(alto)) {
                nuevaAreaText.setAlto(alto.valor.toString());
            }

            if (!esNulo(ancho)) {
                nuevaAreaText.setAncho(ancho.valor.toString());
            }

            if (!esNulo(fuente)) {
                nuevaAreaText.setFuente(fuente.valor.toString());
            }

            if (!esNulo(tam)) {
                nuevaAreaText.setTam(tam.valor.toString());
            }

            if (!esNulo(color)) {
                nuevaAreaText.setColor(color.valor.toString());
            }

            if (!esNulo(x)) {
                nuevaAreaText.setX(x.valor.toString());
            }

            if (!esNulo(y)) {
                nuevaAreaText.setY(y.valor.toString());
            }

            if (!esNulo(negrilla)) {
                nuevaAreaText.setNegrilla(negrilla.valor.toString());
            }

            if (!esNulo(cursiva)) {
                nuevaAreaText.setCurvisa(cursiva.valor.toString());
            }

            if (!esNulo(defecto)) {
                if(!defecto.tipo.equals("$nulo"))
                {
                    nuevaAreaText.setTexto(defecto.valor.toString());
                }
                
            }

            if (!esNulo(nombre)) {
                if(!nombre.tipo.equals("$nulo"))
                {
                    nuevaAreaText.setId(nombre.valor.toString());
                }else
                {
                    nuevaAreaText.setId("");
                }
            }


            /*---------------------------------------------------------------------*/
            if (!esNulo(actualResultado)) {
                if (esClase(actualResultado.valor)) {
                    Clase clase = (Clase) actualResultado.valor;
                    if (clase.Componente != null) {

                        switch (clase.nombre.toLowerCase()) {
                            case "ventana":
                                nuevaAreaText.setBounds(nuevaAreaText.getLocation().x, nuevaAreaText.getLocation().y, nuevaAreaText.getPreferredSize().width, nuevaAreaText.getPreferredSize().height);
                                ((VentanaGenerica) clase.Componente).getContentPane().add(nuevaAreaText);

                                break;
                            case "panel":
                                nuevaAreaText.setBounds(nuevaAreaText.getLocation().x, nuevaAreaText.getLocation().y, nuevaAreaText.getPreferredSize().width, nuevaAreaText.getPreferredSize().height);
                                ((PanelGenerico) clase.Componente).add(nuevaAreaText);
                                break;
                        }

                    }
                }
            }

        }
    }

    public Arreglo obtenerPorEtiqueta(JComponent padre, Arreglo nuevo, String etiqueta_name) {

        Clase cla = null;

        for (Component component : padre.getComponents()) {
            String namecomponente = component.getClass().getSimpleName().toLowerCase();

            switch (namecomponente) {
                /*case "ventanagenerica":
                    try {
                        cla = ((VentanaGenerica)component).classe;
                        nuevo.AGREGAR(new Resultado(cla.nombre,cla));
                    } catch (Exception e) {
                    }
                    break;*/
                case "panelgenerico":
                    try {
                        cla = ((PanelGenerico) component).classe;
                        if (etiqueta_name.equalsIgnoreCase(namecomponente)) {
                            nuevo.AGREGAR(new Resultado(cla.nombre, cla));
                        }
                        obtenerPorEtiqueta(((PanelGenerico) component), nuevo, etiqueta_name);
                    } catch (Exception e) {
                        obtenerPorEtiqueta(((PanelGenerico) component), nuevo, etiqueta_name);
                    }
                    break;
                //*************************    
                case "textogenerico":
                    try {
                        cla = ((TextoGenerico) component).classe;
                        if (etiqueta_name.equalsIgnoreCase(namecomponente)) {
                            nuevo.AGREGAR(new Resultado(cla.nombre, cla));
                        }
                    } catch (Exception e) {
                    }

                    break;
                case "reproductor":
                    try {
                        cla = ((Reproductor) component).classe;
                        if (etiqueta_name.equalsIgnoreCase(namecomponente)) {
                            nuevo.AGREGAR(new Resultado(cla.nombre, cla));
                        }
                    } catch (Exception e) {
                    }

                    break;
                case "desplegablegenerico":
                    try {
                        cla = ((DesplegableGenerico) component).classe;
                        if (etiqueta_name.equalsIgnoreCase(namecomponente) || etiqueta_name.equals("contenedor")) {
                            nuevo.AGREGAR(new Resultado(cla.nombre, cla));
                        }
                    } catch (Exception e) {
                    }
                    break;
                case "cajatextogenerica":
                    try {
                        cla = ((CajaTextoGenerica) component).classe;
                        if (etiqueta_name.equalsIgnoreCase(namecomponente) || etiqueta_name.equals("contenedor")) {
                            nuevo.AGREGAR(new Resultado(cla.nombre, cla));
                        }
                    } catch (Exception e) {
                    }
                    break;
                case "cajanumericagenerica":
                    try {
                        cla = ((CajaNumericaGenerica) component).classe;
                        if (etiqueta_name.equalsIgnoreCase(namecomponente) || etiqueta_name.equals("contenedor")) {
                            nuevo.AGREGAR(new Resultado(cla.nombre, cla));
                        }
                    } catch (Exception e) {
                    }
                    break;
                case "botongenerico":
                    try {
                        cla = ((BotonGenerico) component).classe;
                        if (etiqueta_name.equalsIgnoreCase(namecomponente)) {
                            nuevo.AGREGAR(new Resultado(cla.nombre, cla));
                        }
                    } catch (Exception e) {
                    }
                    break;
                case "areatextogenerica":
                    try {
                        cla = ((AreaTextoGenerica) component).classe;
                        if (etiqueta_name.equalsIgnoreCase(namecomponente) || etiqueta_name.equals("contenedor")) {
                            nuevo.AGREGAR(new Resultado(cla.nombre, cla));
                        }
                    } catch (Exception e) {
                    }
                    break;
            }
        }

        return nuevo;
    }

    public void obtenerPorEtiqueta_NODO(Nodo padre, String etiqueta, Arreglo arr) {

        if (padre.nombre.equalsIgnoreCase(etiqueta)) {
            try {
                generarClase_de_NodoGXML(padre, arr, padre.nombre.toLowerCase());
            } catch (Exception e) {
                Template.reporteError_CJS.agregar("Ejecucion", padre.linea, padre.columna, "Error en obtenerPorEtiqueta [" + etiqueta + "] " + e.getMessage());
            }
        }

        Nodo componentesH;
        switch (padre.nombre.toLowerCase()) {
            case "ventana":
            case "control":
            case "boton":
            case "listadatos":
            case "enviar":
            case "contenedor":
                try {
                    componentesH = padre.get(2);
                    for (Nodo hijo : componentesH.hijos) {
                        obtenerPorEtiqueta_NODO(hijo, etiqueta, arr);
                    }
                } catch (Exception e) {
                }
                break;
            case "raiz":
                try {
                    componentesH = padre.get(1);
                    for (Nodo hijo : componentesH.hijos) {
                        obtenerPorEtiqueta_NODO(hijo, etiqueta, arr);
                    }
                } catch (Exception e) {
                }
                break;
            default:
                break;
        }
    }

    public void obtenerPorID_NODO(Nodo padre, String id, Arreglo arr, String nomATRIBUTO) {

        Nodo atributos = padre.get(0);
        for (Nodo att : atributos.hijos) {
            Nodo n_Atributo = att.get(0);
            Nodo n_Valor = att.get(1);

            if (n_Atributo.nombre.equalsIgnoreCase(nomATRIBUTO.toLowerCase()) && n_Valor.valor.equalsIgnoreCase(id.toLowerCase())) {
                try {
                    JOptionPane.showMessageDialog(null, "yeah");
                    generarClase_de_NodoGXML(padre, arr, padre.nombre.toLowerCase());
                    return;
                } catch (Exception e) {
                    Template.reporteError_CJS.agregar("Ejecucion", padre.linea, padre.columna, "Error en obtenerPorId [" + id + "] " + e.getMessage());
                }
            }
        }

        Nodo componentesH;
        switch (padre.nombre.toLowerCase()) {
            case "ventana":
            case "control":
            case "boton":
            case "listadatos":
            case "enviar":
            case "contenedor":
                try {
                    componentesH = padre.get(2);
                    for (Nodo hijo : componentesH.hijos) {
                        obtenerPorID_NODO(hijo, id, arr, nomATRIBUTO);
                    }
                } catch (Exception e) {
                }
                break;
            case "raiz":
                try {
                    componentesH = padre.get(1);
                    for (Nodo hijo : componentesH.hijos) {
                        obtenerPorID_NODO(hijo, id, arr, nomATRIBUTO);
                    }
                } catch (Exception e) {
                }
                break;
            default:
                break;
        }
    }

    public void parserImportGXML(Nodo impor_t, Arreglo arr) {
        String cad_ruta = impor_t.valor;
        cad_ruta = cad_ruta.replaceAll("\n", "");
        cad_ruta = cad_ruta.replaceAll("\t", "");
        cad_ruta = cad_ruta.replaceAll(">", "");
        cad_ruta = cad_ruta.replaceAll("<", "");
        cad_ruta = cad_ruta.trim();
        String rutaBuena = existeArchivo(cad_ruta);
        if (!rutaBuena.equals("")) {
            File file = new File(rutaBuena);
            String nombre_ = file.getName();
            String ext = Arrays.stream(nombre_.split("\\.")).reduce((a, b) -> b).orElse(null);
            if (ext.equals("gxml")) {
                Nodo r_GXML = null;
                try {
                    LexGxml lex = new LexGxml(new FileReader(rutaBuena));
                    SintacticoGxml sin = new SintacticoGxml(lex);
                    try {
                        sin.parse();
                        r_GXML = sin.getRoot();
                    } catch (Exception e) {
                        Template.reporteError_CJS.agregar("Semantico", impor_t.linea, impor_t.columna, "Erro al compilar archivo " + nombre_);
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(LlamadaMetodo.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (r_GXML != null) {
                    crearArrayDesdeArchivo(r_GXML, arr);
                }
            }
        }
    }

    public void generarClase_de_NodoGXML(Nodo raiz, Arreglo arr, String nm_componente) {
        Nodo vAtributos = raiz.get(0);
        Nodo EXP = crearObjdesdeNodo(vAtributos);
        opL = new OperacionesARL(global, tabla, miTemplate);
        Resultado res = opL.ejecutar(EXP);
        if (!esNulo(res)) {
            if (esClase(res.valor)) {
                Clase clase = (Clase) res.valor;
                clase.nombre = nm_componente;
                clase.ejecutar(miTemplate);
                clase.Inicializada = true;
                clase.raiz_GXML = raiz;
            }
            arr.AGREGAR(res);
        }
    }

    public void crearArrayDesdeArchivoGdato(Nodo raiz, Arreglo arr) {
        Nodo Lventanas = raiz.get(1);

        Resultado res;
        for (Nodo vt : Lventanas.hijos) {
            Nodo vAtributos = vt.get(0);

            Nodo EXP = crearObjdesdeNodo(vAtributos);
            opL = new OperacionesARL(global, tabla, miTemplate);
            res = opL.ejecutar(EXP);
            if (!esNulo(res)) {
                if (esClase(res.valor)) {
                    Clase clase = (Clase) res.valor;
                    clase.nombre = "Principal";
                    clase.ejecutar(miTemplate);
                    clase.Inicializada = true;
                    clase.raiz_GXML = vt;
                }
                arr.AGREGAR(res);
            }

        }
    }
    
    public void crearArrayDesdeArchivo(Nodo raiz, Arreglo arr) {
        Nodo imports = raiz.get(0);

        Nodo Lventanas = raiz.get(1);

        Resultado res;
        for (Nodo vt : Lventanas.hijos) {
            Nodo vAtributos = vt.get(0);

            Nodo EXP = crearObjdesdeNodo(vAtributos);
            opL = new OperacionesARL(global, tabla, miTemplate);
            res = opL.ejecutar(EXP);
            if (!esNulo(res)) {
                if (esClase(res.valor)) {
                    Clase clase = (Clase) res.valor;
                    clase.nombre = "Ventana";
                    clase.ejecutar(miTemplate);
                    clase.Inicializada = true;
                    clase.raiz_GXML = vt;
                }
                arr.AGREGAR(res);
            }

        }
        for (Nodo hijo : imports.hijos) {
            parserImportGXML(hijo, arr);
        }
    }

    public Nodo crearObjdesdeNodo(Nodo vAtributos) {
        //Se crea el Arbol que corresponde a cntObj
        Nodo cntobj = crearNodo("cntobj", "", vAtributos.linea, vAtributos.columna, vAtributos.index);
        Nodo atributos_obj = crearNodo("atributos", "", vAtributos.linea, vAtributos.columna, vAtributos.index);

        for (Nodo Xtributo : vAtributos.hijos) {
            Nodo n_Atributo = Xtributo.get(0);
            Nodo n_Valor = Xtributo.get(1);

            String tipo_estilo = n_Atributo.nombre.toLowerCase();

            /*REVISAR*/
            if (!tipo_estilo.equals("accioninicial") && !tipo_estilo.equals("accionfinal") && !tipo_estilo.equals("accion")) {
                Nodo Atributo = crearNodo("declaracionvarG", "", Xtributo.linea, Xtributo.columna, Xtributo.index);

                Nodo Lt_id = crearNodo("list_id", "", n_Atributo.linea, n_Atributo.columna, n_Atributo.index);
                Lt_id.add(crearNodo("id", tipo_estilo, n_Atributo.linea, n_Atributo.columna, n_Atributo.index));

                Nodo Asign = crearNodo("asign", "", n_Valor.linea, n_Valor.columna, n_Valor.index);
                Asign.add(n_Valor);

                Atributo.add(Lt_id);
                Atributo.add(Asign);

                atributos_obj.add(Atributo);

            }
        }
        cntobj.add(atributos_obj);
        return cntobj;
    }

    public Nodo crearNodoObj(Nodo raiz, String stilos[], int limit) {
        //Se crea el Arbol que corresponde a cntObj
        Nodo cntobj = crearNodo("cntobj", "", raiz.linea, raiz.columna, raiz.index);
        Nodo atributos = crearNodo("atributos", "", raiz.linea, raiz.columna, raiz.index);

        for (int m = 0; m < raiz.get(0).size(); m++) {
            Nodo hijo = raiz.get(0).get(m);

            Nodo Atributo = crearNodo("declaracionvarG", "", raiz.linea, raiz.columna, raiz.index);

            Nodo Lt_id = crearNodo("list_id", "", raiz.linea, raiz.columna, raiz.index);
            Lt_id.add(crearNodo("id", stilos[m], raiz.linea, raiz.columna, raiz.index));

            Nodo Asign = crearNodo("asign", "", raiz.linea, raiz.columna, raiz.index);
            Asign.add(hijo);

            Atributo.add(Lt_id);
            Atributo.add(Asign);

            atributos.add(Atributo);

            if (m == limit) {
                break;
            }
        }
        cntobj.add(atributos);

        return cntobj;

    }

    public boolean esArreglo(Object valor) {
        try {
            Arreglo ar = (Arreglo) valor;
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean esClase(Object valor) {
        try {
            Clase ar = (Clase) valor;
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    
}
