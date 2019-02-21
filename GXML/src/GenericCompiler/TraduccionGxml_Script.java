/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GenericCompiler;

import Estructuras.Nodo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fernando
 */

  //!!!!!!!!!!!!!!!!!!!!DUDAS
    /*
        Metodo Accion en Venta existe?
    */

public class TraduccionGxml_Script {
    public String codigoScript = "";
    public String salto = "\n";
    public void IniciarTraduccion(Nodo RAIZ)
    {
        Nodo imports = RAIZ.get(0);
        Nodo Lventanas = RAIZ.get(1);
        ListaVentanas(Lventanas);
        System.out.println(codigoScript);
    }
    
    public void ListaVentanas(Nodo RAIZ)
    {
        
        RAIZ.hijos.forEach((ventana) -> {
            crearVentana(ventana);
        });
    }
    
  
    public void FRecursiva(Nodo RAIZ,String padre)
    {
        Nodo vAtributos = RAIZ.get(0);
        Nodo vExplicit  = RAIZ.get(1);
        Nodo vHijos     = RAIZ.get(2);
        
        for (Nodo hijo : vHijos.hijos) {
             switch(hijo.nombre.toLowerCase())
            {
                case "contenedor":
                    crearContenedor(hijo,padre);
                    break;
                case "texto":
                    break;
                case "boton":
                    break;
                default:
                    //ERROR
                    break;
            }
        }
        
    }
    
    
    public void crearVentana(Nodo RAIZ)
    {
        Nodo vAtributos = RAIZ.get(0);
        Nodo vExplicit  = RAIZ.get(1);
        Nodo vHijos     = RAIZ.get(2);
        
        ArrayList<String> parametros  = new ArrayList<>();
        parametros.add("\"\"");
        parametros.add("\"0\"");
        parametros.add("\"0\"");
        String alCargar="";
        String alCerrar="";
        
        
        for (Nodo atributo : vAtributos.hijos) {
            Nodo n_atributo = atributo.get(0);
            String n_valor =  "\""+atributo.get(1).valor+"\"";
            String val = atributo.get(1).valor;
            switch(n_atributo.nombre.toLowerCase())
            {              
                case "accioninicial":
                    alCargar  = val;
                    break;     
                case "accionfinal":
                    alCerrar  = val;
                    break;         
                case "color":
                    parametros.set(0,n_valor);
                    break;
                case "alto":
                    parametros.set(1,n_valor);
                    break;
                case "ancho":
                    parametros.set(2,n_valor);
                    break;
                case "id":
                    RAIZ.valor= val;
                    break; 
                
                default:
                    //ERROR
                    break;
            }
        }
        
        if(RAIZ.valor.equals("")){ RAIZ.valor =  String.valueOf(RAIZ.index);}
        
        codigoScript +="var "+RAIZ.valor+" = crearventana(";
        parametros.forEach((cad)->{
            if(!cad.equals(""))
            {
                codigoScript +=cad+",";
            }
            
        });
        codigoScript=recortarString(codigoScript,0,codigoScript.length()-1);
        codigoScript+=");";
        
        if(!alCargar.equals(""))
        {
            alCargar= recortarString(alCargar,1,alCargar.length()-1);
            codigoScript+=salto+RAIZ.valor+"."+"alcargar("+alCargar+");";
        }
        if(!alCerrar.equals(""))
        {
            alCerrar= recortarString(alCerrar,1,alCerrar.length()-1);
            codigoScript+=salto+RAIZ.valor+"."+"alcerrar("+alCerrar+");";
        }
        
        FRecursiva(RAIZ,RAIZ.valor);
       
    }
    
    
    /*
        var contenendor_n = crearcontenedor(...);
        ventan1.crearcontenedor(contenedor_n.borde, ...);
    
        o
    
        ventan1.crearcontenedor(contenedor);
    */
    public void crearContenedor(Nodo RAIZ, String padre)
    {
        Nodo vAtributos = RAIZ.get(0);
        Nodo vExplicit  = RAIZ.get(1);
        Nodo vHijos     = RAIZ.get(2);
        
        ArrayList<String> parametros  = new ArrayList<>();
        parametros.add("\"0\"");//alto
        parametros.add("\"0\"");//ancho
        parametros.add("\"\"");//color
        parametros.add("\"falso\"");//borde
        parametros.add("\"0\"");//iniciox
        parametros.add("\"0\"");//inicioY
        
        
        
        for (Nodo atributo : vAtributos.hijos) {
            Nodo n_atributo = atributo.get(0);
            String n_valor =  "\""+atributo.get(1).valor+"\"";
            String val = atributo.get(1).valor;
            switch(n_atributo.nombre.toLowerCase())
            {              
                case "alto":
                    parametros.set(0,n_valor);
                    break;
                case "ancho":
                    parametros.set(1,n_valor);
                    break;
                case "color":
                    parametros.set(2,n_valor);
                    break;
                case "borde":
                    parametros.set(3,n_valor);
                    break;
                case "x":
                    parametros.set(4,n_valor);
                    break;     
                case "y":
                    parametros.set(5,n_valor);
                    break;         
                case "id":
                    RAIZ.valor= val;
                    break; 
                
                default:
                    //ERROR
                    break;
            }
        }
        
        if(RAIZ.valor.equals("")){ RAIZ.valor =  String.valueOf(RAIZ.index);}
        
        codigoScript +=salto+"var "+RAIZ.valor+" = crearcontenedor(";
        parametros.forEach((cad)->{
            if(!cad.equals(""))
            {
                codigoScript +=cad+",";
            }
            
        });
        codigoScript=recortarString(codigoScript,0,codigoScript.length()-1);
        codigoScript+=");";
        
        codigoScript +=salto+padre+".crearcontenedor("+RAIZ.valor+");";
        
        FRecursiva(RAIZ,RAIZ.valor);
       
    }
    
    
    public String recortarString(String cad, int inicio, int fin)
    {
        try {
            return cad.substring(inicio,fin);
        } catch (Exception e) {
            return "";
        }
    }
}
