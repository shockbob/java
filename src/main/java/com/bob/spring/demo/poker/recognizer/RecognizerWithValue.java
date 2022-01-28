package com.bob.spring.demo.poker.recognizer;

import com.bob.spring.demo.poker.recognizer.HandRecognizer;

public class RecognizerWithValue {
    final private HandRecognizer recognizer;
    final private int handValue;

    public RecognizerWithValue(HandRecognizer recognizer, int handValue) {
        this.recognizer = recognizer;
        this.handValue = handValue;
    }

    public HandRecognizer getRecognizer() {
        return recognizer;
    }

    public int getHandValue() {
        return handValue;
    }
}
