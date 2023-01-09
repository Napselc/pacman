package com.logic.modifiers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.Constants;
import com.logic.GameInterpretation;

public class GhostsModifier implements ComponentModifier {
    List<GhostModifier> ghosts ;

    public GhostsModifier(){
        ghosts = new ArrayList<>();
        IntStream.range(0, Constants.NUMBER_OF_GHOSTS).forEach( index -> ghosts.add(new GhostModifier(index)));
    }

    @Override
    public void updaGameInterpretation(GameInterpretation currentInterpretation) {
        ghosts.forEach(modifier -> modifier.updaGameInterpretation(currentInterpretation));
    }
    
}
