package org.example;

import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws LexicalError, SyntaxError, EvalError, IOException {
        HashMap<String, Double> h = new HashMap<>();
//        ExprTokenizer t = new ExprTokenizer("m = 5^2 if(10-12)then if(m)then n = nearby up else {} else {n=65}");
//        ExprTokenizer t = new ExprTokenizer("m = (5^2) n=2-5 while(((m))) if(n) then m=m+3 else if(n) then shoot up 20 else {move up m=m-5}");
//        ExprTokenizer t = new ExprTokenizer("m = (5^2) n=10 while(((m))){m=m-5 while(n){n=n-2}}");
//        ExprTokenizer t = new ExprTokenizer("m = (5^2) n=5-0 if(n) then m=m+3 else if(n) then {shoot up 20} else {move up m=m-5}"); //error = expected
//        ExprTokenizer t = new ExprTokenizer("m = (5^2) n=2-5 if(n) then m=m+3 else if(n) then {shoot up 20} else {move up m=m-5}");
//        ExprTokenizer t = new ExprTokenizer("m = (5^2) n = 2-5 if(n) then m=m+3 else {done} "); // error = expected
//        ExprTokenizer t = new ExprTokenizer("m = 5 n = 2-3 while((m)) {m=m-10 while(n){n = n+1} ");
//        ExprTokenizer t = new ExprTokenizer("m =5 n =2 if(m) then  else{} "); // do nothing
//        ExprTokenizer t = new ExprTokenizer("m= 5 n =2 if(m) then collect 0 else {}");
//        ExprTokenizer t = new ExprTokenizer("m =5^2 if (3-2) then if(m) then n = nearby downleft else{} else {n=32}");
//        ExprTokenizer t = new ExprTokenizer("m =5^2 if (3) then if(m) then n = nearby downleft else{} else {n=32}");
//        ExprTokenizer t = new ExprTokenizer("m =5^2 if(3) then  relocate downleft else{}");
//        ExprTokenizer t = new ExprTokenizer("m =5 if(3-4)then {} else{ collect 5 }"); // = expected
//        ExprTokenizer t = new ExprTokenizer("m =5 if(3-4)then {} else{ collect 5 }"); // = expected
//        ExprTokenizer t = new ExprTokenizer("m =5 if(1)then {invest m} else{}"); // = expected
//        ExprTokenizer t = new ExprTokenizer("m =5 if(1)then {opponent} else{ collect 5 }"); // still not yet implement opponent
        ExprTokenizer t = new ExprTokenizer("m = 1500 while(m){m = m-300}");
        ExprParser p = new ExprParser(t,h);
        p.Plan();
    }
}