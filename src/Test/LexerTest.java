package Test;

import Lexer.DFAState;
import groovyjarjarantlr4.runtime.DFA;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

    @Test
    void match() {
        String word = "asdf";
        Set<Integer> set0 = new HashSet<Integer>(){{
            add(1);
            add(2);
            add(3);
            add(4);
        }};
        Set<Integer> set1 = new HashSet<Integer>(){{
            add(0);
            add(2);
            add(3);
            add(4);
        }};
        Set<Integer> set2 = new HashSet<Integer>(){{
            add(1);
            add(0);
            add(3);
            add(4);
        }};
        Set<Integer> set3 = new HashSet<Integer>(){{
            add(1);
            add(2);
            add(0);
            add(4);
        }};
        Set<Integer> set4 = new HashSet<Integer>(){{
            add(1);
            add(2);
            add(3);
            add(0);
        }};


        DFAState a0 = new DFAState(0,false,set0);
        DFAState a1 = new DFAState(1,false,set1);
        DFAState a2 = new DFAState(2,false,set2);
        DFAState a3 = new DFAState(3,false,set3);
        DFAState a4 = new DFAState(4,true,set4);
        Map<DFAState, Map<Character, DFAState>> stateTransitionTable = new HashMap<>();
        Map<Character, DFAState> temp0 = new HashMap<>();
        temp0.put('a',a1);
        Map<Character, DFAState> temp1 = new HashMap<>();
        temp1.put('s',a2);
        Map<Character, DFAState> temp2 = new HashMap<>();
        temp2.put('d',a3);
        Map<Character, DFAState> temp3 = new HashMap<>();
        temp3.put('f',a4);
        Map<Character, DFAState> temp4 = new HashMap<>();
        temp4.put('a',a1);
        stateTransitionTable.put(a0,temp0);
        stateTransitionTable.put(a1,temp1);
        stateTransitionTable.put(a2,temp2);
        stateTransitionTable.put(a3,temp3);
        stateTransitionTable.put(a4,temp4);
        char[] letters = word.toCharArray();
        Object[] test =  stateTransitionTable.keySet().toArray();
        DFAState state = getState(test, 0);
        for (char letter : letters) {
            if (stateTransitionTable.get(state).get(letter) != null) {
                state = stateTransitionTable.get(state).get(letter);
            } else System.out.println("false");
        }
            assertTrue(state.isAcceptingState);





    }

    @Test
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