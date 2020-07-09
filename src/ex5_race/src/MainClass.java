// Домашнее задание
// Организуем гонки
// Есть набор правил:
// Все участники должны стартовать одновременно, несмотря на то что на подготовку у каждого уходит разное время
// В туннель не может заехать одновременно больше половины участников(условность)
// Попробуйте все это синхронизировать
// Как только первая машина пересекает финиш, необходимо ее объявить победителем(победитель
// должен быть только один, и вообще должен быть)
// Только после того как все завершат гонку нужно выдать объявление об окончании
// Можете корректировать классы(в т.ч. конструктор машин)
// и добавлять объекты классов из пакета util.concurrent

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static final CyclicBarrier cb = new CyclicBarrier(CARS_COUNT+1);
    public static final CountDownLatch cdl = new CountDownLatch(CARS_COUNT);
    public static ReentrantLock lock = new ReentrantLock();


    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(new Semaphore(CARS_COUNT/2)), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        ExecutorService threads = Executors.newFixedThreadPool(CARS_COUNT);

        for (int i = 0; i < cars.length; i++) {
            threads.execute(() -> new Car(race, 20 + (int) (Math.random() * 10), cb, cdl, lock).run());
        }

        cb.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

        cdl.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");

        threads.shutdown();
    }
}
