/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptCompiler.OperacionesARL;

import Estructuras.Nodo;
import INTERFAZ.Template;
import ScriptCompiler.Arreglo;
import ScriptCompiler.Clase;
import ScriptCompiler.Metodo;
import ScriptCompiler.Resultado;
import ScriptCompiler.Script;
import ScriptCompiler.Sentencias.Declaracion;
import ScriptCompiler.Sentencias.LlamadaMetodo;
import ScriptCompiler.Simbolo;
import ScriptCompiler.TablaSimbolo;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.JOptionPane;

/**
 *
 * @author fernando
 */
public class OperacionesARL {

    protected TablaSimbolo tabla;
    protected TablaSimbolo global;

    private int linea1 = 0;
    private int linea2 = 0;

    private int columna1 = 0;
    private int columna2 = 0;

    public Template miTemplate;

    public OperacionesARL(TablaSimbolo global, TablaSimbolo local, Template template1) {
        this.miTemplate = template1;
        this.tabla = local;
        this.global = global;
    }

    public Resultado ejecutar(Nodo nodo) {
        Resultado result = new Resultado("-1", null);
        switch (nodo.nombre) {
            /**
             * ********* EXPRESIONES LOGICAS *****
             */
            case "trn":
                linea1 = nodo.hijos.get(0).linea;
                columna1 = nodo.hijos.get(0).columna;
                linea2 = nodo.hijos.get(1).linea;
                columna2 = nodo.hijos.get(1).columna;

                result = operacionTernaria(nodo);

                break;
            case "and":
                linea1 = nodo.hijos.get(0).linea;
                columna1 = nodo.hijos.get(0).columna;
                linea2 = nodo.hijos.get(1).linea;
                columna2 = nodo.hijos.get(1).columna;

                Resultado r_and1 = ejecutar(nodo.hijos.get(0));
                Resultado r_and2 = ejecutar(nodo.hijos.get(1));
                result = operacionesLogicas(r_and1, r_and2, "AND");

                break;
            case "or":
                linea1 = nodo.hijos.get(0).linea;
                columna1 = nodo.hijos.get(0).columna;
                linea2 = nodo.hijos.get(1).linea;
                columna2 = nodo.hijos.get(1).columna;

                Resultado r_or1 = ejecutar(nodo.hijos.get(0));
                Resultado r_or2 = ejecutar(nodo.hijos.get(1));
                result = operacionesLogicas(r_or1, r_or2, "OR");

                break;
            case "not":
                linea1 = nodo.hijos.get(0).linea;
                columna1 = nodo.hijos.get(0).columna;
                linea2 = 0;
                columna2 = 0;

                Resultado r_not1 = ejecutar(nodo.hijos.get(0));
                result = operacionesLogicas(r_not1, null, "NOT");

                break;
            /**
             * ********* EXPRESIONES RELACIONALES *****
             */
            case "menq":
                linea1 = nodo.hijos.get(0).linea;
                columna1 = nodo.hijos.get(0).columna;
                linea2 = nodo.hijos.get(1).linea;
                columna2 = nodo.hijos.get(1).columna;

                Resultado r_mm1 = ejecutar(nodo.hijos.get(0));
                Resultado r_mm2 = ejecutar(nodo.hijos.get(1));
                result = operacionesRelacionales(r_mm1, r_mm2, "MENQ");

                break;
            case "meniq":
                linea1 = nodo.hijos.get(0).linea;
                columna1 = nodo.hijos.get(0).columna;
                linea2 = nodo.hijos.get(1).linea;
                columna2 = nodo.hijos.get(1).columna;

                Resultado r_mmi1 = ejecutar(nodo.hijos.get(0));
                Resultado r_mmi2 = ejecutar(nodo.hijos.get(1));
                result = operacionesRelacionales(r_mmi1, r_mmi2, "MENIQ");

                break;
            case "mayq":
                linea1 = nodo.hijos.get(0).linea;
                columna1 = nodo.hijos.get(0).columna;
                linea2 = nodo.hijos.get(1).linea;
                columna2 = nodo.hijos.get(1).columna;

                Resultado r_my1 = ejecutar(nodo.hijos.get(0));
                Resultado r_my2 = ejecutar(nodo.hijos.get(1));
                result = operacionesRelacionales(r_my1, r_my2, "MAYQ");

                break;
            case "mayiq":
                linea1 = nodo.hijos.get(0).linea;
                columna1 = nodo.hijos.get(0).columna;
                linea2 = nodo.hijos.get(1).linea;
                columna2 = nodo.hijos.get(1).columna;

                Resultado r_myi1 = ejecutar(nodo.hijos.get(0));
                Resultado r_myi2 = ejecutar(nodo.hijos.get(1));
                result = operacionesRelacionales(r_myi1, r_myi2, "MAYIQ");

                break;
            case "ig_ig":
                linea1 = nodo.hijos.get(0).linea;
                columna1 = nodo.hijos.get(0).columna;
                linea2 = nodo.hijos.get(1).linea;
                columna2 = nodo.hijos.get(1).columna;

                Resultado r_ig1 = ejecutar(nodo.hijos.get(0));
                Resultado r_ig2 = ejecutar(nodo.hijos.get(1));
                result = operacionesRelacionales(r_ig1, r_ig2, "IG_IG");

                break;
            case "dif":
                linea1 = nodo.hijos.get(0).linea;
                columna1 = nodo.hijos.get(0).columna;
                linea2 = nodo.hijos.get(1).linea;
                columna2 = nodo.hijos.get(1).columna;

                Resultado r_df1 = ejecutar(nodo.hijos.get(0));
                Resultado r_df2 = ejecutar(nodo.hijos.get(1));
                result = operacionesRelacionales(r_df1, r_df2, "DIF");

                break;
            /**
             * ********* EXPRESIONES ARITMETICAS *****
             */
            case "add":
                linea1 = nodo.hijos.get(0).linea;
                columna1 = nodo.hijos.get(0).columna;
                linea2 = 0;
                columna2 = 0;
                Resultado r_add1 = ejecutar(nodo.hijos.get(0));
                result = operacionesSimplificadas(r_add1, "ADD");
                //
                break;
            case "sub":
                linea1 = nodo.hijos.get(0).linea;
                columna1 = nodo.hijos.get(0).columna;
                linea2 = 0;
                columna2 = 0;
                Resultado r_sub1 = ejecutar(nodo.hijos.get(0));
                //System.out.println("voy a DECREMENTAR:"+r_sub1.valor);
                result = operacionesSimplificadas(r_sub1, "SUB");
                //System.out.println("===>"+result.valor);
                //imprimirResultado(result.valor);
                break;
            case "pot":
                linea1 = nodo.hijos.get(0).linea;
                columna1 = nodo.hijos.get(0).columna;
                linea2 = nodo.hijos.get(1).linea;
                columna2 = nodo.hijos.get(1).columna;
                Resultado r_pt1 = ejecutar(nodo.hijos.get(0));
                Resultado r_pt2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_pt1, r_pt2, "POT");
                //
                break;
            case "div":
                linea1 = nodo.hijos.get(0).linea;
                columna1 = nodo.hijos.get(0).columna;
                linea2 = nodo.hijos.get(1).linea;
                columna2 = nodo.hijos.get(1).columna;
                Resultado r_d1 = ejecutar(nodo.hijos.get(0));
                Resultado r_d2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_d1, r_d2, "DIV");
                //
                break;
            case "por":
                linea1 = nodo.hijos.get(0).linea;
                columna1 = nodo.hijos.get(0).columna;
                linea2 = nodo.hijos.get(1).linea;
                columna2 = nodo.hijos.get(1).columna;
                Resultado r_p1 = ejecutar(nodo.hijos.get(0));
                Resultado r_p2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_p1, r_p2, "POR");
                // 
                break;
            case "menos":
                linea1 = nodo.hijos.get(0).linea;
                columna1 = nodo.hijos.get(0).columna;
                linea2 = nodo.hijos.get(1).linea;
                columna2 = nodo.hijos.get(1).columna;
                Resultado r_m1 = ejecutar(nodo.hijos.get(0));
                Resultado r_m2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_m1, r_m2, "MENOS");
                //
                break;
            case "mas":
                linea1 = nodo.hijos.get(0).linea;
                columna1 = nodo.hijos.get(0).columna;
                linea2 = nodo.hijos.get(1).linea;
                columna2 = nodo.hijos.get(1).columna;
                Resultado r1 = ejecutar(nodo.hijos.get(0));
                Resultado r2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r1, r2, "MAS");
                //
                break;
            case "int_literal":
                result = new Resultado("Integer", Integer.parseInt(nodo.valor));
                break;
            case "nulo":
                result = new Resultado("$nulo", "nulo");
                break;

            case "double_literal":
                result = new Resultado("Double", Double.parseDouble(nodo.valor));
                break;
            case "string_literal":
                result = new Resultado("String", nodo.valor + "");
                break;
            case "bool_literal":
                if (nodo.valor.equalsIgnoreCase("true") | nodo.valor.equalsIgnoreCase("verdadero")) {
                    result = new Resultado("Boolean", true);
                } else {
                    result = new Resultado("Boolean", false);
                }
                break;

            /* - - -  - - - ARREGLOS  - -- - - - -- - - - - -- */
            case "cntarray":

                Arreglo arreglo = new Arreglo(nodo, tabla, global, miTemplate);
                if (arreglo.estado) {
                    result = new Resultado(arreglo.getClass().getSimpleName(), arreglo);
                }
                break;
            case "cntobj":
                Declaracion declara = new Declaracion(global, tabla, miTemplate);
                Clase clase = declara.crearInstanciaEstructura(nodo.get(0));
                result = new Resultado(clase.getClass().getSimpleName(), clase);
                break;
            case "Accion_Obtener":
                Nodo pto_Obtener = nodo.hijos.get(0);
                Nodo id = pto_Obtener.hijos.get(0);
                id.nombre = "id_componente";
                Resultado rrrr = ejecutar(id);
                if (rrrr != null) {
                    result = rrrr;
                }
                break;
            case "id_componente":
                Resultado res_cmp = acceso(nodo);
                if (res_cmp != null) {
                    result = res_cmp;
                }
                break;
            case "id_cmp":
                Resultado re2s_cmp = acceso(nodo);
                if (re2s_cmp != null) {
                    result = re2s_cmp;
                }
                break;

            case "acceso":
                Resultado res101 = acceso(nodo);
                linea1 = nodo.linea;
                columna1 = nodo.columna;
                if (res101 != null) {
                    result = res101;
                }
                break;

            case "accesoAr":
                Resultado res01 = acceso(nodo);
                linea1 = nodo.linea;
                columna1 = nodo.columna;
                if (res01 != null) {
                    result = res01;
                }
                break;
            case "id":
                Resultado res0 = acceso(nodo);
                linea1 = nodo.linea;
                columna1 = nodo.columna;
                if (res0 != null) {
                    result = res0;
                    if (result.tipo.equals("0")) {
                        Template.reporteError_CJS.agregar("Error Semantico", nodo.linea, nodo.columna, "La variable " + nodo.valor + " no ha sido inicialiada");
                        //result=null;
                    }
                } else {
                    //Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No existe el Simbolo: "+nodo.valor);
                }

                break;
            case "llamadaFuncion":
                Resultado callR = acceso(nodo);
                if (callR != null) {
                    result = callR;
                }
                break;
            case "unario":
                Resultado resu = ejecutar(nodo.hijos.get(0));
                if (resu != null) {
                    try {
                        if (resu.tipo.equals("Integer")) {
                            resu.valor = (Integer) resu.valor * -1;
                            result = resu;
                        } else if (resu.tipo.equals("Double")) {
                            resu.valor = (Double) resu.valor * -1.0;
                            result = resu;
                        } else {
                            Template.reporteError_CJS.agregar("Error Semantico", linea1, columna1, "Unario no aplica a tipo: " + resu.tipo);
                        }
                    } catch (Exception e) {
                    }
                }
                break;
        }
        return result;
    }

    public Resultado operacionTernaria(Nodo raiz) {
        Resultado result = new Resultado("-1", null);
        JOptionPane.showMessageDialog(null, "es operacion ternaria " + raiz.linea + "-" + raiz.columna);
        try {
            Resultado condicion = ejecutar(raiz.hijos.get(0));
            Resultado sentenciaV = ejecutar(raiz.hijos.get(1));
            Resultado sentenciaF = ejecutar(raiz.hijos.get(2));

            if (condicion.tipo.equalsIgnoreCase("Integer")) {
                if ((Integer) condicion.valor == 1) {
                    return new Resultado("Boolean", true);
                } else if ((Integer) condicion.valor == 0) {
                    return new Resultado("Boolean", false);
                }
            }
            if (condicion.tipo.equalsIgnoreCase("Boolean")) {
                if ((Boolean) condicion.valor) {
                    return sentenciaV;
                } else {
                    return sentenciaF;
                }
            } else {
                Template.reporteError_CJS.agregar("Semantico", linea1, columna1, "Solo se permiten valores booleanos en la condicion de la sentencia TERNARIA o enteros 0 o 1");
            }
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "ha ocurrido un error al ejecutar la sentencia TERNARIA:" + e.getMessage());
        }
        return result;
    }

    public Resultado operacionesLogicas(Resultado r1, Resultado r2, String op) {
        Resultado result = new Resultado("-1", null);

        if (op.equals("NOT")) {
            if (verNulabilidad(r1)) {
                return result;
            }
        } else {
            if (verNulabilidad(r1, r2)) {
                return result;
            }
        }

        if (op.equals("AND")) {
            if (r1.tipo.equals(r2.tipo) && r1.tipo.equals("Boolean")) {

                result = new Resultado("Boolean", false);
                if ((Boolean) r1.valor && (Boolean) r2.valor) {
                    result = new Resultado("Boolean", true);
                }
            } else {
                Template.reporteError_CJS.agregar("Error Semantico", linea1, columna1, "No aplica AND entre " + r1.tipo + "-" + r2.tipo);
            }
        } else if (op.equals("OR")) {
            if (r1.tipo.equals(r2.tipo) && r1.tipo.equals("Boolean")) {
                result = new Resultado("Boolean", false);
                if ((Boolean) r1.valor || (Boolean) r2.valor) {
                    result = new Resultado("Boolean", true);
                }
            } else {
                Template.reporteError_CJS.agregar("Error Semantico", linea1, columna1, "No aplica OR entre " + r1.tipo + "-" + r2.tipo);
            }
        } else if (op.equals("NOT")) {
            if (r1.tipo.equals("boolean")) {
                result = new Resultado("Boolean", false);
                if (!(Boolean) r1.valor) {
                    result = new Resultado("Boolean", true);
                }
            } else {
                Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica NOT a " + r1.tipo);
            }
        }
        return result;
    }

    public Resultado operacionesRelacionales(Resultado r1, Resultado r2, String op) {

        Resultado result = new Resultado("-1", null);
        if (verNulabilidad(r1, r2)) {
            return result;
        }
        if (op.equals("MENQ")) {
            switch (r1.tipo) {
                case "Integer":
                    switch (r2.tipo) {
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if ((Integer) r1.valor < (Integer) r2.valor) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if (((Integer) r1.valor).doubleValue() < (Double) r2.valor) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica < QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch (r2.tipo) {
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if ((Double) r1.valor < ((Integer) r2.valor).doubleValue()) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if ((Double) r1.valor < (Double) r2.valor) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica < QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "Boolean":
                    switch (r2.tipo) {
                        case "Boolean":
                            result = new Resultado("Boolean", false);
                            if (getBoolValor(r1.valor) < getBoolValor(r2.valor)) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica < QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;

                case "String":
                    switch (r2.tipo) {
                        case "String":
                            result = new Resultado("Boolean", false);
                            if (obtenerSumaCaracteres((String) r1.valor) < obtenerSumaCaracteres((String) r2.valor)) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica < QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico", linea1, columna1, "No aplica < QUE entre " + r1.tipo + "-" + r2.tipo);
                    break;
            }
        } else if (op.equals("MENIQ")) {
            switch (r1.tipo) {
                case "Integer":
                    switch (r2.tipo) {
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if ((Integer) r1.valor <= (Integer) r2.valor) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if (((Integer) r1.valor).doubleValue() <= (Double) r2.valor) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica <= QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch (r2.tipo) {
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if ((Double) r1.valor <= ((Integer) r2.valor).doubleValue()) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if ((Double) r1.valor <= (Double) r2.valor) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica <= QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "Boolean":
                    switch (r2.tipo) {
                        case "Boolean":
                            result = new Resultado("Boolean", false);
                            if (getBoolValor(r1.valor) <= getBoolValor(r2.valor)) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica <= QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;

                case "String":
                    switch (r2.tipo) {
                        case "String":
                            result = new Resultado("Boolean", false);
                            if (obtenerSumaCaracteres((String) r1.valor) <= obtenerSumaCaracteres((String) r2.valor)) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica <= QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico", linea1, columna1, "No aplica <= QUE entre " + r1.tipo + "-" + r2.tipo);
                    break;
            }
        } else if (op.equals("MAYQ")) {
            switch (r1.tipo) {
                case "Integer":
                    switch (r2.tipo) {
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if ((Integer) r1.valor > (Integer) r2.valor) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if (((Integer) r1.valor).doubleValue() > (Double) r2.valor) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica > QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch (r2.tipo) {
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if ((Double) r1.valor > ((Integer) r2.valor).doubleValue()) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if ((Double) r1.valor > (Double) r2.valor) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica > QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "Boolean":
                    switch (r2.tipo) {
                        case "Boolean":
                            result = new Resultado("Boolean", false);
                            if (getBoolValor(r1.valor) > getBoolValor(r2.valor)) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica > QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;

                case "String":
                    switch (r2.tipo) {
                        case "String":
                            result = new Resultado("Boolean", false);
                            if (obtenerSumaCaracteres((String) r1.valor) > obtenerSumaCaracteres((String) r2.valor)) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica > QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico", linea1, columna1, "No aplica > QUE entre " + r1.tipo + "-" + r2.tipo);
                    break;
            }
        } else if (op.equals("MAYIQ")) {
            switch (r1.tipo) {
                case "Integer":
                    switch (r2.tipo) {
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if ((Integer) r1.valor >= (Integer) r2.valor) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if (((Integer) r1.valor).doubleValue() >= (Double) r2.valor) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica >= QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch (r2.tipo) {
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if ((Double) r1.valor >= ((Integer) r2.valor).doubleValue()) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if ((Double) r1.valor >= (Double) r2.valor) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica >= QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "Boolean":
                    switch (r2.tipo) {
                        case "Boolean":
                            result = new Resultado("Boolean", false);
                            if (getBoolValor(r1.valor) >= getBoolValor(r2.valor)) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica >= QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;

                case "String":
                    switch (r2.tipo) {
                        case "String":
                            result = new Resultado("Boolean", false);
                            if (obtenerSumaCaracteres((String) r1.valor) >= obtenerSumaCaracteres((String) r2.valor)) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica >= QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico", linea1, columna1, "No aplica >= QUE entre " + r1.tipo + "-" + r2.tipo);
                    break;
            }
        } else if (op.equals("IG_IG")) {
            switch (r1.tipo) {
                case "Integer":
                    switch (r2.tipo) {
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if (Objects.equals((Integer) r1.valor, (Integer) r2.valor)) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if (((Integer) r1.valor).doubleValue() == (Double) r2.valor) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "$nulo":
                            result = new Resultado("Boolean", false);
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica == QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch (r2.tipo) {
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if ((Double) r1.valor == ((Integer) r2.valor).doubleValue()) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if (Objects.equals((Double) r1.valor, (Double) r2.valor)) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "$nulo":
                            result = new Resultado("Boolean", false);
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica == QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "Boolean":
                    switch (r2.tipo) {
                        case "Boolean":
                            result = new Resultado("Boolean", false);
                            if (Objects.equals(getBoolValor(r1.valor), getBoolValor(r2.valor))) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "$nulo":
                            result = new Resultado("Boolean", false);
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica == QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;

                case "String":
                    switch (r2.tipo) {
                        case "String":
                            result = new Resultado("Boolean", false);
                            if (Objects.equals(obtenerSumaCaracteres((String) r1.valor), obtenerSumaCaracteres((String) r2.valor))) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "$nulo":
                            result = new Resultado("Boolean", false);
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica == QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "$nulo":
                    switch (r2.tipo) {
                        case "$nulo":
                            result = new Resultado("Boolean", true);
                            break;
                        default:
                            result = new Resultado("Boolean", false);
                            break;
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico", linea1, columna1, "No aplica == QUE entre " + r1.tipo + "-" + r2.tipo);
                    break;
            }
        } else if (op.equals("DIF")) {
            switch (r1.tipo) {
                case "Integer":
                    switch (r2.tipo) {
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if (!Objects.equals((Integer) r1.valor, (Integer) r2.valor)) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if (((Integer) r1.valor).doubleValue() != (Double) r2.valor) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "$nulo":
                            result = new Resultado("Boolean", true);
                            break;

                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica != QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch (r2.tipo) {
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if ((Double) r1.valor != ((Integer) r2.valor).doubleValue()) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if (!Objects.equals((Double) r1.valor, (Double) r2.valor)) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "$nulo":
                            result = new Resultado("Boolean", true);
                            break;

                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica != QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "Boolean":
                    switch (r2.tipo) {
                        case "Boolean":
                            result = new Resultado("Boolean", false);
                            if (!Objects.equals(getBoolValor(r1.valor), getBoolValor(r2.valor))) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "$nulo":
                            result = new Resultado("Boolean", true);
                            break;

                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica != QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;

                case "String":
                    switch (r2.tipo) {
                        case "String":
                            result = new Resultado("Boolean", false);
                            if (!Objects.equals(obtenerSumaCaracteres((String) r1.valor), obtenerSumaCaracteres((String) r2.valor))) {
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "$nulo":
                            result = new Resultado("Boolean", true);
                            break;

                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica != QUE entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "$nulo":
                    switch (r2.tipo) {
                        case "$nulo":
                            result = new Resultado("Boolean", false);
                            break;
                        default:
                            result = new Resultado("Boolean", true);
                            break;
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico", linea1, columna1, "No aplica != QUE entre " + r1.tipo + "-" + r2.tipo);
                    break;
            }
        }
        return result;
    }

    public Resultado operacionesAritmeticas(Resultado r1, Resultado r2, String op) {
        Resultado result = new Resultado("-1", null);
        if (verNulabilidad(r1, r2)) {
            return result;
        }

        Object valor;
        if (op.equals("MAS")) {
            switch (r1.tipo) {
                case "Integer":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = (Integer) r1.valor + (Integer) r2.valor;
                            result = new Resultado("Integer", valor);
                            break;
                        case "Double":
                            valor = ((Integer) r1.valor).doubleValue() + (Double) r2.valor;
                            result = new Resultado("Double", valor);
                            break;
                        case "Boolean":
                            valor = (Integer) r1.valor + getBoolValor(r2.valor);
                            result = new Resultado("Integer", valor);
                            break;
                        case "String":
                            valor = ((Integer) r1.valor).toString() + (String) r2.valor;
                            result = new Resultado("String", valor);
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Suma entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = (Double) r1.valor + ((Integer) r2.valor).doubleValue();
                            result = new Resultado("Double", valor);
                            break;
                        case "Double":
                            valor = (Double) r1.valor + (Double) r2.valor;
                            result = new Resultado("Double", valor);
                            break;
                        case "Boolean":
                            valor = (Double) r1.valor + getBoolValor(r2.valor).doubleValue();
                            result = new Resultado("Double", (Double) valor);
                            break;
                        case "String":
                            valor = ((Double) r1.valor).toString() + (String) r2.valor;
                            result = new Resultado("String", valor);
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Suma entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "Boolean":
                    switch (r2.tipo) {
                        case "String":
                            result = new Resultado("String", ((Boolean) r1.valor).toString() + (String) r2.valor);
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Suma entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "String":
                    switch (r2.tipo) {
                        case "Integer":
                            result = new Resultado("String", (String) r1.valor + ((Integer) r2.valor).toString());
                            break;
                        case "Double":
                            result = new Resultado("String", (String) r1.valor + ((Double) r2.valor).toString());
                            break;
                        case "Boolean":
                            result = new Resultado("String", (String) r1.valor + ((Boolean) r2.valor).toString());
                            break;
                        case "String":
                            result = new Resultado("String", (String) r1.valor + (String) r2.valor);
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Suma entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico", linea1, columna1, "No aplica Suma entre " + r1.tipo + "-" + r2.tipo);
                    break;
            }
        } else if (op.equals("MENOS")) {
            switch (r1.tipo) {
                case "Integer":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = (Integer) r1.valor - (Integer) r2.valor;
                            result = new Resultado("Integer", valor);
                            break;
                        case "Double":
                            valor = ((Integer) r1.valor).doubleValue() - (Double) r2.valor;
                            result = new Resultado("Double", valor);
                            break;
                        case "String":
                            if (((String) r2.valor).length() == 1) {
                                valor = (Integer) r1.valor - obtenerSumaCaracteres((String) r2.valor);
                                result = new Resultado("Integer", valor);
                            } else {
                                Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Resta entre " + r1.tipo + "-" + r2.tipo);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Resta entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = (Double) r1.valor - (Integer) r2.valor;
                            result = new Resultado("Integer", valor);
                            break;
                        case "Double":
                            valor = (Double) r1.valor - (Double) r2.valor;
                            result = new Resultado("Double", valor);
                            break;
                        case "String":
                            if (((String) r2.valor).length() == 1) {
                                valor = (Double) r1.valor - Double.parseDouble(String.valueOf(obtenerSumaCaracteres((String) r2.valor)));
                                result = new Resultado("Double", valor);
                            } else {
                                Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Resta entre " + r1.tipo + "-" + r2.tipo);
                            }

                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Resta entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico", linea1, columna1, "No aplica Resta entre " + r1.tipo + "-" + r2.tipo);
                    break;
            }
        } else if (op.equals("POR")) {
            switch (r1.tipo) {
                case "Integer":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = (Integer) r1.valor * (Integer) r2.valor;
                            result = new Resultado("Integer", valor);
                            break;
                        case "Double":
                            valor = ((Integer) r1.valor).doubleValue() * (Double) r2.valor;
                            result = new Resultado("Double", valor);
                            break;
                        case "String":
                            if (((String) r2.valor).length() == 1) {
                                valor = (Integer) r1.valor * obtenerSumaCaracteres((String) r2.valor);
                                result = new Resultado("Integer", valor);
                            } else {
                                Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Multiplicacion entre " + r1.tipo + "-" + r2.tipo);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Multiplicacion entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = (Double) r1.valor * ((Integer) r2.valor).doubleValue();
                            result = new Resultado("Double", valor);
                            break;
                        case "Double":
                            valor = (Double) r1.valor * (Double) r2.valor;
                            result = new Resultado("Double", valor);
                            break;
                        case "String":
                            if (((String) r2.valor).length() == 1) {
                                valor = (Double) r1.valor * obtenerSumaCaracteres((String) r2.valor).doubleValue();
                                result = new Resultado("Double", valor);
                            } else {
                                Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Multiplicacion entre " + r1.tipo + "-" + r2.tipo);
                            }

                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Suma Multiplicacion " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;

                default:
                    Template.reporteError_CJS.agregar("Error Semantico", linea1, columna1, "No aplica Multiplicacion entre " + r1.tipo + "-" + r2.tipo);
                    break;
            }
        } else if (op.equals("DIV")) {

            if (siDivisorZero(r2)) {
                return result;
            }
            switch (r1.tipo) {

                case "Integer":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = ((Integer) r1.valor).doubleValue() / ((Integer) r2.valor).doubleValue();
                            result = new Resultado("Double", valor);
                            break;
                        case "Double":
                            valor = ((Integer) r1.valor).doubleValue() / (Double) r2.valor;
                            result = new Resultado("Double", valor);
                            break;
                        case "String":
                            if (((String) r2.valor).length() == 1) {
                                valor = ((Integer) r1.valor).doubleValue() / obtenerSumaCaracteres((String) r2.valor).doubleValue();
                                result = new Resultado("Double", valor);
                            } else {
                                Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Division entre " + r1.tipo + "-" + r2.tipo);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Division entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = (Double) r1.valor / ((Integer) r2.valor).doubleValue();
                            result = new Resultado("Double", valor);
                            break;
                        case "Double":
                            valor = (Double) r1.valor / (Double) r2.valor;
                            result = new Resultado("Double", valor);
                            break;
                        case "String":
                            if (((String) r2.valor).length() == 1) {
                                valor = (Double) r1.valor / obtenerSumaCaracteres((String) r2.valor).doubleValue();
                                result = new Resultado("Double", valor);
                            } else {
                                Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Division entre " + r1.tipo + "-" + r2.tipo);
                            }

                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Division Multiplicacion " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;

                default:
                    Template.reporteError_CJS.agregar("Error Semantico", linea1, columna1, "No aplica Division entre " + r1.tipo + "-" + r2.tipo);
                    break;
            }
        } else if (op.equals("POT")) {
            switch (r1.tipo) {
                case "Integer":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = (Math.pow(((Integer) r1.valor).doubleValue(), ((Integer) r2.valor).doubleValue()));
                            result = new Resultado("Integer", ((Double) valor).intValue());
                            break;
                        case "Double":
                            valor = Math.pow(((Integer) r1.valor).doubleValue(), (Double) r2.valor);
                            result = new Resultado("Double", valor);
                            break;
                        case "Boolean":
                            valor = Math.pow(((Integer) r1.valor).doubleValue(), getBoolValor(r2.valor).doubleValue());
                            result = new Resultado("Integer", ((Double) valor).intValue());
                            break;
                        case "String":
                            if (((String) r2.valor).length() == 1) {
                                valor = Math.pow(((Integer) r1.valor).doubleValue(), obtenerSumaCaracteres((String) r2.valor).doubleValue());
                                result = new Resultado("Integer", ((Double) valor).intValue());
                            } else {
                                Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Suma entre " + r1.tipo + "-" + r2.tipo);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Suma entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = Math.pow((Double) r1.valor, ((Integer) r2.valor).doubleValue());
                            result = new Resultado("Double", valor);
                            break;
                        case "Double":
                            valor = Math.pow((Double) r1.valor, (Double) r2.valor);
                            result = new Resultado("Double", valor);
                            break;
                        case "Boolean":
                            valor = Math.pow((Double) r1.valor, getBoolValor(r2.valor).doubleValue());
                            result = new Resultado("Double", valor);
                            break;
                        case "String":
                            if (((String) r2.valor).length() == 1) {
                                valor = Math.pow((Double) r1.valor, obtenerSumaCaracteres((String) r2.valor).doubleValue());
                                result = new Resultado("Double", valor);
                            } else {
                                Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Suma entre " + r1.tipo + "-" + r2.tipo);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Suma entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;

                case "Boolean":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = Math.pow(getBoolValor(r1.valor).doubleValue(), ((Integer) r2.valor).doubleValue());
                            result = new Resultado("Integer", ((Double) valor).intValue());
                            break;
                        case "Double":
                            valor = Math.pow(getBoolValor(r1.valor).doubleValue(), (Double) r2.valor);
                            result = new Resultado("Integer", ((Double) valor).intValue());
                            break;
                        case "Boolean":
                            valor = Math.pow(getBoolValor(r1.valor).doubleValue(), getBoolValor(r2.valor).doubleValue());
                            result = new Resultado("Integer", ((Double) valor).intValue());
                            break;
                        case "String":
                            if (((String) r2.valor).length() == 1) {
                                valor = Math.pow(getBoolValor(r1.valor).doubleValue(), obtenerSumaCaracteres((String) r2.valor).doubleValue());
                                result = new Resultado("Integer", ((Double) valor).intValue());
                            } else {
                                Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Suma entre " + r1.tipo + "-" + r2.tipo);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico", linea2, columna2, "No aplica Suma entre " + r1.tipo + "-" + r2.tipo);
                            break;
                    }
                    break;

                default:
                    Template.reporteError_CJS.agregar("Error Semantico", linea1, columna1, "No aplica Suma entre " + r1.tipo + "-" + r2.tipo);
                    break;
            }
        }
        return result;
    }

    public Resultado operacionesSimplificadas(Resultado r1, String op) {

        Resultado resultado = new Resultado("-1", null);
        if (verNulabilidad(r1)) {
            return resultado;
        }

        Object valor;
        if (op.equals("ADD")) {
            if (r1.tipo.equals("Integer")) {
                valor = (Integer) r1.valor + 1;
                resultado = new Resultado("Integer", valor);
            } else if (r1.tipo.equals("Double")) {
                valor = (Double) r1.valor + 1;
                resultado = new Resultado("Double", valor);
            } else {
                Template.reporteError_CJS.agregar("Error Semantico", linea1, columna1, "No aplica Incremento a " + r1.tipo);
            }
        } else if (op.equals("SUB")) {
            if (r1.tipo.equals("Integer")) {
                valor = (Integer) r1.valor - 1;
                resultado = new Resultado("Integer", valor);
            } else if (r1.tipo.equals("Double")) {
                valor = (Double) r1.valor - 1;
                resultado = new Resultado("Double", valor);
            } else {
                Template.reporteError_CJS.agregar("Error Semantico", linea1, columna1, "No aplica Decremento a " + r1.tipo);
            }
        }
        return resultado;
    }

    public Resultado acceso(Nodo raiz) {

        //JOptionPane.showMessageDialog(null, "dame");
        Clase aux = Script.claseActual;
        TablaSimbolo tablaAux = tabla;
        Resultado retorno = new Resultado("-1", null);
        //----------------------------------------------------------------------
        int nivel = 0;

        for (int x = 0; x < raiz.size(); x++) {
            Nodo acceso = raiz.get(x);
            String nombre;
            Simbolo simbolo;
            switch (acceso.nombre) {
                case "accesoar":
                    aux.tabla = tabla;
                    tabla = tablaAux;
                    Resultado nR = new Resultado("-1", null);
                    Resultado tmpRet = accesoAr(acceso, nivel, aux);
                    switch (tmpRet.tipo) {
                        case "Integer":
                        case "Double":
                        case "String":
                        case "Boolean":
                        case "$nulo":
                            //if (x == raiz.size() - 1) {
                            nR.valor = tmpRet.valor;
                            nR.tipo = tmpRet.tipo;
                            if (tmpRet.simbolo != null) {
                                nR.simbolo = tmpRet.simbolo;
                            }
                            retorno = nR;
                            //}

                            break;
                        default:
                            try {
                                nivel++;
                                aux = (Clase) tmpRet.valor;
                                tabla = aux.tabla;

                                //if (x == raiz.size() - 1) {
                                nR.valor = tmpRet.valor;
                                nR.tipo = tmpRet.tipo;
                                if (tmpRet.simbolo != null) {
                                    nR.simbolo = tmpRet.simbolo;
                                }
                                //}
                                retorno = nR;
                            } catch (Exception ex) {
                            }
                            break;
                    }
                    break;
                case "id_cmp":
                    nombre = acceso.valor;
                    simbolo = tabla.getSimbolo(nombre, aux);
                    if (simbolo != null) {
                        if (simbolo.inicializado) {
                            switch (simbolo.tipo) {
                                case "opcion":
                                case "imagen":
                                case "boton":
                                case "enlace":
                                case "panel":
                                case "spinner":
                                case "tabla":
                                case "caja":
                                case "cajaopciones":
                                case "cajatexto":
                                case "texto":
                                case "areatexto":
                                    retorno.valor = simbolo.valor;
                                    retorno.tipo = simbolo.tipo;
                                    retorno.simbolo = simbolo;
                                    break;
                            }
                        } else {
                            retorno.tipo = "";
                            retorno.valor = null;
                            Template.reporteError_CJS.agregar("Semantico", acceso.linea, acceso.columna, "La variable " + nombre + " no ha sido inicializada");
                            return retorno;
                        }
                    }
                    break;
                case "id_componente":
                    /*nombre = raiz.valor;
                if(miTemplate.lista_componentes.containsKey(nombre)){
                    Componente componente=miTemplate.lista_componentes.get(nombre);
                    retorno.valor = componente;
                    retorno.tipo  = componente.tipo;
                    retorno.simbolo = null;
                }else {
                    //retorno.tipo = "-1";
                    //retorno.valor = null;
                    Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " no existe en la lista de componentes CHTML");
                    return null;
                }*/
                    break;
                case "id":
                    Resultado nuevoR = new Resultado("-1", null);

                    nombre = acceso.valor;
                    simbolo = tabla.getSimbolo(nombre, aux);
                    if (simbolo != null) {
                        if (simbolo.inicializado) {
                            switch (simbolo.tipo) {
                                case "Integer":
                                case "Double":
                                case "String":
                                case "Boolean":
                                case "$nulo":

                                    nuevoR.valor = simbolo.valor;
                                    nuevoR.tipo = simbolo.tipo;
                                    nuevoR.simbolo = simbolo;
                                    break;

                                default:
                                    try {
                                        nivel++;
                                        if (!simbolo.esArreglo) {
                                            aux = (Clase) simbolo.valor;
                                            tabla = aux.tabla;
                                            nuevoR.tipo = simbolo.tipo;
                                            nuevoR.valor = simbolo.valor;
                                            nuevoR.simbolo = simbolo;
                                        }
                                    } catch (Exception e) {
                                    }

                                    break;
                            }
                            retorno = nuevoR;
                        } else {
                            Template.reporteError_CJS.agregar("Semantico", acceso.linea, acceso.columna, "La variable " + nombre + " no ha sido inicializada");
                            return nuevoR;
                        }
                    } else {
                        Template.reporteError_CJS.agregar("Semantico", acceso.linea, acceso.columna, "La variable " + nombre + " no existe en el ambito donde fue invocada");
                        return nuevoR;

                    }
                    break;

                case "llamada":
                    LlamadaMetodo llamada = new LlamadaMetodo(aux, nivel, retorno, acceso);

                    /*
                    Proceder es una variable tipo booleano que me indica si la llamada a metodo fue una funcion nativa
                     */
                    if (llamada.proceder) {
                        Metodo metodo = null;
                        try {
                            metodo = llamada.ejecutar(acceso);
                        } catch (Exception e) {
                            Template.reporteError_CJS.agregar("Ejecucion", acceso.linea, acceso.columna, " Error al ejecutar el metodo " + e.getMessage());
                        }
                        if (metodo != null) {
                            if (metodo.retorno != null) {
                                retorno = metodo.retorno;
                                metodo.estadoRetorno = false;
                                if (!retorno.tipo.equalsIgnoreCase("String") && !retorno.tipo.equalsIgnoreCase("Integer") && !retorno.tipo.equalsIgnoreCase("Double") && !retorno.tipo.equalsIgnoreCase("Boolean") && !retorno.tipo.equalsIgnoreCase("")) {
                                    try {
                                        aux = (Clase) retorno.valor;
                                        tabla = aux.tabla;
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }
                    } else {
                        if (llamada.res_nativas != null) {
                            retorno = llamada.res_nativas;
                        }
                        if (llamada.ComponenteRes != null) {
                            retorno = llamada.ComponenteRes;
                        }
                    }

                    if (!verNulabilidad(retorno)) {
                        if (esClase(retorno.valor)) {
                            Clase clase = (Clase) retorno.valor;

                            if (!clase.Inicializada) {
                                clase.nombre = "";
                                clase.ejecutar(miTemplate);
                                clase.Inicializada = true;
                            }

                            try {
                                nivel++;
                                aux = clase;
                                tabla = aux.tabla;
                            } catch (Exception e) {
                            }

                        }
                    }

                    break;
            }
        }

        tabla = tablaAux;
        return retorno;
    }

    private Resultado accesoAr(Nodo raiz, int nivel, Clase aux) {
        Resultado resultado = new Resultado("-1", null);
        Simbolo simbolo;
        simbolo = aux.tabla.getSimbolo((String) raiz.valor, aux);
        if (simbolo != null) {
            if (simbolo.inicializado) {
                if (simbolo.esArreglo) {
                    Arreglo arreglo = (Arreglo) simbolo.valor;
                    ArrayList<Integer> indices = new ArrayList<>();
                    OperacionesARL opL = new OperacionesARL(global, tabla, miTemplate);

                    /*__ Se obtiene el primer indice de la lista de indices ___ */
                    Resultado indice = opL.ejecutar(raiz.get(0).get(0));
                    if (!verNulabilidad(indice)) {
                        if (indice.tipo.equals("Integer")) {
                            Integer iii = (Integer) indice.valor;
                            indices.add(iii);
                        }
                    } else {
                        Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Solo se permiten valores enteros al acceder a un indce de un arreglo");
                        return resultado;
                    }

                    /*Se pregunta si la cantidad de indices a acceder es mayor a 1*/
                    if (raiz.get(0).size() > 1) {
                        Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "Para acceder a un valor de un arreglo solo se permite que sea una dimension: arr[n]");
                    }

                    Object valor = arreglo.getValor(indices);
                    if (valor != null) {

                        Resultado ree = (Resultado) valor;
                        resultado = ree;
                    } else {
                        Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "No se pudo acceder al indice del arreglo: " + simbolo.nombre + " Indice fuera del limite" + "[" + indices.get(0) + "]");
                    }
                } else {
                    Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + raiz.valor + " no es arreglo");
                }
            } else {
                Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + raiz.valor + " no ha sido inicializada");

            }
        }
        return resultado;
    }

    public Integer getBoolValor(Object objeto) {
        Boolean valor = (Boolean) objeto;
        if (valor) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getStringValor(Object objeto, Nodo raiz) {
        String valor = (String) objeto;
        try {
            return Integer.parseInt(valor);
        } catch (Exception e) {
            //Inicio.reporteError.agregar("Semantico", raiz.linea, raiz.columna, "No se pudo castear la cadena a tipo Bool");
            return -1;
        }
    }

    public boolean verNulabilidad(Resultado r1, Resultado r2) {

        if (r1 == null || r2 == null) {
            return true;
        } else {
            if (r1.tipo.equals("-1") || r2.tipo.equals("-1")) {
                return true;
            } else {
                if (r1.valor == null || r2.valor == null) {
                    return true;
                } else {
                    return false;
                }
            }

        }

    }

    public boolean verNulabilidad(Resultado r1) {

        if (r1 == null) {
            return true;
        } else {
            if (r1.tipo.equals("-1")) {
                return true;
            } else {
                if (r1.valor == null) {
                    return true;
                } else {
                    return false;
                }
            }

        }

    }

    public boolean siDivisorZero(Resultado r) {
        String tipo = r.tipo;
        switch (tipo) {
            case "Integer":
                if ((Integer) r.valor == 0) {
                    Template.reporteError_CJS.agregar("Semantico", linea2, columna2, "Divisor es 0");

                    return true;
                } else {
                    return false;
                }
            case "Double":
                if ((Double) r.valor == 0.0) {
                    Template.reporteError_CJS.agregar("Semantico", linea2, columna2, "Divisor es 0");

                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }

    }

    public Integer obtenerSumaCaracteres(String cadena) {
        Integer suma = 0;
        try {
            for (int i = 0; i < cadena.length(); i++) {
                suma += cadena.codePointAt(i);
            }
        } catch (Exception e) {
            Template.reporteError_CJS.agregar("Error Ejecucion", linea1, columna1, "Error al extraer ascii de la cadena [" + cadena + "]");
        }
        return suma;
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
