package com.bob.spring.demo;

import com.bob.spring.demo.poker.Poker;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class Controller {
    @GetMapping(path="/test")
    public String mapping(){
        return "boo";
    }
    @GetMapping(path="/compare")
    public String compare(@RequestParam String hand1, @RequestParam String hand2){
        Poker.PokerHand pokerHand1 = new Poker.PokerHand(hand1);
        Poker.PokerHand pokerHand2 = new Poker.PokerHand(hand2);
        Boolean greaterThan = pokerHand1.isGreaterThan(pokerHand2);
        if (greaterThan){
            return hand1;
        }
        return hand2;
    }
    @GetMapping(path="/validate")
    public void validate(@RequestParam String hand) {
        try {
            Poker.PokerHand pokerHand1 = new Poker.PokerHand(hand);
        } catch (IllegalArgumentException e){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
}
