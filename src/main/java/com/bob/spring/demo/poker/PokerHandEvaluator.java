package com.bob.spring.demo.poker;

import java.util.Arrays;

public class PokerHandEvaluator {
    static RankFrequencyRecognizer pairRecognizer = new RankFrequencyRecognizer(1, 1, 1, 2);
    static RankFrequencyRecognizer twoPairRecognizer = new RankFrequencyRecognizer(1, 2, 2);
    static RankFrequencyRecognizer threeOfAKindRecognizer = new RankFrequencyRecognizer(1, 1, 3);
    static RankFrequencyRecognizer fullHouseRecognizer = new RankFrequencyRecognizer(2, 3);
    static RankFrequencyRecognizer fourOfAKindRecognizer = new RankFrequencyRecognizer(1, 4);

    // Recognizers are given in highest poker hand value order (straight flush beats four of a kind which beats...

    static RecognizerWithValue[] recognizerWithValues = new RecognizerWithValue[]{
            new RecognizerWithValue(new StraightFlushRecognizer(), 10),
            new RecognizerWithValue(fourOfAKindRecognizer, 9),
            new RecognizerWithValue(fullHouseRecognizer, 8),
            new RecognizerWithValue(new FlushRecognizer(), 7),
            new RecognizerWithValue(new StraightRecognizer(), 6),
            new RecognizerWithValue(threeOfAKindRecognizer, 5),
            new RecognizerWithValue(twoPairRecognizer, 4),
            new RecognizerWithValue(pairRecognizer, 3)};

    public static Boolean isGreaterThan(PokerHand hand1, PokerHand hand2) {
        return getHandValue(hand1) > getHandValue(hand2);
    }

    private static int getHandValue(PokerHand hand) {
        return Arrays.stream(recognizerWithValues).
                filter(v -> v.recognizer.matches(hand)).
                findFirst().
                map(m -> m.handValue).
                orElse(0);
    }

}
