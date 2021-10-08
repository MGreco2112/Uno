package card;

public class Card {
//    Card:
//    String Color
//    int value
//    boolean isSkip
//    boolean isReverse
//    boolean isWild
//    boolean isDraw
//
//    constructor sets each of above
//
//    determinePowers()
//	            if value == regular card: return;
//	            else: set proper boolean(s) to true;


    protected final String COLOR;
    protected final int VALUE;
    protected boolean isSkip = false;
    protected boolean isReverse = false;
    protected boolean isWild = false;
    protected boolean isDraw = false;

    public Card(String COLOR, int value) {
        this.COLOR = COLOR;
        this.VALUE = value;

        determinePowers();
    }


    private void determinePowers() {
        switch (VALUE) {
            case 10 -> isSkip = true;
            case 11 -> isReverse = true;
            case 12 -> isDraw = true;
            case 13 -> isWild = true;
            case 14 -> {isDraw = true; isWild = true;}
            default -> isSkip = false;

        }
    }

    public StringBuilder displayCard() {
        StringBuilder card = new StringBuilder();

        if (VALUE < 13) {
            card.append(COLOR).append(" ");
        }

        if (VALUE <= 9) {
            card.append(VALUE);
        } else {
            switch (VALUE) {
                case 10 -> card.append("Skip");
                case 11 -> card.append("Reverse");
                case 12 -> card.append("Draw 2");
                case 13 -> card.append("Wild");
                case 14 -> card.append("Wild Draw 4");
            }
        }

        return card;
    }


}
