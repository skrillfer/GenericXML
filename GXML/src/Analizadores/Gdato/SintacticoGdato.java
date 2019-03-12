
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Mon Mar 11 20:06:33 CST 2019
//----------------------------------------------------

package Analizadores.Gdato;

import java_cup.runtime.*;
import java.util.*;
import Estructuras.Nodo;

/** CUP v0.11a beta 20060608 generated parser.
  * @version Mon Mar 11 20:06:33 CST 2019
  */
public class SintacticoGdato extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public SintacticoGdato() {super();}

  /** Constructor which sets the default scanner. */
  public SintacticoGdato(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public SintacticoGdato(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\021\000\002\002\004\000\002\002\003\000\002\003" +
    "\002\000\002\003\003\000\002\004\005\000\002\005\005" +
    "\000\002\005\003\000\002\006\004\000\002\006\003\000" +
    "\002\010\005\000\002\007\005\000\002\011\003\000\002" +
    "\011\005\000\002\012\004\000\002\012\003\000\002\013" +
    "\005\000\002\014\005" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\040\000\006\002\uffff\007\005\001\002\000\004\002" +
    "\000\001\002\000\004\004\011\001\002\000\004\002\ufffe" +
    "\001\002\000\004\002\010\001\002\000\004\002\001\001" +
    "\002\000\006\010\014\012\015\001\002\000\006\005\036" +
    "\011\037\001\002\000\006\005\ufff9\011\ufff9\001\002\000" +
    "\004\002\ufffb\001\002\000\004\004\017\001\002\000\004" +
    "\002\ufffd\001\002\000\006\013\024\015\020\001\002\000" +
    "\004\004\033\001\002\000\006\005\ufff7\011\ufff7\001\002" +
    "\000\006\005\ufff3\014\ufff3\001\002\000\006\005\025\014" +
    "\026\001\002\000\006\005\ufff6\011\ufff6\001\002\000\004" +
    "\013\032\001\002\000\004\004\030\001\002\000\006\005" +
    "\ufff4\014\ufff4\001\002\000\004\016\031\001\002\000\006" +
    "\005\ufff2\014\ufff2\001\002\000\006\005\ufff5\011\ufff5\001" +
    "\002\000\004\016\034\001\002\000\006\005\ufff1\014\ufff1" +
    "\001\002\000\006\005\ufffa\011\ufffa\001\002\000\004\010" +
    "\042\001\002\000\004\004\040\001\002\000\006\013\024" +
    "\015\020\001\002\000\006\005\ufff8\011\ufff8\001\002\000" +
    "\004\002\ufffc\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\040\000\010\002\006\003\003\004\005\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\010\005\015\006\011\007" +
    "\012\001\001\000\004\010\034\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\010\011\020\012\022\014\021\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\004\013\026\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\010\011" +
    "\040\012\022\014\021\001\001\000\002\001\001\000\002" +
    "\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$SintacticoGdato$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$SintacticoGdato$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$SintacticoGdato$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 0;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}



    /**
     * Este metodo retorna la RAIZ del arbol  generado 
     **/ 
    
    public Nodo getRoot(){
        return action_obj.root;
    }

    /**
     * Esta funcion recorta el token cualquier cosa
    */
    public String recortarString(String text)
    {
        try
        {
            text = text.substring(1, text.length()-1);
        }catch(Exception ex)
        {
            text = "";
        }                                        
        text = text.replaceAll("\n", " ");
        text = text.replaceAll("\t", " ");
        text = text.trim();
        return text;
    }

    public String recortarString2(int inicio,int fin,String text)
    {
        try
        {
            text = text.substring(inicio, fin);
        }catch(Exception ex)
        {
            text = "";
        }                                        
        text = text.replaceAll("\n", " ");
        text = text.replaceAll("\t", " ");
        text = text.trim();
        return text;
    }
    
    /**
     * Método al que se llama para crear un nuevo nodo
     **/ 
    public Nodo crearNodo(String nombre,String valor,int linea,int columna){
        Nodo nuevo = new Nodo(nombre,valor,linea,columna,action_obj.Index);
        action_obj.Index++;
        return nuevo;
    }
    /**
     * Método al que se llama automáticamente ante algún error sintactico.
     **/ 
    public void syntax_error(Symbol s){ 
        System.out.println("Error Sintáctico en la Línea " + (s.left) +
        " Columna "+s.right+ ". No se esperaba este componente: " +s.value+"."); 
    } 

    /**
     * Método al que se llama automáticamente ante algún error sintáctico 
     * en el que ya no es posible una recuperación de errores.
     **/ 
    public void unrecovered_syntax_error(Symbol s) throws java.lang.Exception{ 
        System.out.println("Error síntactico irrecuperable en la Línea " + 
        (s.left)+ " Columna "+s.right+". Componente " + s.value + 
        " no reconocido."); 
    }  

}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$SintacticoGdato$actions {


    //se declaran variables globales etc.
        public int Index=1;
        public Nodo root = new Nodo();


  private final SintacticoGdato parser;

  /** Constructor */
  CUP$SintacticoGdato$actions(SintacticoGdato parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$SintacticoGdato$do_action(
    int                        CUP$SintacticoGdato$act_num,
    java_cup.runtime.lr_parser CUP$SintacticoGdato$parser,
    java.util.Stack            CUP$SintacticoGdato$stack,
    int                        CUP$SintacticoGdato$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$SintacticoGdato$result;

      /* select the action based on the action number */
      switch (CUP$SintacticoGdato$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // ETIQUETASformax1 ::= idA2 explicit idF1 
            {
              Nodo RESULT =null;
		int mleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).left;
		int mright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).right;
		token m = (token)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).value;
		int eleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).right;
		token e = (token)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).value;
		 
                            String text = parser.recortarString(e.getCadena());
                            
                            StringMatcher sss = new StringMatcher();
                            String tipo = sss.esTipo(text);

                            if(tipo.equals("string_literal"))
                            {
                                text = text.substring(1, text.length()-1);
                            }
                            if(tipo.equals(""))
                            {
                                tipo = "string_literal";
                            }

                            RESULT = parser.crearNodo("atributo","",m.getLinea(),m.getColumna());       
                            RESULT.add(parser.crearNodo(m.getCadena(),m.getCadena(),m.getLinea(),m.getColumna()));      
                            RESULT.add(parser.crearNodo(tipo,text,e.getLinea(),e.getColumna())); 
                      
              CUP$SintacticoGdato$result = parser.getSymbolFactory().newSymbol("ETIQUETASformax1",10, ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)), ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), RESULT);
            }
          return CUP$SintacticoGdato$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // ETIQUETASformax2 ::= idA1 explicit idF1 
            {
              Nodo RESULT =null;
		int mleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).left;
		int mright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).right;
		token m = (token)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).value;
		int eleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).right;
		token e = (token)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).value;
		 
                            String text = parser.recortarString(e.getCadena());
                            //System.out.println(text);

                            String etq  = parser.recortarString2(1,m.getCadena().length(),m.getCadena());
                            //System.out.println(etq);
                            
                            StringMatcher sss = new StringMatcher();
                            String tipo = sss.esTipo(text);
                            //System.out.println("es tipo:"+tipo);
                            
                            if(tipo.equals("string_literal"))
                            {
                                text = text.substring(1, text.length()-1);
                            }
                            if(tipo.equals(""))
                            {
                                tipo = "string_literal";
                            }

                            RESULT = parser.crearNodo("atributo","",m.getLinea(),m.getColumna());       
                            RESULT.add(parser.crearNodo(etq,etq,m.getLinea(),m.getColumna()));       
                            RESULT.add(parser.crearNodo(tipo,text,e.getLinea(),e.getColumna())); 
                      
              CUP$SintacticoGdato$result = parser.getSymbolFactory().newSymbol("ETIQUETASformax2",9, ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)), ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), RESULT);
            }
          return CUP$SintacticoGdato$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // CONTENIDOCUERPO2 ::= ETIQUETASformax1 
            {
              Nodo RESULT =null;
		int hleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()).left;
		int hright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()).right;
		Nodo h = (Nodo)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.peek()).value;
		
                        RESULT = parser.crearNodo("atributos","",h.linea,h.columna);
                        RESULT.add(h);
                   
              CUP$SintacticoGdato$result = parser.getSymbolFactory().newSymbol("CONTENIDOCUERPO2",8, ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), RESULT);
            }
          return CUP$SintacticoGdato$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // CONTENIDOCUERPO2 ::= CONTENIDOCUERPO2 ETIQUETASformax2 
            {
              Nodo RESULT =null;
		int rleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).left;
		int rright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).right;
		Nodo r = (Nodo)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).value;
		int hleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()).left;
		int hright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()).right;
		Nodo h = (Nodo)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.peek()).value;
		
                        r.add(h);   RESULT = r;
                   
              CUP$SintacticoGdato$result = parser.getSymbolFactory().newSymbol("CONTENIDOCUERPO2",8, ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)), ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), RESULT);
            }
          return CUP$SintacticoGdato$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // CONTENIDOPrincipal ::= CONTENIDOCUERPO2 menq principalF1 
            {
              Nodo RESULT =null;
		int mleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).left;
		int mright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).right;
		Nodo m = (Nodo)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).value;
		 RESULT = m;  
              CUP$SintacticoGdato$result = parser.getSymbolFactory().newSymbol("CONTENIDOPrincipal",7, ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)), ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), RESULT);
            }
          return CUP$SintacticoGdato$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // CONTENIDOPrincipal ::= principalF1 
            {
              Nodo RESULT =null;
		 RESULT = parser.crearNodo("atributos","",0,0);  
              CUP$SintacticoGdato$result = parser.getSymbolFactory().newSymbol("CONTENIDOPrincipal",7, ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), RESULT);
            }
          return CUP$SintacticoGdato$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // ETIQUETASforma1 ::= principalA2 explicit CONTENIDOPrincipal 
            {
              Nodo RESULT =null;
		int mleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).left;
		int mright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).right;
		token m = (token)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).value;
		int eleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).right;
		token e = (token)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).value;
		int Rleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()).left;
		int Rright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()).right;
		Nodo R = (Nodo)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.peek()).value;
		 
                        RESULT = parser.crearNodo("principal","",m.getLinea(),m.getColumna());
                        RESULT.add(R); 
                        RESULT.add(parser.crearNodo("","",m.getLinea(),m.getColumna()));
                        RESULT.add(parser.crearNodo("","",m.getLinea(),m.getColumna()));
                    
              CUP$SintacticoGdato$result = parser.getSymbolFactory().newSymbol("ETIQUETASforma1",5, ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)), ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), RESULT);
            }
          return CUP$SintacticoGdato$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // ETIQUETASforma2 ::= principalA1 explicit CONTENIDOPrincipal 
            {
              Nodo RESULT =null;
		int mleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).left;
		int mright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).right;
		token m = (token)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).value;
		int eleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).right;
		token e = (token)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).value;
		int Rleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()).left;
		int Rright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()).right;
		Nodo R = (Nodo)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.peek()).value;
		 
                        RESULT = parser.crearNodo("principal","",m.getLinea(),m.getColumna());
                        RESULT.add(R); 
                        RESULT.add(parser.crearNodo("","",m.getLinea(),m.getColumna()));
                        RESULT.add(parser.crearNodo("","",m.getLinea(),m.getColumna()));
                    
              CUP$SintacticoGdato$result = parser.getSymbolFactory().newSymbol("ETIQUETASforma2",6, ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)), ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), RESULT);
            }
          return CUP$SintacticoGdato$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // CONTENIDOCUERPO ::= ETIQUETASforma1 
            {
              Nodo RESULT =null;
		int hleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()).left;
		int hright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()).right;
		Nodo h = (Nodo)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.peek()).value;
		
                        RESULT = parser.crearNodo("principales","",h.linea,h.columna);
                        RESULT.add(h);
                   
              CUP$SintacticoGdato$result = parser.getSymbolFactory().newSymbol("CONTENIDOCUERPO",4, ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), RESULT);
            }
          return CUP$SintacticoGdato$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // CONTENIDOCUERPO ::= CONTENIDOCUERPO ETIQUETASforma2 
            {
              Nodo RESULT =null;
		int rleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).left;
		int rright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).right;
		Nodo r = (Nodo)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).value;
		int hleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()).left;
		int hright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()).right;
		Nodo h = (Nodo)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.peek()).value;
		
                        r.add(h);   RESULT = r;
                   
              CUP$SintacticoGdato$result = parser.getSymbolFactory().newSymbol("CONTENIDOCUERPO",4, ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)), ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), RESULT);
            }
          return CUP$SintacticoGdato$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // CONTENIDOLISTA ::= listaF1 
            {
              Nodo RESULT =null;
		 RESULT = parser.crearNodo("principales","",0,0); 
              CUP$SintacticoGdato$result = parser.getSymbolFactory().newSymbol("CONTENIDOLISTA",3, ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), RESULT);
            }
          return CUP$SintacticoGdato$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // CONTENIDOLISTA ::= CONTENIDOCUERPO menq listaF1 
            {
              Nodo RESULT =null;
		int rleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).left;
		int rright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).right;
		Nodo r = (Nodo)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).value;
		    RESULT = r; 
              CUP$SintacticoGdato$result = parser.getSymbolFactory().newSymbol("CONTENIDOLISTA",3, ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)), ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), RESULT);
            }
          return CUP$SintacticoGdato$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // LISTAX ::= listaA1 explicit CONTENIDOLISTA 
            {
              Nodo RESULT =null;
		int mleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).left;
		int mright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).right;
		token m = (token)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)).value;
		int h2left = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).left;
		int h2right = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).right;
		token h2 = (token)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).value;
		int h3left = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()).left;
		int h3right = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()).right;
		Nodo h3 = (Nodo)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.peek()).value;
		
                    RESULT = parser.crearNodo("lista","",m.getLinea(),m.getColumna());
                    RESULT.add(parser.crearNodo("","",m.getLinea(),m.getColumna()));//estos seria como los imports
                    RESULT.add(h3);
                    
                    
               
              CUP$SintacticoGdato$result = parser.getSymbolFactory().newSymbol("LISTAX",2, ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-2)), ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), RESULT);
            }
          return CUP$SintacticoGdato$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // S_X ::= LISTAX 
            {
              Nodo RESULT =null;
		int hleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()).left;
		int hright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()).right;
		Nodo h = (Nodo)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.peek()).value;
		  RESULT = h; 
              CUP$SintacticoGdato$result = parser.getSymbolFactory().newSymbol("S_X",1, ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), RESULT);
            }
          return CUP$SintacticoGdato$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // S_X ::= 
            {
              Nodo RESULT =null;
		       RESULT = parser.crearNodo("lista","",0,0);
                    RESULT.add(parser.crearNodo("","",0,0));
                    RESULT.add(parser.crearNodo("","",0,0));
             
           
              CUP$SintacticoGdato$result = parser.getSymbolFactory().newSymbol("S_X",1, ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), RESULT);
            }
          return CUP$SintacticoGdato$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // INICIO ::= S_X 
            {
              Nodo RESULT =null;
		int hleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()).left;
		int hright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()).right;
		Nodo h = (Nodo)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.peek()).value;
		
                    root = h;
               
              CUP$SintacticoGdato$result = parser.getSymbolFactory().newSymbol("INICIO",0, ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), RESULT);
            }
          return CUP$SintacticoGdato$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // $START ::= INICIO EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).right;
		Nodo start_val = (Nodo)((java_cup.runtime.Symbol) CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)).value;
		RESULT = start_val;
              CUP$SintacticoGdato$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.elementAt(CUP$SintacticoGdato$top-1)), ((java_cup.runtime.Symbol)CUP$SintacticoGdato$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$SintacticoGdato$parser.done_parsing();
          return CUP$SintacticoGdato$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}

