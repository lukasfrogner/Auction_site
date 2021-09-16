package com.projektgik2h9.auctionsite.test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Round {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
    
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
