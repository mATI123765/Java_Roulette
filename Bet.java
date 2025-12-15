public class Bet {
    private int betAmount;
    private int number; // equals to -1 if not betting
    private String color; // empty if not betting on color 
    private int row; // 0 if row has not bet
    private int twelfth; // 0 if 12th has no bet
    private int eighteenth; // 0 if 18th has no bet
    private int evenOdd; // 0 if no bet, 1 for even, 2 for odd

    // setters
    public Bet(int betAmount, int number, String color, int row, int twelfth, int eighteenth, int evenOdd) {
        this.betAmount = betAmount;
        this.number = number;
        this.color = color;
        this.row = row;
        this.twelfth = twelfth;
        this.eighteenth = eighteenth;
        this.evenOdd = evenOdd;
    }

    // getters
    public int getBetAmount() { return betAmount; }
    public int getNumber() { return number; }
    public String getColor() { return color; }
    public int getRow() { return row; }
    public int getTwelfth() { return twelfth; }
    public int getEighteenth() { return eighteenth; }
    public int getEvenOdd() { return evenOdd; }
    
    public boolean hasBetOnNumber() { return number != -1; }
    public boolean hasBetOnColor() { return !color.isEmpty(); }
    public boolean hasBetOnRow() { return row != 0; }
    public boolean hasBetOnTwelfth() { return twelfth != 0; }
    public boolean hasBetOnEighteenth() { return eighteenth != 0; }
    public boolean hasBetOnEvenOdd() { return evenOdd != 0; }
}