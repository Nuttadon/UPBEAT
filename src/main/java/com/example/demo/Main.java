package com.example.demo;

import com.example.demo.Exception.EvalError;
import com.example.demo.Exception.SyntaxError;
import com.example.demo.GameState.Territory;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws EvalError, SyntaxError, IOException {
        Territory t = new Territory();
        Scanner keyboard = new Scanner(System.in);
        t.players[0].intCDstart();

        System.out.println(" enter your initial plan :");
        String s = keyboard.nextLine();
        System.out.println(s);

        t.players[0].initPlan("move up move up x1 = currow y1 = curcol");
        t.players[0].startPlan();
        System.out.println(t.players[0].variables.get("x1"));
        System.out.println(t.players[0].variables.get("y1"));
        System.out.println("P1 "+t.players[0].getIntTime()[0]+":"+t.players[0].getIntTime()[1]);
    }
}