package com.utility;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.Direction;

public  class PositionUtility {
    static Random randomizer = new Random();
    static List<Direction> directions = Arrays.asList(Direction.UP,Direction.DOWN,Direction.RIGHT,Direction.LEFT);
    
    private PositionUtility(){
        // empty private constructor for static utility
    }

    public static Direction getRandomDirection(){
        return directions.get(randomizer.nextInt(0,4));
    }
}
