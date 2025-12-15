public class rouletteWheel {
    private static final int MIN_NUMBER = 0;
    private static final int MAX_NUMBER = 36;

    public rouletteWheel(int number){
    }
    // Spin the wheel to get a random number between 0 and 36
    public int spin() {
        return (int)Math.floor(Math.random() * (MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER);
    }
    // Get color
    public String getColor(int number) { // Method used to get the color data of each number
        if (number == 0) {
            return "GREEN";
        }   
        // Red numbers
        final int[] RED_NUMBERS = {1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36};
        for (int red : RED_NUMBERS) {
            if (number == red) {
                return "RED";
            }
        }
        return "BLACK"; // If not red or green, it must be black
    }
    // Check row
    public boolean isInRow(int row, int number) {
        if (number == 0 || row == 0)  return false;
        
        int actualRow = (number % 3 == 0) ? 3 : number % 3;
        return actualRow == row;
    }
    // Check 12th
    public boolean isIn12th(int number, int twelfth) {
        if (number == 0 || twelfth == 0) return false;
        
        if (twelfth == 1) {
            return number >= 1 && number <= 12;
        } else if (twelfth == 2) {
            return number >= 13 && number <= 24;
        } else if (twelfth == 3) {
            return number >= 25 && number <= 36;
        }
        return false;
    }
    // Check 18th
    public boolean isIn18th(int number, int eighteenth) {
        if (number == 0 || eighteenth == 0) return false;
        
        if (eighteenth == 1) {
            return number >= 1 && number <= 18;
        } else if (eighteenth == 2) {
            return number >= 19 && number <= 36;
        }
        return false;
    }
    // Check even or odd
    public boolean isEven(int number) {
        if (number == 0) return false;
        return number % 2 == 0;
    }
    public boolean isOdd(int number) {
        if (number == 0) return false;
        return number % 2 != 0;
    }
}
