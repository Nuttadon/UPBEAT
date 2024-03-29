package com.example.demo.GameState;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Territory {
    private int curPlayerTurn;
    private int cols;
    private int rows;
    private int curTurn;
    private double maxDeposit;
    private double initPercent;
    public Player[] players ;
    private Region[][] regions;


    public Territory(){
        players = new Player[2];
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
                }if(line.contains("max_dep")) {
                    line = line.replace("max_dep=","");
                    maxDeposit = Double.parseDouble(line);
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        regions = new Region[rows][cols];
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                regions[i][j] = new Region(maxDeposit,j+1,i+1);
            }
        }
        players[0] = new Player("Player1",this);
        players[1] = new Player("Player2",this);

        curTurn = 1;
        if(Math.random()>0.5) curPlayerTurn = 0;
        else curPlayerTurn = 1;
    }

    public int getRows(){
        int r = rows;
        return r;
    }
    public int getCols(){
        int c = cols;
        return  c;
    }
    public Region[][] getRegions(){
        return regions;
    }

    public double getMaxDep() {
        double m = maxDeposit;
        return m;
    }
    public int winCheck() {
        if(players[0].curCityCenter==null) return 1;//player 1 loses
        else if(players[1].curCityCenter==null)return 2;//player loses
        else return 0;//no one loses
    }
    public void nextTurn() {
        curPlayerTurn = (curPlayerTurn+1)%2;
        curTurn++;
        calculatePct();
    }
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

    public String getWhoTurn(){
        String s = players[curPlayerTurn].getName();
        return s;
    }

    public int getTurn(){
        return curTurn;
    }
}
