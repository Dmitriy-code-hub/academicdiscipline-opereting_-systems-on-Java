
import java.util.concurrent.locks.ReentrantLock;
import java.util.LinkedList;
import java.util.Queue;


    public class Barbershop {

        public static void main(String[] args) {

            int n = 3;
            Kreslo[] kreslo = new Kreslo[n];
            for (int i =0; i<n; i++)
            {
                kreslo[i] = new Kreslo(i);
            }
            //1 брадобрей
            BarberThread b = new BarberThread();
            Thread t1 = new Thread(b);
            t1.setName("Barber ");
            t1.start();
            //несколько клиентов
            for (int i = 1; i < 6; i++) {

                Thread t = new Thread(new KlientThread(b, kreslo, "Klient " + i));
                t.setName("Thread " + i);
                t.start();
                //немного подождать
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }


    class BarberThread implements Runnable {


        //CommonResource res;
        ReentrantLock lock;
        Boolean is_sleep;
        //Kreslo kreslo;
        Queue<Kreslo> ochered = new LinkedList<>();

        BarberThread() {
            //this.res = res;
            lock = new ReentrantLock(); // создаем заглушку;
            is_sleep = true;//zasnul//sosdaldalsa spyazim
            // kreslo = null;
        }

        public void razbudit(Kreslo k)
        {
            if (is_sleep) {
                is_sleep = false;
                System.out.printf("%s разбудил Barber \n", k.kt.myName);
            }
            //kreslo = k;
            ochered.add(k);//FIFO
        }

        public void run() {
            //int rabotal = 0;
            //while (rabotal<20) {
            for (int i = 1; i < 25; i++) {
                if (is_sleep) {
                    System.out.printf("Barber спит \n");
                } else {
                    Kreslo kreslo = ochered.poll();
                    if (kreslo==null) continue;
                    System.out.printf("Barber бреет %s \n", kreslo.kt.myName);
                    kreslo.kt.porbrit();
                    //rabotal++;
                    if ((!lock.hasQueuedThreads()) && ochered.isEmpty() )//hasQueuedThreads - vozvrazaet istinu esli est potoki otkritiya blokiratorapozvolyart proverit
                    {//esli sidyat ludi on ne zasipaet
                        //в очереди никого - засыпает
                        is_sleep = true;
                    }
                }
                //другие клиенты могут занять места
                try {
                    Thread.sleep(110);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    class Kreslo
    {
        ReentrantLock lock;
        KlientThread kt ;
       int nomer;
        Kreslo(int nomer)
        {
           this.nomer = nomer;
            lock = new ReentrantLock();
            kt = null;
        }
        Boolean zanyat(KlientThread kt)
        {
            if (lock.tryLock())
            {
                this.kt = kt;
                return true;
            }
            return false;
        }

        void osvobodit()
        {
            try {
                lock.unlock();
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        @Override
        public String toString()
        {
         return "kreslo "+nomer;
          //  return "kreslo";
        }
    }

    class KlientThread implements Runnable {

        BarberThread br;
        Kreslo[] kreslo;
        Boolean is_inside;
        String myName;
        int zanyato;

        KlientThread(BarberThread br, Kreslo[] kreslo, String name) {
            this.br = br;
            this.kreslo = kreslo;
            is_inside = false;
            myName = name;
            zanyato = -1;
        }

        public void run() {
            for (int i = 1; i < 3; i++) {
                //пытается занять место
                while (!is_inside) {
                    for (int j = 0; j < kreslo.length; j++)
                    {
                        if(kreslo[j].zanyat(this))
                        {
                            is_inside = true;
                            zanyato = j;
                            System.out.printf("%s клиент занял место \n", myName);
                            br.lock.lock();
                            br.razbudit(kreslo[j]);
                            br.lock.unlock();

                            break;
                        }
                    }
                    if (!is_inside) {
                        System.out.printf("%s клиент не занял место и ушел \n", myName);
                        try {
                            Thread.sleep(210);
                        } catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }

                //ждет в очереди
                while (is_inside) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
                kreslo[zanyato].osvobodit();

                try {
                    Thread.sleep(1100);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                //System.out.printf("  %s клиент завершен проход цикла номер %d \n", myName, i);
            }
        }

        public void pobrit() {
            System.out.printf("%s клиент освободил место \n", myName);
            is_inside = false;
        }

    }
