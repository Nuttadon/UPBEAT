package AST.Statement;

import AST.RestricWord.Command;
import AST.Expression.Expression;
import Exception.EvalError;
import GameState.Player;

public class RegionCommand implements Statement {
    private Command action;
    private Expression expression;

    public RegionCommand(Command command, Expression expression){
        if(command.equals(Command.invest) || command.equals(Command.collect)){
            this.action = command;
            this.expression = expression;
        }
    }

    public void prettyPrint(StringBuilder s) {
        s.append(this.action);
        s.append(" ");
        this.expression.prettyPrint(s);
    }

    public boolean eval(Player player) throws EvalError {
        if(action.equals(Command.invest)){ // invest command
            player.invest(expression.eval(player));
        }else{ // collect command
            player.collect(expression.eval(player));
        }
        return true;
    }
}
