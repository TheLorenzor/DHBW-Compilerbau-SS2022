package Lexer;

import java.util.HashMap;
import java.util.Map;

public class Lexer
{
    private Map<DFAState, Map<Character, DFAState>> stateTransitionTable = new HashMap<>();;
    public Lexer(Map<DFAState, Map<Character, DFAState>> stateTransitionTable)
    {
        this.stateTransitionTable = stateTransitionTable;
    }


    public boolean match(String word)
    {

        char[] letters = word.toCharArray();
        Object[] test =  stateTransitionTable.keySet().toArray();
        DFAState state = getState(test, 0);
        for(int i = 0; i < letters.length; i++){
            char letter = letters[i];
            if(stateTransitionTable.get(state).get(letter) != null) {
                state = stateTransitionTable.get(state).get(letter);
            }
            else return false;
        }
        return state.isAcceptingState;
    }

    public DFAState getState(Object[] obj, int in){
        for(int i = 0; i <= obj.length; i++){
            DFAState temp = (DFAState) obj[i];
            if(temp.index == in){
                return temp;
            }
        }
        return null;
    }

}
