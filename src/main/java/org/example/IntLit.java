package org.example;

import java.util.Map;

public class IntLit implements Expr{
    private double val;
    public IntLit(double val){
        this.val =val;
    }
    @Override
    public double eval(Map<String, Double> bindings) {
        return val;
    }

    @Override
    public void prettyPrint(StringBuilder s) {
        s.append(val);
    }
}
