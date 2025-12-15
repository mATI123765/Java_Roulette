import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidator {
    private Scanner scanner;
    
    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }
    
    public int getNumberBet() {
        while (true) {
            System.out.print("What number will you bet on? (0-36 / N for none): ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("N")) {
                return -1; // -1 means the user didn't bet for this
            }
            
            try {
                int number = Integer.parseInt(input);
                if (number >= 0 && number <= 36) {
                    return number;
                }
                System.out.println("Invalid number! Must be between 0 and 36.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a number or 'N'.");
            }
        }
    }
    
    public String getColorBet() {
        while (true) {
            System.out.print("What color will you bet on? (RED, BLACK / 0 for none): ");
            String input = scanner.nextLine().trim().toUpperCase();
            
            if (input.equals("0")) {
                return "";
            } else if (input.equals("RED") || input.equals("BLACK")) {
                return input;
            }
            System.out.println("Invalid color! Choose RED, BLACK, or 0.");
        }
    }
    
    public int getRowBet() {
        while (true) {
            System.out.print("What row will you bet on? (1, 2, 3 / 0 for none): ");
            String input = scanner.nextLine().trim();
            
            try {
                int row = Integer.parseInt(input);
                if (row >= 0 && row <= 3) {
                    return row;
                }
                System.out.println("You can only choose row 0, 1, 2, or 3.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a number 0-3.");
            }
        }
    }
    public int get12thBet() {
        while (true) {
            System.out.print("What 12th will you bet on? (1, 2, 3 / 0 for none):");
            String input = scanner.nextLine().trim();

              try {
                int twelve = Integer.parseInt(input);
                if (twelve >= 0 && twelve <= 3) {
                    return twelve;
                }
                System.out.println("You can only choose 12th 1, 2 or 3");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a number 0-3.");
            }
        }
    }
    public int get18thBet() {
        while (true) {
            System.out.print("What 18th will you bet on? (1, 2 / 0 for none):");
            String input = scanner.nextLine().trim();

              try {
                int eighteen = Integer.parseInt(input);
                if (eighteen >= 0 && eighteen <= 2) {
                    return eighteen;
                }
                System.out.println("You can only choose 18th 1 or 2");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a number 1-2.");
            }
        }
    }
    public int getevenOddBet() {
        while (true) {
            System.out.print("Will you bet even or odd? (Even, Odd / 0 for none):");
            String input = scanner.nextLine().trim().toUpperCase();

              try {
                int evenOdd = Integer.parseInt(input);
                if (evenOdd >= 0 && evenOdd <= 2) {
                    return evenOdd;
                }
                System.out.println("You can only choose even, odd or none(0)");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Enter a number 0-2.");
            }
        }
    }
}