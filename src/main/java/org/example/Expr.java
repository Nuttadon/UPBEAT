package org.example;

import java.util.Map;

public interface Expr extends Node{
    double eval(Map<String, Integer> bindings) throws EvalError;
}
