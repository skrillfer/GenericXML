package Analizadores.Gdato;


import java.util.LinkedList;
import java_cup.runtime.*;
%%
%class LexGdato
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


comm_multilinea = "#$" ["$"]* [^$] ~"$#" | "#$" ["$"]* "$#"

comm_linea = ["#"] ["#"] [^\r\n]* [^\r\n]

%%
<YYINITIAL> "/"           {return new Symbol(sym.div, new token(yycolumn, yyline, yytext()));}

>([^<]*)<                 {return new Symbol(sym.explicit, new token(yycolumn, yyline, yytext()));     }

<YYINITIAL> "<"           {return new Symbol(sym.menq, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> ">"           {return new Symbol(sym.mayq, new token(yycolumn, yyline, yytext()));}


<YYINITIAL> "<lista"      {return new Symbol(sym.listaA1, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "/lista>"     {return new Symbol(sym.listaF1, new token(yycolumn, yyline, yytext()));}


<YYINITIAL> "<principal"  {return new Symbol(sym.principalA1, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> "principal"   {return new Symbol(sym.principalA2, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> "/principal>" {return new Symbol(sym.principalF1, new token(yycolumn, yyline, yytext()));}


<YYINITIAL> [<]{Id}       {return new Symbol(sym.idA1, new token(yycolumn, yyline, yytext()));}
<YYINITIAL> {Id}          {return new Symbol(sym.idA2, new token(yycolumn, yyline, yytext()));}

<YYINITIAL> [/]{Id}[>]    {return new Symbol(sym.idF1, new token(yycolumn, yyline, yytext()));}



<YYINITIAL> {comm_multilinea} {}
<YYINITIAL> {comm_linea}      {}

{LineTerminator} {/* ignorar */}
{WhiteSpace} {/* ignorar */}
. {System.err.println(yyline+","+yycolumn+"=["+yytext()+"],"+yychar); }

