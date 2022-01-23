package Test;


import Parser.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

    @Test
    @Order(1)
    @DisplayName("Test Parser correct Regular Expression")
    public void rightSyntax() {
        Parser parser = new Parser("(aa(b|c)*)#");
        Visitable toTest = parser.Start();

        Visitable verficiation = new BinOpNode("°",
                new BinOpNode("°",
                        new BinOpNode("°",
                            new OperandNode("a"),new OperandNode("a")),
                            new UnaryOpNode("*",
                                new BinOpNode("|",
                                        new OperandNode("b"),new OperandNode("c")
                                )
                            )
         ) ,new OperandNode("#"));

        assertTrue(equals(toTest,verficiation));

        Parser parser2 = new Parser("(aaa(b|c)*)#");
        Visitable toTest2 = parser2.Start();

        Visitable verficiation2 = new BinOpNode("°",
                new BinOpNode("°",
                        new BinOpNode("°",
                                new OperandNode("a"),new OperandNode("a")),
                        new UnaryOpNode("*",
                                new BinOpNode("|",
                                        new OperandNode("b"),new OperandNode("c")
                                )
                        )
                ) ,new OperandNode("#"));

        assertFalse(equals(toTest2,verficiation2));
    }

    @Test
    @Order(2)
    @DisplayName("Test Parser wrong Regular Expression")
    public void wrongSyntax() {
        Parser parser = new Parser("(aa(b|c*)#");
        boolean error = false;
        try {
            parser.Start();
        } catch (RuntimeException ex) {
            error = true;
        }
        assertTrue(error);

        String errorMessage="";
        Parser parser2 = new Parser("(aa(b|c)*)");
        try {
            parser2.Start();
        } catch (RuntimeException ex) {
           errorMessage = ex.getMessage();
           error = true;
        }
        assertTrue(errorMessage=="End of input reached !");
        assertTrue(error);

    }



    private static boolean equals(Visitable v1, Visitable v2) {
        if (v1 == v2)
            return true;
        if (v1 == null)
            return false;
        if (v2 == null)
            return false;
        if (v1.getClass() != v2.getClass())
            return false;
        if (v1.getClass() == OperandNode.class) {
            OperandNode op1 = (OperandNode) v1;
            OperandNode op2 = (OperandNode) v2;
            return op1.position == op2.position && op1.symbol.equals(op2.symbol);
        }
        if (v1.getClass() == UnaryOpNode.class) {
            UnaryOpNode op1 = (UnaryOpNode) v1;
            UnaryOpNode op2 = (UnaryOpNode) v2;
            return op1.operator.equals(op2.operator) && equals(op1.subNode,
                    op2.subNode);
        }
        if (v1.getClass() == BinOpNode.class) {
            BinOpNode op1 = (BinOpNode) v1;
            BinOpNode op2 = (BinOpNode) v2;
            return op1.operator.equals(op2.operator) &&
                    equals(op1.left, op2.left) &&
                    equals(op1.right, op2.right);
        }
        throw new IllegalStateException("Ungueltiger Knotentyp");
    }
}
