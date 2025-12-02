import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Create a new class called "Ruleta" to save it in the ArrayList
class Ruleta {
    private String color;
    private int number;

    // Craete the roulette builder 
    public Ruleta(String color, int number) {
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

public class Roulette {
    // Ansi code colors so it's shown in the console 
    public static final String RESET = "\u001B[0m";      // Reset color
    public static final String RED = "\u001B[31m";      // RED text
    public static final String GREEN = "\u001B[32m";     // GREEN text
    public static final String BLACK = "\u001B[30m";     // BLACK text
    public static final String WHITE_BACKGROUND = "\u001B[47m"; // WHITE BACKGROUND for BLACK
    public static final String BOLD = "\u001B[1m";    // Text in BOLD
    public static int money = 10000;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // HashMap that saves de statics of how many times a number appears
        HashMap<Integer, Integer> statistics = new HashMap<>();
        
        // ArrayList with the history of the last rolls
        ArrayList<Ruleta> history = new ArrayList<>();
        
        printLinebreak(10);
        rouletteMenu();
        
        boolean continue = true;
        boolean repeat = false;
        String user_bet;
        int bet;
        while (continue) {
            
            System.out.println("\n///////////////////");
            System.out.println("--------NEW BET------");
            do {
            System.out.print("How much money do you want to bet? (0 para salir): ");
            System.out.printf("You have %s pounds \n", money);
            bet = scanner.nextInt();

            if (bet == 0) {
                continue = false;
                break;
            } else if (bet > money) {
                repeat = true;
                System.out.println("Chill bro, you ain't got that much money.");
                System.out.print("How much money do you want to bet? (0 to leave): ");
                System.out.printf("You have %s pounds \n", money);
                bet = scanner.nextInt();
                } else {
                    repeat = false;
                    break;
                }
            } while (repeat);
            try {
                System.out.print("What number will you bet on? (0-36 / N for none): ");
                user_bet = scanner.next();
                System.out.print("What color will you bet on? (RED-BLACK / enter for none): ");      
                user_bet = scanner.nextLine();          
            // Validate that the number is within the range
            if (0 > Integer.parseInt(user_bet) || 30 < Integer.parseInt(user_bet)) {

                System.out.println("Invalid number! It must be between 0 and 36.");
                continue;
            }   else if (user_bet.equalsIgnoreCase("N"))  {
                
            }   

            } catch (NumberFormatException e) {
                }
           
            user_bet = scanner.nextLine();

            // ROULETTE SPIN - randomly generated number between 1 and 30
            int winningNumber = randomNumber(1, 31); // 31 is exclusive, gives 1-30
            String winningColor = getColor(winningNumber);
            
            // Create object "Ruleta" with the result
            Ruleta result = new Ruleta(winningColor, winningNumber);
            history.add(result);
            
            // Update statistics and increment the counter of the displayed number
            statistics.put(winningNumber, statistics.getOrDefault(winningNumber, 0) + 1);
            
            // Show result with animation
            spinningAnimation();
            printLinebreak(3);
            System.out.println("\n The ball is spinning...!");
            printLinebreak(1);
            
            // Show number with color
            printNumberColor(result.getNumber(), result.getColor());
            printLinebreak(1);
            
            // Verify if the user won and add or subtract money from total earnings
            if (user_bet.equals(String.valueOf(winningNumber)) || user_bet.equalsIgnoreCase(winningColor)) {
                int earnings = bet * 2.5; // In roulette (for now) it pays 2.5 times what you bet
                System.out.println("¡¡¡YOU WON!!!");
                System.out.printf("You bet: %d | you win: %d\n", bet, earnings);
                money += earnings;
            } else{
                System.out.println(" You lost your bet.");
                money = money - bet;
            }
            // Verify if the money is negative
            if (money < 0) {
                money = 0;
            }
            // Show history of last 5 spins
            showHistory(history);
            
            // Ask if the user wants to see the statistics (stats)
            System.out.print("\n Check stats? (y/n): ");
            String answer = scanner.next().toLowerCase();
            if (answer.equalsIgnoreCase("y")) {
                mostrarstatistics(statistics, history.size());
                printLinebreak(3);
            } else {
                printLinebreak(12);
                rouletteMenu();
            }
            
        }
        
        System.out.println("\n Thanks for playing!");
        scanner.close();
    }
    
    // Determines the color based on the number
    static String getColor(int number) {
        if (number == 0) {
            return "GREEN"; // 0 is GREEN 
        }
        
        // RED numbers in the roulette
        int[] REDs = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 32, 34, 36};
        
        for (int RED : REDs) {
            if (number == RED) {
                return "RED";
            }
        }
        
        return "BLACK"; // If it's not RED, it will be BLACK my guy,
    }
    
