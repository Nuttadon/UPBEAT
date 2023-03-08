package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class PlayerClass implements Player{
    private String name;
    private boolean firstTurn;
    private boolean planFinish;
    private boolean win;
    private double budget;
    private int initCenterDep;
    private int intPlanMin;
    private int intPlanSec;
    private int planRevMin;
    private int planRevSec;
    private int revCost;
    private Region curCityCenter;
    private int[] curCityCenterPos = new int[2];
    private int[] curCityCrewPos = new int[2];
    private TerritoryClass t;
    private ExprTokenizer tok;
    private ExprParser par;
    HashMap<String, Double> identifiers = new HashMap<>();

    public PlayerClass(String name,TerritoryClass t){
        Path file = Paths.get("configFile.txt");
        Charset charset = Charset.forName("UTF-8");
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                    if(line.contains("init_plan_min")){
                        line = line.replace("init_plan_min=","");
                        intPlanMin = Integer.parseInt(line);
                    }if(line.contains("init_plan_sec")) {
                        line = line.replace("init_plan_sec=","");
                        intPlanSec = Integer.parseInt(line);
                    }if(line.contains("init_budget")) {
                        line = line.replace("init_budget=","");
                        budget = Integer.parseInt(line);
                    }if(line.contains("init_center_dep")) {
                        line = line.replace("init_center_dep=","");
                        initCenterDep = Integer.parseInt(line);
                    }if(line.contains("plan_rev_min")) {
                        line = line.replace("plan_rev_min=","");
                        planRevMin = Integer.parseInt(line);
                    }if(line.contains("plan_rev_sec")) {
                        line = line.replace("plan_rev_sec=","");
                        planRevSec = Integer.parseInt(line);
                    }if(line.contains("rev_cost")) {
                        line = line.replace("rev_cost=","");
                        revCost = Integer.parseInt(line);
                    }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        firstTurn = true;
        win = false;
        this.name = name;
        RegionClass[][] r = t.getRegions();
        do{
            curCityCenterPos[0] = (int) ((Math.random()*100)%t.getRows());
//            System.out.println("row = "+curCityCenterPos[0]+" ");
            curCityCenterPos[1] = (int) ((Math.random()*100)%t.getCols());
//            System.out.print("col = "+curCityCenterPos[1]);
            curCityCenter = r[curCityCenterPos[0]][curCityCenterPos[1]];
        }while(curCityCenter.getOwner()!=null);
        r[curCityCenterPos[0]][curCityCenterPos[1]].conquer(this);
        r[curCityCenterPos[0]][curCityCenterPos[1]].setAsCityCenter();
        r[curCityCenterPos[0]][curCityCenterPos[1]].deposit(initCenterDep);
    }
    public String getName(){
        String n = name;
        return n;
    }
    public boolean getWin(){
        boolean w = win;
        return w;
    }
    @Override
    public int opponent() {
        return 0;
    }

    @Override
    public int nearby(int direction) {
        return 0;
    }

    @Override
    public int done() {
        return 0;
    }

    @Override
    public void relocate() {

    }

    @Override
    public void move(int direction) {

    }

    @Override
    public void invest(int amount) {

    }

    @Override
    public void collect(int amount) {

    }

    @Override
    public void shoot(int damage, int direction) {

    }

    @Override
    public void plan() {

    }
}
