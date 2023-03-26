package AST.Statement;

import AST.RestricWord.Direction;
import Exception.EvalError;
import GameState.Player;
import GameState.Region;

public class MoveCommand implements Statement {
    private Direction direction;

    public MoveCommand(Direction direction){
        this.direction = direction;
    }

    public void prettyPrint(StringBuilder s) {
        s.append("move ");
        s.append(this.direction);
    }

    public boolean eval(Player player) throws EvalError {
//        if(player.getBudget() < player.territory().FEE_CHARGE()) return false;
//        player.subBudget(player.territory().FEE_CHARGE());
//
//        Region next = player.cityCrew.gotoDirection(this.direction);
//        if (next != null && (next.owner == null || next.owner == player))
//            player.cityCrew = next;
        return true;
    }
}
