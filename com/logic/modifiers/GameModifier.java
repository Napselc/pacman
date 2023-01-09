package com.logic.modifiers;

import java.util.ArrayList;
import java.util.List;

import com.logic.GameInterpretation;

public class GameModifier implements ComponentModifier {
    List<ComponentModifier> componentModifiers;

    public GameModifier(){
        componentModifiers = new ArrayList<>();
        componentModifiers.add(new GhostsModifier());
    }


    @Override
    public void updaGameInterpretation(GameInterpretation currentInterpretation) {
         componentModifiers.forEach(modifier -> modifier.updaGameInterpretation(currentInterpretation));
    }
    
}
