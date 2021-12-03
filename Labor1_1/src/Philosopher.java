
import java.util.Random;

public class Philosopher extends Thread {
    private int place;
    private Fork left, right;
    int eatCount;

    public Philosopher(int place, Fork left, Fork right) {
        this.place = place;
        this.left = left;
        this.right = right;
        eatCount = 0;
    }

    @Override
    public String toString() {
        return "Philosopher "  + place;
    }

    @Override
    public void run() {

        while(eatCount<1) {
            if (right.take()) {System.out.println(toString() + " took right");
            if (left.take()) {
                System.out.println(toString() + " took left");
                System.out.println(toString() + " eating");
                eatCount++;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                right.putOn();
                System.out.println(toString() + " put right");
                left.putOn();
                System.out.println(toString() + " put left");
            } else {right.putOn();
                System.out.println(toString() + " put right");

            }
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}

