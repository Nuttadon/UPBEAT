package org.example;

import java.util.Map;

class VarLit implements Expr {
    private String name;
    public VarLit(String name) {
        this.name = name;
    }
    public double eval(Map<String, Double> bindings) throws EvalError {
        if (bindings.containsKey(name)) return bindings.get(name);
        throw new EvalError("undefined variable: " + name);
    }
    public void prettyPrint(
            StringBuilder s) {
        s.append(name);
    }
}
