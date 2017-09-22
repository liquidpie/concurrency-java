package com.vivek.threading;

/**
 * Created by VJaiswal on 29/03/17.
 */
public class MethodAccessCheckForTwoThreads {

    synchronized static void methodA() {
        System.out.println(Thread.currentThread().getName() + "executing");
        System.out.println("In method A");

        for (int i = 0; i < 100; i++) {
            System.out.print(i + ", ");
        }
        System.out.println();

        System.out.println("Exiting A");
    }

    synchronized void methodB() {
        System.out.println(Thread.currentThread().getName() + "executing");
        System.out.println("In method B");

        for (int i = 0; i < 100; i++) {
            System.out.print(i + ", ");
        }
        System.out.println();

        System.out.println("Exiting B");
    }

    public static void main(String[] args) {
        MethodAccessCheckForTwoThreads o = new MethodAccessCheckForTwoThreads();

        Runnable r1 = new RunnableA();
        Runnable r2 = new RunnableB(o);

        Thread t1 = new Thread(r1, "t1");
        Thread t2 = new Thread(r2, "t2");

        Thread t11 = new Thread(r1, "t11");
        Thread t21 = new Thread(r2, "t21");

        t1.start();
        t11.start();
//        t2.start();
//        t21.start();
    }

    static class RunnableA implements Runnable {
        @Override
        public void run() {
            methodA();
        }
    }

    static class RunnableB implements Runnable {
        MethodAccessCheckForTwoThreads o;

        public RunnableB(MethodAccessCheckForTwoThreads o) {
            this.o = o;
        }

        @Override
        public void run() {
            o.methodB();
        }
    }
}
