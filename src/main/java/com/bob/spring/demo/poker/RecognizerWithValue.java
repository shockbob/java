package com.bob.spring.demo.poker;

class RecognizerWithValue {
    HandRecognizer recognizer;
    int handValue;

    RecognizerWithValue(HandRecognizer recognizer, int handValue) {
        this.recognizer = recognizer;
        this.handValue = handValue;
    }
}
