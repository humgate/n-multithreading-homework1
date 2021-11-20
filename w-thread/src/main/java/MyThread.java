public class MyThread extends Thread {
    //no magic numbers
    final int SLEEP_TIMEOUT = 2500;

    @Override
    public void run() {
        try {
            while(!isInterrupted()) {
                Thread.sleep(SLEEP_TIMEOUT);
                System.out.println("Всем привет! Это тред (с имя:id) " + getName() + ":" + getId());
            }
        } catch (InterruptedException err) {
            System.out.println(
                    "тред (с имя:id) " + getName() + ":" + getId() + " был interrupted пока находился в sleep\n");
        }
        System.out.println(
                "тред (с имя:id) " + getName() + ":" + getId() + " завершен\n");
    }

    public MyThread (ThreadGroup threadGroup, String threadName) {
       super(threadGroup, threadName);
    }
}
