/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GenericCompiler;

import Estructuras.Nodo;
import java.util.ArrayList;
import java.util.HashMap;
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
        Nodo vHijos     = null;
        if(RAIZ.size()==3)
        {
                vHijos = RAIZ.get(2);
        }else
        {
                vHijos = new Nodo();
        }
        
            
        for (Nodo hijo : vHijos.hijos) {
             switch(hijo.nombre.toLowerCase())
            {
                case "contenedor":
                    crearContenedor(hijo,padre);
                    break;
                case "texto":
                    crearTexto(hijo,padre);
                    break;
                case "control":
                    crearControl(hijo,padre);
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
    
    public void crearMultimedia(Nodo RAIZ, String padre)
    {
        Nodo vAtributos = RAIZ.get(0);
        
        HashMap<String, String> hashMap = new HashMap<>();
        
        for (Nodo atributo : vAtributos.hijos) {
            Nodo at_nombre = atributo.get(0);
            Nodo at_valor  = atributo.get(1);
            if(!hashMap.containsKey(at_nombre.nombre.toLowerCase()))
            {
                hashMap.put(at_nombre.nombre.toLowerCase(), at_valor.valor);
            }else
            {
                //Error Atributo Repetido
            }
        }
        
        ArrayList<String> parametros  = new ArrayList<>();
        
        //Obtener id o nombre del control y setear el nombre que tomara
        String nombre = obtenerAtributo(hashMap, "nombre");
        if(!nombre.equals("\"\""))
        {
            RAIZ.valor = recortarString(nombre, 1, nombre.length()-1);;
        }else
        {
            RAIZ.valor =  RAIZ.valor + String.valueOf(RAIZ.index); 
        }
        
        //Obtener el atributo TIPO de Control
        if(hashMap.containsKey("tipo"))
        {
            String tipoControl = hashMap.get("tipo").toLowerCase();
            switch(tipoControl)
            {
                case "imagen":
                case "video":
                    String type="crear"+tipoControl;
                    
                    parametros.add(obtenerAtributo(hashMap, "path"));
                    parametros.add(obtenerAtributo(hashMap, "x"));
                    parametros.add(obtenerAtributo(hashMap, "y"));
                    parametros.add(obtenerAtributo(hashMap, "auto-reproduccion"));
                    parametros.add(obtenerAtributo(hashMap, "alto"));
                    parametros.add(obtenerAtributo(hashMap, "ancho"));
                    agregarAPadre(RAIZ, parametros, padre, type);
                    FRecursiva(RAIZ,RAIZ.valor);
                    break;    
                case "musica":
                    parametros.add(obtenerAtributo(hashMap, "path"));
                    parametros.add(obtenerAtributo(hashMap, "x"));
                    parametros.add(obtenerAtributo(hashMap, "y"));
                    parametros.add(obtenerAtributo(hashMap, "auto-reproduccion"));
                    parametros.add(obtenerAtributo(hashMap, "alto"));
                    parametros.add(obtenerAtributo(hashMap, "ancho"));
                    agregarAPadre(RAIZ, parametros, padre, "crearreproductor");
                    FRecursiva(RAIZ,RAIZ.valor);

                    break;
                    
                    
            }
        }
    }
    
    public void crearBoton(Nodo RAIZ, String padre)
    {
        Nodo vAtributos = RAIZ.get(0);
        Nodo vExplicit  = RAIZ.get(1);

        HashMap<String, String> hashMap = new HashMap<>();
        
        for (Nodo atributo : vAtributos.hijos) {
            Nodo at_nombre = atributo.get(0);
            Nodo at_valor  = atributo.get(1);
            if(!hashMap.containsKey(at_nombre.nombre.toLowerCase()))
            {
                System.out.println("key:"+at_nombre.nombre.toLowerCase());
                System.out.println("valor:"+at_valor.valor.toLowerCase());
                hashMap.put(at_nombre.nombre.toLowerCase(), at_valor.valor);
            }else
            {
                //Error Atributo Repetido
            }
        }
        
        ArrayList<String> parametros  = new ArrayList<>();
        
        //Obtener id o nombre del control y setear el nombre que tomara
        String nombre = obtenerAtributo(hashMap, "nombre");
        if(!nombre.equals("\"\""))
        {
            RAIZ.valor = recortarString(nombre, 1, nombre.length()-1);;
        }else
        {
            RAIZ.valor =  RAIZ.valor + String.valueOf(RAIZ.index); 
        }
        
        //Texto que mostrara el boton
        String str_Defecto;
        vExplicit.valor = recortarString(vExplicit.valor, 1, vExplicit.valor.length()-1);
        vExplicit.valor = vExplicit.valor.replaceAll("\n", " ");
        vExplicit.valor = vExplicit.valor.replaceAll("\t", " ");
        vExplicit.valor = vExplicit.valor.trim();
        str_Defecto = "\""+vExplicit.valor+"\"";
        
        
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

        agregarAPadre(RAIZ, parametros, padre, "crearboton");
        
        /*  Si tiene el atributo Accion*/
        String accion = obtenerAtributo(hashMap, "accion");
        if(!accion.equals("\"\""))
        {
            accion= recortarString(accion,1,accion.length()-1);
            accion= recortarString(accion,1,accion.length()-1);

            codigoScript+=salto+RAIZ.valor+".alclic("+accion+");";
        }
        
        FRecursiva(RAIZ,RAIZ.valor);
    }
    
    public void crearControl(Nodo RAIZ, String padre)
    {
        Nodo vAtributos = RAIZ.get(0);
        
        HashMap<String, String> hashMap = new HashMap<>();
        
        for (Nodo atributo : vAtributos.hijos) {
            Nodo at_nombre = atributo.get(0);
            Nodo at_valor  = atributo.get(1);
            if(!hashMap.containsKey(at_nombre.nombre.toLowerCase()))
            {
                System.out.println("key:"+at_nombre.nombre.toLowerCase());
                System.out.println("valor:"+at_valor.valor.toLowerCase());
                hashMap.put(at_nombre.nombre.toLowerCase(), at_valor.valor);
            }else
            {
                //Error Atributo Repetido
            }
        }
        
        ArrayList<String> parametros  = new ArrayList<>();
        
        //Obtener id o nombre del control y setear el nombre que tomara
        String nombre = obtenerAtributo(hashMap, "nombre");
        if(!nombre.equals("\"\""))
        {
            RAIZ.valor = recortarString(nombre, 1, nombre.length()-1);;
        }else
        {
            RAIZ.valor =  RAIZ.valor + String.valueOf(RAIZ.index); 
        }
        
        //Obtener <defecto> </defecto>
        Nodo DEFECTO = obtenerHijo(RAIZ, "defecto");
        String str_Defecto = "\"\"";
        if(DEFECTO!=null)
        {
            Nodo vExplicit = DEFECTO.get(1);
            vExplicit.valor = recortarString(vExplicit.valor, 1, vExplicit.valor.length()-1);
            vExplicit.valor = vExplicit.valor.replaceAll("\n", " ");
            vExplicit.valor = vExplicit.valor.replaceAll("\t", " ");
            vExplicit.valor = vExplicit.valor.trim();
            str_Defecto = "\""+vExplicit.valor+"\"";
        }
        
        //Obtener el atributo TIPO de Control
        if(hashMap.containsKey("tipo"))
        {
            String tipoControl = hashMap.get("tipo").toLowerCase();
            switch(tipoControl)
            {
                case "textoarea":
                case "texto":
                    String type="crearcajatexto";
                    if(tipoControl.equals("textoarea")){type= "crearareatexto";}
                    parametros.add(obtenerAtributo(hashMap, "alto"));
                    parametros.add(obtenerAtributo(hashMap, "ancho"));
                    parametros.add(obtenerAtributo(hashMap, "fuente"));
                    parametros.add(obtenerAtributo(hashMap, "alto"));
                    parametros.add(obtenerAtributo(hashMap, "tam"));
                    parametros.add(obtenerAtributo(hashMap, "color"));
                    parametros.add(obtenerAtributo(hashMap, "x"));
                    parametros.add(obtenerAtributo(hashMap, "y"));
                    parametros.add(obtenerAtributo(hashMap, "negrilla"));
                    parametros.add(obtenerAtributo(hashMap, "cursiva"));
                    parametros.add(str_Defecto);
                    parametros.add(obtenerAtributo(hashMap, "nombre"));
                    agregarAPadre(RAIZ, parametros, padre, type);
                    FRecursiva(RAIZ,RAIZ.valor);
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
                    agregarAPadre(RAIZ, parametros, padre, "crearcontrolnumerico");
                    FRecursiva(RAIZ,RAIZ.valor);

                    break;
                case "desplegable":
                    //Obtener Lista Datos
                    Nodo LISTA = obtenerHijo(RAIZ, "listadatos");

                    parametros.add(obtenerAtributo(hashMap, "alto"));
                    parametros.add(obtenerAtributo(hashMap, "ancho"));
                    
                    //LISTA
                    if(LISTA!=null)
                    {
                        parametros.add(getArrayListaDatos(LISTA));
                    }else
                    {
                        parametros.add("[]");
                        str_Defecto = "\"\"";
                    }
                    
                    
                    parametros.add(obtenerAtributo(hashMap, "x"));
                    parametros.add(obtenerAtributo(hashMap, "y"));
                    
                    //defecto
                    parametros.add(str_Defecto);
                    
                    parametros.add(obtenerAtributo(hashMap, "nombre"));
                    agregarAPadre(RAIZ, parametros, padre, "creardesplegable");
                    FRecursiva(RAIZ,RAIZ.valor);

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
        
        agregarAPadre(RAIZ, parametros, padre, "crearcontenedor");
        FRecursiva(RAIZ,RAIZ.valor);
       
    }
    
    
    
    public void crearTexto(Nodo RAIZ, String padre)
    {
        Nodo vAtributos = RAIZ.get(0);
        Nodo vExplicit  = RAIZ.get(1);
        
        vExplicit.valor = recortarString(vExplicit.valor, 1, vExplicit.valor.length()-1);
        vExplicit.valor = vExplicit.valor.replaceAll("\n", "");
        ArrayList<String> parametros  = new ArrayList<>();
        parametros.add("\"\"");//fuente
        parametros.add("\"\"");//tam
        parametros.add("\"\"");//color
        parametros.add("\"0\"");//x
        parametros.add("\"0\"");//y
        parametros.add("\"falso\"");//negrilla
        parametros.add("\"falso\"");//cursiva
        parametros.add("\"\"");//referencia
        parametros.add("\""+vExplicit.valor+"\"");//valor
        
        
        
        for (Nodo atributo : vAtributos.hijos) {
            Nodo n_atributo = atributo.get(0);
            String n_valor =  "\""+atributo.get(1).valor+"\"";
            String val = atributo.get(1).valor;
            switch(n_atributo.nombre.toLowerCase())
            {              
                case "fuente":
                    parametros.set(0,n_valor);
                    break;
                case "tam":
                    parametros.set(1,n_valor);
                    break;
                case "color":
                    parametros.set(2,n_valor);
                    break;
                case "x":
                    parametros.set(3,n_valor);
                    break;
                case "y":
                    parametros.set(4,n_valor);
                    break;     
                case "negrilla":
                    parametros.set(5,n_valor);
                    break;     
                case "cursiva":
                    parametros.set(6,n_valor);
                    break;
                case "referencia":
                    parametros.set(7,n_valor);
                    break;
                case "nombre":
                case "id":    
                    RAIZ.valor= val;
                    break; 
                
                default:
                    //ERROR
                    break;
            }
        }
        
        agregarAPadre(RAIZ, parametros, padre, "creartexto");
        FRecursiva(RAIZ,RAIZ.valor);
       
    }
    
    public void agregarAPadre(Nodo RAIZ,ArrayList<String> parametros,String padre, String type)
    {        
        codigoScript +=salto+"var "+RAIZ.valor+" = "+type+"(";
        parametros.forEach((cad)->{
            if(!cad.equals(""))
            {
                codigoScript +=cad+",";
            }
            
        });
        
        codigoScript=recortarString(codigoScript,0,codigoScript.length()-1);
        codigoScript+=");";
        
        codigoScript +=salto+padre+"."+type+"("+RAIZ.valor+");";
    
    }
    
    
    
    public String obtenerAtributo(HashMap<String,String> hashMap, String key)
    {
        if(hashMap.containsKey(key.toLowerCase()))
        {
            return "\""+hashMap.get(key)+"\"";
        }else
        {
            return "\"\"";
        }
    }
    
    public Nodo obtenerHijo(Nodo RAIZ,String nombre)
    {
        Nodo RETORNO= null;
        if(RAIZ.size()==3)
        {
            Nodo vHijos = RAIZ.get(2);
            for (Nodo hijo : vHijos.hijos) {
                if(hijo.nombre.toLowerCase().equals(nombre.toLowerCase()))
                {
                    RETORNO = hijo;
                    break;
                }
            }
        }
        return RETORNO;
    }
    
    
    public String recortarString(String cad, int inicio, int fin)
    {
        try {
            return cad.substring(inicio,fin);
        } catch (Exception e) {
            return "";
        }
    }
    
    
    public String getArrayListaDatos(Nodo RAIZ)
    {
        String array = "[";
        if(RAIZ.size()==3)
        {
         
            Nodo vHijos = RAIZ.get(2);
            if(!vHijos.hijos.isEmpty())
            {
                for (Nodo dato : vHijos.hijos) {
                    Nodo vExplicit = dato.get(1);
                    vExplicit.valor = recortarString(vExplicit.valor, 1, vExplicit.valor.length()-1);
                    vExplicit.valor = vExplicit.valor.replaceAll("\n", " ");
                    vExplicit.valor = vExplicit.valor.replaceAll("\t", " ");
                    vExplicit.valor = vExplicit.valor.trim();
                    array+="\""+vExplicit.valor+"\",";
                }

                array=recortarString(array,0,array.length()-1);
            }
            
        }
        return array+"]";
    }
}
