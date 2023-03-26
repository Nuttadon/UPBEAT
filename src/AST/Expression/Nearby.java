package AST.Expression;

import AST.RestricWord.Direction;
import Exception.EvalError;
import GameState.Player;
import GameState.Region;

public class Nearby implements Expression{
    private Direction direction;

    public Nearby(Direction direction){
        this.direction = direction;
    }

    public double eval(Player player) throws EvalError {
//        Region current = player.cityCrew;
//        double distance = 0;
//        while (current.gotoDirection(this.direction) != null) {
//            distance++;
//            current = current.gotoDirection(this.direction);
//            if(current.owner != null && current.owner != player)
//                return 100*distance + Math.floor(Math.log10(current.getDeposit()));
//        }
        return 0;
    }

    public void prettyPrint(StringBuilder s) {
        s.append("nearby ");
        s.append(this.direction);
    }
}
