package org.example;

import java.util.Map;

public class Lit implements Expr{
    private double val;
    public Lit(double val){
        this.val = val;
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
