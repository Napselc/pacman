package com.logic.modifiers;

import com.Direction;
import com.logic.GameInterpretation;
import com.logic.Position;
import com.utility.PositionUtility;

public class GhostModifier implements ComponentModifier {
    Direction previousDirection = Direction.DOWN;
    private int index;

    public GhostModifier(int index) {
        this.index = index;
    }

    @Override
    public void updaGameInterpretation(GameInterpretation currentInterpretation) {
        Position currentPosition = currentInterpretation.getGhostPositions().get(index);
        //TODO : we need to verfy if this is the correct position (wall)
        currentPosition.move(PositionUtility.getRandomDirection());
    }
    
}
