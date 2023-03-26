package Parser;

import AST.Node;
import Exception.SyntaxError;

public interface Parser {
    Node parse() throws SyntaxError;
}
