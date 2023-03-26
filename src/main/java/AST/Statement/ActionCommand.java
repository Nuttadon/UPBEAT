package AST.Statement;

import AST.RestricWord.Command;
import GameState.Player;

public class ActionCommand implements Statement {
    private Command action;

    public ActionCommand(Command command){
        if(command.equals(Command.done) || command.equals(Command.relocate)) this.action = command;
    }
    public void prettyPrint(StringBuilder s) {
        s.append(this.action);
    }

    public boolean eval(Player player){
//        if(action.equals(Command.relocate)){ // relocate command
//            int ccx = player.cityCenter.getRow(); //CityCenterX
//            int ccy = player.cityCenter.getCol(); //CityCenterY
//            int newCCX = player.cityCrew.getRow();
//            int newCCY = player.cityCrew.getCol();
//            int r = ccx - (ccy - ccy % 2) / 2;
//            int newR = newCCX - (newCCY - newCCY % 2) / 2;
//            int minDistance = (Math.abs(newCCY - ccy) + Math.abs(newR - r) + Math.abs((-newCCY-newR) + (ccy + r))) / 2;
//            double cost = 5 * minDistance + 10;
//            if(player.getBudget() >= cost && player.cityCrew.owner == player){
//                player.subBudget(cost);
//                player.cityCenter = player.cityCrew;
//            }
//        }
        return false;
    }
}
