package Parser;

import AST.Expression.Number;
import AST.Expression.*;
import Exception.*;
import Tokenizer.Tokenizer;

public class ExprParser extends StatementParser implements Parser{
    private SyntaxError syntaxError_long = new SyntaxError("Expected Number");

    public ExprParser(Tokenizer tkz) {
        super(tkz);
    }

    @Override
    public Expression parse() throws SyntaxError {
        return Expression();
    }

    private Expression Expression() throws SyntaxError {
        Expression e = Term();
        while (tkz.peek("+") || tkz.peek("-")){
            if(tkz.peek("+")){
                tkz.consume();
                e =  new BinaryArithExpr(e,"+", Term());
            }else if(tkz.peek("-")){
                tkz.consume();
                e =  new BinaryArithExpr(e,"-", Term());
            }
        }
        return e;
    }

    private Expression Term() throws SyntaxError {
        Expression e = Factor();
        while (tkz.peek("*") || tkz.peek("/") || tkz.peek("%")) {
            if(tkz.peek("*")) {
                tkz.consume();
                e = new BinaryArithExpr(e,"*", Factor());
            }else if(tkz.peek("/")) {
                tkz.consume();
                e = new BinaryArithExpr(e,"/", Factor());
            }else if(tkz.peek("%")) {
                tkz.consume();
                e = new BinaryArithExpr(e,"%", Factor());
            }
        }
        return e;
    }

    private Expression Factor() throws SyntaxError {
        Expression p = Power();
        if (tkz.peek("^")){
            tkz.consume();
            Expression f = Factor();
            return new BinaryArithExpr(p,"^",f);
        }
        return p;
    }

    private Expression Power() throws SyntaxError{
        Expression e;
        String number = tkz.peek();
        if(isDigit(number.charAt(0))){
            try {
                tkz.consume();
                e = new Number(Long.parseUnsignedLong(number));
            }catch (NumberFormatException error){
                throw error;
            }
        }else if(tkz.peek("(")){
            tkz.consume("(");
            e = Expression();
            tkz.consume(")");
        }else if(tkz.peek("opponent")){
            tkz.consume();
            e = Info(true);
        }else if(tkz.peek("nearby")){
            tkz.consume();
            e = Info(false);
        }else{
            String identifier = tkz.consume();
            if(!isIdentifier(identifier)) throw syntaxError_identifier;
            e = new Identifier(identifier);
        }
        return e;
    }

    private Expression Info(boolean isOpponent) throws SyntaxError {
        if(isOpponent) return new Opponent();
        else return new Nearby(parseDirection());
    }
}