    // Show the last 5 spins with the colors 
    static void showHistory(ArrayList<Ruleta> history) {
        int quantity = Math.min(5, history.size()); // Last 5 or less
        
        if (quantity == 0) return;
        
        System.out.println("\n/////////////////////");
        System.out.println("------LAST  PLAYS------");
        System.out.println("/////////////////////\n");
        for (int i = history.size() - 1; i >= history.size() - quantity; i--) {
            Ruleta r = history.get(i);
            
            // Print each number with its color 
            String colorCode = getColorCode(r.getColor());
            if (r.getColor().equals("BLACK")) {
                // BLACK needs a white background to be visible 
                System.out.print(WHITE_BACKGROUND + BLACK + BOLD + r.getNumber() + RESET + " ");
            } else {
                System.out.print(colorCode + BOLD + r.getNumber() + RESET + " ");
            }
        }
        System.out.println();
    }
    
    // Function to print the winning number color in a bigger format
    static void printNumberColor(int number, String color) {
        String colorCode = getColorCode(color);
        
        System.out.print("   Número: ");
        
        if (color.equals("BLACK")) {
            // BLACK with white background so it's visible
            System.out.print(WHITE_BACKGROUND + BLACK + BOLD + number + RESET);
        } else {
            System.out.print(colorCode + BOLD + number + RESET);
        }
        
        System.out.println(" (" + colorCode + color + RESET + ")");
    }
    
    // Returns the ANSI code depending on the color
    static String getColorCode(String color) {
        switch (color) {
            case "RED":
                return RED;
            case "GREEN":
                return GREEN;
            case "BLACK":
                return BLACK;
            default:
                return RESET;
        }
    }
    
    // Shows statistics of all numbers shown in all spins
    static void showStatistics(HashMap<Integer, Integer> stats, int totalBets) {
        System.out.println("\n======================");
        System.out.println("=====STATISTICS=====");
        System.out.println("======================");
        System.out.printf("Total plays: %d\n\n", totalBets);
        System.out.println("Money total : " + money);
        System.out.println("Most frequent numbers: ");
        stats.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue())) // Ordenar por frecuencia
            .limit(5) // Top 5
            .forEach(entry -> {
                double percentage = (entry.getValue() * 100.0) / totalBets;
                System.out.printf("  Number %d: %d times (%.1f%%)\n", 
                    entry.getKey(), entry.getValue(), percentage);
            });
    }
    
    // Simple "spinning animation"
    static void spinningAnimation() {
        System.out.print("\nGirando");
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(300);
                System.out.print(".");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Line break printer
    static void printLinebreak(int linebreak) {
        for (int i = 0; i < linebreak; i++) {
            System.out.println();
        }
    }
    
    // Generate a random number between the range
    static int randomNumber(int MIN, int MAX) {
        return (int) (Math.random() * (MAX - MIN)) + MIN;
    }

    static void rouletteMenu() {
        System.out.println("========================");
        System.out.println("====Casino roulette=====");
        System.out.println("========================");
        System.out.println("Numbers from 0 to 36");
        System.out.println("------------------------");
    }
}

