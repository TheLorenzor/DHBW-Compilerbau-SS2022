package Parser;

public class Parser {
    private int position;
    private final String eingabe;

    public Parser(String eingabe) {
        this.eingabe = eingabe;
        this.position = 0;
    }

    public Visitable start(Visitable parameter) {

        return null;
    }

    private Visitable alphanum(Visitable parameter) {
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
