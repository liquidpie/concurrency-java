package com.vivek.threading;

import java.util.concurrent.Semaphore;

/**
 * Created by VJaiswal on 14/05/18.
 */
public class RunThreadsInOrderUsingSemaphore {

    private static Semaphore semA = new Semaphore(0);
    private static Semaphore semB = new Semaphore(0);
    private static Semaphore semC = new Semaphore(1);

    static class Foo {

        /* If funcA is called, a new thread will be created and
           the corresponding action will be executed. */
        void funcA() {
            Runnable action = () -> {
                try {
                    semC.acquire(1);
                    Thread.sleep(4000);
                } catch (InterruptedException ignored) {}
                System.out.println("Running method funcA");
                semA.release(1);
            };

            execute(action);
        }

        /* If funcB is called, a new thread will be created and
           the corresponding action will be executed. */
        void funcB() throws InterruptedException {

            Runnable action = () -> {
                try {
                    semA.acquire(1);

                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
                System.out.println("Running method funcB");
                semB.release(1);
            };

            execute(action);
        }

        /* If funcC is called, a new thread will be created and
           the corresponding action will be executed. */
        void funcC() throws InterruptedException {

            Runnable action = () -> {
                try {
                    semB.acquire(1);
                } catch (InterruptedException ignored) {}
                System.out.println("Running method funcC");
                semC.release(1);
            };

            execute(action);
        }

        private void execute(Runnable action) {
            Thread thread = new Thread(action);
            thread.start();
        }

    }

    public static void main(String[] args) throws Exception {
        Foo foo = new Foo();

        foo.funcA();
        foo.funcB();
        foo.funcC();

        foo.funcA();
        foo.funcB();
        foo.funcC();

        foo.funcA();
        foo.funcB();
        foo.funcC();
    }

}
