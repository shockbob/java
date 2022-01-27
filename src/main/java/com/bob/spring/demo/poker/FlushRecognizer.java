package com.bob.spring.demo.poker;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class FlushRecognizer implements HandRecognizer {
    public boolean matches(PokerHand hand) {
        Map<String, List<Card>> ranks = getMapBySuit(hand);
        return ranks.size() == 1; // only one suit for all cards
    }

    private Map<String, List<Card>> getMapBySuit(PokerHand hand) {
        return hand.getCards().
                stream().
                collect(Collectors.groupingBy(card -> card.suit));
    }

}
