public class Main {
    //группа
    static ThreadGroup mainGroup = new ThreadGroup("main group");

    //сами потоки
    static final MyThread thread1 = new MyThread(mainGroup, "трэд1");
    static final MyThread thread2 = new MyThread(mainGroup, "трэд2");
    static final MyThread thread3 = new MyThread(mainGroup, "трэд3");
    static final MyThread thread4 = new MyThread(mainGroup, "трэд4");

    public static void main(String[] args) {

        //запускаем
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            //ждем 15 сек
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //посылаем всем тредам группы задачу остановиться
            mainGroup.interrupt();
        }
    }
}
