package org.example;

import java.io.IOException;
import java.util.HashMap;

public class ExprParser implements Parser{
    private ExprTokenizer tkz;
    private boolean isWhile = false;
    private int bc=0;
    private boolean turnEnd = false;
    private PlayerClass parserOwner;
    private StringBuilder whileStatement = new StringBuilder();
    private StringBuilder s = new StringBuilder();
    ArithExprFactory arthFac = ArithExprFactory.instance();
    private HashMap<String, Double> identifiers;
    public ExprParser(ExprTokenizer tkz,HashMap<String, Double> h,PlayerClass p) {

        this.tkz = tkz;
        this.identifiers = h;
        this.parserOwner = p;
    }

    public void Plan() throws SyntaxError, LexicalError, EvalError, IOException {
        while (tkz.hasNextToken()) {
            if(turnEnd) {
                turnEnd = false;
                break;
            }else{
                if(tkz.peek("}")) tkz.consume();
                Statement();
            }
        }
    }
    private void Statement() throws SyntaxError, LexicalError, EvalError, IOException {
        WhileStatement();
        IfStatement();
        BlockStatement();
        Command();
    }
    private void WhileStatement() throws SyntaxError, LexicalError, EvalError, IOException {
        if (tkz.peek("while")){
            if(isWhile) whileStatement.append("while");
            tkz.consume("while");
            if(isWhile) whileStatement.append("(");
            tkz.consume("(");
            Expr t = Expression();
            if(isWhile) whileStatement.append(")");
            tkz.consume(")");
            if(t.eval(identifiers)>0){
                isWhile = true;
                Statement();
                System.out.println(whileStatement.toString());
                System.out.println(identifiers.get("m"));
                System.out.println(identifiers.get("n"));
                isWhile = false;
                for(int counter = 0 ; counter < 10000 && t.eval(identifiers)>0;counter++){
                    ExprTokenizer l = new ExprTokenizer(whileStatement.toString());
                    ExprParser p = new ExprParser(l,identifiers,parserOwner);
                    p.Plan();
                    System.out.println(identifiers.get("m"));
                    System.out.println(identifiers.get("n"));
                }
            }else {
                clearState();
            }

        }
    }
    private void IfStatement() throws SyntaxError, LexicalError, EvalError, IOException {
        int ctfe = 0;
        if (tkz.peek("if")){
            if(isWhile) whileStatement.append("if");
            tkz.consume("if");
            ctfe++;
            if(isWhile) whileStatement.append("(");
            tkz.consume("(");
            Expr t = Expression();
            if(isWhile) whileStatement.append(")");
            tkz.consume(")");
            if(t.eval(identifiers)>0){
                if(isWhile) whileStatement.append("then");
                tkz.consume("then");
                Statement();
                while((ctfe%2)!=0){
                    if(tkz.peek("else")) {
                        if(isWhile) whileStatement.append("else");
                        tkz.consume();
                        ctfe++;
                    }
                    if(tkz.peek("if")) {
                        if(isWhile) whileStatement.append("if");
                        tkz.consume();
                        ctfe++;
                    }
                    if((ctfe%2)==0)break;
                    else {
                        if(isWhile) whileStatement.append(tkz.peek());
                        tkz.consume();
                    }
                }
                clearState();
            }else{
                while(true){
                    if(tkz.peek("else")) {
                        ctfe++;
                        if(ctfe%2==0) break;
                        else {
                            if(isWhile) whileStatement.append(tkz.peek());
                            tkz.consume();
                        }
                    }
                    else if(tkz.peek("if")) {
                        if(isWhile) whileStatement.append("if");
                        tkz.consume();
                        ctfe++;
                    }
                    else {
                        if(isWhile) whileStatement.append(tkz.peek());
                        tkz.consume();
                    }

                }
                if(isWhile) whileStatement.append("else");
                tkz.consume("else");
                Statement();
            }
        }
    }

