package com.bob.spring.demo.poker;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Poker {
    public static Map<Integer, List<Card>> getMapByRank(PokerHand hand) {
        return hand.getCards().
                stream().
                collect(Collectors.groupingBy(Card::getRank));
    }
}


