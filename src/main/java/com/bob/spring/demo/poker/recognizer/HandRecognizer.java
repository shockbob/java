package com.bob.spring.demo.poker.recognizer;

import com.bob.spring.demo.poker.PokerHand;

public interface HandRecognizer {
    boolean matches(PokerHand hand);
}
