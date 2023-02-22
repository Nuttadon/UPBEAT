package org.example;

public class ArithExprFactory {
    private static ArithExprFactory instance;
    private ArithExprFactory(){}
    public BinaryArithExpr newArithExpr(Expr left, String op, Expr right){
        return new BinaryArithExpr(left,op,right);
    }
    public static ArithExprFactory instance() {
        if (instance == null) {
            // lazy instantiation
            instance = new ArithExprFactory();
        }
        return instance;
    }

}
