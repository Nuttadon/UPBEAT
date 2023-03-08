package org.example;

public class RegionClass implements Region{
    private Player owner;
    private Player cityCenterOf ;
    private double curDeposit;
    private double maxDeposit;
    private int xpos;
    private int ypos;

    public RegionClass(double maxDep,int x,int y){
        owner = null;
        cityCenterOf = null;
        curDeposit = 0;
        this.maxDeposit = maxDep;
        xpos=x;
        ypos=y;

    }

    @Override
    public void conquer(Player p) {
        owner = p;
    }

    @Override
    public void freeOwner() {
        if(owner!=null) owner=null;
    }

    @Override
    public void freeCityCenter() {
        if(cityCenterOf!=null) cityCenterOf=null;
    }

    @Override
    public void setAsCityCenter() {
        if(owner!=null) cityCenterOf = owner;
    }

    @Override
    public void deposit(int i) {
        curDeposit += i;
    }

    @Override
    public void withdrawn(int i) {
        curDeposit -= i;
    }

    @Override
    public double getDep() {
        double d = curDeposit;
        return d;
    }


    @Override
    public Player getOwner() {
        Player p = owner;
        return p;
    }
    public int getX() {
        int x = xpos;
        return x;
    }
    public int getY() {
        int y = ypos;
        return y;
    }
}
