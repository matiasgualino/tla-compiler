package ar.edu.itba.geb.compiler.java;

import java_cup.runtime.Symbol;

%%

%public
%class AnalizadorLexico
%cup
%line
%column

%{
	private void error(String message)
	{
		System.err.println("Error at line " + (yyline+1) + ", column " + (yycolumn+1) + " : " + message);
	}
%}

letter          = [A-Za-z]
digit           = [0-9]
alphanumeric    = {letter}|{digit}
other_id_char   = [_]
identifier      = {letter}({alphanumeric}|{other_id_char})*
integer = {digit}({digit})*
whiteSpace = [\r\n\z\t\f ]
string = "\"" [^\"]* "\""
char            = [^\r\n]
comment_body    = {char}*\n
comment         = "//" {comment_body}
%%

//keywords

"class"		{return new Symbol(sym.CLASS,yyline+1,yycolumn+1);}
"public"	{return new Symbol(sym.PUBLIC,yyline+1,yycolumn+1);}	
"static"	{return new Symbol(sym.STATIC,yyline+1,yycolumn+1);}
"void"		{return new Symbol(sym.VOID,yyline+1,yycolumn+1);}
"main"		{return new Symbol(sym.MAIN,yyline+1,yycolumn+1);}
"length"		{return new Symbol(sym.LENGTH,yyline+1,yycolumn+1);}
"Str"		{return new Symbol(sym.STRING,yyline+1,yycolumn+1);}
"extends"	{return new Symbol(sym.EXTENDS,yyline+1,yycolumn+1);}
"return"	{return new Symbol(sym.RETURN,yyline+1,yycolumn+1);}
"int"		{return new Symbol(sym.INT,yyline+1,yycolumn+1);}
"bool"	{return new Symbol(sym.BOOL,yyline+1,yycolumn+1);}
"si"		{return new Symbol(sym.IF,yyline+1,yycolumn+1);}
"sino"		{return new Symbol(sym.ELSE,yyline+1,yycolumn+1);}
"mientras"	{return new Symbol(sym.WHILE,yyline+1,yycolumn+1);}
"imprimirInt"	{return new Symbol(sym.PRINT_INT,yyline+1,yycolumn+1);}
"imprimirStr"	{return new Symbol(sym.PRINT_STRING,yyline+1,yycolumn+1);}
"true"		{return new Symbol(sym.TRUE,yyline+1,yycolumn+1);}
"false"		{return new Symbol(sym.FALSE,yyline+1,yycolumn+1);}
"this"		{return new Symbol(sym.THIS,yyline+1,yycolumn+1);}
"new"		{return new Symbol(sym.NEW,yyline+1,yycolumn+1);}
"null"		{return new Symbol(sym.NULL_LITERAL, yyline+1, yycolumn+1);}

"++"		{return new Symbol(sym.ADD_OP, yyline+1, yycolumn+1);}
"--"		{return new Symbol(sym.SUB_OP, yyline+1, yycolumn+1);}
"&&"		{return new Symbol(sym.AND, yyline+1, yycolumn+1);}
"=="		{return new Symbol(sym.EQUAL, yyline+1, yycolumn+1);}
"<="		{return new Symbol(sym.LESS_OR_EQUAL, yyline+1, yycolumn+1);}
">="		{return new Symbol(sym.GREATER_OR_EQUAL, yyline+1, yycolumn+1);}
"!="		{return new Symbol(sym.NOT_EQUAL, yyline+1, yycolumn+1);}
"+="		{return new Symbol(sym.ADD_ASSIGN, yyline+1, yycolumn+1);}
"-="		{return new Symbol(sym.SUB_ASSIGN, yyline+1, yycolumn+1);}
"*="		{return new Symbol(sym.MULT_ASSIGN, yyline+1, yycolumn+1);}
"/="		{return new Symbol(sym.DIV_ASSIGN, yyline+1, yycolumn+1);}
"%="		{return new Symbol(sym.MOD_ASSIGN, yyline+1, yycolumn+1);}
"||"		{return new Symbol(sym.OR, yyline+1, yycolumn+1);}
"!"			{return new Symbol(sym.NOT, yyline+1, yycolumn+1);}
"="			{return new Symbol(sym.ASSIGN, yyline+1, yycolumn+1);}
"<"			{return new Symbol(sym.LESS, yyline+1, yycolumn+1);}
">"			{return new Symbol(sym.GREATER, yyline+1, yycolumn+1);}
"/"			{return new Symbol(sym.DIV, yyline+1, yycolumn+1);}
"%"			{return new Symbol(sym.MOD, yyline+1, yycolumn+1);}
"+"			{return new Symbol(sym.ADD,yyline+1,yycolumn+1);}
"-"			{return new Symbol(sym.SUB,yyline+1,yycolumn+1);}
"*"			{return new Symbol(sym.MULT,yyline+1,yycolumn+1);}

","			{return new Symbol(sym.COMMA,yyline+1,yycolumn+1);}
":"			{return new Symbol(sym.COLON,yyline+1,yycolumn+1);}
";"			{return new Symbol(sym.SEMI,yyline+1,yycolumn+1);}
"."			{return new Symbol(sym.DOT,yyline+1,yycolumn+1);}
"{"			{return new Symbol(sym.LBRACE,yyline+1,yycolumn+1);}
"}"			{return new Symbol(sym.RBRACE,yyline+1,yycolumn+1);}
"("			{return new Symbol(sym.LPARENS,yyline+1,yycolumn+1);}
")"			{return new Symbol(sym.RPARENS,yyline+1,yycolumn+1);}
"["			{return new Symbol(sym.LBRACKET,yyline+1,yycolumn+1);}
"]"			{return new Symbol(sym.RBRACKET,yyline+1,yycolumn+1);}

{string}		{return new Symbol(sym.STRING_LITERAL, yyline+1, yycolumn+1, yytext());}
{identifier}	{return new Symbol(sym.ID, yyline+1, yycolumn+1, yytext());}
{integer}		{return new Symbol(sym.NUM, yyline+1, yycolumn+1,  Integer.parseInt(yytext()));}
{whiteSpace} 	{}
{comment} 		{ /* Ignore comment. */ }

" "		{}