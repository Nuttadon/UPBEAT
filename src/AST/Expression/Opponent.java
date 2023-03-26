package AST.Expression;

import AST.RestricWord.Direction;
import Exception.EvalError;
import GameState.Player;
import GameState.Region;

public class Opponent implements Expression{
    public Opponent() {};

    public double eval(Player player) throws EvalError {
//        Region current = player.cityCrew;
//        int minDistOpp = Integer.MAX_VALUE;
//        Direction[] directions = Direction.values();
//        for (int Dir = 0 ; Dir < 6 ; Dir++){
//            for (int distance = 1 ; distance < minDistOpp/10 && current.gotoDirection(directions[Dir]) != null ; distance++){
//                current = current.gotoDirection(directions[Dir]);
//                if(current.owner != null && current.owner != player){
//                    int distNum = 10*distance + Dir+1;
//                    if(distNum < minDistOpp) minDistOpp = distNum;
//                }
//            }
//            current = player.cityCrew;
//        }
        return 0;
    }

    public void prettyPrint(StringBuilder s) {
        s.append("opponent");
    }
}
