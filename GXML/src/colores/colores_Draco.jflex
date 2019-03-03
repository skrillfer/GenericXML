package Gramatica_D;
import java.io.*;   
import javax.swing.text.Segment;   
import org.fife.ui.rsyntaxtextarea.*;   
%%   
%public   
%caseless
%class colores_Draco
%extends AbstractJFlexCTokenMaker   
%unicode   
%type org.fife.ui.rsyntaxtextarea.Token   
/**   
 * A simple TokenMaker example.   
 */   
%{   
   /**   
    * Constructor.  This must be here because JFlex does not generate a   
    * no-parameter constructor.   
    */   
   public ExampleTokenMaker() {   
   }   
   
   /**   
    * Adds the token specified to the current linked list of tokens.   
    *   
    * @param tokenType The token's type.   
    * @see #addToken(int, int, int)   
    */   
   private void addHyperlinkToken(int start, int end, int tokenType) {   
      int so = start + offsetShift;   
      addToken(zzBuffer, start,end, tokenType, so, true);   
   }   
   
   /**   
    * Adds the token specified to the current linked list of tokens.   
    *   
    * @param tokenType The token's type.   
    */   
   private void addToken(int tokenType) {   
      addToken(zzStartRead, zzMarkedPos-1, tokenType);   
   }   
   
   /**   
    * Adds the token specified to the current linked list of tokens.   
    *   
    * @param tokenType The token's type.   
    * @see #addHyperlinkToken(int, int, int)   
    */   
   private void addToken(int start, int end, int tokenType) {   
      int so = start + offsetShift;   
      addToken(zzBuffer, start,end, tokenType, so, false);   
   }   
   
   /**   
    * Adds the token specified to the current linked list of tokens.   
    *   
    * @param array The character array.   
    * @param start The starting offset in the array.   
    * @param end The ending offset in the array.   
    * @param tokenType The token's type.   
    * @param startOffset The offset in the document at which this token   
    *        occurs.   
    * @param hyperlink Whether this token is a hyperlink.   
    */   
   public void addToken(char[] array, int start, int end, int tokenType,   
                  int startOffset, boolean hyperlink) {   
      super.addToken(array, start,end, tokenType, startOffset, hyperlink);   
      zzStartRead = zzMarkedPos;   
   }   
   
   /**   
    * Returns the text to place at the beginning and end of a   
    * line to "comment" it in a this programming language.   
    *   
    * @return The start and end strings to add to a line to "comment"   
    *         it out.   
    */   
   public String[] getLineCommentStartAndEnd() {   
      return new String[] { "//", null };   
   }   
   
   /**   
    * Returns the first token in the linked list of tokens generated   
    * from <code>text</code>.  This method must be implemented by   
    * subclasses so they can correctly implement syntax highlighting.   
    *   
    * @param text The text from which to get tokens.   
    * @param initialTokenType The token type we should start with.   
    * @param startOffset The offset into the document at which   
    *        <code>text</code> starts.   
    * @return The first <code>Token</code> in a linked list representing   
    *         the syntax highlighted text.   
    */   
   public Token getTokenList(Segment text, int initialTokenType, int startOffset) {   
   
      resetTokenList();   
      this.offsetShift = -text.offset + startOffset;   
   
      // Start off in the proper state.   
      int state = Token.NULL;   
      switch (initialTokenType) {   
                  case Token.COMMENT_MULTILINE:   
            state = MLC;   
            start = text.offset;   
            break;   
   
         /* No documentation comments */   
         default:   
            state = Token.NULL;   
      }   
   
      s = text;   
      try {   
         yyreset(zzReader);   
         yybegin(state);   
         return yylex();   
      } catch (IOException ioe) {   
         ioe.printStackTrace();   
         return new TokenImpl();   
      }   
   
   }   
   
   /**   
    * Refills the input buffer.   
    *   
    * @return      <code>true</code> if EOF was reached, otherwise   
    *              <code>false</code>.   
    */   
   private boolean zzRefill() {   
      return zzCurrentPos>=s.offset+s.count;   
   }   
   
   /**   
    * Resets the scanner to read from a new input stream.   
    * Does not close the old reader.   
    *   
    * All internal variables are reset, the old input stream    
    * <b>cannot</b> be reused (internal buffer is discarded and lost).   
    * Lexical state is set to <tt>YY_INITIAL</tt>.   
    *   
    * @param reader   the new input stream    
    */   
   public final void yyreset(Reader reader) {   
      // 's' has been updated.   
      zzBuffer = s.array;   
      /*   
       * We replaced the line below with the two below it because zzRefill   
       * no longer "refills" the buffer (since the way we do it, it's always   
       * "full" the first time through, since it points to the segment's   
       * array).  So, we assign zzEndRead here.   
       */   
      //zzStartRead = zzEndRead = s.offset;   
      zzStartRead = s.offset;   
      zzEndRead = zzStartRead + s.count - 1;   
      zzCurrentPos = zzMarkedPos = zzPushbackPos = s.offset;   
      zzLexicalState = YYINITIAL;   
      zzReader = reader;   
      zzAtBOL  = true;   
      zzAtEOF  = false;   
   }   
   
%}      
DIGITO                     = ([0-9])   
CUALQUIERCARACTER1   = ([^\\'])   
CUALQUIERCARACTER2   = ([^\\\"\n])   
CARACTER               = ([\']({CUALQUIERCARACTER1})[\'])      
CADENA            = ([\"]({CUALQUIERCARACTER2})*[\"])      
  
ACOMEN               = "$*"   
CCOMEN               = "*$"   
COMENSIMPLE         = "$$"   
   
NUMEROS         = ({DIGITO}+)  
%state MLC   
   
%%   
   
<YYINITIAL> {   
    /* palabras reservadas*/   
    "var" |   
    "if" |
    "not" |
    "elif" |
    "smash" |
    "while" |
    "for" |
    "print" |
    "true" |
    "false" |
    "runmultdasm" |
    "point" |
    "quadrate" |
    "oval" |
    "string" |  
    "line"      { addToken(Token.RESERVED_WORD); }   
   
    {CARACTER}            { addToken(Token.LITERAL_CHAR); }
    {CADENA}            { addToken(Token.LITERAL_STRING_DOUBLE_QUOTE); }   

   /* COMENTARIOS */   
   {ACOMEN}               { start = zzMarkedPos-2; yybegin(MLC); }   
   {COMENSIMPLE}.*      { addToken(Token.COMMENT_EOL); addNullToken(); return firstToken; }   
   
   /* Numbers */   
   {NUMEROS}         { addToken(Token.LITERAL_NUMBER_DECIMAL_INT); }   
   
   /* Ended with a line not in a string or comment. */   
   \n |   
   <<EOF>>                  { addNullToken(); return firstToken; }   
   
   /* Catch any other (unhandled) characters. */   
   .                     { addToken(Token.IDENTIFIER); }   
   
}   
   
<MLC> {   
   [^\n*]+            {}   
   {CCOMEN}         { yybegin(YYINITIAL); addToken(start,zzStartRead+2-1, Token.COMMENT_MULTILINE); }   
   "*"               {}   
   \n |   
   <<EOF>>            { addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); return firstToken; }   
}   