package AST.Expression;

import Exception.EvalError;
import GameState.Player;

public class Identifier implements Expression {
    private String name;

    public Identifier(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public double eval(Player player) throws EvalError {
        if(name.equals("rows")) return player.territory().TERRITORY_ROW();
        else if(name.equals("cols")) return player.territory().TERRITORY_COL();
        else if(name.equals("currow")) return player.cityCrew.getRow();
        else if(name.equals("curcol")) return player.cityCrew.getCol();
        else if(name.equals("budget")) return Math.floor(player.getBudget());
        else if(name.equals("deposit")){
            double deposit = Math.floor(player.cityCrew.getDeposit());
            if(player.cityCrew.owner == player) return deposit;
            else return 0-deposit;
        }else if(name.equals("int")) return Math.round(player.cityCrew.getInterestRate());
        else if(name.equals("maxdeposit")) return player.territory().MAX_DEPOSIT();
        else if(name.equals("random")) return Math.floor(Math.random()*1000);

        if(!player.variables.containsKey(name)) return 0;
        return player.variables.get(name);
    }

    public void prettyPrint(StringBuilder s) {
        s.append(name);
    }
}
