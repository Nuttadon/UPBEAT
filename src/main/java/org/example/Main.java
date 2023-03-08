package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws LexicalError, SyntaxError, EvalError, IOException {
        TerritoryClass t = new TerritoryClass();
//        StringBuilder sb = new StringBuilder();
//        HashMap<String, Double> m = new HashMap<>();
//        Path file = Paths.get("player1Plan.txt");
//        Charset charset = Charset.forName("UTF-8");
//        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//        }
//        ExprTokenizer tk = new ExprTokenizer(sb.toString());
//        ExprParser p = new ExprParser(tk,m);
//        p.Plan();
        System.out.println(t.getRegions()[0][1].getOwner());


    }
}