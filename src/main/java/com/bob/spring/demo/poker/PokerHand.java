package com.bob.spring.demo.poker;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PokerHand {

    private final String handAsString;
    private final List<Card> cards;

    private PokerHand(String hand, List<Card> cards) {
        this.handAsString = hand;
        this.cards = cards;
    }

    public static PokerHand createPokerHand(String hand) {
        List<Card> cards = Arrays.stream(hand.split(",")).
                map(Card::build).
                collect(Collectors.toList());
        return new PokerHand(hand, cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return handAsString;
    }

}
