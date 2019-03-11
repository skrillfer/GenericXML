
package Analizadores.Gxml;


import java.util.LinkedList;
import java_cup.runtime.*;
%%
%class LexGxml
%public
%full
%unicode
%line
%column
%char
%ignorecase
%cup

LineTerminator = \r|\n|\r\n|\n\r|\t
WhiteSpace = {LineTerminator} | [ \t\f]|\t
Id = [:jletter:]["�"|"�"|"�"|"�"|"�"|[:jletterdigit:]|"_"|]*
Numero = ([-])?[:digit:][[:digit:]]* 
Decimal = ([-])?([:digit:][[:digit:]]*)? ([.][:digit:][[:digit:]]*)?
cadena = [\"] [^(\")]* [\"]


comm_multilinea = "#$" ["$"]* [^$] ~"$#" | "#$" ["$"]* "$#"

comm_linea = ["#"] ["#"] [^\r\n]* [^\r\n]

%%
/* SIGNOS */
<YYINITIAL> {cadena} {return new Symbol(sym.string_literal, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "/" {return new Symbol(sym.div, new token(yycolumn, yyline, yytext()));}



<YYINITIAL> "=" {return new Symbol(sym.igual, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "{" {return new Symbol(sym.alla, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "}" {return new Symbol(sym.clla, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> ";" {return new Symbol(sym.pyc, new token(yycolumn, yyline, yytext()));}

>([^<]*)< {System.out.println("tk:"+yytext()); return new Symbol(sym.explicit, new token(yycolumn, yyline, yytext()));}
"{"([^}]*)"}" {System.out.println("Yk:"+yytext()); return new Symbol(sym.event, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<" {System.out.println("tk:"+yytext()); return new Symbol(sym.menq, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> ">" {System.out.println("tk:"+yytext()); return new Symbol(sym.mayq, new token(yycolumn, yyline, yytext()));}


/* PALABRAS RESERVADAS - COMPONENTES */

<YYINITIAL> "<importar"    { return new Symbol(sym.importarA2, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "importar>"    {return new Symbol(sym.importarF1, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<ventana"     {System.out.println("tk:"+yytext()); return new Symbol(sym.ventanaA1, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "ventana>"     {System.out.println("tk:"+yytext()); return new Symbol(sym.ventanaF1, new token(yycolumn, yyline, yytext()));}



<YYINITIAL> "<contenedor"  {System.out.println("tk:"+yytext()); return new Symbol(sym.contenedorA1, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "contenedor"   {System.out.println("tk:"+yytext()); return new Symbol(sym.contenedorA2, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "/contenedor>"  {System.out.println("tk:"+yytext()); return new Symbol(sym.contenedorF1, new token(yycolumn, yyline, yytext()));}


<YYINITIAL> "<boton"        {System.out.println("tk:"+yytext()); return new Symbol(sym.botonA1, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "boton"         {System.out.println("tk:"+yytext()); return new Symbol(sym.botonA2, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "/boton>"       {System.out.println("tk:"+yytext()); return new Symbol(sym.botonF1, new token(yycolumn, yyline, yytext()));}


<YYINITIAL> "<texto"        {System.out.println("tk:"+yytext()); return new Symbol(sym.textoA1, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "texto"         {System.out.println("tk:"+yytext()); return new Symbol(sym.textoA2, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "/texto>"       {System.out.println("tk:"+yytext()); return new Symbol(sym.textoF1, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<control"        {System.out.println("tk:"+yytext()); return new Symbol(sym.controlA1, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "control"         {System.out.println("tk:"+yytext()); return new Symbol(sym.controlA2, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "/control>"       {System.out.println("tk:"+yytext()); return new Symbol(sym.controlF1, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<dato"        {System.out.println("tk:"+yytext()); return new Symbol(sym.datoA1, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "dato"         {System.out.println("tk:"+yytext()); return new Symbol(sym.datoA2, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "/dato>"       {System.out.println("tk:"+yytext()); return new Symbol(sym.datoF1, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<defecto"        {System.out.println("tk:"+yytext()); return new Symbol(sym.defectoA1, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "defecto"         {System.out.println("tk:"+yytext()); return new Symbol(sym.defectoA2, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "/defecto>"       {System.out.println("tk:"+yytext()); return new Symbol(sym.defectoF1, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<listadatos"        {System.out.println("tk:"+yytext()); return new Symbol(sym.listadatosA1, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "listadatos"         {System.out.println("tk:"+yytext()); return new Symbol(sym.listadatosA2, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "/listadatos>"       {System.out.println("tk:"+yytext()); return new Symbol(sym.listadatosF1, new token(yycolumn, yyline, yytext()));}


<YYINITIAL> "<listadatos"        {System.out.println("tk:"+yytext()); return new Symbol(sym.listadatosA1, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "listadatos"         {System.out.println("tk:"+yytext()); return new Symbol(sym.listadatosA2, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "/listadatos>"       {System.out.println("tk:"+yytext()); return new Symbol(sym.listadatosF1, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<enviar"        {System.out.println("tk:"+yytext()); return new Symbol(sym.enviarA1, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "enviar"         {System.out.println("tk:"+yytext()); return new Symbol(sym.enviarA2, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "/enviar>"       {System.out.println("tk:"+yytext()); return new Symbol(sym.enviarF1, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "<multimedia"        {System.out.println("tk:"+yytext()); return new Symbol(sym.multimediaA1, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "multimedia"         {System.out.println("tk:"+yytext()); return new Symbol(sym.multimediaA2, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "/multimedia>"       {System.out.println("tk:"+yytext()); return new Symbol(sym.multimediaF1, new token(yycolumn, yyline, yytext()));}

/*
HAY que preguntar sobre BOTON
*/


/* PALABRAS RESERVADAS - ATRIBUTOS de COMPONENTES */
<YYINITIAL> "tipo"         {return new Symbol(sym.tipo, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "nombre"       {return new Symbol(sym.tipo, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "id"           {return new Symbol(sym.id, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "alto"         {return new Symbol(sym.alto, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "ancho"        {return new Symbol(sym.ancho, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "fuente"       {return new Symbol(sym.fuente, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "tam"          {return new Symbol(sym.tam, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "color"        {return new Symbol(sym.color, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "x"            {return new Symbol(sym.x, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "y"            {return new Symbol(sym.y, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "negrita"      {return new Symbol(sym.negrita, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "cursiva"      {return new Symbol(sym.cursiva, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "borde"        {return new Symbol(sym.borde, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "maximo"       {return new Symbol(sym.maximo, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "minimo"       {return new Symbol(sym.minimo, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "referencia"   {return new Symbol(sym.referencia, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "path"         {return new Symbol(sym.path, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "auto-reproduccion"    {return new Symbol(sym.autorepro, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "accioninicial"        {return new Symbol(sym.accionini, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "accionfinal"          {return new Symbol(sym.accionfin, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "accion"       {return new Symbol(sym.accion, new token(yycolumn, yyline, yytext()));}




<YYINITIAL> {Id} {return new Symbol(sym.ID, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> {Numero} {return new Symbol(sym.int_literal, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> {Decimal} {return new Symbol(sym.double_literal, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "verdadero"|"falso" {return new Symbol(sym.bool_literal, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> {comm_multilinea} {}
<YYINITIAL> {comm_linea}      {}

{LineTerminator} {/* ignorar */}
{WhiteSpace} {/* ignorar */}
. {System.err.println(yyline+","+yycolumn+"=["+yytext()+"],"+yychar); }
