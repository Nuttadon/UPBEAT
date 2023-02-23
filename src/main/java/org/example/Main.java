package org.example;

import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws LexicalError, SyntaxError, EvalError, IOException {
        HashMap<String, Double> h = new HashMap<>();
//        ExprTokenizer t = new ExprTokenizer("m = 5^2 if(10-12)then if(m)then n = nearby up else {} else {n=65}");
//        ExprTokenizer t = new ExprTokenizer("m = (5^2) n=2-5 while(((m))) if(n) then m=m+3 else if(n) then shoot up 20 else {move up m=m-5}");
//        ExprTokenizer t = new ExprTokenizer("m = (5^2) n=10 while(((m))){m=m-5 while(n){n=n-2}}");
        ExprTokenizer t = new ExprTokenizer("m = (5^2) n=2-5 if(n) then m=m+3 else if(n) {then shoot up 20 mov} else {move up m=m-5}");
        ExprParser p = new ExprParser(t,h);
        p.Plan();
    }
}