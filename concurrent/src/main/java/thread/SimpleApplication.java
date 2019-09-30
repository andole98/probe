package thread;

public class SimpleApplication {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> System.out.println("Hello from new thread"));

        thread.start();
        Thread.yield();
        System.out.println("Hello from main thread");
        thread.join();
    }
}
