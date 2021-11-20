import java.util.concurrent.Callable;

public class MyCallable implements Callable<Integer> {
    //no magic numbers
    final int SLEEP_TIMEOUT = 2500; //таймаут между выводами потоком сообщений
    final int MSGS_COUNT = 4; //Кол-во сообщений

    @Override
    public Integer call() {
        long threadID = Thread.currentThread().getId();
        String threadName = Thread.currentThread().getName();
        int msgsWritten = 0;

        try {
            while(!Thread.currentThread().isInterrupted() && msgsWritten != MSGS_COUNT) {
                Thread.sleep(SLEEP_TIMEOUT);
                System.out.println("Всем привет! Это тред (с имя:id) " + threadName + ":" + threadID);
                msgsWritten++;
            }
        } catch (InterruptedException err) {
            System.out.println(
                    "тред (с имя:id) " + threadName + ":" + threadID + " был interrupted пока находился в sleep\n");
        }
        System.out.println(
                "тред (с имя:id) " + threadName + ":" + threadID + " завершен\n");
        return msgsWritten;
    }
}
