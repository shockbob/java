package com.bob.spring.demo.poker;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Poker {

    /*
     * Given a set of 5 playing card identifiers such as 2H, 7C, QS, 10D, 2D;
     * determine if this hand is better than some other hand, according to the rules of poker.
     *
     * Hands will be a string with 5 cards comma separated,
     * each card will have 1-2 digits or JQKA and a suit indicator C,D,S,H (i.e. 10C, KH)
     *
     * Possible Hand Types Below:
     *   Straight flush
     *   Four of a kind x
     *   Full house x
     *   Flush
     *   Straight
     *   Three of a kind x
     *   Two pair x
     *   One pair x
     *
     * The goal of this is to compare between the hand types.
     * Comparing 2 of the same type (i.e. 2 straights) to determine a winner is outside the scope
     * and will not be tested.
     *
     * Implement PokerHand.isGreaterThan(...) method and return whether or not the first hand wins over the second hand.
     */

    interface HandRecognizer {
        boolean matches(PokerHand hand);
    }

    static class RankFrequencyRecognizer implements HandRecognizer {
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
    }

    static class FlushRecognizer implements HandRecognizer {
        public boolean matches(PokerHand hand) {
            Map<String, List<Card>> ranks = getMapBySuit(hand);
            return ranks.size() == 1; // only one suit for all cards
        }
    }

    static class StraightRecognizer implements HandRecognizer {

        public boolean matches(PokerHand hand) {
            Map<Integer, List<Card>> rankMap = getMapByRank(hand);
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

    static class StraightFlushRecognizer implements HandRecognizer {

        public boolean matches(PokerHand hand) {
            FlushRecognizer flushRecognizer = new FlushRecognizer();
            if (flushRecognizer.matches(hand)) {
                StraightRecognizer straightRecognizer = new StraightRecognizer();
                return straightRecognizer.matches(hand);
            }
            return false;
        }
    }


    private static Map<String, List<Card>> getMapBySuit(PokerHand hand) {
        return hand.cards.
                stream().
                collect(Collectors.groupingBy(card -> card.suit));
    }

    private static List<Integer> getSortedRankFrequencyPattern(PokerHand hand) {
        return getMapByRank(hand).
                values().
                stream().
                map(List::size).
                sorted().
                collect(Collectors.toList());
    }

    private static Map<Integer, List<Card>> getMapByRank(PokerHand hand) {
        return hand.cards.
                stream().
                collect(Collectors.groupingBy(card -> card.rank));
    }

    static class Card {
        String suit;
        int rank;

        private Card(String suit, int rank) {
            this.suit = suit;
            this.rank = rank;
        }

        static Card build(String card) {
            Pattern p = Pattern.compile("([0-9JQKA]*)([SHCD])");
            Matcher matcher = p.matcher(card);
            if (!matcher.find()) {
                throw new IllegalArgumentException("Unable to parse card " + card);
            }
            String suit = matcher.group(2);
            String rank = matcher.group(1);
            int rankValue;
            char ch = rank.charAt(0);
            if (Character.isDigit(ch)) {
                rankValue = Integer.parseInt(rank);
            } else {
                int i = "JQKA".indexOf(ch);
                rankValue = 11 + i;
            }
            return new Card(suit, rankValue);
        }

    }

    static class RecognizerWithValue {
        HandRecognizer recognizer;
        int handValue;

        RecognizerWithValue(HandRecognizer recognizer, int handValue) {
            this.recognizer = recognizer;
            this.handValue = handValue;
        }
    }

    public static class PokerHand {

        private final String handAsString;
        private final List<Poker.Card> cards;

        public PokerHand(String hand) {
            handAsString = hand;
            cards = Arrays.stream(hand.split(",")).
                    map(Poker.Card::build).
                    collect(Collectors.toList());
        }

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

        public Boolean isGreaterThan(PokerHand hand2) {
            return getHandValue(this) > getHandValue(hand2);
        }

        private int getHandValue(PokerHand hand) {
            return Arrays.stream(recognizerWithValues).
                    filter(v -> v.recognizer.matches(hand)).
                    findFirst().
                    map(m -> m.handValue).
                    orElse(0);
        }

        @Override
        public String toString() {
            return handAsString;
        }

    }
}


