package Visitor_2;

import Parser.*;
import Visitor_1.DepthFirstIterator;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;

public class FollowPosTableGenerator implements Visitor {

    private final SortedMap<Integer, FollowposTableEntry> followposTableEntries;

    public FollowPosTableGenerator(SortedMap<Integer, FollowposTableEntry> followposTableEntries) {
        this.followposTableEntries = followposTableEntries;
    }

    public void startEvaluation(Visitable root) {
        DepthFirstIterator.traverse(root, this);
    }

    @Override
    public void visit(OperandNode node) {
        followposTableEntries.put(
                node.position,
                new FollowposTableEntry(node.position, node.symbol));
    }

    @Override
    public void visit(BinOpNode node) {
        if ("°".equals(node.operator)) {
            for (Integer i:                                       //iterire über alle lastpos
                    ((BinOpNode)node.left).lastpos ) {
                if(((BinOpNode)node.right).firstpos.contains(i))  //followpos(Knoten an Position i) & firstpos(akt. innerem Knoten)
                followposTableEntries.get(i).Setfollowpos(i);     //aktualiesiere den Eintrag
            }
        }
        //alternative spielt keine Rolle
    }

    @Override
    public void visit(UnaryOpNode node) {
        if ("?".equals(node.operator))
        {
            return; // Option spielt keine Rolle
        }

        if ("*".equals(node.operator) || "+".equals(node.operator))
        {
            for (Integer i:
                    ((UnaryOpNode)node.subNode).lastpos) {
                if(((UnaryOpNode)node.subNode).firstpos.contains(i))            //followpos(Knoten an Position i) & firstpos(akt. innerem Knoten)
                    followposTableEntries.get(i).Setfollowpos(i);               //aktualiesiere den Eintrag
            }
        }
    }

    public SortedMap<Integer, FollowposTableEntry> getFollowposTable()
    {return followposTableEntries;}
}