    private void BlockStatement() throws SyntaxError, LexicalError, EvalError, IOException {
        if (tkz.peek("{")) {
//            if(isWhile) whileStatement.append("{");
            tkz.consume("{");
            for(int counter = 0 ; counter < 10000 && !(tkz.peek("}"));counter++){
                Statement();
            }
//            if(isWhile) whileStatement.append("}");
            tkz.consume("}");
        }


    }
    private void Command() throws SyntaxError, LexicalError, EvalError, IOException {
        ActionCommand();
        AssignmentStatement();
    }
    private void ActionCommand() throws SyntaxError, LexicalError, EvalError, IOException {
        if(tkz.peek("done")){
            if(isWhile) whileStatement.append("done");
            tkz.consume("done");
            parserOwner.done();
            turnEnd = true;
            System.out.println("Turn End");
            return;
        }
        if(tkz.peek("relocate")){
            if(isWhile) whileStatement.append("relocate");
            tkz.consume("relocate");
            parserOwner.relocate();
        }
        AttackCommand();
        RegionCommand();
        MoveCommand();
    }
    private void AttackCommand() throws SyntaxError, LexicalError, EvalError, IOException {
        if(tkz.peek("shoot")){
            if(isWhile) whileStatement.append("shoot");
            tkz.consume("shoot");
            Expr direc = Direction();
            Expr dmg = Expression();
            //shoot(direc.eval(),dmg.eval());
        }
    }
    private void RegionCommand() throws SyntaxError, LexicalError, EvalError, IOException {
        if(tkz.peek("collect")){
            if(isWhile) whileStatement.append("collect");
            tkz.consume("collect");
            Expr i = Expression();
            //collect(i.eval());
        }
        if(tkz.peek("invest")){
            if(isWhile) whileStatement.append("invest");
            tkz.consume("invest");
            Expr i = Expression();
            parserOwner.invest((int)i.eval(identifiers));
        }
    }
    private void MoveCommand() throws SyntaxError, LexicalError, IOException, EvalError {
        if(tkz.peek("move")){
            if(isWhile) whileStatement.append("move");
            tkz.consume("move");
            Expr d = Direction();
            parserOwner.move((int) d.eval(identifiers));
        }
    }
    private void AssignmentStatement() throws SyntaxError, LexicalError, EvalError, IOException {
        if((!(tkz.peek("collect")||tkz.peek("done")||tkz.peek("down")||tkz.peek("downleft")||tkz.peek("downright")||tkz.peek("else")||tkz.peek("if")||tkz.peek("invest")||tkz.peek("move")||tkz.peek("nearby")||tkz.peek("opponent")||tkz.peek("relocate")||tkz.peek("shoot")||tkz.peek("then")||tkz.peek("up")||tkz.peek("upleft")||tkz.peek("upright")||tkz.peek("while")||tkz.peek("}")))&&tkz.hasNextToken()) {
            if(isWhile) whileStatement.append(tkz.peek());
            String va = tkz.consume();
            if(isWhile) whileStatement.append("=");
            tkz.consume("=");
            Expr v = Expression();
            identifiers.put(va, v.eval(identifiers));

        }
    }
    private Expr Expression() throws SyntaxError, LexicalError, EvalError, IOException {
        Expr e = Term();
        while (tkz.peek("+")||tkz.peek("-")) {
            if(tkz.peek("+")){
                if(isWhile) whileStatement.append("+");
                tkz.consume();
                e = arthFac.newArithExpr(e, "+", Term());
            }else if(tkz.peek("-")){
                if(isWhile) whileStatement.append("-");
                tkz.consume();
                e = arthFac.newArithExpr(e, "-", Term());
            }
        }
        return e;

    }
    private Expr Term() throws SyntaxError, LexicalError, EvalError, IOException {
        Expr t = Factor();
        while(tkz.peek("*")||tkz.peek("/")||tkz.peek("%")){
            if (tkz.peek("*")) {
                if(isWhile) whileStatement.append("*");
                tkz.consume();
                t = arthFac.newArithExpr(t, "*", Factor());
            }
            else if (tkz.peek("/")) {
                if(isWhile) whileStatement.append("/");
                tkz.consume();
                t = arthFac.newArithExpr(t, "/", Factor());
                if(t.eval(identifiers)==Double.POSITIVE_INFINITY) {
                    throw new ArithmeticException("Divied by 0!");
                }
            }
            else if(tkz.peek("%")) {
                if(isWhile) whileStatement.append("%");
                tkz.consume();
                t = arthFac.newArithExpr(t, "%", Factor());
                if(Double.toString(t.eval(identifiers)).equals("NaN")) {
                    throw new ArithmeticException("Moded by 0!");
                }
            }
        }
        return t;
    }
    private Expr Factor() throws SyntaxError, LexicalError, EvalError, IOException {
        Expr f = Power();
        while(tkz.peek("^")){
            if(isWhile) whileStatement.append("^");
            tkz.consume();
            f = arthFac.newArithExpr(Power(), "^",f);
        }
        return f;
    }
    private Expr Power() throws SyntaxError, LexicalError, EvalError, IOException {
        if(identifiers.containsKey(tkz.peek())){
            Expr p = new VarLit(tkz.peek());
            if(isWhile) whileStatement.append(tkz.peek());
            tkz.consume();
            return p;
        }else if(isNumber(tkz.peek())) {
            if(isWhile) whileStatement.append(tkz.peek());
            String inb = tkz.consume();
            Expr p = new Lit(Integer.parseInt(inb));
            return p;
        }else if(tkz.peek("(")) {
            if(isWhile) whileStatement.append("(");
            tkz.consume();
            Expr p = Expression();
            if(isWhile) whileStatement.append(")");
            tkz.consume(")");
            return p;
        }
        else{
            Expr p = InfoExpression();
            return p;
        }
    }
    private Expr InfoExpression() throws SyntaxError, LexicalError, IOException {
        if(tkz.peek("opponent")){
            if(isWhile) whileStatement.append("opponent");
            tkz.consume("opponent");
            //int oppo = opponent()
            Expr op =new Lit(3);
            return op;
        }else{
            if(isWhile) whileStatement.append("nearby");
            tkz.consume("nearby");
            Expr t = Direction();
            //int near = nearby(t.eval);
            Expr nb =new Lit(1);
            return nb;
        }
    }
    private Expr Direction() throws SyntaxError, LexicalError, IOException {
        if(tkz.peek("up")){
            Expr e = new Lit(1);
            if(isWhile) whileStatement.append("up");
            tkz.consume();
            return e;
        }else if(tkz.peek("down")){
            if(isWhile) whileStatement.append("down");
            Expr e = new Lit(4);
            tkz.consume();
            return e;
        }else if(tkz.peek("upleft")){
            if(isWhile) whileStatement.append("upleft");
            Expr e = new Lit(6);
            tkz.consume();
            return e;
        }else if(tkz.peek("upright")){
            if(isWhile) whileStatement.append("upright");
            Expr e = new Lit(2);
            tkz.consume();
            return e;
        }else if(tkz.peek("downleft")){
            if(isWhile) whileStatement.append("downleft");
            Expr e = new Lit(5);
            tkz.consume();
            return e;
        }else if(tkz.peek("downright")){
            if(isWhile) whileStatement.append("downright");
            Expr e = new Lit(3);
            tkz.consume();
            return e;
        }else{
            throw new IOException();
        }
    }

