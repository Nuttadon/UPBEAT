package org.example;

public class RegionClass implements Region{
    private Player owner;
    private Player cityCenterOf ;
    private double curDeposit;
    private double maxDeposit;
    private int colpos;
    private int rowpos;

    public RegionClass(double maxDep,int x,int y){
        owner = null;
        cityCenterOf = null;
        curDeposit = 0;
        this.maxDeposit = maxDep;
        colpos=x;
        rowpos=y;

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
    public void deposit(double i) {
        if(curDeposit+i<= maxDeposit) curDeposit += i;
        else curDeposit = maxDeposit;
    }

    @Override
    public void withdrawn(int i) {

        if(curDeposit-i<=0) curDeposit -= i;
        else curDeposit = 0;
    }

    @Override
    public double getDep() {
        double d ;
        if(owner!=null) d = curDeposit;
        else d = (-1)*curDeposit;
        return d;
    }


    @Override
    public Player getOwner() {
        Player p = owner;
        return p;
    }
    @Override
    public int getColPos() {
        int x = colpos;
        return x;
    }
    @Override
    public int getRowPos() {
        int y = rowpos;
        return y;
    }
    @Override
    public void setDep(int amount){
        curDeposit = amount;
    }
    @Override
    public Player getCityCenterOwner(){
        return cityCenterOf;
    }
}
