public class BankAccount {
    private int balance;
    
    // Constructor/builder - starts with initial money
    public BankAccount(int startingMoney) {
        this.balance = startingMoney;
    }
    
    // Add money to account (when player wins)
    public void deposit(int amount) {
        balance += amount;  // Add amount to balance
    }
    
    // Remove money from account (when player loses)
    public boolean withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;       // Check if they have enough money    
            return true;            // Can afford it
        } else {            
            return false;           // Can't afford it
        }
    }
    
    // Get current balance
    public int getBalance() {
        return balance; // Return the balance
    }
    
    // Check if player can afford a bet
    public boolean canAfford(int amount) {
        if  (amount > balance) {
            System.out.println("Chill bro, you're trying to bet more money than you have, don't be silly");
            return false;
        }
        return true;
    }
}