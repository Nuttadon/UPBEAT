package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PlayerClass implements Player{
    private boolean firstTurn;
    private boolean planFinish;
    private boolean alive;
    private boolean win;
    private double budget;
    private double centerDep;
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

    public PlayerClass(){
        Path file = Paths.get("configFile.txt");
        Charset charset = Charset.forName("UTF-8");
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                    if(line.contains("init_plan_min")){
                        line = line.replace("init_plan_min=","");
                        intPlanMin = Integer.parseInt(line);
                        System.out.println(intPlanMin);
                    }
                    if(line.contains("init_plan_sec")) {
                        line = line.replace("init_plan_sec=","");
                        intPlanSec = Integer.parseInt(line);
                        System.out.println(intPlanSec);
                    }if(line.contains("init_budget")) {
                        line = line.replace("init_budget=","");
                        budget = Integer.parseInt(line);
                    System.out.println(budget);
                    }if(line.contains("init_center_dep")) {
                        line = line.replace("init_center_dep=","");
                        centerDep = Integer.parseInt(line);
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
//        do{
//            curCityCenterPos[0] = Math.random(10);
//            curCityCenterPos[1] = random(0 to rows-1);
//            curCityCenter = t.Region[curCityCenterPos[0]][curCityCenterPos[1]];
//        }while(curCityCenter.owner!=null)
        firstTurn = true;
        alive = true;
        win = false;
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
