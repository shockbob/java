package com.bob.spring.demo.poker;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class RankFrequencyRecognizer implements HandRecognizer {
    private final List<Integer> pattern;

    RankFrequencyRecognizer(Integer... a) {
        this(Arrays.asList(a));
    }

    RankFrequencyRecognizer(List<Integer> pattern) {
        this.pattern = pattern;
    }

    public boolean matches(PokerHand hand) {
        List<Integer> handPattern = getSortedRankFrequencyPattern(hand);
        return pattern.equals(handPattern);
    }


    public static List<Integer> getSortedRankFrequencyPattern(PokerHand hand) {
        return Poker.getMapByRank(hand).
                values().
                stream().
                map(List::size).
                sorted().
                collect(Collectors.toList());
    }
}
