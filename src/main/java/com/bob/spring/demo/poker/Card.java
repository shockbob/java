package com.bob.spring.demo.poker;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
public class Card {
    private  String suit;
    private  int rank;

    public static Card build(String card) {
        Pattern p = Pattern.compile("([0-9JQKA]*)([SHCD])");
        Matcher matcher = p.matcher(card);
        if (!matcher.matches()) {
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
