package com.bob.spring.demo;

import com.bob.spring.demo.poker.PokerHand;
import com.bob.spring.demo.poker.PokerHandEvaluator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping(path = "/compare")
    public String compare(@RequestParam String hand1, @RequestParam String hand2) {
        PokerHand pokerHand1 = PokerHand.createPokerHand(hand1);
        PokerHand pokerHand2 = PokerHand.createPokerHand(hand2);
        Boolean greaterThan = PokerHandEvaluator.isGreaterThan(pokerHand1, pokerHand2);
        if (greaterThan) {
            return hand1;
        }
        return hand2;
    }

    @GetMapping(path = "/validate")
    public ResponseEntity<String> validate(@RequestParam String hand) {
        try {
            PokerHand pokerHand1 = PokerHand.createPokerHand(hand);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok("validation successful");
    }
}
