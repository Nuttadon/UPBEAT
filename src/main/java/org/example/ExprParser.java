package org.example;

import java.io.IOException;
import java.util.HashMap;

public class ExprParser implements Parser{
    private ExprTokenizer tkz;
    private static boolean isWhile=false;
    private static boolean whileConCount=false;

    private StringBuilder s = new StringBuilder();
    ArithExprFactory arthFac = ArithExprFactory.instance();
    private HashMap<String, Double> identifiers = new HashMap<String, Double>();
    public ExprParser(ExprTokenizer tkz) {
        this.tkz = tkz;
    }

    public void Plan() throws SyntaxError, LexicalError, EvalError, IOException {
        Statement();
        while (tkz.hasNextToken()) {
            Statement();
        }
        System.out.println("m"+identifiers.get("m"));
        System.out.println("n"+identifiers.get("n"));
    }
    private void Statement() throws SyntaxError, LexicalError, EvalError, IOException {
        WhileStatement();
        IfStatement();
        BlockStatement();
        Command();
    }
    private void WhileStatement() throws SyntaxError, LexicalError, EvalError, IOException {
        if (tkz.peek("while")){
            tkz.consume("while");
            isWhile = true;
            tkz.consume("(");
            Expr t = Expression();
            tkz.consume(")");
            System.out.println(identifiers.get("m"));
            whileConCount = true;
            for(int counter = 0 ; counter < 10000 && t.eval(identifiers)>0;counter++){
                Statement();
                System.out.println(identifiers.get("m"));
                whileConCount = false;
                isWhile = false;
                //tkz.stepBack();
                while(tkz.peek("(")) tkz.consume("(");
                t = Expression();
                System.out.println(identifiers.get("m"));
                while(tkz.peek(")")) tkz.consume(")");
            }
            tkz.resetWhile();
            clearState();

        }
    }
    private void IfStatement() throws SyntaxError, LexicalError, EvalError, IOException {
        int ctfe = 0;
        if (tkz.peek("if")){
            tkz.consume("if");
            ctfe++;
            tkz.consume("(");
            Expr t = Expression();
            tkz.consume(")");
            if(t.eval(identifiers)>0){
                tkz.consume("then");
                Statement();
                while((ctfe%2)!=0){
                    if(tkz.peek("else")) {
                        tkz.consume();
                        ctfe++;
                    }
                    else if(tkz.peek("if")) {
                        tkz.consume();
                        ctfe++;
                    }
                    else tkz.consume();
                }
                clearState();

            }else{
                while(true){
                    if(tkz.peek("else")) {
                        ctfe++;
                        if(ctfe%2==0) break;
                        else tkz.consume();
                    }
                    else if(tkz.peek("if")) {
                        tkz.consume();
                        ctfe++;
                    }
                    else tkz.consume();

                }
                tkz.consume("else");
                Statement();
            }


        }
    }

