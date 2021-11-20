import java.util.concurrent.*;

/**
 * Реализация запуска потоков методом submit() и получения результатов методом get()
 * как с, так и без и использования Future.isDone()
 */
public class Main {
    static int threadCounter = 0; //для использования в формировании инкремента в имени потоков

    public static void main(String[] args) {
        // Создаем согласно условиям задачи 4 задачи с результатом типа Integer
        Callable<Integer> myCallable1 = new MyCallable();
        Callable<Integer> myCallable2 = new MyCallable();
        Callable<Integer> myCallable3 = new MyCallable();
        Callable<Integer> myCallable4 = new MyCallable();

        /* По условиям задания требуется создавать потоки задавая каждому из них свое им имя. Поэтому создаем пул
        * потоков размера равного кол-ву доступных процессоров и с использованием кастомной фабрики ThreadFactory,
        * которую передаем в метод newFixedThreadPool в виде лямбды, имплементирующей "выдачу" потокам при их
        * создании простого имени вида "тред1" "тред2" итд
         */
        final ExecutorService threadPool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors(),r -> new Thread(r, "трэд" + ++threadCounter)
                );

        // Отправляем первую задачу на выполнение в пул потоков используя Future
        final Future<Integer> task1 = threadPool.submit(myCallable1);

        //просим дать нам результат треда1, трэд main заблокируется пока трэд1 не вернет результат
        try {
             System.out.println("Первый поток напечатал " + task1.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // Отправляем вторую задачу на выполнение в пул потоков используя Future
        final Future<Integer> task2 = threadPool.submit(myCallable2);

        // Отправляем третью задачу на выполнение в пул потоков используя Future
        final Future<Integer> task3 = threadPool.submit(myCallable3);

        //подождем 15 сек
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*
         * Нет ли результата треда2? Если мы не хотим блокировать наш трэд main, то сначала мы проверим,
         * не завершился ли тред2. И только если он завершился, запросим результат
         */
        if (task2.isDone()) try {
            System.out.println("Второй поток напечатал " + task2.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // Отправляем четвертую задачу на выполнение в пул потоков используя Future
        final Future<Integer> task4 = threadPool.submit(myCallable4);

        //подождем 15 сек
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Нет ли результата треда4?
        if (task4.isDone()) try {
            System.out.println("Четвертый поток напечатал " + task2.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        //Нет ли результата треда3?
        if (task3.isDone()) try {
            System.out.println("Третий поток напечатал " + task2.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        threadPool.shutdown();
    }
}
