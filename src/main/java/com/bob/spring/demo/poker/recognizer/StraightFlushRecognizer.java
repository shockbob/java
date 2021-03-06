package com.bob.spring.demo.poker.recognizer;

import com.bob.spring.demo.poker.PokerHand;

public class StraightFlushRecognizer implements HandRecognizer {

    public boolean matches(PokerHand hand) {
        FlushRecognizer flushRecognizer = new FlushRecognizer();
        if (flushRecognizer.matches(hand)) {
            StraightRecognizer straightRecognizer = new StraightRecognizer();
            return straightRecognizer.matches(hand);
        }
        return false;
    }
}
