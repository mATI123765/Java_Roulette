import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Crear una clase nueva llamada ruleta para guardarla en el ArrayList
class Ruleta {
    private String color;
    private int number;

    // Crear el constructor ruleta
    public Ruleta(String color, int number) {
        this.color = color;
        this.number = number;
    }

    public String getColor() { // Método para leer los datos del color de cada número
        return color;
    }

    public int getNumber() { // Método para leer los datos de los números que salgan
        return number;
    }
}

public class Roulette {
    // Tener los colores en código ANSI para que salga en cada número en la consola
    public static final String RESET = "\u001B[0m";      // Resetear color
    public static final String RED = "\u001B[31m";      // Texto RED
    public static final String GREEN = "\u001B[32m";     // Texto GREEN
    public static final String BLACK = "\u001B[30m";     // Texto BLACK
    public static final String WHITE_BACKGROUND = "\u001B[47m"; // Fondo blanco para BLACK
    public static final String BOLD = "\u001B[1m";    // Texto en BOLD
    public static int money = 10000;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // HashMap para guardar estadísticas de cuántas veces salió cada número
        HashMap<Integer, Integer> statistics = new HashMap<>();
        
        // ArrayList con el history de las últimas tiradas
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
            System.out.print("¿Cuánto dinero bets? (0 para salir): ");
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
            // Validar que el número esté en el rango correcto
            if (0 > Integer.parseInt(user_bet) || 30 < Integer.parseInt(user_bet)) {

                System.out.println("Invalid number! It must be between 0 and 36.");
                continue;
            }   else if (user_bet.equalsIgnoreCase("N"))  {
                
            }   

            } catch (NumberFormatException e) {
                }
           
            user_bet = scanner.nextLine();

            // GIRO DE LA RULETA - genera número aleatorio entre 1 y 30
            int winningNumber = randomNumber(1, 31); // 31 es exclusivo, da 1-30
            String winningColor = getColor(winningNumber);
            
            // Crear objeto Ruleta con el result
            Ruleta result = new Ruleta(winningColor, winningNumber);
            history.add(result);
            
            // Actualizar estadísticas e incrementar contador del número que salió
            statistics.put(winningNumber, statistics.getOrDefault(winningNumber, 0) + 1);
            
            // Mostrar result con animación
            spinningAnimation();
            printLinebreak(3);
            System.out.println("\n The ball is spinning...!");
            printLinebreak(1);
            
            // Mostrar número con color
            printNumberColor(result.getNumber(), result.getColor());
            printLinebreak(1);
            
            // Verificar si ganó y restar o sumar la earnings al dinero total
            if (user_bet.equals(String.valueOf(winningNumber)) || user_bet.equalsIgnoreCase(winningColor)) {
                int earnings = bet * 35; // En ruleta se paga 35 veces lo que se bet
                System.out.println("¡¡¡YOU WON!!!");
                System.out.printf("You bet: %d | you win: %d\n", bet, earnings);
                money += earnings;
            } else{
                System.out.println(" You lost your bet.");
                money = money - bet;
            }
            // Verificar si el dinero no es negativo
            if (money < 0) {
                money = 0;
            }
            // Mostrar history de últimas 5 tiradas
            showHistory(history);
            
            // Preguntar si quiere ver estadísticas
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
    
    // Determina el color según el número de la ruleta real de un casino
    static String getColor(int number) {
        if (number == 0) {
            return "GREEN"; // El 0 es GREEN en la ruleta del casino
        }
        
        // Números REDs en una ruleta
        int[] REDs = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 32, 34, 36};
        
        for (int RED : REDs) {
            if (number == RED) {
                return "RED";
            }
        }
        
        return "BLACK"; // Si no es RED , será el color BLACK lokete
    }
    
    // Muestra las últimas tiradas con los colores de cada número
    static void showHistory(ArrayList<Ruleta> history) {
        int quantity = Math.min(5, history.size()); // Últimas 5 o menos
        
        if (quantity == 0) return;
        
        System.out.println("\n/////////////////////");
        System.out.println("------LAST  PLAYS------");
        System.out.println("/////////////////////\n");
        for (int i = history.size() - 1; i >= history.size() - quantity; i--) {
            Ruleta r = history.get(i);
            
            // Imprimir cada número con su color
            String colorCode = getColorCode(r.getColor());
            if (r.getColor().equals("BLACK")) {
                // BLACK necesita fondo blanco para verse
                System.out.print(WHITE_BACKGROUND + BLACK + BOLD + r.getNumber() + RESET + " ");
            } else {
                System.out.print(colorCode + BOLD + r.getNumber() + RESET + " ");
            }
        }
        System.out.println();
    }
    
    // Función para imprimir el número ganador con color grande
    static void printNumberColor(int number, String color) {
        String colorCode = getColorCode(color);
        
        System.out.print("   Número: ");
        
        if (color.equals("BLACK")) {
            // BLACK con fondo blanco para que se vea bien
            System.out.print(WHITE_BACKGROUND + BLACK + BOLD + number + RESET);
        } else {
            System.out.print(colorCode + BOLD + number + RESET);
        }
        
        System.out.println(" (" + colorCode + color + RESET + ")");
    }
    
    // Devuelve el código ANSI según el color que lleva
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
    
    // Muestra estadísticas de todos los números que han salido durante las bets
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
    
    // Animación simple del giro de ruleta
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
    
    // Imprimir punto y aparte
    static void printLinebreak(int linebreak) {
        for (int i = 0; i < linebreak; i++) {
            System.out.println();
        }
    }
    
    // Genera número aleatorio entre MIN y MAX
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

