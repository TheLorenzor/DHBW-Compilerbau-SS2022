package DefiniteFiniteAutomata;

import Visitor_2.FollowposTableEntry;

import java.util.*;

public class DFACreator {
    private final Set<Integer> positionsForStartState;
    private final SortedMap<Integer, FollowposTableEntry> followposTable;
    private final Map<DFAState, Map<String, DFAState>> stateTransitionTable;
    private int counter = 1;

    /**
     * Man beachte ! Parameter <code>positionsForStartState</code> muss vom Aufrufer
     * mit der firstpos-Menge des Wurzelknotens des Syntaxbaums initialisiert werden !
     */
    public DFACreator(Set<Integer> positionsForStartState,
                      SortedMap<Integer, FollowposTableEntry> followposTable) {
        this.positionsForStartState = positionsForStartState;
        this.followposTable = followposTable;
        this.stateTransitionTable = new HashMap<>();
    }


    public void populateStateTransitionTable() {

        Set<String> alphabet = getAlphabet();
        List<DFAState> qStates = new ArrayList<>();
        int posOfTerminatingSymbol = followposTable.lastKey(); // Schluessel des letzten Eintrags
        DFAState startState = new DFAState(
                counter++,
                positionsForStartState.contains(posOfTerminatingSymbol),
                positionsForStartState
        );
        qStates.add(startState);

        while (!qStates.isEmpty()) {

            DFAState currentState = qStates.get(0);
            qStates.remove(0);
            stateTransitionTable.put(currentState, new HashMap<>());

            for (String symbol : alphabet) {
                Set<Integer> followpospersymbol = lookForNextState(symbol,currentState);
                if (!followpospersymbol.isEmpty()) {
                    DFAState followState = lookForExistingState(followpospersymbol, qStates);
                    if (followState == null) {
                        followState = new DFAState(
                                counter++,
                                positionsForStartState.contains(posOfTerminatingSymbol),
                                followpospersymbol
                        );
                        qStates.add(followState);
                    }
                    stateTransitionTable.get(currentState).put(symbol, followState);
                }
            }
        }
    }
    private Set<String>getAlphabet(){
        Set<String> alphabet = new HashSet<>();
        for (FollowposTableEntry entry : followposTable.values()) {
            if (!"#".equals(entry.symbol)) {
                alphabet.add(entry.symbol);
            }
        }
        return alphabet;
    }

    private Set<Integer> lookForNextState(String symbol,DFAState currentState){
        Set<Integer> followpospersymbol = new HashSet<>();
        for (FollowposTableEntry entry : followposTable.values()) {
            if (entry.symbol.equals(symbol) && currentState.positionsSet.contains(entry.position)) {
                followpospersymbol.addAll(entry.followpos);
            }
        }
        return followpospersymbol;
    }

    private DFAState lookForExistingState(Set<Integer> followState, List<DFAState> qStates) {
        for (DFAState processed : stateTransitionTable.keySet()) {
            if (processed.positionsSet.equals(followState)) {
                return processed;
            }
        }
        for (DFAState quedState : qStates) {
            if (quedState.positionsSet.equals(followState)) {
                return quedState;
            }
        }
        return null;
    }

    public Map<DFAState, Map<String, DFAState>> getStateTransitionTable() {
        return stateTransitionTable;
    }
}
