package org.example;

public interface Region {
    public void conquer(Player p);
    public void freeOwner();
    public void freeCityCenter();
    // public void conquer(Player p);
    public void setAsCityCenter();
    public void deposit(int i);
    public void withdrawn(int i);
    public double getDep();
    public Player getOwner();
}
