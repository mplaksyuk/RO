public class Main {
    public static void main(String[] args){
        Recruits recruits = new Recruits(100);
        recruits.printRecruits();

        ThreadManager manager = new ThreadManager(recruits, 50);
    }
}