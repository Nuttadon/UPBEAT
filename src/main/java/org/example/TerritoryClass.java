package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TerritoryClass implements Territory{
    private int curPlayerTurn;//0 = p1 1 = p2
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
        curPlayerTurn = (int) ((Math.random()*10)%2);
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

    public int getTurn(){
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

    }

    @Override
    public double calculatePct() {
        return 0;
    }

}
