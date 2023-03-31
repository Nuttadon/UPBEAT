package AST.Statement;

import AST.RestricWord.Direction;
import AST.Expression.Expression;
import Exception.EvalError;
import GameState.Player;

public class ShootCommand implements Statement {
    private Direction direction;
    private Expression expression;

    public ShootCommand(Direction direction, Expression expression){
        this.direction = direction;
        this.expression = expression;
    }

    public void prettyPrint(StringBuilder s) {
        s.append("shoot ");
        s.append(this.direction);
        s.append(" ");
        this.expression.prettyPrint(s);
    }

    public boolean eval(Player player) throws EvalError {
        player.shoot(expression.eval(player),direction);
        return true;
    }
}