    private void BlockStatement() throws SyntaxError, LexicalError, EvalError, IOException {
        if (tkz.peek("{")) {
            tkz.consume("{");
            while(!(tkz.peek("}"))){
                Statement();
                System.out.println(identifiers.get("m"));
            }
            tkz.consume("}");
        }
    }
    private void Command() throws SyntaxError, LexicalError, EvalError, IOException {
        ActionCommand();
        AssignmentStatement();
    }
    private void ActionCommand() throws SyntaxError, LexicalError, EvalError, IOException {
        if(tkz.peek("relocate")){
            tkz.consume("relocate");
            //setCityCenter();
        }else if(tkz.peek("done")){
            tkz.consume("done");
            //nextTurn();
        }
        AttackCommand();
        RegionCommand();
        MoveCommand();
    }
    private void AttackCommand() throws SyntaxError, LexicalError, EvalError, IOException {
        if(tkz.peek("shoot")){
            tkz.consume("shoot");
            Expr direc = Direction();
            Expr dmg = Expression();
            //shoot(direc.eval(),dmg.eval());
        }
    }
    private void RegionCommand() throws SyntaxError, LexicalError, EvalError, IOException {
        if(tkz.peek("collect")){
            tkz.consume("collect");
            Expr i = Expression();
            //collect(i.eval());
        }else if(tkz.peek("invest")){
            tkz.consume("invest");
            Expr i = Expression();
            //invest(i.eval());
        }
    }
    private void MoveCommand() throws SyntaxError, LexicalError, EvalError, IOException {
        if(tkz.peek("move")){
            tkz.consume("move");
            Expr d = Direction();
            //move(d.eval());
        }
    }
    private void AssignmentStatement() throws SyntaxError, LexicalError, EvalError, IOException {
        if((!(tkz.peek("collect")||tkz.peek("done")||tkz.peek("down")||tkz.peek("downleft")||tkz.peek("downright")||tkz.peek("else")||tkz.peek("if")||tkz.peek("invest")||tkz.peek("move")||tkz.peek("nearby")||tkz.peek("opponent")||tkz.peek("relocate")||tkz.peek("shoot")||tkz.peek("then")||tkz.peek("up")||tkz.peek("upleft")||tkz.peek("upright")||tkz.peek("while")))&&tkz.hasNextToken()) {
            String va = tkz.consume();
            tkz.consume("=");
            Expr v = Expression();
            identifiers.put(va, v.eval(identifiers));
        }
    }
    private Expr Expression() throws SyntaxError, LexicalError, EvalError, IOException {
        Expr e = Term();
        while (tkz.peek("+")||tkz.peek("-")) {
            if(tkz.peek("+")){
                tkz.consume();
                e = arthFac.newArithExpr(e, "+", Term());
            }else if(tkz.peek("-")){
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
                tkz.consume();
                t = arthFac.newArithExpr(t, "*", Factor());
            }
            else if (tkz.peek("/")) {
                tkz.consume();
                t = arthFac.newArithExpr(t, "/", Factor());
                if(t.eval(identifiers)==Double.POSITIVE_INFINITY) {
                    throw new ArithmeticException("Divied by 0!");
                }
            }
            else if(tkz.peek("%")) {
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
            tkz.consume();
            f = arthFac.newArithExpr(f, "^", Power());
        }
        return f;
    }
    private Expr Power() throws SyntaxError, LexicalError, EvalError, IOException {
        if(identifiers.containsKey(tkz.peek())){
            Expr p = new Lit(identifiers.get(tkz.peek()));
            tkz.consume();
            return p;
        }else if(isNumber(tkz.peek())) {
            Expr p = new Lit(Integer.parseInt(tkz.consume()));
            return p;
        }else if(tkz.peek("(")) {
            tkz.consume();
            Expr p = Expression();
            tkz.consume(")");
            return p;
        }
//        else if(!(tkz.peek("collect")||tkz.peek("done")||tkz.peek("down")||tkz.peek("downleft")||tkz.peek("downright")||tkz.peek("else")||tkz.peek("if")||tkz.peek("invest")||tkz.peek("move")||tkz.peek("nearby")||tkz.peek("opponent")||tkz.peek("relocate")||tkz.peek("shoot")||tkz.peek("then")||tkz.peek("up")||tkz.peek("upleft")||tkz.peek("upright")||tkz.peek("while"))){
//            if(wLoop) wLoopStatement.append(tkz.peek());
//            Expr p = new VarLit(tkz.consume());
//            return p;
//        }
        else{
            Expr p = InfoExpression();
            return p;
        }
    }
    private Expr InfoExpression() throws SyntaxError, LexicalError, EvalError, IOException {
        if(tkz.peek("opponent")){
            tkz.consume("opponent");
            //int oppo = opponent()
            Expr op =new Lit(3);
            return op;
        }else{
            tkz.consume("nearby");
            Expr t = Direction();
            //int near = nearby(t.eval);
            Expr nb =new Lit(1);
            return nb;
        }
    }
    private Expr Direction() throws SyntaxError, LexicalError, EvalError, IOException {
        if(tkz.peek("up")){
            Expr e = new Lit(1);
            tkz.consume();
            return e;
        }else if(tkz.peek("down")){
            Expr e = new Lit(4);
            tkz.consume();
            return e;
        }else if(tkz.peek("upleft")){
            Expr e = new Lit(6);
            tkz.consume();
            return e;
        }else if(tkz.peek("upright")){
            Expr e = new Lit(2);
            tkz.consume();
            return e;
        }else if(tkz.peek("downleft")){
            Expr e = new Lit(5);
            tkz.consume();
            return e;
        }else if(tkz.peek("downright")){
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

    public  static  boolean checkWhile(){
        return isWhile;
    }

    public  static  boolean checkWhileCon(){
        return whileConCount;
    }
    private void clearState() throws LexicalError, SyntaxError {
        if(tkz.peek("{")){
            while(!(tkz.peek("}"))) tkz.consume();
            tkz.consume("}");
        } else if (tkz.peek("done")||tkz.peek("relocate")||tkz.peek("opponent")) {
            tkz.consume();
        } else if (tkz.peek("move")||tkz.peek("nearby")) {
            tkz.consume();
            tkz.consume();
        } else if (tkz.peek("shoot")) {
            tkz.consume();
            tkz.consume();
            tkz.consume();
        } else if (tkz.peek("invest")||tkz.peek("collect")) {
            tkz.consume();
            tkz.consume();
            while(tkz.peek("+")||tkz.peek("-")||tkz.peek("*")||tkz.peek("/")||tkz.peek("%")||tkz.peek("^")){
                tkz.consume();
                tkz.consume();
            }
        }else if (identifiers.containsKey(tkz.peek())) {
            tkz.consume();
            tkz.consume();
            tkz.consume();
            while(tkz.peek("+")||tkz.peek("-")||tkz.peek("*")||tkz.peek("/")||tkz.peek("%")||tkz.peek("^")){
                tkz.consume();
                tkz.consume();
            }
        }
    }


}






