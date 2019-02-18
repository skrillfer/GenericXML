package Analizadores.Script;


import java.util.LinkedList;
import java_cup.runtime.*;
%%
%class LexScript
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
Numero = [:digit:][[:digit:]]* 
Decimal = ([:digit:][[:digit:]]*)? ([.][:digit:][[:digit:]]*)?
cadena = [\"] [^(\")]* [\"]

%%
/* SIGNOS */

/* OPERADORES ASIGNACION */
<YYINITIAL> "+=" {return new Symbol(sym.asigmas, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "-=" {return new Symbol(sym.asigmen, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "*=" {return new Symbol(sym.asigpor, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "/=" {return new Symbol(sym.asigdiv, new token(yycolumn, yyline, yytext()));}


/* OPERADORES ARITMETICOS */
<YYINITIAL> "+" {return new Symbol(sym.mas, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "-" {return new Symbol(sym.menos, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "*" {return new Symbol(sym.por, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "/" {return new Symbol(sym.div, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "^" {return new Symbol(sym.pot, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "++" {return new Symbol(sym.add, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "--" {return new Symbol(sym.sub, new token(yycolumn, yyline, yytext()));}

/* OPERADORES RELACIONALES */
<YYINITIAL> "<"     {return new Symbol(sym.menq, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "<="    {return new Symbol(sym.meniq, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> ">"     {return new Symbol(sym.mayq, new token(yycolumn, yyline, yytext()));} 
<YYINITIAL> ">="    {return new Symbol(sym.mayiq, new token(yycolumn, yyline, yytext()));} 
<YYINITIAL> "=="    {return new Symbol(sym.ig_ig, new token(yycolumn, yyline, yytext()));} 
<YYINITIAL> "!="    {return new Symbol(sym.dif, new token(yycolumn, yyline, yytext()));} 



/* OPERADORES LOGICOS */
<YYINITIAL> "&&" {return new Symbol(sym.and, new token(yycolumn, yyline, yytext()));} 
<YYINITIAL> "||" {return new Symbol(sym.or, new token(yycolumn, yyline, yytext()));} 
<YYINITIAL> "!" {return new Symbol(sym.not, new token(yycolumn, yyline, yytext()));} 



<YYINITIAL> ";" {return new Symbol(sym.pyc, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "," {return new Symbol(sym.coma, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "." {return new Symbol(sym.pto, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> ":" {return new Symbol(sym.dspts, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "=" {return new Symbol(sym.igual, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "(" {return new Symbol(sym.apar, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> ")" {return new Symbol(sym.cpar, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "{" {return new Symbol(sym.alla, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "}" {return new Symbol(sym.clla, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "[" {return new Symbol(sym.acorch, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "]" {return new Symbol(sym.ccorch, new token(yycolumn, yyline, yytext()));}


/* PALABRAS RESERVADAS - FunctionalScript */
<YYINITIAL> "var"               {return new Symbol(sym.var, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "imprimir"          {return new Symbol(sym.imprimir, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "importar"          {return new Symbol(sym.importar, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "funcion"           {return new Symbol(sym.funcion, new token(yycolumn, yyline, yytext()));}

/* PALABRAS RESERVADAS - Instruccions */

<YYINITIAL> "detener"           {return new Symbol(sym.detener, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "selecciona"        {return new Symbol(sym.selecciona, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "caso"              {return new Symbol(sym.caso, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "defecto"           {return new Symbol(sym.defecto, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "retornar"          {return new Symbol(sym.retornar, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "si"                {return new Symbol(sym.si, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "sino"              {return new Symbol(sym.sino, new token(yycolumn, yyline, yytext()));}


/* PALABRAS RESERVADAS -NATIVE Method o Functions */
<YYINITIAL> "descendente"       {return new Symbol(sym.descendente, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "ascendente"        {return new Symbol(sym.ascendente, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "creararraydesdearchivo"         {return new Symbol(sym.creararraydesdearchivo, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "invertir"          {return new Symbol(sym.invertir, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "maximo"            {return new Symbol(sym.maximo, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "minimo"            {return new Symbol(sym.minimo, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "reduce"            {return new Symbol(sym.reduce, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "todos"             {return new Symbol(sym.todos, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "alguno"            {return new Symbol(sym.alguno, new token(yycolumn, yyline, yytext()));}


/* PALABRAS RESERVADAS - Arrow Functions */

<YYINITIAL> "filtrar"           {return new Symbol(sym.filtrar, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "buscar"            {return new Symbol(sym.buscar, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "map"               {return new Symbol(sym.map, new token(yycolumn, yyline, yytext()));}

/* PALABRAS RESERVADAS - Funciones Nativa de INTERFAZ */
<YYINITIAL> "leergxml"                  {return new Symbol(sym.leergxml, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "obtenerporetiqueta"        {return new Symbol(sym.obtenerporetiqueta, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "obtenerporid"              {return new Symbol(sym.obtenerporid, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "obtenerpornombre"          {return new Symbol(sym.obtenerpornombre, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "crearventana"              {return new Symbol(sym.crearventana, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "crearcontenedor"           {return new Symbol(sym.crearcontenedor, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "creartexto"                {return new Symbol(sym.creartexto, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "crearcajatexto"            {return new Symbol(sym.crearcajatexto, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "crearareatexto"            {return new Symbol(sym.crearareatexto, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "crearcontrolnumerico"      {return new Symbol(sym.crearcontrolnumerico, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "creardesplegable"          {return new Symbol(sym.creardesplegable, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "crearboton"                {return new Symbol(sym.crearboton, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "crearimagen"               {return new Symbol(sym.crearimagen, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "crearreproductor"          {return new Symbol(sym.crearreproductor, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "crearvideo"                {return new Symbol(sym.crearvideo, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "alclic"                    {return new Symbol(sym.alclic, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "alcargar"                  {return new Symbol(sym.alcargar, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "alcerrar"                  {return new Symbol(sym.alcerrar, new token(yycolumn, yyline, yytext()));}


<YYINITIAL> {cadena} {return new Symbol(sym.string_literal, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> {Id} {return new Symbol(sym.iden, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> {Numero} {return new Symbol(sym.int_literal, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> {Decimal} {return new Symbol(sym.double_literal, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "verdadero"|"falso" {return new Symbol(sym.bool_literal, new token(yycolumn, yyline, yytext()));}


{LineTerminator} {/* ignorar */}
{WhiteSpace} {/* ignorar */}
. {System.out.println(yyline+","+yycolumn+"=["+yytext()+"],"+yychar); }
