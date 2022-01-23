package Parser;

public class Parser {
    private int position;
    private final String eingabe;

    public Parser(String eingabe) {
        this.eingabe = eingabe;
        this.position = 0;
    }

    public Visitable Start() {
        switch (eingabe.charAt(this.position)) {
            case '#' -> {
                match('#');
                assertEndOfInput();
                return new OperandNode("#");
            }
            case '(' -> {
                match('(');
                Visitable leaf = new OperandNode("#");
                Visitable retur = RegEx(null);
                Visitable root = new BinOpNode("°", retur, leaf);
                match(')');
                match('#');
                assertEndOfInput();
                return root;
            }
            default -> throw new RuntimeException();
        }
    }

    private Visitable RegEx(Visitable visit) {
        Visitable term = Term(null);
        return RE(term);

    }

    private Visitable RE(Visitable visit) {
        switch (eingabe.charAt(position)) {
            case '|':
                match('|');
                Visitable term = Term(null);
                Visitable root = new BinOpNode("|", visit, term);
                return RE(root);
            case ')':
                return visit;
            default:
                throw new RuntimeException();
        }
    }

    private Visitable Term(Visitable visit) {
        if (eingabe.charAt(position) == '(' || Character.isLetterOrDigit(eingabe.charAt(position))) {
            Visitable factor = Factor(null);
            Visitable root;
            if (visit != null) {
                root = new BinOpNode("°", visit, factor);

            } else {
                root = factor;
            }
            return Term(root);
        } else if (eingabe.charAt(position) == '|' || eingabe.charAt(position) == ')') {
            return visit;
        } else {
            throw new RuntimeException("Syntax Error!");
        }
    }

    private Visitable Factor(Visitable visit) {
        if (Character.isLetterOrDigit(eingabe.charAt(position)) || eingabe.charAt(position) == '(') {
            Visitable ret = Elem(null);
            return Hop(ret);
        } else {
            throw new RuntimeException("Syntax error !");
        }
    }

    private Visitable Hop(Visitable visit) {
        if (eingabe.charAt(position) == '*') {
            match('*');
            return new UnaryOpNode("*", visit);
        } else if (eingabe.charAt(position) == '+') {
            match('+');
            return new UnaryOpNode("+", visit);
        } else if (eingabe.charAt(position) == '?') {
            match('?');
            return new UnaryOpNode("?", visit);
        } else if (Character.isLetterOrDigit(eingabe.charAt(position)) || eingabe.charAt(position) == '(' || eingabe.charAt(position) == '|' || eingabe.charAt(position) == ')') {
            return visit;
        } else {
            throw new RuntimeException("Syntax error !");
        }
    }

    private Visitable Elem(Visitable visit) {
        if (eingabe.charAt(position) == '(') {
            match('(');
            Visitable ret = RegEx(null);
            match(')');
            return ret;
        } else if (Character.isLetterOrDigit(eingabe.charAt(position))) {
            return Alphanum(null);
        } else {
            throw new RuntimeException("Syntax error !");
        }
    }

    private Visitable Alphanum(Visitable visit) {
        char check = eingabe.charAt(position);
        if (Character.isLetterOrDigit(check)) {
            match(check);
        } else {
            throw new RuntimeException("Syntax error !");
        }

        return new OperandNode(String.valueOf(check));
    }

    private void match(char symbol) {
        if ((eingabe == null) || ("".equals(eingabe))) {
            throw new RuntimeException("Syntax error !");
        }
        if (position >= eingabe.length()) {
            throw new RuntimeException("End of input reached !");
        }
        if (eingabe.charAt(position) != symbol) {
            throw new RuntimeException("Syntax error !");
        }
        position++;
    }

    private void assertEndOfInput() {
        if (position < eingabe.length()) {
            throw new RuntimeException(" No end of input reached !");
        }
    }
}
