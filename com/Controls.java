package com;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * This is used to capture the input controls to a Game Pannel.
 */
public class Controls extends KeyAdapter {
    Direction direction = Direction.UP;

    /**
     * This method captures the direction of movement based on keyboard arrow keys.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        
        int key = e.getKeyCode();

        switch(key){
            case KeyEvent.VK_LEFT:
                this.direction = Direction.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                this.direction = Direction.RIGHT;
                break;
            case KeyEvent.VK_UP:
                this.direction = Direction.UP;
                break;
            case KeyEvent.VK_DOWN:
                this.direction = Direction.DOWN;
                break;
            
        }

    }

    /**
     * This method should be used to read the user input.
     */
    public Direction getDirection() {
        return this.direction;
    }

    
}
