package AST.Statement;

import AST.Node;
import Exception.EvalError;
import GameState.Player;

public interface Statement extends Node {
    boolean eval(Player player) throws EvalError;
}
