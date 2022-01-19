package Parser;

import Parser.OperandNode;

public interface Visitor {
    public void visit(OperandNode node);
    public void visit(UnaryOpNode node);
    public void visit(BinOpNode node);
}
