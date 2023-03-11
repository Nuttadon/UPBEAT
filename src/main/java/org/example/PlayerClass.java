package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

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
        this.t = t;
        RegionClass[][] r = t.getRegions();
        do{
            curCityCenterPos[0] = (int) (((Math.random()*100)%t.getRows())+1);
//            if(name.equals("Player1"))curCityCenterPos[0] = (int) (3+1);
//            else curCityCenterPos[0] = (int) (4+1);
            System.out.print("row = "+curCityCenterPos[0]+" ");
            curCityCenterPos[1] = (int) (((Math.random()*100)%t.getCols())+1);
//            if(name.equals("Player1"))curCityCenterPos[1] = (int) (1+1);
//            else curCityCenterPos[1] = (int) (4+1);
            System.out.println("col = "+curCityCenterPos[1]);
            curCityCenter = r[curCityCenterPos[0]-1][curCityCenterPos[1]-1];
        }while(curCityCenter.getOwner()!=null);
        r[curCityCenterPos[0]-1][curCityCenterPos[1]-1].conquer(this);
        r[curCityCenterPos[0]-1][curCityCenterPos[1]-1].setAsCityCenter();
        r[curCityCenterPos[0]-1][curCityCenterPos[1]-1].deposit(initCenterDep);
    }
    @Override
    public String getName(){
        String n = name;
        return n;
    }
    @Override
    public boolean getWin(){
        boolean w = win;
        return w;
    }
    @Override
    public void initPlan(){
        Scanner keyboard = new Scanner(System.in);
        System.out.println(name+" enter your initial plan :");
        String s = keyboard.nextLine();
        System.out.println(s);
        String player ;
        if(name.equals("Player1")) player = "player1Plan.txt";
        else player = "player2Plan.txt";
        Path file2 = Paths.get(player);  // path string
        Charset charset = Charset.forName("UTF-8");
        try (BufferedWriter writer = Files.newBufferedWriter(file2, charset)) {
                writer.write(s, 0, s.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
    @Override
    public void startPlan() throws IOException, LexicalError, SyntaxError, EvalError {
        curCityCrewPos[0] = t.getRegions()[curCityCenterPos[0]-1][curCityCenterPos[1]-1].getY();
        curCityCrewPos[1] = t.getRegions()[curCityCenterPos[0]-1][curCityCenterPos[1]-1].getX();
        StringBuilder sb = new StringBuilder();
        String player ;
        if(name.equals("Player1")) player = "player1Plan.txt";
        else player = "player2Plan.txt";
        Path file = Paths.get(player);
        Charset charset = Charset.forName("UTF-8");
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        tok = new ExprTokenizer(sb.toString());
        par = new ExprParser(tok,identifiers,this);
        par.Plan();
    }

    @Override
    public int opponent() {
        Region[][] r = t.getRegions() ;
        int distance = 0;
        int direction = 0;
        StringBuilder sb = new StringBuilder();
        int crewPos0 = curCityCrewPos[0];
        int crewPos1 = curCityCrewPos[1];


        int dis = 0;
        //upleft
        while(true){
            if(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner()!=null&&!(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner().equals(this))){
                if(distance<=dis) {
                    distance = dis;
                    dis=0;
                    direction=6;
                }
                break;
            }
            if(curCityCrewPos[0]==1||curCityCrewPos[1]==1) break;
            move(6);
            dis++;
        }
        curCityCrewPos[0] = crewPos0;
        curCityCrewPos[1] = crewPos1;
        dis =0;
        //downleft
        while(true){
            if(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner()!=null&&!(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner().equals(this))){
                if(distance<=dis) {
                    distance = dis;
                    dis=0;
                    direction=5;
                }
                break;
            }
            if(curCityCrewPos[0]==t.getRows()||curCityCrewPos[1]==1) break;
            move(5);
            dis++;
        }
        curCityCrewPos[0] = crewPos0;
        curCityCrewPos[1] = crewPos1;
        dis =0;
        //down
        while(true){
            if(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner()!=null&&!(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner().equals(this))){
                if(distance<=dis) {
                    distance = dis;
                    dis=0;
                    direction=4;
                }
                break;
            }
            if(curCityCrewPos[0]==t.getRows()) break;
            move(4);
            dis++;
        }
        curCityCrewPos[0] = crewPos0;
        curCityCrewPos[1] = crewPos1;
        dis =0;
        //downright
        while(true){
            if(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner()!=null&&!(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner().equals(this))){
                if(distance<=dis) {
                    distance = dis;
                    dis=0;
                    direction=3;
                }
                break;
            }
            if(curCityCrewPos[0]==t.getRows()||curCityCrewPos[1]==t.getCols()) break;
            move(3);
            dis++;
        }
        curCityCrewPos[0] = crewPos0;
        curCityCrewPos[1] = crewPos1;
        dis =0;
        //upright
        while(true){
            if(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner()!=null&&!(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner().equals(this))){
                if(distance<=dis) {
                    distance = dis;
                    dis=0;
                    direction=2;
                }
                break;
            }
            if(curCityCrewPos[0]==1||curCityCrewPos[1]==t.getCols()) break;
            move(2);
            dis++;
        }
        curCityCrewPos[0] = crewPos0;
        curCityCrewPos[1] = crewPos1;
        dis =0;
        //up
        while(true){
            if(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner()!=null&&!(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner().equals(this))){
                distance = dis;
                dis =0;
                direction=1;
                break;
            }
            if(curCityCrewPos[0]==1) break;
            move(1);
            dis++;
        }
        curCityCrewPos[0] = crewPos0;
        curCityCrewPos[1] = crewPos1;
        String sDistance = Integer.toString(distance) ;
        String sDirection = Integer.toString(direction) ;
        sb.append(sDistance);
        sb.append(sDirection);
        return Integer.parseInt(sb.toString());
    }

    @Override
    public int nearby(int direction) {
        Region[][] r = t.getRegions() ;
        int distance = 0;
        Region regionFound = null;
        StringBuilder sb = new StringBuilder();
        int crewPos0 = curCityCrewPos[0];
        int crewPos1 = curCityCrewPos[1];

        //up
        int dis = 0;
        if(direction==1){
            while(true){
                if(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner()!=null&&!(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner().equals(this))){
                    distance = dis;
                    dis =0;
                    regionFound = r[curCityCrewPos[0]-1][curCityCrewPos[1]-1];
                    break;
                }
                if(curCityCrewPos[0]==1) break;
                move(1);
                dis++;
            }
        }
        curCityCrewPos[0] = crewPos0;
        curCityCrewPos[1] = crewPos1;
        dis =0;
        if(direction==2){
            while(true){
                if(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner()!=null&&!(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner().equals(this))){
                    distance = dis;
                    dis=0;
                    regionFound = r[curCityCrewPos[0]-1][curCityCrewPos[1]-1];
                    break;
                }
                if(curCityCrewPos[0]==1||curCityCrewPos[1]==t.getCols()) break;
                move(2);
                dis++;
            }
        }
        curCityCrewPos[0] = crewPos0;
        curCityCrewPos[1] = crewPos1;
        dis =0;
        if(direction==3){
            while(true){
                if(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner()!=null&&!(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner().equals(this))){
                    distance = dis;
                    dis=0;
                    regionFound = r[curCityCrewPos[0]-1][curCityCrewPos[1]-1];
                    break;
                }
                if(curCityCrewPos[0]==t.getRows()||curCityCrewPos[1]==t.getCols()) break;
                move(3);
                dis++;
            }
        }
        curCityCrewPos[0] = crewPos0;
        curCityCrewPos[1] = crewPos1;
        dis =0;
        if(direction==4){
            while(true){
                if(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner()!=null&&!(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner().equals(this))){
                    distance = dis;
                    dis=0;
                    regionFound = r[curCityCrewPos[0]-1][curCityCrewPos[1]-1];
                    break;
                }
                if(curCityCrewPos[0]==t.getRows()) break;
                move(4);
                dis++;
            }
        }
        curCityCrewPos[0] = crewPos0;
        curCityCrewPos[1] = crewPos1;
        dis =0;
        if(direction==5){
            while(true){
                if(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner()!=null&&!(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner().equals(this))){
                    distance = dis;
                    dis=0;
                    regionFound = r[curCityCrewPos[0]-1][curCityCrewPos[1]-1];
                    break;
                }
                if(curCityCrewPos[0]==t.getRows()||curCityCrewPos[1]==1) break;
                move(5);
                dis++;
            }
        }
        curCityCrewPos[0] = crewPos0;
        curCityCrewPos[1] = crewPos1;
        dis =0;
        if(direction==6){
            while(true){
                if(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner()!=null&&!(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner().equals(this))){
                    distance = dis;
                    dis=0;
                    regionFound = r[curCityCrewPos[0]-1][curCityCrewPos[1]-1];
                    break;
                }
                if(curCityCrewPos[0]==1||curCityCrewPos[1]==1) break;
                move(6);
                dis++;
            }
        }
        curCityCrewPos[0] = crewPos0;
        curCityCrewPos[1] = crewPos1;
        int output;
        if(regionFound==null) output = 0 ;
        else{
            int sDistance = distance*100;
            int y = Integer.toString((int)regionFound.getDep()).length();
            output = sDistance+y;
        }

        return output;
    }

    @Override
    public void done() {
        t.nextTurn();
    }

    @Override
    public void relocate() {
        Region[][] r = t.getRegions() ;
        if(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner().getName().equals(name)) {
            r[curCityCenterPos[0]-1][curCityCenterPos[1]-1].freeCityCenter();
            r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].setAsCityCenter();
            curCityCenter = r[curCityCrewPos[0]-1][curCityCrewPos[1]-1];
        }
    }

    @Override
    public void move(int direction) {
        if(direction==1){
            if(curCityCrewPos[0]-1>0){
                if(budget-1>=0){
                    curCityCrewPos[0]-=1;
                    budget-=1;
                }
            }
        }
        else if(direction==2){
            if(curCityCrewPos[1]%2==0){
                if(curCityCrewPos[0]-1>0&&curCityCrewPos[1]+1<=t.getCols()){
                    if(budget-1>=0){
                        curCityCrewPos[0]-=1;
                        curCityCrewPos[1]+=1;
                        budget-=1;
                    }
                }
            }else{
                if(curCityCrewPos[1]+1<=t.getCols()){
                    if(budget-1>=0){
                        curCityCrewPos[1]+=1;
                        budget-=1;
                    }
                }
            }
        }
        else if(direction==3){
            if(curCityCrewPos[1]%2==0){
                if(curCityCrewPos[1]+1<=t.getCols()){
                    if(budget-1>=0){
                        curCityCrewPos[1]+=1;
                        budget-=1;
                    }
                }
            }else{
                if(curCityCrewPos[0]+1<=t.getRows()&&curCityCrewPos[1]+1<=t.getCols()){
                    if(budget-1>=0){
                        curCityCrewPos[0]+=1;
                        curCityCrewPos[1]+=1;
                        budget-=1;
                    }
                }
            }
        }
        else if(direction==4){
            if(curCityCenterPos[0]+1<t.getRows()){
                if(budget-1>=0){
                    curCityCrewPos[0]+=1;
                    budget-=1;
                }
            }
        }
        else if(direction==5){
            if(curCityCrewPos[1]%2==0){
                if(curCityCrewPos[1]-1>0){
                    if(budget-1>=0){
                        curCityCrewPos[1]-=1;
                        budget-=1;
                    }
                }
            }else{
                if(curCityCrewPos[0]+1<=t.getRows()&&curCityCrewPos[1]-1>0){
                    if(budget-1>=0){
                        curCityCrewPos[0]+=1;
                        curCityCrewPos[1]-=1;
                        budget-=1;
                    }

                }
            }
        }
        else {
            if(curCityCrewPos[1]%2==0){
                if(curCityCrewPos[1]-1>0&&curCityCrewPos[0]-1>0){
                    if(budget-1>=0){
                        curCityCrewPos[0]-=1;
                        curCityCrewPos[1]-=1;
                        budget-=1;
                    }
                }
            }else{
                if(curCityCrewPos[1]-1>0){
                    if(budget-1>=0){
                        curCityCrewPos[1]-=1;
                        budget-=1;
                    }

                }
            }
        }
    }

    @Override
    public void invest(int amount) {
        Region[][] r = t.getRegions() ;
        if(budget-amount-1>=0){
            if(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner()==null) {
                r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].deposit(amount);
                r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].conquer(this);
                budget-=(amount+1);
            }
        }

    }

    @Override
    public void collect(int amount) {
        Region[][] r = t.getRegions() ;
        if(budget-amount-1>=0){
            if(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getOwner().equals(this)
                    &&r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getDep()>=amount) {
                r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].withdrawn(amount);
                if(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].getDep()==0){
                    r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].freeOwner();
                    if(r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].equals(curCityCenter)){
                        r[curCityCrewPos[0]-1][curCityCrewPos[1]-1].freeCityCenter();
                        curCityCenter = null;
                    }
                }
                budget-=1;
                budget+=amount;
            }
        }
    }

    @Override
    public void shoot(int damage, int direction) {

    }
    @Override
    public void plan() {

    }

}
