package com.bob.spring.demo.poker;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class PokerHand {
    private  String handAsString;
    private  List<Card> cards;

    public static PokerHand createPokerHand(String hand) {
        List<Card> cards = Arrays.stream(hand.split(",")).
                map(Card::build).
                collect(Collectors.toList());
        return new PokerHand(hand, cards);
    }

}
