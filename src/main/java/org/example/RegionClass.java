package org.example;

public class RegionClass implements Region{
    private Player owner;
    private Player cityCenterOf ;
    private double curDeposit;
    private double maxDeposit;

    public RegionClass(double maxDep){
        owner = null;
        cityCenterOf = null;
        curDeposit = 0;
        this.maxDeposit = maxDep;
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
}
