package org.example;

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
}
