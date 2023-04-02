package com.example.demo;

import com.example.demo.Exception.EvalError;
import com.example.demo.Exception.SyntaxError;
import com.example.demo.GameState.Territory;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UPBEAT {
    @Getter
    private Territory board;

    @Getter
    private String[][] gameGrid;

    @Getter
    private String[] P1inttime;
    @Getter
    private String[] P1revtime;

    @Getter
    private String[] P2inttime;
    @Getter
    private String[] P2revtime;

    @Getter
    private int P1budget;

    @Getter
    private int P2budget;
    @Getter
    private int countTurn;
    @Getter
    private String whoTurn;
    @Getter
    private int whoWin;
    @Getter
    private final int rows;
    @Getter
    private final int cols;


    public UPBEAT() {
        board = new Territory();
        rows = board.getRows();
        cols = board.getCols();
        gameGrid = new String[rows][cols];
        for(int y=0;y<rows;y++){
            for(int x=0;x<cols;x++ ){gameGrid[y][x] = " ";}
        }

        P1inttime = board.players[0].getIntTime();
        P1revtime = board.players[0].getRevTime();
        P1budget = (int)board.players[0].getBudget();

        P2inttime = board.players[1].getIntTime();
        P2revtime = board.players[1].getRevTime();
        P2budget = (int)board.players[1].getBudget();

        whoTurn = board.getWhoTurn();
        countTurn = board.getTurn();
        whoWin = board.winCheck();
    }

    public UPBEAT replan(PlanMessage planMessage) {//get msg. from txt. input box
        int py = planMessage.getPlayer();
        String pl = planMessage.getPlan();
        board.players[py].rePlan(pl);
        return this;
    }

    public UPBEAT startrevtime(PlayerNameMessage playerNameMessage){//active when click replan btn.
        int py = playerNameMessage.getPlayer();
        board.players[py].revCDstart();
        return this;
    }

    public UPBEAT startinttime(PlayerNameMessage playerNameMessage) {//active when start of the first turn
        int py = playerNameMessage.getPlayer();
        board.players[py].intCDstart();
        return this;
    }
    public UPBEAT wincheck() {
        whoWin = board.winCheck();
        return this;
    }
    public UPBEAT nextturn() {
        board.nextTurn();
        whoTurn = board.getWhoTurn();
        countTurn = board.getTurn();
        return this;
    }

    public UPBEAT action(PlayerNameMessage playerNameMessage) throws SyntaxError, EvalError, IOException {
        int py = playerNameMessage.getPlayer();
        board.players[py].startPlan();
        return this;
    }
}
