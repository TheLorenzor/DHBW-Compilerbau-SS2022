package Test;

import DefiniteFiniteAutomata.DFACreator;
import DefiniteFiniteAutomata.DFAState;
import Visitor_2.FollowposTableEntry;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.*;

import static groovy.test.GroovyTestCase.assertEquals;

public class DFATest {
    @Test
    @Order(1)
    public void testDFACreator()
    {
// hart kodiert
        // hart kodiert
        SortedMap<Integer, FollowposTableEntry> followposTableEntries=new TreeMap<>();
        followposTableEntries.put(1, new FollowposTableEntry(1,"a"));
        followposTableEntries.get(1).followpos.addAll(Arrays.asList( 1, 2, 3));
        followposTableEntries.put(2, new FollowposTableEntry(2,"b"));
        followposTableEntries.get(2).followpos.addAll(Arrays.asList( 1, 2, 3));
        followposTableEntries.put(3, new FollowposTableEntry(3,"c"));
        followposTableEntries.get(3).followpos.addAll(Arrays.asList( 4,5));
        followposTableEntries.put(4, new FollowposTableEntry(4,"d"));
        followposTableEntries.get(4).followpos.addAll(Arrays.asList( 4,5));
        followposTableEntries.put(5, new FollowposTableEntry(5,"#"));
// hart kodiert. Stellt das erwartete Ergebnis dar
        Set<Integer> tempIntegerSet = new HashSet<>(Arrays.asList(1, 2, 3));
        DFAState start=new DFAState(1,false,tempIntegerSet);
        tempIntegerSet = new HashSet<>(Arrays.asList(4, 5));
        DFAState ende=new DFAState(1,true,tempIntegerSet);
        Map<String, DFAState> tempMap = new HashMap<>();
        tempMap.put("a",start);
        tempMap.put("b",start);
        tempMap.put("c",ende);
        Map<DFAState, Map<String, DFAState>> stateTransitionTable = new HashMap<>();
        stateTransitionTable.put(start,tempMap);
        tempMap = new HashMap<>();
        tempMap.put("d",ende);
        stateTransitionTable.put(ende,tempMap);


        Set<Integer> positionsOfStartState = new HashSet<>(Arrays.asList(1, 2, 3));
        DFACreator creator = new DFACreator(
                positionsOfStartState,
                followposTableEntries
        );
        creator.populateStateTransitionTable();
        assertEquals(stateTransitionTable, creator.getStateTransitionTable());
    }
}
