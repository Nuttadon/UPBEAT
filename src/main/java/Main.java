import AST.Plan;
import AST.RestricWord.Direction;
import AST.Statement.*;
import Exception.EvalError;
import Exception.SyntaxError;
import GameState.Player;
import GameState.Region;
import GameState.Territory;
import Parser.StatementParser;
import Tokenizer.Tokenizer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws EvalError, SyntaxError, IOException {
        Territory t = new Territory();
        t.players[0].startPlan();
//        System.out.println(t.players[0].variables.get("m"));


    }
}