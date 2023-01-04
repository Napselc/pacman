package com.logic;

import java.awt.Graphics2D;

import com.Direction;
import com.Drawing.Ghost;
import com.Drawing.Maze;
import com.Drawing.Painting;

public class GameInterpretation{
    private final short leveldata[] = {
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
    
    private Painting maze = new Maze();
    private Painting ghost = new Ghost();

    private Direction userInputDirection;

    
    
    //TODO : divide this logic into components eg. getMaze, getDots , getGhosts
    public short[] getLeveldata() {
        return leveldata;
    }

    public void draw(Graphics2D graphics) {
        maze.setInterpretation(this);
        ghost.setInterpretation(this);
        maze.draw(graphics);
        ghost.draw(graphics);
    }

    public void setUserInputDirection(Direction direction) {
        this.userInputDirection = direction;
    }

    public Direction getUserInputDirection() {
        return this.userInputDirection;
    }
}