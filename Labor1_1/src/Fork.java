import java.util.concurrent.*;

public class Fork {
    private Semaphore sem = new Semaphore(1);

    public boolean take () {

    if (sem.tryAcquire()) {
        return true;
            }

    return false;
    }

    public void putOn () {
        sem.release(); // Дает доступ
    }
}