    private static boolean isNumber(String string) {
        if(string == null || string.equals("")) {
            return false;
        }
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private void clearState() throws LexicalError, SyntaxError {
        if(tkz.peek("{")){
            while(!(tkz.peek("}"))) {
                if(isWhile) whileStatement.append(tkz.peek());
                tkz.consume();
            }
            if(isWhile) whileStatement.append(tkz.peek());
            tkz.consume("}");
        }else if (tkz.peek("done")||tkz.peek("relocate")||tkz.peek("opponent")) {
            if(isWhile) whileStatement.append(tkz.peek());
            tkz.consume();
        } else if (tkz.peek("move")||tkz.peek("nearby")) {
            if(isWhile) whileStatement.append(tkz.peek());
            tkz.consume();
            if(isWhile) whileStatement.append(tkz.peek());
            tkz.consume();
        } else if (tkz.peek("shoot")) {
            if(isWhile) whileStatement.append(tkz.peek());
            tkz.consume();
            if(isWhile) whileStatement.append(tkz.peek());
            tkz.consume();
            if(isWhile) whileStatement.append(tkz.peek());
            tkz.consume();
        } else if (tkz.peek("invest")||tkz.peek("collect")) {
            if(isWhile) whileStatement.append(tkz.peek());
            tkz.consume();
            if(isWhile) whileStatement.append(tkz.peek());
            tkz.consume();
            while(tkz.peek("+")||tkz.peek("-")||tkz.peek("*")||tkz.peek("/")||tkz.peek("%")||tkz.peek("^")){
                if(isWhile) whileStatement.append(tkz.peek());
                tkz.consume();
                if(isWhile) whileStatement.append(tkz.peek());
                tkz.consume();
            }
        }else if (identifiers.containsKey(tkz.peek())) {
            if(isWhile) whileStatement.append(tkz.peek());
            tkz.consume();
            if(isWhile) whileStatement.append(tkz.peek());
            tkz.consume();
            if(isWhile) whileStatement.append(tkz.peek());
            tkz.consume();
            while(tkz.peek("+")||tkz.peek("-")||tkz.peek("*")||tkz.peek("/")||tkz.peek("%")||tkz.peek("^")){
                if(isWhile) whileStatement.append(tkz.peek());
                tkz.consume();
                if(isWhile) whileStatement.append(tkz.peek());
                tkz.consume();
            }
        }
    }


}





