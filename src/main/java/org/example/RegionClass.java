package org.example;

public class RegionClass implements Region{
    private Player owner;
    private Player cityCenter ;
    private double curDeposit;

    public RegionClass(){
        owner = null;
        cityCenter = null;
        curDeposit = 0;
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
        if(cityCenter!=null) cityCenter=null;
    }

    @Override
    public void setAsCityCenter() {
        if(owner!=null) cityCenter = owner;
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
        return curDeposit;
    }


    @Override
    public Player getOwner() {
        return owner;
    }
}
