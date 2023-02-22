package org.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;

import static java.lang.Character.*;

public class ExprTokenizer implements Tokenizer{
    private String src, next;
    private int pos;
    public ExprTokenizer(String src) throws LexicalError, SyntaxError {
        this.src = src;
        pos = 0;
        computeNext();
    }
    @Override
    public boolean hasNextToken()
    { return next != null; }
    @Override
    public String peek() {
        if (!hasNextToken()) throw new NoSuchElementException("no more tokens");
        return next;
    }
    @Override
    public String consume() throws LexicalError, SyntaxError {
        if (!hasNextToken()) throw new NoSuchElementException("no more tokens");
        String result = next;
        computeNext();
        System.out.println("Consumed : "+result);
        return result;
    }

    public boolean peek(String s) {
        if (!hasNextToken()) return false;
        return peek().equals(s);
    }


    public void consume(String s) throws SyntaxError, LexicalError {
        if (peek(s)) consume();
        else  throw new SyntaxError(s + " expected");
    }


    private void computeNext() throws LexicalError, SyntaxError {
        StringBuilder s = new StringBuilder();
        while (pos < src.length() && isWhitespace(src.charAt(pos))){
            pos++;  // ignore whitespace
        }
        if (pos == src.length()) {
            next = null;
            return;
        }  // no more tokens
        char c = src.charAt(pos);
        if (isLetter(c)) {
            s.append(c);
            for (pos++; pos < src.length() && isLetter(src.charAt(pos)); pos++){
                s.append(src.charAt(pos));
            }
        }
        else if (c == '{' || c == '}'||c == '+'||c == '-' ||c == '*'||c == '/' ||c == '%' ||c == '(' ||c == ')'||c == '^'||c == '=') {
            s.append(c);
            pos++;
        }
        else if (isDigit(c)) {
            s.append(c);
            for (pos++; pos < src.length() && isDigit(src.charAt(pos)); pos++){
                s.append(src.charAt(pos));
            }
        }
        else {
            throw new LexicalError("unknown character: " + c);
        }
        next = s.toString();
    }
}


