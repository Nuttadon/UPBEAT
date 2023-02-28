package org.example;

public interface Region {
    public void freeOwner();
    public void freeCityCenter();
    // public void conquer(Player p);
    public void setCityCenter();
    public void deposit(int i);
    public void withdrawn(int i);
    public void depCheck();
    public void whoOwn();
}
