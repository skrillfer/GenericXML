/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptCompiler.Sentencias;

import Estructuras.Nodo;
import INTERFAZ.Template;
import ScriptCompiler.Arreglo;
import ScriptCompiler.Clase;
import ScriptCompiler.Compilador;
import ScriptCompiler.Metodo;
import ScriptCompiler.OperacionesARL.OperacionesARL;
import ScriptCompiler.Resultado;
import ScriptCompiler.TablaSimbolo;
import WRAPERS.AreaTextoGenerica;
import WRAPERS.BotonGenerico;
import WRAPERS.CajaNumericaGenerica;
import WRAPERS.CajaTextoGenerica;
import WRAPERS.PanelGenerico;
import WRAPERS.TextoGenerico;
import WRAPERS.VentanaGenerica;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

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
            TablaSimbolo tablaTemp = new TablaSimbolo();
            tabla = tablaTemp;
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
        /*for (Resultado resultado : parametros) {
            if (resultado.valor != null) {
                if (resultado.valor.getClass().getSimpleName().equalsIgnoreCase("arreglo")) {
                    Arreglo arr = (Arreglo) resultado.valor;
                    nombre += resultado.tipo + arr.dimensiones.size();
                } else {
                    nombre += resultado.tipo;
                }
            }
        }*/
        nombre += "_" + parametros.size();
        return nombre;
    }

    private ArrayList<Resultado> getParametros(Nodo raiz) {
        ArrayList<Resultado> parametros = new ArrayList<>();
        Nodo nodoParametros = raiz.hijos.get(0);
        for (Nodo hijo : nodoParametros.hijos) {
            opL = new OperacionesARL(global, tabla, miTemplate);
            Resultado resultado = opL.ejecutar(hijo);
            parametros.add(resultado);
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
            //NOTA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //aqui deberia de buscarlo en los importados
            /*if (actual.herencia != null) {
                actual = actual.herencia;
                metodo = getMetodo(id);
            }*/
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

        if (!esNulo(actualResultado)) {
            //El resultado anterior fu un arreglo
            if (esArreglo(actualResultado.valor)) {
                Arreglo arr;
                switch (raiz.valor.toLowerCase()) {
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
                                            vt.setBounds(100, 10, maxWidth, maxHeigth);
                                        }
                                        vt.setLocationRelativeTo(null);
                                        vt.setVisible(true);
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

                }
            }
        } else {
            //Creacion de componentes GRAFICOS
            switch (raiz.valor.toLowerCase()) {
                case "crearventana":
                    try {
                        crearVentana();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "crearVentana error:" + e.getMessage());
                    }

                    break;
            }
        }
    }

    /*  ====    ====            CREAR VENTANA            ====    ====    */
    public void crearVentana() {
        String[] stilos = {"color", "ancho", "alto", "id"};
        proceder = false;
        ArrayList<Resultado> parametros = getParametros(raiz);

        VentanaGenerica nuevaVentana = new VentanaGenerica(raiz);
        nuevaVentana.setearCore(global, tabla, miTemplate);
        /*----------------###############################---------------------*/
        Resultado color = null, alto = null, ancho = null, id = null;
        try {
            color = parametros.get(0);
        } catch (Exception e) {
        }

        try {
            alto = parametros.get(1);
        } catch (Exception e) {
        }

        try {
            ancho = parametros.get(2);
        } catch (Exception e) {
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
            }

            /*---------------------------------------------------------------------*/
            if (!esNulo(id)) {
                if (id.tipo.equals("String")) {
                    nuevaVentana.setId((String) id.valor);
                } else {
                    nuevaVentana.setId("");
                }
            } else {
                nuevaVentana.setId("");
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

            System.out.println("VENTANA = >");
            System.out.println(alto.valor.toString() + "-" + ancho.valor.toString());
            /*---------------------------------------------------------------------*/

            listaVentanas.add(nuevaVentana);//Pensar bien si si o no

            //nuevaVentana.add(new PanelGenerico(raiz));
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
        }

        try {
            ancho = parametros.get(1);
        } catch (Exception e) {
        }

        try {
            color = parametros.get(2);
        } catch (Exception e) {
        }

        try {
            borde = parametros.get(3);
        } catch (Exception e) {
        }

        try {
            x = parametros.get(4);
        } catch (Exception e) {
        }

        try {
            y = parametros.get(5);
        } catch (Exception e) {
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
        }

        try {
            tam = parametros.get(1);
        } catch (Exception e) {
        }

        try {
            color = parametros.get(2);
        } catch (Exception e) {
        }

        try {
            x = parametros.get(3);
        } catch (Exception e) {
        }

        try {
            y = parametros.get(4);
        } catch (Exception e) {
        }

        try {
            referencia = parametros.get(5);
        } catch (Exception e) {
        }

        try {
            valor = parametros.get(6);
        } catch (Exception e) {
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
        }

        try {
            tam = parametros.get(1);
        } catch (Exception e) {
        }

        try {
            color = parametros.get(2);
        } catch (Exception e) {
        }

        try {
            x = parametros.get(3);
        } catch (Exception e) {
        }

        try {
            y = parametros.get(4);
        } catch (Exception e) {
        }

        try {
            negrilla = parametros.get(5);
        } catch (Exception e) {
        }

        try {
            cursiva = parametros.get(6);
        } catch (Exception e) {
        }

        try {
            valor = parametros.get(7);
        } catch (Exception e) {
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
            }

            /*---------------------------------------------------------------------*/
            if (!esNulo(fuente)) {
                nuevaTexto.setFuente((String) fuente.valor);
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
                nuevaTexto.setTexto(valor.valor.toString());
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
        }

        try {
            ancho = parametros.get(1);
        } catch (Exception e) {
        }

        try {
            maximo = parametros.get(2);
        } catch (Exception e) {
        }

        try {
            minimo = parametros.get(3);
        } catch (Exception e) {
        }

        try {
            x = parametros.get(4);
        } catch (Exception e) {
        }

        try {
            y = parametros.get(5);
        } catch (Exception e) {
        }

        try {
            defecto = parametros.get(6);
        } catch (Exception e) {
        }

        try {
            nombre = parametros.get(7);
        } catch (Exception e) {
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
                nuevaNumerica.setTexto(defecto.valor.toString());
            }

            if (!esNulo(nombre)) {
                nuevaNumerica.setId(nombre.valor.toString());
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

        CajaTextoGenerica nuevaAreaText = new CajaTextoGenerica(raiz);

        /*----------------###############################---------------------*/
        Resultado alto = null, ancho = null, fuente = null, tam = null, color = null, x = null, y = null, negrilla = null, cursiva = null, nombre = null, defecto = null;

        try {
            alto = parametros.get(0);
        } catch (Exception e) {
        }

        try {
            ancho = parametros.get(1);
        } catch (Exception e) {
        }

        try {
            fuente = parametros.get(2);
        } catch (Exception e) {
        }

        try {
            tam = parametros.get(3);
        } catch (Exception e) {
        }

        try {
            color = parametros.get(4);
        } catch (Exception e) {
        }

        try {
            x = parametros.get(5);
        } catch (Exception e) {
        }

        try {
            y = parametros.get(6);
        } catch (Exception e) {
        }

        try {
            negrilla = parametros.get(7);
        } catch (Exception e) {
        }

        try {
            cursiva = parametros.get(8);
        } catch (Exception e) {
        }

        try {
            defecto = parametros.get(9);
        } catch (Exception e) {
        }

        try {
            nombre = parametros.get(10);
        } catch (Exception e) {
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
                clase.nombre = "CajaTexto";
                clase.ejecutar(miTemplate);
                clase.Inicializada = true;
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
                nuevaAreaText.setTexto(defecto.valor.toString());
            }

            if (!esNulo(nombre)) {
                nuevaAreaText.setId(nombre.valor.toString());
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
        }

        try {
            ancho = parametros.get(1);
        } catch (Exception e) {
        }

        try {
            fuente = parametros.get(2);
        } catch (Exception e) {
        }

        try {
            tam = parametros.get(3);
        } catch (Exception e) {
        }

        try {
            color = parametros.get(4);
        } catch (Exception e) {
        }

        try {
            x = parametros.get(5);
        } catch (Exception e) {
        }

        try {
            y = parametros.get(6);
        } catch (Exception e) {
        }

        try {
            negrilla = parametros.get(7);
        } catch (Exception e) {
        }

        try {
            cursiva = parametros.get(8);
        } catch (Exception e) {
        }

        try {
            defecto = parametros.get(9);
        } catch (Exception e) {
        }

        try {
            nombre = parametros.get(10);
        } catch (Exception e) {
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
                nuevaAreaText.setTexto(defecto.valor.toString());
            }

            if (!esNulo(nombre)) {
                nuevaAreaText.setId(nombre.valor.toString());
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
