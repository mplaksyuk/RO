package smth;


import java.util.UUID;


public class Client {
    private UUID uid;
    public UUID getUid() {
        return uid;
    }

    private Bank bank;
    public Bank getBank() {
        return bank;
    }

    private Creator creator;

    public Client(Bank bank) {
        this.bank = bank;
        uid = UUID.randomUUID();
        creator = new Creator(this);
        new Thread( creator ).start();
    }

    @Override
    public String toString() {
        return "Client{" +
                "uid=" + uid +
                '}';
    }
}