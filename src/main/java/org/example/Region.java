package org.example;

public interface Region {
    void conquer(Player p);
    void freeOwner();
    void freeCityCenter();

    void setAsCityCenter();
    void deposit(double i);
    void withdrawn(int i);
    double getDep();
    Player getOwner();
    int getColPos();
    int getRowPos();
    void setDep(int amount);
    Player getCityCenterOwner();

}
