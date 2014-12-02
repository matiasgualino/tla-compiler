import java_cup.runtime.Symbol;

%%

%cup

NUMBER = [1-9][0-9]*

%%

{NUMBER} {return new Symbol(sym.NUMERO, new Integer(yytext()));}
"+" {return new Symbol(sym.MAS);}
"-" {return new Symbol(sym.MENOS);}
"*" {return new Symbol(sym.POR);}
"/" {return new Symbol(sym.DIV);}
"(" {return new Symbol(sym.LPAREN);}
")" {return new Symbol(sym.RPAREN);}
";" {return new Symbol(sym.FIN);}
.|\n {}