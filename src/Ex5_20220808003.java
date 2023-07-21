import java.util.ArrayList;
import java.util.List;

public class Ex5_20220808003 {
    public static void main(String[] args) {
    }
}
class Account{
    private String accountNumber;
    private double balance;
    public Account(String accountNumber, double balance)throws InsufficientFundsException{
        if (balance < 0) {
            throw new InsufficientFundsException(balance);
        }
this.accountNumber = accountNumber;
this.balance = balance;
    }
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }
    public void deposit(double amount)throws InvalidTransactionException{
        if (amount < 0){
            throw new InvalidTransactionException(amount);
        }
        balance += amount;
    }
    public void withdraw(double amount) throws InvalidTransactionException, InsufficientFundsException {
        if (amount < 0) {
            throw new InvalidTransactionException(amount);
        }
        if (balance < amount) {
            throw new InsufficientFundsException(balance, amount);
        }
        balance -= amount;
    }
    public String toString(){
        return "Account: " + accountNumber + ", Balance: " + balance;
    }
}

class Customer{
    private String name;
    private List<Account> accounts;
    public Customer (String name){
        this.name = name;
        accounts = new ArrayList<>();
    }
    private Account getAccount(String accountNumber)throws AccountNotFoundException{
        for (Account account : accounts){
            if(account.getAccountNumber().equals(accountNumber)){
                return account;
            }
        }
        throw new AccountNotFoundException(accountNumber);
    }
    public void addAccount(Account account) throws AccountAlreadyExistsException {
        try {
            getAccount(account.getAccountNumber());
            throw new AccountAlreadyExistsException(account.getAccountNumber());
        } catch (AccountNotFoundException e) {
            accounts.add(account);
        }finally {
            System.out.println(this);
        }
            System.out.println("Added account: " + account.getAccountNumber() + " with " + account.getBalance());
        }
    public void removeAccount(String accountNumber) throws AccountNotFoundException {
        Account account = getAccount(accountNumber);
        accounts.remove(account);
    }
    public void transfer(String fromAccount, String toAccount, double amount) throws InvalidTransactionException {
        try {
            Account from = getAccount(fromAccount);
            Account to = getAccount(toAccount);
            from.withdraw(amount);
            to.deposit(amount);
        } catch (InvalidTransactionException e) {
            throw new InvalidTransactionException(e, "cannot transfer funds from account " + fromAccount + " to account " + toAccount);
        }
    }
    public String toString(){
        String output = "Customer " + name + ":\n";
        for (Account account : accounts) {
            output += "\t" + account.toString() + "\n";
        }
        return output;
    }
}

class InsufficientFundsException extends RuntimeException{
    private double amount;
    private double balance;
    public InsufficientFundsException(double balance) {
        super("Wrong balance: " + balance);
    }
    public InsufficientFundsException(double amount, double balance){
        super("Required amount is " + amount + " but only " + balance + " remaining");
    }
}

class AccountAlreadyExistsException extends RuntimeException{
    public AccountAlreadyExistsException(String accountNumber){
        super("Account number " + accountNumber + " already exists");
    }
}
class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String accountNumber){
        super("Account number " + accountNumber + " already exists");
    }
}

class InvalidTransactionException extends Exception{
    public InvalidTransactionException(double amount){
        super("Invalid amount: " + amount);
    }
    public InvalidTransactionException(InvalidTransactionException e, String message) {
        super(message + ":\n\t" + e.getMessage());
    }
}