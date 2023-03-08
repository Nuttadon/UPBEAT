package org.example;

import java.io.IOException;

public class UPBEAT {
    public UPBEAT() throws LexicalError, SyntaxError, EvalError, IOException {
        TerritoryClass t = new TerritoryClass();
        Player[] p = t.getPlayers();
//        p[0].initPlan();
//        p[1].initPlan();
        System.out.println(t.getTurn());
        while(t.getTurn()) p[0].startPlan();
        p[1].startPlan();



    }
}
