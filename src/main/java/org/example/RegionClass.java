package org.example;

public class RegionClass implements Region{
    //private Player owner;
    //private Player CityCenter = null;
    private Boolean isP1CityCenter;
    private Double curDeposit = 0.00;
    private Boolean hasOwner = false;

    @Override
    public void freeOwner() {
        if(hasOwner.equals(true)){
            hasOwner = false;
        }
    }

    @Override
    public void freeCityCenter() {

    }

    @Override
    public void setCityCenter() {

    }

    @Override
    public void deposit(int i) {

    }

    @Override
    public void withdrawn(int i) {

    }

    @Override
    public void depCheck() {

    }

    @Override
    public void whoOwn() {

    }
}
