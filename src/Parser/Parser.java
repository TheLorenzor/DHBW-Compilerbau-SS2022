package Parser;

public class Parser {
    private int position;
    private final String eingabe;

    public Parser(String eingabe) {
        this.eingabe = eingabe;
        this.position = 0;
    }

    public Visitable start(Visitable parameter) {
        try {
            switch (eingabe.charAt(this.position)) {
                case '#':
                    match('#');
                    assertEndOfInput();
                    return new OperandNode("#");
                case '(':
                    match('(');
                    Visitable leaf = new OperandNode("#");
                default:
                    throw new RuntimeException();
            }
        } catch (RuntimeException runtimeEx) {
            System.out.println("Kein valides File");
        }
        return null;
    }

    private Visitable RegEx(Visitable visit) {
        return null;
    }

    private Visitable RE(Visitable visit) {
        return null;
    }

    private Visitable Term(Visitable visit) {
        return null;
    }

    private Visitable Factor(Visitable visit) {
        return null;
    }

    private Visitable Hop(Visitable visit) {
        return null;
    }

    private Visitable Elem(Visitable visit) {
        return null;
    }

    private Visitable Alphanum(Visitable visit) {
        return null;
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
