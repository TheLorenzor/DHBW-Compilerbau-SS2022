package Test;

import Parser.*;
import Visitor_1.SyntaxTreeEvaluator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FirstVisitorTest {

    @Test
    @Order(1)
    @DisplayName("Test if sytaxTree correct created")
    public void testFirstVisitor() {
        //(aa(b|c)*)#
        OperandNode nodeA1 = new OperandNode("a");
        nodeA1.nullable = false;
        nodeA1.position = 1;
        nodeA1.firstpos.add(1);
        nodeA1.lastpos.add(1);

        OperandNode nodeA2 = new OperandNode("a");
        nodeA2.nullable = false;
        nodeA2.position = 2;
        nodeA2.firstpos.add(2);
        nodeA2.lastpos.add(2);

        OperandNode nodeB = new OperandNode("b");
        nodeB.nullable = false;
        nodeB.position = 3;
        nodeB.firstpos.add(3);
        nodeB.lastpos.add(3);

        OperandNode nodeC = new OperandNode("c");
        nodeC.nullable = false;
        nodeC.position = 4;
        nodeC.firstpos.add(4);
        nodeC.lastpos.add(4);

        OperandNode nodeEnd = new OperandNode("#");
        nodeEnd.nullable = false;
        nodeEnd.position = 5;
        nodeEnd.firstpos.add(5);
        nodeEnd.lastpos.add(5);

        BinOpNode binOpNode1 = new BinOpNode("°",nodeA1, nodeA2);
        binOpNode1.nullable = false;
        binOpNode1.firstpos.add(1);
        binOpNode1.lastpos.add(2);

        BinOpNode binOpNode2 = new BinOpNode("|", nodeB, nodeC);
        binOpNode2.nullable = false;
        binOpNode2.firstpos.add(3);
        binOpNode2.firstpos.add(4);
        binOpNode2.lastpos.add(3);
        binOpNode2.lastpos.add(4);

        UnaryOpNode unaryOpNode = new UnaryOpNode("*", binOpNode2);
        unaryOpNode.nullable = true;
        unaryOpNode.firstpos.add(3);
        unaryOpNode.firstpos.add(4);
        unaryOpNode.lastpos.add(3);
        unaryOpNode.lastpos.add(4);

        BinOpNode binOpNode3 = new BinOpNode("°",binOpNode1, unaryOpNode);
        binOpNode3.nullable = false;
        binOpNode3.firstpos.add(1);
        binOpNode3.lastpos.add(2);
        binOpNode3.lastpos.add(3);
        binOpNode3.lastpos.add(4);

        BinOpNode binOpNode4 = new BinOpNode("°",binOpNode3, nodeEnd);
        binOpNode4.nullable = false;
        binOpNode4.firstpos.add(1);
        binOpNode4.lastpos.add(5);

        Parser parser = new Parser("(aa(b|c)*)#");
        Visitable parserRoot = parser.Start();

        SyntaxTreeEvaluator visitor1 = new SyntaxTreeEvaluator();
        visitor1.startEvaluation(parserRoot);


        assert(equals(binOpNode4, parserRoot));
    }

    private boolean equals(Visitable expected, Visitable visited)
    {
        if (expected == null && visited == null) return true;
        if (expected == null || visited == null) return false;
        if (expected.getClass() != visited.getClass()) return false;
        if (expected.getClass() == BinOpNode.class)
        {
            BinOpNode op1 = (BinOpNode) expected;
            BinOpNode op2 = (BinOpNode) visited;
            return op1.nullable.equals(op2.nullable) &&
                    op1.firstpos.equals(op2.firstpos) &&
                    op1.lastpos.equals(op2.lastpos) &&
                    equals(op1.left, op2.left) &&
                    equals(op1.right, op2.right);
        }
        if (expected.getClass() == UnaryOpNode.class)
        {
            UnaryOpNode op1 = (UnaryOpNode) expected;
            UnaryOpNode op2 = (UnaryOpNode) visited;
            return op1.nullable.equals(op2.nullable) &&
                    op1.firstpos.equals(op2.firstpos) &&
                    op1.lastpos.equals(op2.lastpos) &&
                    equals(op1.subNode, op2.subNode);
        }
        if (expected.getClass() == OperandNode.class)
        {
            OperandNode op1 = (OperandNode) expected;
            OperandNode op2 = (OperandNode) visited;
            return op1.nullable.equals(op2.nullable) &&
                    op1.firstpos.equals(op2.firstpos) &&
                    op1.lastpos.equals(op2.lastpos);
        }
        throw new IllegalStateException(
                String.format( "Beide Wurzelknoten sind Instanzen der Klasse %1$s !"
            + " Dies ist nicht erlaubt!",
            expected.getClass().getSimpleName())
 );
    }

}