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

public class Main {

    public static void main(String[] args) throws EvalError, SyntaxError {
        Player g = new Player(1000);
        StringBuilder s = new StringBuilder();
        Tokenizer t = new Tokenizer("m=25 while(m){m=m-5} m=budget");
        StatementParser p = new StatementParser(t);
        Plan cp = (Plan) p.parse();
        cp.prettyPrint(s);
//        System.out.println(s.toString());
        cp.eval(g);
        System.out.println(g.variables.get("m"));

    }
}