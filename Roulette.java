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
    public static final String ROJO = "\u001B[31m";      // Texto rojo
    public static final String VERDE = "\u001B[32m";     // Texto verde
    public static final String NEGRO = "\u001B[30m";     // Texto negro
    public static final String FONDO_BLANCO = "\u001B[47m"; // Fondo blanco para negro
    public static final String NEGRITA = "\u001B[1m";    // Texto en negrita
    public static int money = 10000;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // HashMap para guardar estadísticas de cuántas veces salió cada número
        HashMap<Integer, Integer> estadisticas = new HashMap<>();
        
        // ArrayList con el historial de las últimas tiradas
        ArrayList<Ruleta> historial = new ArrayList<>();
        
        printLinebreak(10);
        ruletaMenu();
        
        boolean continuar = true;
        boolean repetir = false;
        String respuesta_apuesta;
        int apuesta;
        while (continuar) {
            
            System.out.println("\n//////////////////");
            System.out.println("---NUEVA TIRADA---");
            do {
            System.out.print("¿Cuánto dinero apuestas? (0 para salir): ");
            System.out.printf("Tienes %s euros \n", money);
            apuesta = scanner.nextInt();

            if (apuesta == 0) {
                continuar = false;
                break;
            } else if (apuesta > money) {
                repetir = true;
                System.out.println("Calma bro, no tienes tanto dinero.");
                System.out.print("¿Cuánto dinero apuestas? (0 para salir): ");
                System.out.printf("Tienes %s euros \n", money);
                apuesta = scanner.nextInt();
                } else {
                    repetir = false;
                    break;
                }
            } while (repetir);
            try {
                System.out.print("¿A qué número apuestas? (0-36 / N para ninguno): ");
                respuesta_apuesta = scanner.next();
                System.out.print("¿A qué color apuestas? (Rojo-Negro): ");      
                respuesta_apuesta = scanner.nextLine();          
            // Validar que el número esté en el rango correcto
            if (0 > Integer.parseInt(respuesta_apuesta) || 30 < Integer.parseInt(respuesta_apuesta)) {

                System.out.println("¡Número inválido! Debe ser entre 0 y 36.");
                continue;
            }   else if (respuesta_apuesta.equalsIgnoreCase("N"))  {
                
            }   

            } catch (NumberFormatException e) {
                }
           
            respuesta_apuesta = scanner.nextLine();

            // GIRO DE LA RULETA - genera número aleatorio entre 1 y 30
            int numeroGanador = randomNumber(1, 31); // 31 es exclusivo, da 1-30
            String colorGanador = obtenerColor(numeroGanador);
            
            // Crear objeto Ruleta con el resultado
            Ruleta resultado = new Ruleta(colorGanador, numeroGanador);
            historial.add(resultado);
            
            // Actualizar estadísticas e incrementar contador del número que salió
            estadisticas.put(numeroGanador, estadisticas.getOrDefault(numeroGanador, 0) + 1);
            
            // Mostrar resultado con animación
            animarGiro();
            printLinebreak(3);
            System.out.println("\n ¡La bola va ha caer en...!");
            printLinebreak(1);
            
            // Mostrar número con color
            imprimirNumeroConColor(resultado.getNumber(), resultado.getColor());
            printLinebreak(1);
            
            // Verificar si ganó y restar o sumar la ganancia al dinero total
            if (respuesta_apuesta.equals(String.valueOf(numeroGanador)) || respuesta_apuesta.equalsIgnoreCase(colorGanador)) {
                int ganancia = apuesta * 35; // En ruleta se paga 35 veces lo que se apuesta
                System.out.println("¡¡¡GANASTE!!!");
                System.out.printf("Apostaste: %d | Ganas: %d\n", apuesta, ganancia);
                money += ganancia;
            } else{
                System.out.println("No acertaste. Pierdes tu apuesta.");
                money = money - apuesta;
            }
            // Verificar si el dinero no es negativo
            if (money < 0) {
                money = 0;
            }
            // Mostrar historial de últimas 5 tiradas
            mostrarHistorial(historial);
            
            // Preguntar si quiere ver estadísticas
            System.out.print("\n¿Ver estadísticas? (s/n): ");
            String respuesta = scanner.next().toLowerCase();
            if (respuesta.equalsIgnoreCase("s")) {
                mostrarEstadisticas(estadisticas, historial.size());
                printLinebreak(3);
            } else {
                printLinebreak(12);
                ruletaMenu();
            }
            
        }
        
        System.out.println("\n¡Gracias por jugar!");
        scanner.close();
    }
    
    // Determina el color según el número de la ruleta real de un casino
    static String obtenerColor(int numero) {
        if (numero == 0) {
            return "VERDE"; // El 0 es verde en la ruleta del casino
        }
        
        // Números rojos en una ruleta
        int[] rojos = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 32, 34, 36};
        
        for (int rojo : rojos) {
            if (numero == rojo) {
                return "ROJO";
            }
        }
        
        return "NEGRO"; // Si no es rojo , será el color negro lokete
    }
    
    // Muestra las últimas tiradas con los colores de cada número
    static void mostrarHistorial(ArrayList<Ruleta> historial) {
        int cantidad = Math.min(5, historial.size()); // Últimas 5 o menos
        
        if (cantidad == 0) return;
        
        System.out.println("\n/////////////////////");
        System.out.println("---ÚLTIMAS TIRADAS---");
        System.out.println("/////////////////////\n");
        for (int i = historial.size() - 1; i >= historial.size() - cantidad; i--) {
            Ruleta r = historial.get(i);
            
            // Imprimir cada número con su color
            String colorCode = obtenerCodigoColor(r.getColor());
            if (r.getColor().equals("NEGRO")) {
                // Negro necesita fondo blanco para verse
                System.out.print(FONDO_BLANCO + NEGRO + NEGRITA + r.getNumber() + RESET + " ");
            } else {
                System.out.print(colorCode + NEGRITA + r.getNumber() + RESET + " ");
            }
        }
        System.out.println();
    }
    
    // Función para imprimir el número ganador con color grande
    static void imprimirNumeroConColor(int numero, String color) {
        String colorCode = obtenerCodigoColor(color);
        
        System.out.print("   Número: ");
        
        if (color.equals("NEGRO")) {
            // Negro con fondo blanco para que se vea bien
            System.out.print(FONDO_BLANCO + NEGRO + NEGRITA + numero + RESET);
        } else {
            System.out.print(colorCode + NEGRITA + numero + RESET);
        }
        
        System.out.println(" (" + colorCode + color + RESET + ")");
    }
    
    // Devuelve el código ANSI según el color que lleva
    static String obtenerCodigoColor(String color) {
        switch (color) {
            case "ROJO":
                return ROJO;
            case "VERDE":
                return VERDE;
            case "NEGRO":
                return NEGRO;
            default:
                return RESET;
        }
    }
    
    // Muestra estadísticas de todos los números que han salido durante las apuestas
    static void mostrarEstadisticas(HashMap<Integer, Integer> stats, int totalTiradas) {
        System.out.println("\n==================");
        System.out.println("===ESTADISTÍCAS===");
        System.out.println("==================");
        System.out.printf("Total de tiradas: %d\n\n", totalTiradas);
        System.out.println("Dinero total: " + money);
        System.out.println("Números más frecuentes:");
        stats.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue())) // Ordenar por frecuencia
            .limit(5) // Top 5
            .forEach(entry -> {
                double porcentaje = (entry.getValue() * 100.0) / totalTiradas;
                System.out.printf("  Número %d: %d veces (%.1f%%)\n", 
                    entry.getKey(), entry.getValue(), porcentaje);
            });
    }
    
    // Animación simple del giro de ruleta
    static void animarGiro() {
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

    static void ruletaMenu() {
        System.out.println("========================");
        System.out.println("====RULETA DE CASINO====");
        System.out.println("========================");
        System.out.println("Números del 0 al 36");
        System.out.println("------------------------");
    }
}

