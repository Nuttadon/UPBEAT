package org.example;

import java.io.IOException;

public class UPBEAT {
    private TerritoryClass t;
    private boolean curTurn ;
    private int rows;
    private int cols;
    private boolean p1Win;
    private double p1Dep;
    private double p1RevTimeMin;
    private double p1RevTimeSec;
    private double p1InitTimeMin;
    private double p1InitTimeSec;
    private boolean p2Win;
    private double p2RevTimeMin;
    private double p2RevTimeSec;
    private double p2InitTimeMin;
    private double p2InitTimeSec;

    public UPBEAT() throws LexicalError, SyntaxError, EvalError, IOException {
        t = new TerritoryClass();
        curTurn = t.getTurn();
        rows = t.getRows();
        cols = t.getCols();
        p1Win = t.getPlayers()[0].getWin();
        p1RevTimeMin = t.getPlayers()[0].getTime("RevMin");
        p1RevTimeSec = t.getPlayers()[0].getTime("RevSec");;
        p1InitTimeMin = t.getPlayers()[0].getTime("initMin");;
        p1InitTimeSec = t.getPlayers()[0].getTime("initSec");;
        p2Win = t.getPlayers()[1].getWin();
        p2RevTimeMin = t.getPlayers()[1].getTime("RevMin");
        p2RevTimeSec = t.getPlayers()[1].getTime("RevSec");;
        p2InitTimeMin = t.getPlayers()[1].getTime("initMin");;
        p2InitTimeSec = t.getPlayers()[1].getTime("initSec");;
    }

    public UPBEAT planNStart() throws LexicalError, SyntaxError, EvalError, IOException {
        if(curTurn){
            t.getPlayers()[0].initPlan();
            t.getPlayers()[0].startPlan();
        }else{
            t.getPlayers()[1].initPlan();
            t.getPlayers()[1].startPlan();
        }
        return this;
    }
}
