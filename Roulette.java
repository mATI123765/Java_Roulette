import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


// Create a new class called "Roulette" with the color ansi codes and the money variable
    public class Roulette {
        // Ansi code colors so it's shown in the console 
        public static final String RESET = "\u001B[0m";      // Reset color
        public static final String RED = "\u001B[31m";      // RED text
        public static final String GREEN = "\u001B[32m";     // GREEN text
        public static final String BLACK = "\u001B[30m";     // BLACK text
        public static final String WHITE_BACKGROUND = "\u001B[47m"; // WHITE BACKGROUND for BLACK
        public static final String BOLD = "\u001B[1m";    // Text in BOLD
        private static BankAccount account;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputValidator validator = new InputValidator(scanner);
        rouletteWheel wheel = new rouletteWheel(0);

        // HashMap that saves de statics of how many times a number appears
        HashMap<Integer, Integer> statistics = new HashMap<>();
        
        // ArrayList with the history of the last rolls
        ArrayList<ruleta> history = new ArrayList<>();

        boolean shouldContinue = true;

        account = new BankAccount(10000);
    do {
        rouletteMenu(); 
        int betAmount = getBetAmount(scanner, account);
        if (betAmount == -1) {
            shouldContinue = false;
            break;
        }
        String bet = String.valueOf(betAmount);
        Bet playerBet = new Bet(
            betAmount,
            validator.getNumberBet(),
            validator.getColorBet(),
            validator.getRowBet(),
            validator.get12thBet(),
            validator.get18thBet(),
            validator.getevenOddBet()
        );


            // ROULETTE SPIN - randomly generated number between 1 and 36
            int winningNumber = wheel.spin();
            String winningColor = wheel.getColor(winningNumber);
            
            // Create object "Ruleta" with the result
            ruleta result = new ruleta(winningColor, winningNumber);
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
            boolean wonNumber = playerBet.hasBetOnNumber() && playerBet.getNumber() == winningNumber;
            boolean wonColor = playerBet.hasBetOnColor() && playerBet.getColor().equals(winningColor);      
            boolean wonRow = playerBet.hasBetOnRow() && checkRow(playerBet.getRow(), winningNumber);
            boolean won12th = playerBet.hasBetOnTwelfth() && check12th(playerBet.getTwelfth(), winningNumber);
            boolean won18th = playerBet.hasBetOnEighteenth() && check18th(playerBet.getEighteenth(), winningNumber);
            boolean wonEvenOdd = playerBet.hasBetOnEvenOdd() && checkEvenOdd(playerBet.getEvenOdd(), winningNumber);

            int winnings = calculateWinnings(betAmount, wonNumber, won12th, won18th, wonColor, wonRow, wonEvenOdd);

            if (winnings > 0) {
                System.out.println("¡¡¡YOU WON!!!");
                System.out.println("You bet : "+ bet + " you win: " + winnings);
                account.deposit(winnings);
            } else {
                System.out.println(" You lost your bet.");
                account.withdraw(betAmount);
            }
            // Verify if the money is negative
            if (account.getBalance() <= 0) {
                System.out.println("Bro you ran out of money! You're broke");
                shouldContinue = false;
            }
            // Show history of last 5 spins
            showHistory(history);
            
            // Ask if the user wants to see the statistics (stats)
            System.out.print("\n Check stats? (y/n): ");
            scanner.nextLine();
            String answer = scanner.nextLine().toLowerCase();
            if (answer.equalsIgnoreCase("y")) {
                showStatistics(statistics, history.size());
                printLinebreak(3);
            } else {
                printLinebreak(5);
                rouletteMenu();
            }
    } while(shouldContinue && account.getBalance() > 0);
    scanner.close();
    }
    // Check wether the 18th choice that user made coincides with the winning number
    static boolean check18th(int eighteenthChoice, int winningNumber) {
        if (winningNumber == 0 || eighteenthChoice == 0) return false;
        
        if (eighteenthChoice == 1) {
            return winningNumber >= 1 && winningNumber <= 18;
        } else if (eighteenthChoice == 2) {
            return winningNumber >= 19 && winningNumber <= 36;
        }
        return false;
    }
    // Check wether the row choice that user made coincides with the winning number
    static boolean checkRow(int rowChoice, int winningNumber) {
        if (winningNumber == 0 || rowChoice == 0) return false; // 0 isn't in any row  

        if (rowChoice == 1) {
            return winningNumber % 3 == 1;
        } else if (rowChoice == 2) {
            return winningNumber % 3 == 2;
        } else if (rowChoice == 3) {
            return winningNumber % 3 == 0;
            }
        return false;
        }   
    // checks if winningNumber is between the first, second or third 12th depending on what the user's choice was
    static boolean check12th(int twelfthChoice, int winningNumber) {
    if (winningNumber == 0 || twelfthChoice == 0) return false;
    
    if (twelfthChoice == 1) {
        return winningNumber >= 1 && winningNumber <= 12;
    } else if (twelfthChoice == 2) {
        return winningNumber >= 13 && winningNumber <= 24;
    } else if (twelfthChoice == 3) {
        return winningNumber >= 25 && winningNumber <= 36;
    }
    return false;
}
    static boolean checkEvenOdd(int evenOddChoice, int winningNumber) {
        if (winningNumber == 0 || evenOddChoice == 0) return false;
        
        if (evenOddChoice == 1) { // Even
            return winningNumber % 2 == 0;
        } else if (evenOddChoice == 2) { // Odd
            return winningNumber % 2 != 0;
        }
        return false;
    }
    
    // Show the last 5 spins with the colors 
    static void showHistory(ArrayList<ruleta> history) {
        int quantity = Math.min(5, history.size()); // Last 5 or less
        
        if (quantity == 0) return;
        
        System.out.println("\n/////////////////////");
        System.out.println("------LAST  PLAYS------");
        System.out.println("/////////////////////\n");
        for (int i = history.size() - 1; i >= history.size() - quantity; i--) {
            ruleta r = history.get(i);
            
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
        
        System.out.print("   Number: ");
        
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
        System.out.println("");
        System.out.println("Most frequent numbers: ");
        stats.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue())) // Order by frequency
            .limit(5) // Top 5
            .forEach(entry -> { 
                double percentage = (entry.getValue() * 100.0) / totalBets;
                System.out.printf("  Number %d: %d times (%.1f%%)\n", 
                    entry.getKey(), entry.getValue(), percentage);
            });
    }
    // Simple "spinning animation"
    static void spinningAnimation() {
        System.out.print("\nSpinning");
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


    static void rouletteMenu () {
        System.out.println("  ____    _    ____ ___ _   _  ___  ");
        System.out.println(" / ___|  / \\  / ___|_ _| \\ | |/ _ \\ ");
        System.out.println("| |     / _ \\ \\___ \\| ||  \\| | | | |");
        System.out.println("| |___ / ___ \\ ___) | || |\\  | |_| |");
        System.out.println(" \\____/_/   \\_\\____/___|_| \\_|\\___/ ");
        System.out.println(" ____   ___  _   _ _     _____ _____ _____ _____ ");
        System.out.println("|  _ \\ / _ \\| | | | |   | ____|_   _|_   _| ____|");
        System.out.println("| |_) | | | | | | | |   |  _|   | |   | | |  _|  ");
        System.out.println("|  _ <| |_| | |_| | |___| |___  | |   | | | |___ ");
        System.out.println("|_| \\_\\\\___/ \\___/|_____|_____| |_|   |_| |_____|");
    }
   static int getBetAmount(Scanner scanner, BankAccount account) {
    while (true) {
        System.out.print("How much money do you want to bet? (all / [number]) (0 to leave): ");
        System.out.printf("You have %s pounds\n", account.getBalance());
        String bet = scanner.nextLine().trim();
        
        if (bet.equalsIgnoreCase("all")) {
            return account.getBalance();
        }
            try {
                int betAmount = Integer.parseInt(bet);
                if (betAmount == 0) {
                    return -1; // Signal to exit
                } else if (betAmount > account.getBalance()) {
                    System.out.println("Chill bro, you ain't got that much money.");
                } else if (betAmount < 0) {
                    System.out.println("Bet amount must be positive.");
                } else {
                    return betAmount;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid bet amount. Please enter a number or 'all'.");
            }
        }
    }
    static int calculateWinnings(int betAmount, boolean wonNumber, boolean won12th, boolean won18th, boolean wonColor, boolean wonRow, boolean wonEvenOdd) {
        int totalWinnings = 0;
         if (wonNumber) {
            totalWinnings += betAmount * 35;
        }
        if (wonColor) {
            totalWinnings += betAmount * 2;
        }
        if (wonRow) {
            totalWinnings += betAmount * 3;
        }
        if (won12th) {
            totalWinnings += betAmount * 3;
        }
        if (won18th) {
            totalWinnings += betAmount * 2;
        }
        if (wonEvenOdd) {
            totalWinnings += betAmount * 2;
        }
        return totalWinnings;
    }
}