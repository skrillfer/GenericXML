/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GenericCompiler;

import Analizadores.Gdato.StringMatcher;
import Analizadores.Gxml.LexGxml;
import Analizadores.Gxml.SintacticoGxml;
import Estructuras.Nodo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fernando
 */
//!!!!!!!!!!!!!!!!!!!!DUDAS
/*
        Metodo Accion en Venta existe?
 */
public class TraduccionGxml_Script {

    StringMatcher match = new StringMatcher();

    public String codigoImports = "";
    public String rutaPadre = "";
    public String codigoScript = "";
    public String salto = "\n";

    public void IniciarTraduccion(Nodo RAIZ, String rutaPadre) {
        this.rutaPadre = rutaPadre;

        Nodo imports = RAIZ.get(0);

        Nodo Lventanas = RAIZ.get(1);
        ListaVentanas(Lventanas);

        for (Nodo miniimport : imports.hijos) {
            String importString = recortarString(miniimport.valor, 1, miniimport.valor.length() - 1);
            importString = importString.replaceAll("\n", "");
            importString = importString.replaceAll("\t", "");
            importString = importString.trim();
            if (!importString.equals("")) {
                try {
                    if (match.isString(importString)) {
                        importString = recortarString(importString, 1, importString.length() - 1);
                    }

                    String ext = Arrays.stream(importString.split("\\.")).reduce((a, b) -> b).orElse(null);
                    switch (ext) {
                        case "fs":
                            codigoImports = "importar(\"" + importString + "\");\n" + codigoImports;
                            break;
                        case "gxml":
                            String ruta = existeArchivo(importString);
                            if (!ruta.equals("")) {

                                LexGxml lex = new LexGxml(new FileReader(ruta));
                                SintacticoGxml sin = new SintacticoGxml(lex);
                                try {
                                    sin.parse();
                                    IniciarTraduccion(sin.getRoot(), rutaPadre);
                                } catch (Exception e) {
                                    System.err.println("error al compilar:" + e.getMessage());
                                }
                            } else {
                                //error el archivo .gxml no existe
                            }
                            break;
                        default:
                            codigoImports = "importar(\"" + importString + "\");\n" + codigoImports;
                            break;
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(TraduccionGxml_Script.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

    public void ListaVentanas(Nodo RAIZ) {

        RAIZ.hijos.forEach((ventana) -> {
            crearVentana(ventana);
        });
    }

    public void FRecursiva(Nodo RAIZ, String padre) {
        Nodo vAtributos = RAIZ.get(0);
        Nodo vExplicit = RAIZ.get(1);
        Nodo vHijos = null;

        try {
            if (RAIZ.size() == 3) {
                vHijos = RAIZ.get(2);
            } else {
                vHijos = new Nodo();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        for (Nodo hijo : vHijos.hijos) {
            switch (hijo.nombre.toLowerCase()) {
                case "contenedor":
                    crearContenedor(hijo, padre);
                    break;
                case "texto":
                    crearTexto(hijo, padre);
                    break;
                case "control":
                    crearControl(hijo, padre);
                    break;
                case "boton":
                    crearBoton(hijo, padre);
                    break;
                case "multimedia":
                    crearMultimedia(hijo, padre);
                    break;
                default:
                    //ERROR
                    break;
            }
        }

    }

    public void crearMultimedia(Nodo RAIZ, String padre) {
        Nodo vAtributos = RAIZ.get(0);

        HashMap<String, String> hashMap = new HashMap<>();

        for (Nodo atributo : vAtributos.hijos) {
            Nodo at_nombre = atributo.get(0);
            Nodo at_valor = atributo.get(1);
            if (!hashMap.containsKey(at_nombre.nombre.toLowerCase())) {
                hashMap.put(at_nombre.nombre.toLowerCase(), at_valor.valor);
            } else {
                //Error Atributo Repetido
            }
        }

        ArrayList<String> parametros = new ArrayList<>();

        //Obtener id o nombre del control y setear el nombre que tomara
        String nombre = obtenerAtributo(hashMap, "nombre");
        if (!nombre.equals("\"\"")) {
            RAIZ.valor = recortarString(nombre, 1, nombre.length() - 1);
        } else {
            RAIZ.valor = RAIZ.valor + String.valueOf(RAIZ.index);
        }

        //Obtener el atributo TIPO de Control
        if (hashMap.containsKey("tipo")) {
            String tipoControl = hashMap.get("tipo").toLowerCase();
            switch (tipoControl) {
                case "imagen":
                case "video":
                    String type = "crear" + tipoControl;

                    parametros.add(obtenerAtributo(hashMap, "path"));
                    parametros.add(obtenerAtributo(hashMap, "x"));
                    parametros.add(obtenerAtributo(hashMap, "y"));
                    parametros.add(obtenerAtributo(hashMap, "auto-reproduccion"));
                    parametros.add(obtenerAtributo(hashMap, "alto"));
                    parametros.add(obtenerAtributo(hashMap, "ancho"));
                    agregarAPadre(RAIZ, parametros, padre, type);
                    FRecursiva(RAIZ, RAIZ.valor);
                    break;
                case "musica":
                    parametros.add(obtenerAtributo(hashMap, "path"));
                    parametros.add(obtenerAtributo(hashMap, "x"));
                    parametros.add(obtenerAtributo(hashMap, "y"));
                    parametros.add(obtenerAtributo(hashMap, "auto-reproduccion"));
                    parametros.add(obtenerAtributo(hashMap, "alto"));
                    parametros.add(obtenerAtributo(hashMap, "ancho"));
                    agregarAPadre(RAIZ, parametros, padre, "crearreproductor");
                    FRecursiva(RAIZ, RAIZ.valor);

                    break;

            }
        }
    }

    public void crearBoton(Nodo RAIZ, String padre) {
        Nodo vAtributos = RAIZ.get(0);
        Nodo vExplicit = RAIZ.get(1);

        HashMap<String, String> hashMap = new HashMap<>();

        for (Nodo atributo : vAtributos.hijos) {
            Nodo at_nombre = atributo.get(0);
            Nodo at_valor = atributo.get(1);
            if (!hashMap.containsKey(at_nombre.nombre.toLowerCase())) {
                //System.out.println("key:"+at_nombre.nombre.toLowerCase());
                //System.out.println("valor:"+at_valor.valor.toLowerCase());
                hashMap.put(at_nombre.nombre.toLowerCase(), at_valor.valor);
            } else {
                //Error Atributo Repetido
            }
        }

        ArrayList<String> parametros = new ArrayList<>();

        //Obtener id o nombre del control y setear el nombre que tomara
        String nombre = obtenerAtributo(hashMap, "nombre");
        if (!nombre.equals("\"\"")) {
            RAIZ.valor = recortarString(nombre, 1, nombre.length() - 1);;
        } else {
            RAIZ.valor = RAIZ.valor + String.valueOf(RAIZ.index);
        }

        //Texto que mostrara el boton
        String str_Defecto;
        vExplicit.valor = recortarString(vExplicit.valor, 1, vExplicit.valor.length() - 1);
        vExplicit.valor = cleanExplicit(vExplicit.valor);
        str_Defecto = "\"" + vExplicit.valor + "\"";

        parametros.add(obtenerAtributo(hashMap, "fuente"));
        parametros.add(obtenerAtributo(hashMap, "tam"));
        parametros.add(obtenerAtributo(hashMap, "color"));
        parametros.add(obtenerAtributo(hashMap, "x"));
        parametros.add(obtenerAtributo(hashMap, "y"));
        parametros.add(obtenerAtributo(hashMap, "referencia"));
        //valor
        parametros.add(str_Defecto);
        parametros.add(obtenerAtributo(hashMap, "alto"));
        parametros.add(obtenerAtributo(hashMap, "ancho"));

        codigoScript += "\n//Valores de " + RAIZ.valor + "";
        agregarAPadre(RAIZ, parametros, padre, "crearboton");

        /*  Si tiene el atributo Accion*/
        String accion = obtenerAtributo(hashMap, "accion");
        if (!accion.equals("\"\"")) {
            accion = recortarString(accion, 1, accion.length() - 1);
            accion = recortarString(accion, 1, accion.length() - 1);

            codigoScript += salto + RAIZ.valor + ".alclic(" + accion + ");";
        }

        FRecursiva(RAIZ, RAIZ.valor);
    }

    public void crearControl(Nodo RAIZ, String padre) {
        Nodo vAtributos = RAIZ.get(0);

        HashMap<String, String> hashMap = new HashMap<>();

        for (Nodo atributo : vAtributos.hijos) {
            Nodo at_nombre = atributo.get(0);
            Nodo at_valor = atributo.get(1);
            if (!hashMap.containsKey(at_nombre.nombre.toLowerCase())) {
                hashMap.put(at_nombre.nombre.toLowerCase(), at_valor.valor);
            } else {
                //Error Atributo Repetido
            }
        }

        ArrayList<String> parametros = new ArrayList<>();

        //Obtener id o nombre del control y setear el nombre que tomara
        String nombre = obtenerAtributo(hashMap, "nombre");
        if (!nombre.equals("\"\"")) {
            RAIZ.valor = recortarString(nombre, 1, nombre.length() - 1);;
        } else {
            RAIZ.valor = RAIZ.valor + String.valueOf(RAIZ.index);
        }

        //Obtener <defecto> </defecto>
        Nodo DEFECTO = obtenerHijo(RAIZ, "defecto");
        String str_Defecto = "\"\"";
        if (DEFECTO != null) {
            Nodo vExplicit = DEFECTO.get(1);
            vExplicit.valor = recortarString(vExplicit.valor, 1, vExplicit.valor.length() - 1);
            vExplicit.valor = cleanExplicit(vExplicit.valor);
            str_Defecto = "\"" + vExplicit.valor + "\"";
        }

        //Obtener el atributo TIPO de Control
        if (hashMap.containsKey("tipo")) {
            String tipoControl = hashMap.get("tipo").toLowerCase();
            switch (tipoControl) {
                case "textoarea":
                case "texto":
                    String type = "crearcajatexto";
                    if (tipoControl.equals("textoarea")) {
                        type = "crearareatexto";
                    }
                    parametros.add(getValorFinal("alto", obtenerAtributo(hashMap, "alto")));
                    parametros.add(getValorFinal("ancho", obtenerAtributo(hashMap, "ancho")));
                    parametros.add(getValorFinal("fuente", obtenerAtributo(hashMap, "fuente")));
                    parametros.add(getValorFinal("tam", obtenerAtributo(hashMap, "tam")));
                    parametros.add(getValorFinal("color", obtenerAtributo(hashMap, "color")));
                    parametros.add(getValorFinal("x", obtenerAtributo(hashMap, "x")));
                    parametros.add(getValorFinal("y", obtenerAtributo(hashMap, "y")));
                    parametros.add(getValorFinal("negrilla", obtenerAtributo(hashMap, "negrilla")));
                    parametros.add(getValorFinal("cursiva", obtenerAtributo(hashMap, "cursiva")));
                    parametros.add(str_Defecto);
                    parametros.add(getValorFinal("nombre", obtenerAtributo(hashMap, "nombre")));
                    codigoScript += "\n//Valores de " + RAIZ.valor + "";
                    agregarAPadre(RAIZ, parametros, padre, type);
                    FRecursiva(RAIZ, RAIZ.valor);
                    break;
                case "numerico":
                    parametros.add(obtenerAtributo(hashMap, "alto"));
                    parametros.add(obtenerAtributo(hashMap, "ancho"));
                    parametros.add(obtenerAtributo(hashMap, "maximo"));
                    parametros.add(obtenerAtributo(hashMap, "minimo"));
                    parametros.add(obtenerAtributo(hashMap, "x"));
                    parametros.add(obtenerAtributo(hashMap, "y"));
                    parametros.add(str_Defecto);
                    parametros.add(obtenerAtributo(hashMap, "nombre"));
                    codigoScript += "\n//Valores de " + RAIZ.valor + "";
                    agregarAPadre(RAIZ, parametros, padre, "crearcontrolnumerico");
                    FRecursiva(RAIZ, RAIZ.valor);

                    break;
                case "desplegable":
                    //Obtener Lista Datos
                    Nodo LISTA = obtenerHijo(RAIZ, "listadatos");

                    parametros.add(obtenerAtributo(hashMap, "alto"));
                    parametros.add(obtenerAtributo(hashMap, "ancho"));

                    //LISTA
                    if (LISTA != null) {
                        parametros.add(getArrayListaDatos(LISTA));
                    } else {
                        parametros.add("[]");
                        str_Defecto = "\"\"";
                    }

                    parametros.add(obtenerAtributo(hashMap, "x"));
                    parametros.add(obtenerAtributo(hashMap, "y"));

                    //defecto
                    parametros.add(str_Defecto);

                    parametros.add(obtenerAtributo(hashMap, "nombre"));
                    codigoScript += "\n//Valores de " + RAIZ.valor + "";
                    agregarAPadre(RAIZ, parametros, padre, "creardesplegable");
                    FRecursiva(RAIZ, RAIZ.valor);

                    break;
                default:
                    break;

            }
        }

    }

    public void crearVentana(Nodo RAIZ) {
        Nodo vAtributos = RAIZ.get(0);
        Nodo vExplicit = RAIZ.get(1);
        Nodo vHijos = RAIZ.get(2);

        ArrayList<String> parametros = new ArrayList<>();
        parametros.add("nulo");//color
        parametros.add("nulo");//alto
        parametros.add("nulo");//ancho
        parametros.add("\"\"");//id
        String alCargar = "";
        String alCerrar = "";

        for (Nodo atributo : vAtributos.hijos) {
            Nodo n_atributo = atributo.get(0);
            String n_valor = "\"" + atributo.get(1).valor + "\"";
            String val = atributo.get(1).valor;
            switch (n_atributo.nombre.toLowerCase()) {
                case "accioninicial":
                    alCargar = val;
                    break;
                case "accionfinal":
                    alCerrar = val;
                    break;
                case "color":
                    parametros.set(0, getValorFinal("color", n_valor));
                    break;
                case "alto":
                    parametros.set(1, getValorFinal("alto", val));
                    break;
                case "ancho":
                    parametros.set(2, getValorFinal("ancho", val));
                    break;
                case "id":
                    parametros.set(3, getValorFinal("id", n_valor));
                    RAIZ.valor = val;
                    codigoScript += "\n//---------------------------" + "\n//---------------------------";
                    codigoScript += "\n//---------------------" + RAIZ.valor;
                    codigoScript += "\n//---------------------------" + "\n//---------------------------\n";
                    break;

                default:
                    //ERROR
                    break;
            }
        }

        if (RAIZ.valor.equals("")) {
            RAIZ.valor = "ventana_"+String.valueOf(RAIZ.index);
        }

        codigoScript += "var " + RAIZ.valor + " = crearventana(";
        parametros.forEach((cad) -> {
            if (!cad.equals("")) {
                codigoScript += cad + ",";
            }

        });
        codigoScript = recortarString(codigoScript, 0, codigoScript.length() - 1);
        codigoScript += ");";

        if (!alCargar.equals("")) {
            alCargar = recortarString(alCargar, 1, alCargar.length() - 1);
            codigoScript += salto + RAIZ.valor + "." + "alcargar(" + alCargar + ");";
        }
        if (!alCerrar.equals("")) {
            alCerrar = recortarString(alCerrar, 1, alCerrar.length() - 1);
            codigoScript += salto + RAIZ.valor + "." + "alcerrar(" + alCerrar + ");";
        }

        FRecursiva(RAIZ, RAIZ.valor);

    }

    /*
        var contenendor_n = crearcontenedor(...);
        ventan1.crearcontenedor(contenedor_n.borde, ...);
    
        o
    
        ventan1.crearcontenedor(contenedor);
     */
    public void crearContenedor(Nodo RAIZ, String padre) {
        Nodo vAtributos = RAIZ.get(0);
        Nodo vExplicit = RAIZ.get(1);

        ArrayList<String> parametros = new ArrayList<>();
        parametros.add("\"0\"");//alto
        parametros.add("\"0\"");//ancho
        parametros.add("\"\"");//color
        parametros.add("\"falso\"");//borde
        parametros.add("\"0\"");//iniciox
        parametros.add("\"0\"");//inicioY

        for (Nodo atributo : vAtributos.hijos) {
            Nodo n_atributo = atributo.get(0);
            String n_valor = "\"" + atributo.get(1).valor + "\"";
            String val = atributo.get(1).valor;
            switch (n_atributo.nombre.toLowerCase()) {
                case "alto":
                    parametros.set(0, n_valor);
                    break;
                case "ancho":
                    parametros.set(1, n_valor);
                    break;
                case "color":
                    parametros.set(2, n_valor);
                    break;
                case "borde":
                    parametros.set(3, n_valor);
                    break;
                case "x":
                    parametros.set(4, n_valor);
                    break;
                case "y":
                    parametros.set(5, n_valor);
                    break;
                case "id":
                    RAIZ.valor = val;
                    break;

                default:
                    //ERROR
                    break;
            }
        }

        codigoScript += "\n//Valores de " + RAIZ.valor + "";
        agregarAPadre(RAIZ, parametros, padre, "crearcontenedor");
        FRecursiva(RAIZ, RAIZ.valor);

    }

    public void crearTexto(Nodo RAIZ, String padre) {
        Nodo vAtributos = RAIZ.get(0);
        Nodo vExplicit = RAIZ.get(1);

        HashMap<String, String> hashMap = new HashMap<>();

        for (Nodo atributo : vAtributos.hijos) {
            Nodo at_nombre = atributo.get(0);
            Nodo at_valor = atributo.get(1);
            if (!hashMap.containsKey(at_nombre.nombre.toLowerCase())) {
                hashMap.put(at_nombre.nombre.toLowerCase(), at_valor.valor);
            } else {
                //Error Atributo Repetido
            }
        }

        ArrayList<String> parametros = new ArrayList<>();

        //Obtener id o nombre del control y setear el nombre que tomara
        String nombre = obtenerAtributo(hashMap, "nombre");
        if (!nombre.equals("\"\"")) {
            RAIZ.valor = recortarString(nombre, 1, nombre.length() - 1);;
        } else {
            RAIZ.valor = RAIZ.valor + String.valueOf(RAIZ.index);
        }

        //Texto que mostrara el boton
        String str_Defecto;
        vExplicit.valor = recortarString(vExplicit.valor, 1, vExplicit.valor.length() - 1);
        vExplicit.valor = cleanExplicit(vExplicit.valor);
        if(match.isString(vExplicit.valor))
        {
            str_Defecto = vExplicit.valor ;
        }else
        {
            str_Defecto = "\"" + vExplicit.valor + "\"";
        }
        

        parametros.add(getValorFinal("fuente", obtenerAtributo(hashMap, "fuente")));
        parametros.add(getValorFinal("tam", obtenerAtributo(hashMap, "tam")));
        parametros.add(getValorFinal("color", obtenerAtributo(hashMap, "color")));
        parametros.add(getValorFinal("x", obtenerAtributo(hashMap, "x")));
        parametros.add(getValorFinal("y", obtenerAtributo(hashMap, "y")));
        parametros.add(getValorFinal("negrilla", obtenerAtributo(hashMap, "negrilla")));
        parametros.add(getValorFinal("cursiva", obtenerAtributo(hashMap, "cursiva")));
        parametros.add(str_Defecto);//valor

        codigoScript += "\n//Valores de " + RAIZ.valor + "";
        
        agregarAPadre(RAIZ, parametros, padre, "creartexto");
        FRecursiva(RAIZ, RAIZ.valor);

    }

    public void agregarAPadre(Nodo RAIZ, ArrayList<String> parametros, String padre, String type) {
        codigoScript += salto + "var " + RAIZ.valor + "_" + padre + " = " + padre + "." + type + "(";
        //String strPrs="*";
        parametros.forEach((cad) -> {
            if (!cad.equals("")) {
                codigoScript += cad + ",";
            }

        });

        codigoScript = recortarString(codigoScript, 0, codigoScript.length() - 1);
        codigoScript += ");\n";

        //codigoScript += salto + padre + "." + type + "(" + RAIZ.valor + ");\n\n\n";
    }

    public String obtenerAtributo(HashMap<String, String> hashMap, String key) {
        if (hashMap.containsKey(key.toLowerCase())) {
            return "\"" + hashMap.get(key) + "\"";
        } else {
            return "nulo";
        }
    }

    public Nodo obtenerHijo(Nodo RAIZ, String nombre) {
        Nodo RETORNO = null;
        if (RAIZ.size() == 3) {
            Nodo vHijos = RAIZ.get(2);
            for (Nodo hijo : vHijos.hijos) {
                if (hijo.nombre.toLowerCase().equals(nombre.toLowerCase())) {
                    RETORNO = hijo;
                    break;
                }
            }
        }
        return RETORNO;
    }

    public String recortarString(String cad, int inicio, int fin) {
        try {
            return cad.substring(inicio, fin);
        } catch (Exception e) {
            System.err.println("error al recortar:" + e.getMessage());
            return "";
        }
    }

    public String getArrayListaDatos(Nodo RAIZ) {
        String array = "[";
        if (RAIZ.size() == 3) {

            Nodo vHijos = RAIZ.get(2);
            if (!vHijos.hijos.isEmpty()) {
                for (Nodo dato : vHijos.hijos) {
                    Nodo vExplicit = dato.get(1);
                    vExplicit.valor = recortarString(vExplicit.valor, 1, vExplicit.valor.length() - 1);
                    vExplicit.valor = cleanExplicit(vExplicit.valor);
                    array += "\"" + vExplicit.valor + "\",";
                }

                array = recortarString(array, 0, array.length() - 1);
            }

        }
        return array + "]";
    }

    public String cleanExplicit(String cad) {
        cad = cad.replaceAll("\n", " ");
        cad = cad.replaceAll("\r", " ");
        cad = cad.replaceAll("\t", " ");
        cad = cad.trim();
        return cad;
    }

    public String existeArchivo(String path) {
        String pathPadre = rutaPadre;

        File file1 = new File(pathPadre + "" + path);
        File file2 = new File(pathPadre + "/" + path);
        File file3 = new File(path);
        File file4 = new File("/" + path);

        if (file1.exists()) {
            return file1.getAbsolutePath();
        }

        if (file2.exists()) {
            return file2.getAbsolutePath();
        }

        if (file3.exists()) {
            return file3.getAbsolutePath();
        }

        if (file4.exists()) {
            return file4.getAbsolutePath();
        }
        return "";
    }

    public String getValorFinal(String tipo, String valor) {
        switch (tipo.toLowerCase()) {
            case "ancho":
            case "alto":
            case "tam":
            case "maximo":
            case "minimo":
            case "x":
            case "y":    
                if (match.isString(valor)) {
                    return recortarString(valor, 1, valor.length() - 1);
                }
                return valor;
            case "negrilla":
            case "cursiva":
                if (match.isString(valor)) {
                    String recort = recortarString(valor, 1, valor.length() - 1);
                    if(match.isTrue(recort))
                    {
                        return recort;
                    }else
                    {
                        return "\"" + recort + "\"";
                    }
                    
                }
                return valor;

            default:
                if (!match.isString(valor) && !valor.equals("nulo")) {
                    return "\""+valor+"\"";
                }
                return valor;
        }
    }
}
