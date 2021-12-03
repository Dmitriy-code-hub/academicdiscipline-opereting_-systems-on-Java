import java.util.concurrent.*;
public class Eat {
    private Fork[] forks;
    private Philosopher[] philosophers;

    public Eat (int n) {
        forks = new Fork[n];
        for (int i = 0; i < n; i++) {
            forks[i] = new Fork();
        }
        philosophers = new Philosopher[n];
        for (int i = 0; i < n-1; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1)]);
        }
        //posledniy filosof
        philosophers[n-1] = new Philosopher(n-1, forks[n-1], forks[0]);
        for (Philosopher p : philosophers) {
            p.start();
        }
    }

    public static void main(String[] args) {
        new Eat(5);
    }
}