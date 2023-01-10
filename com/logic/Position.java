package com.logic;

import java.io.Serializable;

import com.Constants;
import com.Direction;

public class Position implements Serializable{
    public int x,y;

    //TODO: save the previous direction and add smooth movement.
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * This API would allow user to update the position of an element based on direction
     */
    public void move(Direction direction){
        switch(direction){
            case DOWN:
                y = y + Constants.BLOCK_SIZE;
                break;
            case LEFT:
                x = x - Constants.BLOCK_SIZE;
                break;
            case RIGHT:
                x = x + Constants.BLOCK_SIZE;
                break;
            case UP:
                y = y - Constants.BLOCK_SIZE;
                break;
            default:
                break;

        }
        // System.out.printf("x: '%s' y: '%s'",x,y);
        
    }

    @Override
    public String toString() {
        return "Position [x=" + x + ", y=" + y + "]";
    }

}
