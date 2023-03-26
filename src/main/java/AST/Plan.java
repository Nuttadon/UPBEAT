package AST;

import AST.Statement.Statement;
import Exception.EvalError;
import GameState.Player;

import java.util.LinkedList;


public class Plan implements Node{
    private LinkedList<Statement> statementList;

    public Plan(LinkedList<Statement> list){
        this.statementList = list;
    }

    public void prettyPrint(StringBuilder s) {
        for (Statement statement : this.statementList){
            statement.prettyPrint(s);
            s.append("\n");
        }
    }

    public void eval(Player player) throws EvalError {
        for (Statement statement : this.statementList){
            if(!statement.eval(player)) return;
        }
    }
}
