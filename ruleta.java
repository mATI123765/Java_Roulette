public class ruleta {
    private String color;
    private int number;
    
    // Create the roulette builder 
    public ruleta(String color, int number) {
        this.color = color;
        this.number = number;
    }

    public String getColor() { // Method used to get the color data of each number
        return color;
    }

    public int getNumber() { // Method that fetches the given number data
        return number;
    }
}
