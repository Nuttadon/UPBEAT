package AST.Expression;

import AST.Node;
import Exception.EvalError;
import GameState.Player;

public interface Expression extends Node {
    double eval(Player player) throws EvalError;
}
