package org.example;

import java.io.IOException;

public interface Player {
    public int opponent();
    public int nearby(int direction);
    public int done();
    public void relocate();
    public void move(int direction);
    public void invest(int amount);
    public void collect(int amount);
    public void shoot(int damage,int direction);
    public void plan();
    public String getName();
    public boolean getWin();
    public void initPlan();
    public void startPlan() throws IOException, LexicalError, SyntaxError, EvalError;
}
