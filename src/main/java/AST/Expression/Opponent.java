package AST.Expression;

import AST.RestricWord.Direction;
import Exception.EvalError;
import GameState.Player;
import GameState.Region;

public class Opponent implements Expression{
    public Opponent() {};

    public double eval(Player player) throws EvalError {
        return player.opponent();
    }

    public void prettyPrint(StringBuilder s) {
        s.append("opponent");
    }
}
