package smth;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank( new Repository() );
        for (int i = 0; i < 40; i++) {
            new Client(bank);
        }
    }
}
