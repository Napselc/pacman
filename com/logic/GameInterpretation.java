package com.logic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.Constants;
import com.Direction;

public class GameInterpretation  implements Serializable {
    private final transient short leveldata[] = {
        0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0,  0,  0,
        0, 19, 26, 26, 18, 26, 18, 26, 26, 26, 26, 18, 26, 18, 26, 26, 24, 26, 22,  0,
        0, 21,  0,  0, 21,  0, 21,  0,  0,  0,  0, 21,  0, 21,  0,  0,  0,  0, 21,  0,
        0, 21,  0,  0, 21,  0, 17, 26, 26, 22,  0, 21,  0, 21,  0,  0,  0,  0, 21,  0,
        0, 21,  0,  0, 21,  0, 21,  0,  0, 21,  0, 21,  0, 21,  0,  0,  0,  0, 21,  0,
        0, 21,  0,  0, 21,  0, 17, 26, 26, 28,  0, 17, 26, 24, 26, 26, 26, 26, 20,  0,
        0, 21,  0,  0, 21,  0, 21,  0,  0,  0,  0, 21,  0,  0,  0,  0,  0,  0, 21,  0,
        0, 21,  0,  0, 21,  0, 17, 26, 26, 26, 26, 24, 26, 26, 26, 26, 22,  0, 21,  0,
        0, 21,  0,  0, 21,  0, 21,  0,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0, 21,  0,
        0, 21,  0,  0, 21,  0, 17, 26, 26, 26, 26, 26, 26, 26, 26, 18, 28,  0, 21,  0,
        0, 21,  0,  0, 21,  0, 21,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,
        0, 21,  0,  0, 21,  0, 25, 26, 26, 26, 26, 26, 18, 26, 26, 24, 26, 26, 20,  0,
        0, 21,  0,  0, 21,  0,  0,  0,  0,  0,  0,  0, 21,  0,  0,  0,  0,  0, 21,  0,
        0, 21,  0,  0, 17, 26, 26, 26, 26, 26, 26, 26, 24, 26, 26, 26, 26, 26, 20,  0,
        26,16, 26, 26, 28,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 17, 26,
        0, 21,  0,  0,  0,  0, 19, 26, 18, 26, 26, 26, 26, 26, 18, 26, 30,  0, 21,  0,
        0, 17, 26, 26, 30,  0, 21,  0, 21,  0,  0,  0,  0,  0, 21,  0,  0,  0, 21,  0,
        0, 21,  0,  0,  0,  0, 21,  0, 21,  0,  0,  0,  0,  0, 21,  0,  0,  0, 21,  0,
        0, 25, 26, 26, 26, 26, 24, 26, 24, 26, 26, 26, 26, 26, 24, 26, 18, 26, 28,  0,
        0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0,  0,  0,
    };
    
    
    private Direction userInputDirection;


    private List<Position> ghosts = Arrays.asList(new Position(24, 120),
                                                  new Position(96,240),
                                                  new Position(144, 360),
                                                  new Position(240, 216));
    
    //TODO : divide this logic into components eg. getMaze, getDots , getGhosts
    public short[] getLeveldata() {
        return leveldata;
    }
    
    public List<Position> getGhostPositions(){
        return ghosts;
    }

    public void setGhosts(List<Position> ghosts) {
    this.ghosts = ghosts;
    }

    public void setUserInputDirection(Direction direction) {
        this.userInputDirection = direction;
    }

    public Direction getUserInputDirection() {
        return this.userInputDirection;
    }

    //Serialization
    public String getString() {
        List<Integer> ghostPositions = new ArrayList<>();
        for (Position position : ghosts) {
            ghostPositions.add(position.x);
            ghostPositions.add(position.y);
        }

        return ghostPositions.stream().map(position -> position.toString()).collect(Collectors.joining(","));
    }

    //De-serialization
    public static GameInterpretation setInterpretation(String interpretation){
        GameInterpretation gameInterpretation = new GameInterpretation();
        List<String> positions = Arrays.asList( interpretation.split(","));
        Iterator<String> iterator = positions.iterator();
        //default interpretation
        IntStream.range(0, Constants.NUMBER_OF_GHOSTS).forEach(ghost ->{
            int x = Integer.parseInt(iterator.next());
            int y = Integer.parseInt(iterator.next());
            Position position = new Position(x,y);
            gameInterpretation.ghosts.set(ghost,position);
        });

        return gameInterpretation;
    }

    @Override
    public String toString() {
        return "GameInterpretation [ ghosts=" + ghosts + "]";
    }
}