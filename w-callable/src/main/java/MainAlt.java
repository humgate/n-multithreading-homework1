import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Реализация запуска потоков методом invokeAny возвращающего результата первой успешно выполненной задачи и
 * отменяющего остальные
 */
public class MainAlt {
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

        List<Callable<Integer>> tasklist = new ArrayList<>();
        tasklist.add(myCallable1);
        tasklist.add(myCallable2);
        tasklist.add(myCallable3);
        tasklist.add(myCallable4);

        Integer result;

        try {
            result = threadPool.invokeAny(tasklist);
            System.out.println("Один из потоков завершился с результатом: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        threadPool.shutdown();
    }

}
