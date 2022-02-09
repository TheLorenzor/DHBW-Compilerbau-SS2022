package Visitor_1;

import Parser.*;

public class SyntaxTreeEvaluator implements Visitor {
    private int counter = 1;

    public SyntaxTreeEvaluator() {}

    public void startEvaluation(Visitable root) {
        DepthFirstIterator.traverse(root, this);
    }

    public void visit(OperandNode node) {
        node.position = counter;
        counter++;
        if (node.symbol.equals("epsilon")) {
            node.nullable =true;
        } else
            node.nullable=false;
        node.firstpos.add(node.position);
        node.lastpos.add(node.position);
    }
    public void visit(BinOpNode node) {
        SyntaxNode leftNode = (SyntaxNode)node.left;
        SyntaxNode rightNode = (SyntaxNode)node.right;

        switch (node.operator) {
            case "°":
                node.nullable = leftNode.nullable && rightNode.nullable;
                if (leftNode.nullable) {
                    node.firstpos.addAll(leftNode.firstpos);
                    node.firstpos.addAll(rightNode.firstpos);
                } else
                    node.firstpos.addAll(leftNode.firstpos);
                if (rightNode.nullable) {
                    node.lastpos.addAll(leftNode.lastpos);
                    node.lastpos.addAll(rightNode.lastpos);
                } else
                    node.lastpos.addAll(rightNode.lastpos);
                break;
            case "|":
                node.nullable = leftNode.nullable || rightNode.nullable;
                node.firstpos.addAll(leftNode.firstpos);
                node.firstpos.addAll(rightNode.firstpos);
                node.lastpos.addAll(leftNode.lastpos);
                node.lastpos.addAll(rightNode.lastpos);
                break;
            default:
                throw new RuntimeException("Not accepted Operator Symbol");
        }
    }
    public void visit(UnaryOpNode node) {
        SyntaxNode subNode = (SyntaxNode)node.subNode;

        switch (node.operator) {
            case "?":
                node.nullable = true;
                node.firstpos.addAll(subNode.firstpos);
                node.lastpos.addAll(subNode.lastpos);
                break;
            case "+":
                node.nullable = subNode.nullable;
                node.firstpos.addAll(subNode.firstpos);
                node.lastpos.addAll(subNode.lastpos);
                break;
            case "*":
                node.nullable = true;
                node.firstpos.addAll(subNode.firstpos);
                node.lastpos.addAll(subNode.lastpos);
                break;
            default:
                throw new RuntimeException("Not accepted Unary Symbol");
        }
    }

}
