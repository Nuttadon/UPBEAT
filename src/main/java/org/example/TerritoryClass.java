package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TerritoryClass implements Territory{
    private boolean curPlayerTurn;
    private int cols;
    private int rows;
    private int curTurn;
    private double curPercent;
    private int maxDeposit;
    private double initPercent;
    private PlayerClass[] players = new PlayerClass[2];
    private RegionClass[][] regions;

    public TerritoryClass(){
        Path file = Paths.get("configFile.txt");
        Charset charset = Charset.forName("UTF-8");
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if(line.contains("col")){
                    line = line.replace("col=","");
                    cols = Integer.parseInt(line);
                }if(line.contains("row")) {
                    line = line.replace("row=","");
                    rows = Integer.parseInt(line);
                }if(line.contains("interest_pct")) {
                    line = line.replace("interest_pct=","");
                    initPercent = Integer.parseInt(line);
                    curPercent = Integer.parseInt(line);
                }if(line.contains("max_dep")) {
                    line = line.replace("max_dep=","");
                    maxDeposit = Integer.parseInt(line);
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        curPlayerTurn = Math.random()<0.5;
        curTurn = 0;
        regions = new RegionClass[rows][cols];
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                regions[i][j] = new RegionClass(maxDeposit,j+1,i+1);
//                System.out.print((j+1)+" ");
            }
//            System.out.println();
        }
        players[0] = new PlayerClass("Player1",this);
        players[1] = new PlayerClass("Player2",this);
    }

    public boolean getTurn(){
        return curPlayerTurn;
    }
    @Override
    public int getRows(){
        int r = rows;
        return r;
    }
    @Override
    public int getCols(){
        int c = cols;
        return  c;
    }
    @Override
    public RegionClass[][] getRegions(){
        return regions;
    }
    @Override
    public PlayerClass[] getPlayers(){
        return players;
    }

    @Override
    public int getMaxDep() {
        int m = maxDeposit;
        return m;
    }

    @Override
    public double getIntPercentage() {
        double p = initPercent;
        return p;
    }

    @Override
    public void winCheck() {
        if(players[0].getWin()) {
            System.out.println("Game end player 1 win");
        }
        if(players[1].getWin()) {
            System.out.println("Game end player 2 win");
        }
    }

    @Override
    public void nextTurn() {
        curPlayerTurn = !curPlayerTurn;
        curTurn++;
        calculatePct();
    }

    @Override
    public void calculatePct() {
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                double b = initPercent;
                double d = regions[i][j].getDep();
                double t = curTurn;
                double r = b * Math.log10(d)*Math.log(t);
                double intOfRegion = (d*r)/100;
                regions[i][j].deposit(intOfRegion);
            }
        }
    }

}
