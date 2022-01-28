package com.bob.spring.demo.poker.recognizer;

import com.bob.spring.demo.poker.Card;
import com.bob.spring.demo.poker.Poker;
import com.bob.spring.demo.poker.PokerHand;
import com.bob.spring.demo.poker.recognizer.HandRecognizer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StraightRecognizer implements HandRecognizer {

    public boolean matches(PokerHand hand) {
        Map<Integer, List<Card>> rankMap = Poker.getMapByRank(hand);
        if (rankMap.size() == 5) { // five unique cards
            List<Integer> ranks = rankMap.keySet().
                    stream().
                    sorted().
                    collect(Collectors.toList());
            // when sorted, the last card is exactly 4 ranks higher than the first
            return ranks.get(0) + 4 == ranks.get(4);
        }
        return false;
    }
}
