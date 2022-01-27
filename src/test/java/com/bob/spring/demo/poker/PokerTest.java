package com.bob.spring.demo.poker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import static org.junit.jupiter.api.Assertions.*;

class PokerTest {
    public void testHand1IsGreaterThanHand2(String hand1AsString,
                                            String hand2AsString,
                                            Boolean expectedResult) {
        PokerHand hand1 = PokerHand.createPokerHand(hand1AsString);
        PokerHand hand2 = PokerHand.createPokerHand(hand2AsString);
        Boolean greaterThan = PokerHandEvaluator.isGreaterThan(hand1, hand2);
        assertEquals(greaterThan, expectedResult, "Hand1[" + hand1 + "] > Hand2[" + hand2 + "] \t-- " +
                "expected: " + expectedResult + ", actual: " + greaterThan);
    }


    @Test
    public void testStraightFlushBeatsStraight() {
        testHand1IsGreaterThanHand2(
                "8C,9C,10C,JC,QC", // straight flush
                "6S,7H,8D,9H,10D",
                true);
    }

    @Test
    public void testFourOfAKindsBeatsTwoPairs() {

        testHand1IsGreaterThanHand2(
                "4H,4D,4C,4S,JS", //four of a kind
                "6C,6S,KH,AS,AD",
                true);
    }

    @Test
    public void testFlushBeatsThreeOfAKind() {

        testHand1IsGreaterThanHand2(
                "6C,6D,6H,9C,KD",
                "5C,3C,10C,KC,7C", // flush
                false);

    }

    @Test
    public void testFullHouseBeatsPair() {
        testHand1IsGreaterThanHand2(
                "4H,4D,4C,KC,KD", // full house
                "9D,6S,KH,AS,AD",
                true);
    }


    @Test
    public void testStraightBeatsThreeOfAKind() {
        testHand1IsGreaterThanHand2(
                "6C,6D,6H,9C,KD",
                "2C,3C,4S,5S,6S", // straight
                false);
    }

    @Test
    public void testThreeOfAKindBeatsPair() {
        testHand1IsGreaterThanHand2(
                "7C,7D,7S,3H,4D", // three of a kind
                "9S,6S,10D,AS,AD",
                true);
    }

    @Test
    public void testTwoPairBeatsPair() {
        testHand1IsGreaterThanHand2(
                "2S,2D,JH,7S,AC",
                "8C,8H,10S,KH,KS", // two pair
                false);
    }

    @Test
    public void testOnePairBeatsHighCard() {
        testHand1IsGreaterThanHand2(
                "AC,AH,3C,QH,10C", // one pair
                "3S,2D,KH,JS,AD",
                true);
    }


    @Test
    public void testException() {
        assertThrows(IllegalArgumentException.class, new Executable() {

            @Override
            public void execute() throws Throwable {
                PokerHand.createPokerHand("AX,AH,3C,QH,10C");
            }
        });
    }
}
