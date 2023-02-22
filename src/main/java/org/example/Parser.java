package org.example;

import java.io.IOException;

public interface Parser {
    /** Attempts to parse the token stream
     *  given to this parser.
     *  throws: SyntaxError if the token
     *          stream cannot be parsed */
    void Plan() throws SyntaxError, LexicalError, EvalError, IOException;

}
