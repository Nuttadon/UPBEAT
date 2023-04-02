package com.example.demo;

import com.example.demo.Exception.EvalError;
import com.example.demo.Exception.SyntaxError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class UPBEATController {
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private UPBEAT canvas;

    @MessageMapping("/replan")
    @SendTo("/topic/canvas")
    public UPBEAT paint(PlanMessage planMessage) {
        return canvas.replan(planMessage);
    }

    @MessageMapping("/startrevtime")
    @SendTo("/topic/canvas")
    public UPBEAT startrevtime(PlayerNameMessage playerNameMessage) {
        return canvas.startrevtime(playerNameMessage);
    }

    @MessageMapping("/startinttime")
    @SendTo("/topic/canvas")
    public UPBEAT startinttime(PlayerNameMessage playerNameMessage) {
        return canvas.startinttime(playerNameMessage);
    }

    @MessageMapping("/wincheck")
    @SendTo("/topic/canvas")
    public UPBEAT wincheck() {
        return canvas.wincheck();
    }

    @MessageMapping("/nextturn")
    @SendTo("/topic/canvas")
    public UPBEAT nextturn() {
        return canvas.nextturn();
    }
    @MessageMapping("/action")
    @SendTo("/topic/canvas")
    public UPBEAT action(PlayerNameMessage playerNameMessage) throws SyntaxError, EvalError, IOException {
        return canvas.action(playerNameMessage);
    }

    @SubscribeMapping("/canvas")
    public UPBEAT sendInitialCanvas() {
        return canvas;
    }
}
