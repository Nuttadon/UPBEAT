package org.example;

public interface Territory {
    public void winCheck();
    public void nextTurn();
    public double calculatePct();
    public int getRows();
    public int getCols();
    public RegionClass[][] getRegions();
    public PlayerClass[] getPlayers();
    public int getMaxDep();
    public double getIntPercentage();
}
