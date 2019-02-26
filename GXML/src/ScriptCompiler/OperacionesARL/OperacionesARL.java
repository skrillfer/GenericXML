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
import ScriptCompiler.Resultado;
import ScriptCompiler.Script;
import ScriptCompiler.Simbolo;
import ScriptCompiler.TablaSimbolo;
import java.util.ArrayList;

/**
 *
 * @author fernando
 */
public class OperacionesARL {
    protected TablaSimbolo tabla;
    protected TablaSimbolo global;
    
    private int linea1=0;
    private int linea2=0;
    
    private int columna1=0;
    private int columna2=0;
    
    
    public Template miTemplate;
      
     
    public OperacionesARL(TablaSimbolo global, TablaSimbolo local,Template template1) {
        this.miTemplate=template1;
        this.tabla = local;
        this.global = global;
    }   

     public Resultado ejecutar(Nodo nodo){
        Resultado result=null;
        switch(nodo.nombre){
            /***********          EXPRESIONES LOGICAS                    ******/    
            case "and":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(1).columna;
                
                Resultado r_and1 = ejecutar(nodo.hijos.get(0));
                Resultado r_and2 = ejecutar(nodo.hijos.get(1));
                result = operacionesLogicas(r_and1, r_and2, "AND");
                
                break;
            case "or":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(1).columna;
                
                Resultado r_or1 = ejecutar(nodo.hijos.get(0));
                Resultado r_or2 = ejecutar(nodo.hijos.get(1));
                result = operacionesLogicas(r_or1, r_or2, "OR");
                
                break;
            case "not":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2=0;        columna2=0;
                
                Resultado r_not1 = ejecutar(nodo.hijos.get(0));
                result = operacionesLogicas(r_not1, null, "NOT");
                
                break;
            /***********          EXPRESIONES RELACIONALES               ******/
            case "menq":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(1).columna;
                
                Resultado r_mm1 = ejecutar(nodo.hijos.get(0));
                Resultado r_mm2 = ejecutar(nodo.hijos.get(1));
                result = operacionesRelacionales(r_mm1, r_mm2, "MENQ");
                
                break;
            case "meniq":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(1).columna;
                
                Resultado r_mmi1 = ejecutar(nodo.hijos.get(0));
                Resultado r_mmi2 = ejecutar(nodo.hijos.get(1));
                result = operacionesRelacionales(r_mmi1, r_mmi2, "MENIQ");
                
                break;
            case "mayq":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(1).columna;
                
                Resultado r_my1 = ejecutar(nodo.hijos.get(0));
                Resultado r_my2 = ejecutar(nodo.hijos.get(1));
                result = operacionesRelacionales(r_my1, r_my2, "MAYQ");
                
                break;
            case "mayiq":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(1).columna;
                
                Resultado r_myi1 = ejecutar(nodo.hijos.get(0));
                Resultado r_myi2 = ejecutar(nodo.hijos.get(1));
                result = operacionesRelacionales(r_myi1, r_myi2, "MAYIQ");
                
                break;
            case "ig_ig":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(1).columna;
                
                Resultado r_ig1 = ejecutar(nodo.hijos.get(0));
                Resultado r_ig2 = ejecutar(nodo.hijos.get(1));
                result = operacionesRelacionales(r_ig1, r_ig2, "IG_IG");
                
                break;
            case "dif":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(1).columna;
                
                Resultado r_df1 = ejecutar(nodo.hijos.get(0));
                Resultado r_df2 = ejecutar(nodo.hijos.get(1));
                result = operacionesRelacionales(r_df1, r_df2, "DIF");
                
                break;
            /***********          EXPRESIONES ARITMETICAS                ******/        
            case "add":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2=0;        columna2=0;
                Resultado r_add1 = ejecutar(nodo.hijos.get(0));
                result = operacionesSimplificadas(r_add1, "ADD");
                //
                break;
            case "sub":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2=0;        columna2=0;
                Resultado r_sub1 = ejecutar(nodo.hijos.get(0));
                //System.out.println("voy a DECREMENTAR:"+r_sub1.valor);
                result = operacionesSimplificadas(r_sub1, "SUB");
                //System.out.println("===>"+result.valor);
                //imprimirResultado(result.valor);
                break;
            case "pot":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(1).columna;
                Resultado r_pt1 = ejecutar(nodo.hijos.get(0));
                Resultado r_pt2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_pt1, r_pt2, "POT");
                //
                break;
            case "div":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(1).columna;
                Resultado r_d1 = ejecutar(nodo.hijos.get(0));
                Resultado r_d2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_d1, r_d2, "DIV");
                //
                break;
            case "por":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(1).columna;
                Resultado r_p1 = ejecutar(nodo.hijos.get(0));
                Resultado r_p2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_p1, r_p2, "POR");
               // 
                break;
            case "menos":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(1).columna;
                Resultado r_m1 = ejecutar(nodo.hijos.get(0));
                Resultado r_m2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r_m1, r_m2, "MENOS");
                //
                break; 
            case "mas":
                linea1=nodo.hijos.get(0).linea;         columna1=nodo.hijos.get(0).columna;
                linea2= nodo.hijos.get(1).linea;        columna2=nodo.hijos.get(1).columna;
                Resultado r1 = ejecutar(nodo.hijos.get(0));
                Resultado r2 = ejecutar(nodo.hijos.get(1));
                result = operacionesAritmeticas(r1, r2, "MAS");
                //
                break;    
            case "int_literal":
                result=new Resultado("Integer", Integer.parseInt(nodo.valor));
                break;
            case "nulo":
                result=new Resultado("0nulo","nulo");
                break;
                
            case "double_literal":
                result=new Resultado("Double", Double.parseDouble(nodo.valor));
                break;    
            case "string_literal":
                result = new Resultado("String", nodo.valor + "");
                break;    
            case "bool_literal":
                if(nodo.valor.equalsIgnoreCase("true") | nodo.valor.equalsIgnoreCase("verdadero")){
                    result=new Resultado("Boolean", true);
                }else{
                    result=new Resultado("Boolean", false);
                }
                break;
            case "Accion_Obtener":
                Nodo pto_Obtener = nodo.hijos.get(0);
                Nodo id = pto_Obtener.hijos.get(0);
                id.nombre="id_componente";
                Resultado rrrr = ejecutar(id);
                if(rrrr!=null){
                    result=rrrr;
                }
                break;    
            case "id_componente":
                Resultado res_cmp=acceso(nodo);
                if(res_cmp!=null){
                    result=res_cmp;
                }
                break;
            case "id_cmp":
                Resultado re2s_cmp=acceso(nodo);
                if(re2s_cmp!=null){
                    result=re2s_cmp;
                }
                break;    
            
            case "accesoAr":
                Resultado res01=acceso(nodo);
                linea1=nodo.linea;
                columna1=nodo.columna;
                if(res01!=null){
                    result=res01;
                }
                break;
            case "id":
                Resultado res0=acceso(nodo);
                linea1=nodo.linea;
                columna1=nodo.columna;
                if(res0!=null){
                    result=res0;
                    if(result.tipo.equals("0")){
                        Template.reporteError_CJS.agregar("Error Semantico",nodo.linea, nodo.columna,"La variable " + nodo.valor + " no ha sido inicialiada");
                        //result=null;
                    }
                }else{
                    //Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No existe el Simbolo: "+nodo.valor);
                }
                
                break;
            case "llamadaFuncion":
                Resultado callR = acceso(nodo);
                if(callR!=null){
                    result = callR;
                }
                break;
            case "unitario":
                Resultado resu=ejecutar(nodo.hijos.get(0));
                if(resu!=null){
                    try {
                        if(resu.tipo.equals("number")){
                            resu.valor = (Double)resu.valor *-1.0;
                            result = resu;
                        }else{
                            Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "Unitario no aplica a tipo: "+resu.tipo);
                        }
                    } catch (Exception e) {}
                }
                break;
        }
        return result;
    }
     
     
     public Resultado operacionesLogicas(Resultado r1, Resultado r2, String op){
        Resultado result = new Resultado("-1", null);
        
        if(op.equals("NOT")){
            if(r1==null){
                return result;
            }
        }else{
            if(verNulabilidad(r1, r2)){
                return result;
            }
       }
        
        if(op.equals("AND")){
            if(r1.tipo.equals(r2.tipo) && r1.tipo.equals("Boolean")){
                
                result = new Resultado("Boolean", false);
                if((Boolean)r1.valor  && (Boolean)r2.valor){
                    result = new Resultado("Boolean", true);
                }
            }else{
                Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica AND entre "+r1.tipo+"-"+r2.tipo);
            }
        }else if(op.equals("OR")){
            if(r1.tipo.equals(r2.tipo) && r1.tipo.equals("Boolean")){
                result = new Resultado("Boolean", false);
                if((Boolean)r1.valor  || (Boolean)r2.valor){
                    result = new Resultado("Boolean", true);
                }
            }else{
               Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica OR entre "+r1.tipo+"-"+r2.tipo);
            }
        }else if(op.equals("NOT")){
            if(r1.tipo.equals("boolean")){
               result = new Resultado("Boolean", false);
                if(!(Boolean)r1.valor){
                    result = new Resultado("Boolean", true);
                }
            }else{
                Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica NOT a "+r1.tipo);
            }
        }
        return result;
    }
    
    
    public Resultado operacionesRelacionales(Resultado r1, Resultado r2, String op){
        if(verNulabilidad(r1, r2)){
            return null;
        }
        Resultado result = null;
        if(op.equals("MENQ")){
            switch(r1.tipo){
                case "Integer":
                    switch(r2.tipo){
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if((Integer)r1.valor < (Integer)r2.valor){
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if( ((Integer)r1.valor).doubleValue() < (Double)r2.valor){
                                result = new Resultado("Boolean", true);
                            }
                            break;    
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch(r2.tipo){
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if((Double)r1.valor <  ((Integer)r2.valor).doubleValue()){
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if( (Double)r1.valor < (Double)r2.valor){
                                result = new Resultado("Boolean", true);
                            }
                            break;    
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                case "Boolean":
                    switch(r2.tipo){
                        case "Boolean":
                            result = new Resultado("Boolean", false);
                            if( getBoolValor(r1.valor) <  getBoolValor(r2.valor)){
                                result = new Resultado("Boolean", true);
                            }
                            break; 
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                
                case "String":
                    switch(r2.tipo){
                        case "String":
                            result = new Resultado("Boolean", false);
                            if(  obtenerSumaCaracteres((String) r1.valor) < obtenerSumaCaracteres((String) r2.valor) ){
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;    
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                    break;
            }
        }else if(op.equals("MENIQ")){
            switch(r1.tipo){
                case "Integer":
                    switch(r2.tipo){
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if((Integer)r1.valor <= (Integer)r2.valor){
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if( ((Integer)r1.valor).doubleValue() <= (Double)r2.valor){
                                result = new Resultado("Boolean", true);
                            }
                            break;    
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch(r2.tipo){
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if((Double)r1.valor <=  ((Integer)r2.valor).doubleValue()){
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if( (Double)r1.valor <= (Double)r2.valor){
                                result = new Resultado("Boolean", true);
                            }
                            break;    
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                case "Boolean":
                    switch(r2.tipo){
                        case "Boolean":
                            result = new Resultado("Boolean", false);
                            if( getBoolValor(r1.valor) <=  getBoolValor(r2.valor)){
                                result = new Resultado("Boolean", true);
                            }
                            break; 
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                
                case "String":
                    switch(r2.tipo){
                        case "String":
                            result = new Resultado("Boolean", false);
                            if(  obtenerSumaCaracteres((String) r1.valor) <= obtenerSumaCaracteres((String) r2.valor) ){
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;    
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                    break;
            }
        }else if(op.equals("MAYQ")){
            switch(r1.tipo){
                case "Integer":
                    switch(r2.tipo){
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if((Integer)r1.valor > (Integer)r2.valor){
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if( ((Integer)r1.valor).doubleValue() > (Double)r2.valor){
                                result = new Resultado("Boolean", true);
                            }
                            break;    
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch(r2.tipo){
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if((Double)r1.valor >  ((Integer)r2.valor).doubleValue()){
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if( (Double)r1.valor > (Double)r2.valor){
                                result = new Resultado("Boolean", true);
                            }
                            break;    
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                case "Boolean":
                    switch(r2.tipo){
                        case "Boolean":
                            result = new Resultado("Boolean", false);
                            if( getBoolValor(r1.valor) >  getBoolValor(r2.valor)){
                                result = new Resultado("Boolean", true);
                            }
                            break; 
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                
                case "String":
                    switch(r2.tipo){
                        case "String":
                            result = new Resultado("Boolean", false);
                            if(  obtenerSumaCaracteres((String) r1.valor) > obtenerSumaCaracteres((String) r2.valor) ){
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;    
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                    break;
            }
        }else if(op.equals("MAYIQ")){
            switch(r1.tipo){
                case "Integer":
                    switch(r2.tipo){
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if((Integer)r1.valor >= (Integer)r2.valor){
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if( ((Integer)r1.valor).doubleValue() >= (Double)r2.valor){
                                result = new Resultado("Boolean", true);
                            }
                            break;    
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch(r2.tipo){
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if((Double)r1.valor >=  ((Integer)r2.valor).doubleValue()){
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if( (Double)r1.valor >= (Double)r2.valor){
                                result = new Resultado("Boolean", true);
                            }
                            break;    
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                case "Boolean":
                    switch(r2.tipo){
                        case "Boolean":
                            result = new Resultado("Boolean", false);
                            if( getBoolValor(r1.valor) >=  getBoolValor(r2.valor)){
                                result = new Resultado("Boolean", true);
                            }
                            break; 
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                
                case "String":
                    switch(r2.tipo){
                        case "String":
                            result = new Resultado("Boolean", false);
                            if(  obtenerSumaCaracteres((String) r1.valor) >= obtenerSumaCaracteres((String) r2.valor) ){
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;    
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                    break;
            }
        }else if(op.equals("IG_IG")){
            switch(r1.tipo){
                case "Integer":
                    switch(r2.tipo){
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if((Integer)r1.valor == (Integer)r2.valor){
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if( ((Integer)r1.valor).doubleValue() == (Double)r2.valor){
                                result = new Resultado("Boolean", true);
                            }
                            break;    
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch(r2.tipo){
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if((Double)r1.valor ==  ((Integer)r2.valor).doubleValue()){
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if( (Double)r1.valor == (Double)r2.valor){
                                result = new Resultado("Boolean", true);
                            }
                            break;    
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                case "Boolean":
                    switch(r2.tipo){
                        case "Boolean":
                            result = new Resultado("Boolean", false);
                            if( getBoolValor(r1.valor) ==  getBoolValor(r2.valor)){
                                result = new Resultado("Boolean", true);
                            }
                            break; 
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                
                case "String":
                    switch(r2.tipo){
                        case "String":
                            result = new Resultado("Boolean", false);
                            if(  obtenerSumaCaracteres((String) r1.valor) == obtenerSumaCaracteres((String) r2.valor) ){
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;    
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                    break;
            }
        }else if(op.equals("DIF")){
            switch(r1.tipo){
                case "Integer":
                    switch(r2.tipo){
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if((Integer)r1.valor != (Integer)r2.valor){
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if( ((Integer)r1.valor).doubleValue() != (Double)r2.valor){
                                result = new Resultado("Boolean", true);
                            }
                            break;    
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch(r2.tipo){
                        case "Integer":
                            result = new Resultado("Boolean", false);
                            if((Double)r1.valor !=  ((Integer)r2.valor).doubleValue()){
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        case "Double":
                            result = new Resultado("Boolean", false);
                            if( (Double)r1.valor != (Double)r2.valor){
                                result = new Resultado("Boolean", true);
                            }
                            break;    
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                case "Boolean":
                    switch(r2.tipo){
                        case "Boolean":
                            result = new Resultado("Boolean", false);
                            if( getBoolValor(r1.valor) !=  getBoolValor(r2.valor)){
                                result = new Resultado("Boolean", true);
                            }
                            break; 
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                
                case "String":
                    switch(r2.tipo){
                        case "String":
                            result = new Resultado("Boolean", false);
                            if(  obtenerSumaCaracteres((String) r1.valor) != obtenerSumaCaracteres((String) r2.valor) ){
                                result = new Resultado("Boolean", true);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                            break;    
                    }
                    break;
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica MENOR QUE entre "+r1.tipo+"-"+r2.tipo);
                    break;
            }
        }
        return result;
    }
    
    public Resultado operacionesAritmeticas(Resultado r1, Resultado r2, String op){
        if(verNulabilidad(r1, r2)){
            return null;
        }
        
        Resultado result = new Resultado("-1",null);
        Object valor = new Object();
        if(op.equals("MAS")){
            switch (r1.tipo) {
                case "Integer":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = (Integer)r1.valor + (Integer)r2.valor;
                            result = new Resultado("Integer", valor );
                            break;
                        case "Double":
                            valor = ((Integer)r1.valor).doubleValue() + (Double)r2.valor;
                            result = new Resultado("Double", valor );
                            break;
                        case "Boolean":
                            valor = (Integer)r1.valor + getBoolValor(r2.valor);
                            result = new Resultado("Integer", valor );
                            break;    
                        case "String":
                            if(((String)r2.valor).length()==1)
                            {
                                valor = (Integer)r1.valor + obtenerSumaCaracteres((String)r2.valor);
                                result = new Resultado("Integer", valor );
                            }else
                            {
                                Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Suma entre "+r1.tipo+"-"+r2.tipo);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Suma entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = (Double)r1.valor + ((Integer)r2.valor).doubleValue();
                            result = new Resultado("Double", valor );
                            break;
                        case "Double":
                            valor = (Double)r1.valor + (Double)r2.valor;
                            result = new Resultado("Double", valor );
                            break;
                        case "Boolean":
                            valor = (Double)r1.valor + getBoolValor(r2.valor).doubleValue();
                            result = new Resultado("Double", (Double)valor);
                            break;    
                        case "String":
                            if(((String)r2.valor).length()==1)
                            {
                                valor = (Double)r1.valor + obtenerSumaCaracteres((String)r2.valor).doubleValue();
                                result = new Resultado("Double", valor );
                            }else
                            {
                                Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Suma entre "+r1.tipo+"-"+r2.tipo);
                            }
                            
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Suma entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;  
                case "Boolean":
                    switch (r2.tipo) {
                        case "String":
                            result = new Resultado("String", (Boolean)r1.valor + (String)r2.valor);
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Suma entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;      
                case "String":
                    switch (r2.tipo) {
                        case "Integer":
                            
                            if(((String)r1.valor).length()==1)
                            {
                                valor = obtenerSumaCaracteres((String)r1.valor) + (Integer)r2.valor;
                                result = new Resultado("Integer", valor );
                            
                            }else
                            {
                                result = new Resultado("String", (String)r1.valor + (Integer)r2.valor);
                            }
                            
                            break;
                        case "Double":
                            if(((String)r1.valor).length()==1)
                            {
                                valor = obtenerSumaCaracteres((String)r1.valor).doubleValue() + (Double)r2.valor;
                                result = new Resultado("Double", valor );
                            
                            }else
                            {
                                result = new Resultado("String", (String)r1.valor + (Double)r2.valor);
                            }
                            break;
                    
                        case "Boolean":
                            result = new Resultado("String", (String)r1.valor + (Boolean)r2.valor);
                            break;
                        case "String":
                            result = new Resultado("String", (String)r1.valor + (String)r2.valor);
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Suma entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;    
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica Suma entre "+r1.tipo+"-"+r2.tipo);    
                    break;
            }
        }else if(op.equals("MENOS")){
            switch (r1.tipo) {
                case "Integer":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = (Integer)r1.valor - (Integer)r2.valor;
                            result = new Resultado("Integer", valor );
                            break;
                        case "Double":
                            valor = ((Integer)r1.valor).doubleValue() - (Double)r2.valor;
                            result = new Resultado("Double", valor );
                            break;
                        case "String":
                            if(((String)r2.valor).length()==1)
                            {
                                valor = (Integer)r1.valor - obtenerSumaCaracteres((String)r2.valor);
                                result = new Resultado("Integer", valor );
                            }else
                            {
                                Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Resta entre "+r1.tipo+"-"+r2.tipo);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Resta entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = (Double)r1.valor - (Integer)r2.valor;
                            result = new Resultado("Integer", valor );
                            break;
                        case "Double":
                            valor = (Double)r1.valor - (Double)r2.valor;
                            result = new Resultado("Double", valor );
                            break;
                        case "String":
                            if(((String)r2.valor).length()==1)
                            {
                                valor = (Double)r1.valor - Double.parseDouble(String.valueOf(obtenerSumaCaracteres((String)r2.valor)));
                                result = new Resultado("Double", valor );
                            }else
                            {
                                Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Resta entre "+r1.tipo+"-"+r2.tipo);
                            }
                            
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Resta entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;    
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica Resta entre "+r1.tipo+"-"+r2.tipo);    
                    break;
            }
        }else if (op.equals("POR")){
            switch (r1.tipo) {
                case "Integer":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = (Integer)r1.valor * (Integer)r2.valor;
                            result = new Resultado("Integer", valor );
                            break;
                        case "Double":
                            valor = ((Integer)r1.valor).doubleValue() * (Double)r2.valor;
                            result = new Resultado("Double", valor );
                            break;     
                        case "String":
                            if(((String)r2.valor).length()==1)
                            {
                                valor = (Integer)r1.valor * obtenerSumaCaracteres((String)r2.valor);
                                result = new Resultado("Integer", valor );
                            }else
                            {
                                Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Multiplicacion entre "+r1.tipo+"-"+r2.tipo);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Multiplicacion entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = (Double)r1.valor * ((Integer)r2.valor).doubleValue();
                            result = new Resultado("Double", valor );
                            break;
                        case "Double":
                            valor = (Double)r1.valor * (Double)r2.valor;
                            result = new Resultado("Double", valor );
                            break; 
                        case "String":
                            if(((String)r2.valor).length()==1)
                            {
                                valor = (Double)r1.valor * obtenerSumaCaracteres((String)r2.valor).doubleValue();
                                result = new Resultado("Double", valor );
                            }else
                            {
                                Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Multiplicacion entre "+r1.tipo+"-"+r2.tipo);
                            }
                            
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Suma Multiplicacion "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;    
                    
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica Multiplicacion entre "+r1.tipo+"-"+r2.tipo);    
                    break;
            }
        }else if (op.equals("DIV")){
            switch (r1.tipo) {
                case "Integer":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = ((Integer)r1.valor).doubleValue() / ((Integer)r2.valor).doubleValue();
                            result = new Resultado("Double", valor );
                            break;
                        case "Double":
                            valor = ((Integer)r1.valor).doubleValue() / (Double)r2.valor;
                            result = new Resultado("Double", valor );
                            break;     
                        case "String":
                            if(((String)r2.valor).length()==1)
                            {
                                valor = ((Integer)r1.valor).doubleValue() / obtenerSumaCaracteres((String)r2.valor).doubleValue();
                                result = new Resultado("Double", valor );
                            }else
                            {
                                Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Division entre "+r1.tipo+"-"+r2.tipo);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Division entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = (Double)r1.valor / ((Integer)r2.valor).doubleValue();
                            result = new Resultado("Double", valor );
                            break;
                        case "Double":
                            valor = (Double)r1.valor / (Double)r2.valor;
                            result = new Resultado("Double", valor );
                            break; 
                        case "String":
                            if(((String)r2.valor).length()==1)
                            {
                                valor = (Double)r1.valor / obtenerSumaCaracteres((String)r2.valor).doubleValue();
                                result = new Resultado("Double", valor );
                            }else
                            {
                                Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Division entre "+r1.tipo+"-"+r2.tipo);
                            }
                            
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Division Multiplicacion "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;    
                    
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica Division entre "+r1.tipo+"-"+r2.tipo);    
                    break;
            }
        }else if (op.equals("POT")){
            switch (r1.tipo) {
                case "Integer":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = Math.pow( ((Integer)r1.valor).doubleValue() , ((Integer)r2.valor).doubleValue() );
                            result = new Resultado("Integer", ((Double)valor).intValue());
                            break;
                        case "Double":
                            valor = Math.pow(((Integer)r1.valor).doubleValue() , (Double)r2.valor);
                            result = new Resultado("Double", valor);
                            break;
                        case "Boolean":
                            valor = Math.pow( ((Integer)r1.valor).doubleValue() , getBoolValor(r2.valor).doubleValue() );
                            result = new Resultado("Integer", ((Double)valor).intValue() );
                            break;    
                        case "String":
                            if(((String)r2.valor).length()==1)
                            {
                                valor = Math.pow(((Integer)r1.valor).doubleValue() , obtenerSumaCaracteres((String)r2.valor).doubleValue());
                                result = new Resultado("Integer", ((Double)valor).intValue() );
                            }else
                            {
                                Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Suma entre "+r1.tipo+"-"+r2.tipo);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Suma entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                case "Double":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = Math.pow( (Double)r1.valor , ((Integer)r2.valor).doubleValue() );
                            result = new Resultado("Double", valor);
                            break;
                        case "Double":
                            valor = Math.pow((Double)r1.valor, (Double)r2.valor);
                            result = new Resultado("Double", valor);
                            break;
                        case "Boolean":
                            valor = Math.pow( (Double)r1.valor , getBoolValor(r2.valor).doubleValue() );
                            result = new Resultado("Double", valor );
                            break;    
                        case "String":
                            if(((String)r2.valor).length()==1)
                            {
                                valor = Math.pow((Double)r1.valor , obtenerSumaCaracteres((String)r2.valor).doubleValue());
                                result = new Resultado("Double", valor );
                            }else
                            {
                                Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Suma entre "+r1.tipo+"-"+r2.tipo);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Suma entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                
                case "Boolean":
                    switch (r2.tipo) {
                        case "Integer":
                            valor = Math.pow( getBoolValor(r1.valor).doubleValue() , ((Integer)r2.valor).doubleValue() );
                            result = new Resultado("Integer", ((Double)valor).intValue() );
                            break;
                        case "Double":
                            valor = Math.pow(getBoolValor(r1.valor).doubleValue(), (Double)r2.valor);
                            result = new Resultado("Integer", ((Double)valor).intValue() );
                            break;
                        case "Boolean":
                            valor = Math.pow( getBoolValor(r1.valor).doubleValue() , getBoolValor(r2.valor).doubleValue() );
                            result = new Resultado("Integer", ((Double)valor).intValue() );
                            break;    
                        case "String":
                            if(((String)r2.valor).length()==1)
                            {
                                valor = Math.pow(getBoolValor(r1.valor).doubleValue() , obtenerSumaCaracteres((String)r2.valor).doubleValue());
                                result = new Resultado("Integer", ((Double)valor).intValue() );
                            }else
                            {
                                Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Suma entre "+r1.tipo+"-"+r2.tipo);
                            }
                            break;
                        default:
                            Template.reporteError_CJS.agregar("Error Semantico",linea2, columna2, "No aplica Suma entre "+r1.tipo+"-"+r2.tipo);
                            break;
                    }
                    break;
                        
                default:
                    Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica Suma entre "+r1.tipo+"-"+r2.tipo);    
                    break;
            }
        }
        return result;
    }
    
    public Resultado operacionesSimplificadas(Resultado r1, String op){
        if(r1==null){
            return null;
        }
        Resultado resultado=null;
        Object valor = new Object();
        if(op.equals("ADD")){
            if(r1.tipo.equals("Integer")){
                valor = (Integer)r1.valor + 1;
                resultado = new Resultado("Integer", valor);
            }else if(r1.tipo.equals("Double"))
            {
                valor = (Double)r1.valor + 1;
                resultado = new Resultado("Double", valor);
            }else{
                Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica Incremento a "+r1.tipo);  
            }
        }else if(op.equals("SUB")){
            if(r1.tipo.equals("Integer")){
                valor = (Integer)r1.valor - 1;
                resultado = new Resultado("Integer", valor);
            }else if(r1.tipo.equals("Double"))
            {
                valor = (Double)r1.valor - 1;
                resultado = new Resultado("Double", valor);
            }else{
                Template.reporteError_CJS.agregar("Error Semantico",linea1, columna1, "No aplica Decremento a "+r1.tipo);
            }
        }
        return resultado;
    }
    
    
    public Resultado acceso(Nodo raiz){
        Clase aux = Script.claseActual;
        TablaSimbolo tablaAux = tabla;
        Resultado retorno = new Resultado("-1", null);
        //----------------------------------------------------------------------
        int nivel = 0;
        String nombre;
        Simbolo simbolo;
        switch(raiz.nombre){
            case "accesoAr":
                    aux.tabla = tabla;
                    tabla = tablaAux;
                    retorno = accesoAr(raiz, nivel, aux);
                    break;
            case "id_cmp":
                nombre =  raiz.valor;
                simbolo = tabla.getSimbolo(nombre, aux);
                if(simbolo!=null){
                    if(simbolo.inicializado){
                        switch(simbolo.tipo){
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
                                retorno.valor=simbolo.valor;
                                retorno.tipo=simbolo.tipo;
                                retorno.simbolo=simbolo;
                                break;
                        }
                    }else{
                        retorno.tipo = "";
                        retorno.valor = null;
                        Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " no ha sido inicializada");
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
                nombre = raiz.valor;
                simbolo = tabla.getSimbolo(nombre, aux);
                if(simbolo != null){
                    if(simbolo.inicializado){
                        switch(simbolo.tipo){
                            case "boolean":
                            case "number":
                            case "string":
                            case "date":
                            case "datetime":
                                retorno.valor = simbolo.valor;
                                retorno.tipo  = simbolo.tipo;
                                retorno.simbolo = simbolo;
                                break;
                        }
                        if(simbolo.esArreglo){
                            retorno.valor = simbolo.valor;
                            retorno.tipo = "Arreglo";
                            retorno.simbolo = simbolo;
                        }
                    }else{
                        retorno.tipo = "";
                        retorno.valor = null;
                        Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " no ha sido inicializada");
                        return retorno;
                    }
                }else {
                    //retorno.tipo = "-1";
                    //retorno.valor = null;
                    Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + nombre + " no existe en el ambito donde fue invocada");
                    return null;
                }
                break;
                
            case "llamadaFuncion":
                /*LlamadaMetodo llamada = new LlamadaMetodo(aux, nivel);
                Metodo metodo = llamada.ejecutar(raiz);
                if (metodo != null) {
                    if (metodo.retorno != null) {
                            retorno = metodo.retorno;
                            metodo.estadoRetorno = false;
                    }
                } else {
                    retorno.tipo = "-1";
                    retorno.valor = null;
                }*/
                break;
        }
        tabla = tablaAux;
        return retorno;
    }
    
    
    
    private Resultado accesoAr(Nodo raiz, int nivel, Clase aux) {
        Simbolo simbolo;
        simbolo = aux.tabla.getSimbolo((String) raiz.valor, aux);
        if(simbolo!=null){
            if (simbolo.inicializado) {
                if(simbolo.esArreglo){
                    Arreglo arreglo = (Arreglo) simbolo.valor;
                    ArrayList<Integer> indices = new ArrayList<>();
                    OperacionesARL opL = new OperacionesARL(global, tabla,miTemplate);
                    Resultado indice = opL.ejecutar(raiz.hijos.get(0));
                    if (indice != null) {
                        if (indice.tipo.equals("number")) {
                            Double iii = (Double) indice.valor;
                            indices.add(iii.intValue());
                        }
                    }
                    Object valor = arreglo.getValor(indices);
                    if (valor != null) {
                        
                        Resultado ree = (Resultado)valor;
                        return ree;
                    } else {
                        Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "No se pudo acceder al indice del arreglo: "+simbolo.nombre+" Indice fuera del limite");
                    }
                }else {
                    Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + raiz.valor + " no es arreglo");
                    return null;
                }
            }else{
                Template.reporteError_CJS.agregar("Semantico", raiz.linea, raiz.columna, "La variable " + raiz.valor + " no ha sido inicializada");
                return null;
            }
        }
        return null;
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
    
    public boolean verNulabilidad(Resultado r1, Resultado r2){
        if(r1 != null && r2!=null){
            return false;
        }else{
            return true;
        }
    }
    
    
    public Integer obtenerSumaCaracteres(String cadena)
    {
        Integer suma = 0;
        try {
            for (int i = 0; i < cadena.length(); i++) {
                suma+= cadena.codePointAt(i);
            }
        } catch (Exception e)
        {   
            Template.reporteError_CJS.agregar("Error Ejecucion",linea1, columna1, "Error al extraer ascii de la cadena ["+cadena+"]");  
        }
        return suma;
    }

    
}
