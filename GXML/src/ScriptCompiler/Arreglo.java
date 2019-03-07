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
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author fernando
 */
public class Arreglo {

    private Hashtable<String, ListaGenerica> VALORES;
    public ArrayList<Integer> dimensiones;
    private ArrayList<Object> datos;
    private TablaSimbolo global;
    private TablaSimbolo tabla;
    public boolean estado = true;
    private OperacionesARL opL;
    public Template miTemplate;
    /* TRUE= todos sus datos son del mismo tipo , FALSE= que no*/
    public boolean Homogeneo = true;
    public boolean esVacio = true;
    public String type = "";

    public Arreglo() {
        dimensiones = new ArrayList<>();
        datos = new ArrayList<>();
        VALORES = new Hashtable<>();
    }

    public Arreglo(Nodo raiz, TablaSimbolo global, TablaSimbolo tabla, ArrayList<Integer> dimensiones, Template template1, int num) {
        this.miTemplate = template1;
        this.dimensiones = dimensiones;
        datos = new ArrayList<>();
        VALORES = new Hashtable<>();

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
        VALORES = new Hashtable<>();

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
        VALORES = new Hashtable<>();

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
        ArrayList<Nodo> val = raiz.get(0).hijos;
        String tipo = "";

        Hashtable<String, String> hash = new Hashtable<>();

        int total = 0;
        for (Nodo hijo : val) {
            Resultado resultado = opL.ejecutar(hijo);
            if (!esNulo(resultado)) {

                if (esClase(resultado.valor)) {
                    Clase clase = (Clase) resultado.valor;
                    clase.nombre = "";
                    if(!clase.Inicializada)
                    {
                        clase.ejecutar(miTemplate);
                    }
                    
                }

                if (!hash.containsKey(resultado.tipo)) {
                    type = resultado.tipo;
                    hash.put(resultado.tipo, resultado.tipo);
                    VALORES.put(resultado.tipo, new ListaGenerica());
                }

                switch (resultado.tipo) {
                    case "String":
                        VALORES.get(resultado.tipo).add(resultado.valor.toString(), 0.0, 0, total);
                        break;
                    case "Double":
                        VALORES.get(resultado.tipo).add(resultado.valor.toString(), (Double) resultado.valor, 0, total);
                        break;
                    case "Integer":
                        VALORES.get(resultado.tipo).add(resultado.valor.toString(), 0.0, (Integer) resultado.valor, total);
                        break;
                }

                total++;
                datos.add(resultado);
            }
        }
        if (dimensiones.isEmpty() && val.size() > 0) {
            dimensiones.add(total);
        }

        Homogeneo = !(hash.size() > 1);
        esVacio = val.isEmpty();
        if (!Homogeneo) {
            type = "";
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
        if (res.tipo.equals("Integer")) {
            int posicion = (Integer) res.valor;

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

    public boolean esNulo(Resultado r) {
        if (r == null) {
            return true;
        } else {
            if (r.tipo.equals("-1")) {
                return true;
            } else {
                if (r.valor == null) {
                    return true;
                } else {
                    return false;
                }
            }

        }
    }

    public void ascendente() {
        System.out.println("\n____________ ASCENDENTE  __________");
        ArrayList<Object> datosNuevo = new ArrayList<>();

        for (Map.Entry<String, ListaGenerica> entry : VALORES.entrySet()) {
            String key = entry.getKey();
            ListaGenerica value = entry.getValue();

            switch (key) {
                case "String":
                    Collections.sort(value.Lista, Item.tipo1ASC);
                    break;
                case "Double":
                    Collections.sort(value.Lista, Item.tipo2ASC);
                    break;
                case "Integer":
                    Collections.sort(value.Lista, Item.tipo3ASC);
            }

            System.out.println("\nType:" + key + "");
            for (Item item : value.Lista) {
                System.out.print("[" + item.index + "]" + item.valor + " ");
                datosNuevo.add(datos.get(item.index));
            }
        }

        if (!datosNuevo.isEmpty()) {
            datos = datosNuevo;
        }
    }

    public void descendente() {
        System.out.println("\n$$$$$$$$$$$$$$$$$$$$ DESCENDENTE  $$$$$$$$$$$$-");

        ArrayList<Object> datosNuevo = new ArrayList<>();

        for (Map.Entry<String, ListaGenerica> entry : VALORES.entrySet()) {
            String key = entry.getKey();
            ListaGenerica value = entry.getValue();

            switch (key) {
                case "String":
                    Collections.sort(value.Lista, Item.tipo1DESC);
                    break;
                case "Double":
                    Collections.sort(value.Lista, Item.tipo2DESC);
                    break;
                case "Integer":
                    Collections.sort(value.Lista, Item.tipo3DESC);
            }

            System.out.println("\nType:" + key + "");
            for (Item item : value.Lista) {
                datosNuevo.add(datos.get(item.index));
                System.out.print("[" + item.index + "]" + item.valor + " ");
            }
        }

        if (!datosNuevo.isEmpty()) {
            datos = datosNuevo;
        }
    }

    public Resultado maximo() {
        Resultado resultado = new Resultado("-1", null);
        if (Homogeneo) {
            for (Map.Entry<String, ListaGenerica> entry : VALORES.entrySet()) {
                String key = entry.getKey();
                ListaGenerica value = entry.getValue();

                Item max;
                switch (key) {
                    case "Double":
                        max = Collections.max(value.Lista, new Comparator<Item>() {
                            public int compare(Item a, Item b) {
                                return Double.compare(a.valorDoble, b.valorDoble);
                            }
                        });
                        resultado = (Resultado) datos.get(max.index);
                        System.out.println("maximo:" + max.valorDoble);
                        break;
                    case "Integer":
                        max = Collections.max(value.Lista, new Comparator<Item>() {
                            public int compare(Item a, Item b) {
                                return Integer.compare(a.valorInteger, b.valorInteger);
                            }
                        });
                        resultado = (Resultado) datos.get(max.index);
                        System.out.println("maximo:" + max.valorInteger);
                }

            }
        }
        return resultado;
    }

    public Resultado minimo() {
        Resultado resultado = new Resultado("-1", null);
        if (Homogeneo) {
            for (Map.Entry<String, ListaGenerica> entry : VALORES.entrySet()) {
                String key = entry.getKey();
                ListaGenerica value = entry.getValue();

                Item min;
                switch (key) {
                    case "Double":
                        min = Collections.min(value.Lista, new Comparator<Item>() {
                            public int compare(Item a, Item b) {
                                return Double.compare(a.valorDoble, b.valorDoble);
                            }
                        });
                        resultado = (Resultado) datos.get(min.index);
                        System.out.println("minimo:" + min.valorDoble);
                        break;
                    case "Integer":
                        min = Collections.min(value.Lista, new Comparator<Item>() {
                            public int compare(Item a, Item b) {
                                return Integer.compare(a.valorInteger, b.valorInteger);
                            }
                        });
                        resultado = (Resultado) datos.get(min.index);
                        System.out.println("minimo:" + min.valorInteger);
                }

            }
        }
        return resultado;
    }

    public Resultado invertir() {
        Resultado resultado = new Resultado("-1", null);

        Collections.reverse(datos);

        VALORES = new Hashtable<>();
        System.out.println("\n----------------INVERTIR  ------------------------");
        for (int x = 0; x < datos.size(); x++) {
            Resultado res1 = (Resultado) datos.get(x);
            if (!VALORES.containsKey(res1.tipo)) {
                VALORES.put(res1.tipo, new ListaGenerica());
            }

            switch (res1.tipo) {
                case "String":
                    VALORES.get(res1.tipo).add(res1.valor.toString(), 0.0, 0, x);
                    break;
                case "Double":
                    VALORES.get(res1.tipo).add(res1.valor.toString(), (Double) res1.valor, 0, x);
                    break;
                case "Integer":
                    VALORES.get(res1.tipo).add(res1.valor.toString(), 0.0, (Integer) res1.valor, x);
                    break;
            }

            System.out.print(((Resultado) datos.get(x)).valor.toString() + " ");
        }

        return resultado;
    }

    /*
        Esta Funcion es aplicable cuando voy a setear un defecto en un JComboBox
        Por tal razon lo busco y si existe entonces lo devuelvo
     */
    public boolean existeOpcion(String opcion) {
        for (Object object : datos) {
            try {
                Resultado res = (Resultado) object;
                switch (res.tipo) {
                    case "String":
                    case "Double":
                    case "Integer":
                    case "Boolean":
                        if(opcion.equalsIgnoreCase(res.valor.toString()))
                        {
                            return true;
                        }
                        break;
                }
            } catch (Exception e) {
            }
        }
        return false;
    }

}

class ListaGenerica {

    public ArrayList<Item> Lista;

    public ListaGenerica() {
        Lista = new ArrayList<>();
    }

    public void add(String valor, Double valorDoble, Integer valorInteger, int index) {
        Lista.add(new Item(valor, valorDoble, valorInteger, index));

    }

}

class Item {

    public String valor;
    public Double valorDoble;
    public Integer valorInteger;
    public int index;

    public Item(String valor, Double valorDoble, Integer valorInteger, int index) {
        this.valor = valor;
        this.valorInteger = valorInteger;
        this.valorDoble = valorDoble;
        this.index = index;
    }

    /*  ######          ASCENDENTE                              ######      */
    public static Comparator<Item> tipo1ASC = (Item o1, Item o2) -> {
        String StudentName1 = o1.valor.toUpperCase();
        String StudentName2 = o2.valor.toUpperCase();

        //ascending order
        return StudentName1.compareTo(StudentName2);
    };

    public static Comparator<Item> tipo2ASC = (Item o1, Item o2) -> {
        Double StudentName1 = o1.valorDoble;
        Double StudentName2 = o2.valorDoble;

        //ascending order
        return StudentName1.compareTo(StudentName2);
    };

    public static Comparator<Item> tipo3ASC = (Item o1, Item o2) -> {
        Integer StudentName1 = o1.valorInteger;
        Integer StudentName2 = o2.valorInteger;

        //ascending order
        return StudentName1.compareTo(StudentName2);
    };

    /*  ######          DESCENDENTE                              ######      */
    public static Comparator<Item> tipo1DESC = (Item o1, Item o2) -> {
        String StudentName1 = o1.valor.toUpperCase();
        String StudentName2 = o2.valor.toUpperCase();

        //ascending order
        return StudentName2.compareTo(StudentName1);
    };

    public static Comparator<Item> tipo2DESC = (Item o1, Item o2) -> {
        Double StudentName1 = o1.valorDoble;
        Double StudentName2 = o2.valorDoble;

        //ascending order
        return StudentName2.compareTo(StudentName1);
    };

    public static Comparator<Item> tipo3DESC = (Item o1, Item o2) -> {
        Integer StudentName1 = o1.valorInteger;
        Integer StudentName2 = o2.valorInteger;

        //ascending order
        return StudentName2.compareTo(StudentName1);
    };

}
