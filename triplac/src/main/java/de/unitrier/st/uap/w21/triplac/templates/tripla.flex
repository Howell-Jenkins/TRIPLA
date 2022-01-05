package de.unitrier.st.uap.w21.triplac.lex;
import de.unitrier.st.uap.w21.triplac.parser.sym;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;
import java_cup.runtime.SymbolFactory;
import java.io.Reader;



%%

%public
%class Lexer
%line
%column
%unicode
%cupsym sym
%cup

%{
    public SymbolFactory sf = new ComplexSymbolFactory();


%}

%eofval{
    return sf.newSymbol("EOF", sym.EOF);
%eofval}

/* Macro Declarations */
LineTerminator  = \r | \n | \r\n
WhiteSpace      = {LineTerminator} | [ \t\f]
Letter = [a-zA-Z]
Digit = [0-9]
Character = '{Letter}' | '{Digit}'
Integer = 0|[1-9]{Digit}*
Boolean = true | false
String = \"(\\.|[^\"])*\"

IdentifierCharacter = {Letter} | {Digit} | "_"
Identifier = {Letter}{IdentifierCharacter}*

/* Comments */
TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
InputCharacter = [^\r\n]
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/*" "*"+ [^/*] ~"*/"
Comment = {TraditionalComment} | {EndOfLineComment} |
          {DocumentationComment}

%%
<YYINITIAL> {
    // Keyword tokens
    "let"			{return sf.newSymbol("LET", sym.LET);}
    "in"			{return sf.newSymbol("IN", sym.IN);}
    "if"			{return sf.newSymbol("IF", sym.IF);}
    "then"			{return sf.newSymbol("THEN", sym.THEN);}
    "else"			{return sf.newSymbol("ELSE", sym.ELSE);}
    "do"            {return sf.newSymbol("DO", sym.DO);}
    "while"         {return sf.newSymbol("WHILE", sym.WHILE);}

    //boolean
    "true"          {return sf.newSymbol("TRUE", sym.TRUE);}
    "false"         {return sf.newSymbol("FALSE", sym.FALSE);}

    // Special Chars
    "="				{return sf.newSymbol("ASSIGN", sym.ASSIGN);}
    "("				{return sf.newSymbol("LPAR", sym.LPAR);}
    ")"				{return sf.newSymbol("RPAR", sym.RPAR);}
    "{"				{return sf.newSymbol("LBRA", sym.LBRA);}
    "}"				{return sf.newSymbol("RBRA", sym.RBRA);}
    ","				{return sf.newSymbol("COMMA", sym.COMMA);}
    ";"				{return sf.newSymbol("SMICOLON", sym.SEMI);}

    // Aop
    "+"				{return sf.newSymbol("ADD", sym.ADD);}
    "-"				{return sf.newSymbol("SUB", sym.SUB);}
    "*"				{return sf.newSymbol("MUL", sym.MUL);}
    "/"				{return sf.newSymbol("DIV", sym.DIV);}

    // Relop
    "=="			{return sf.newSymbol("EQ", sym.EQ);}
    "!="			{return sf.newSymbol("NEQ", sym.NEQ);}
    ">"				{return sf.newSymbol("GT", sym.GT);}
    "<"				{return sf.newSymbol("LT", sym.LT);}
    ">="			{return sf.newSymbol("GTE", sym.GTE);}
    "<="			{return sf.newSymbol("LTE", sym.LTE);}
    "&&"            {return sf.newSymbol("AND", sym.AND);}
    "||"            {return sf.newSymbol("OR", sym.OR);}

    // Positive integers
    {Integer} { return sf.newSymbol("CONST", sym.CONST,  new Integer(yytext()) ); }

    // Identifiers
    {Identifier} {return sf.newSymbol("ID", sym.ID, yytext());}

    // White space
    {WhiteSpace} { /* do nothing */ }

	// Comments
	{Comment}     { /* do nothing */ }
}

// If the input did not match one of the rules above, throw an illegal character IOException
[^] { throw new java.io.IOException("Illegal character <" + yytext() + "> at line " + yyline + ", column " + yycolumn); }

