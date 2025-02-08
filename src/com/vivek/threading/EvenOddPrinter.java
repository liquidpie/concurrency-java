package com.vivek.threading;

public class EvenOddPrinter {

    private final Object lock = new Object();
    private static final int MAX = 10;
    private boolean evenTurn = false;

    public static void main(String[] args) throws Exception {
        EvenOddPrinter printer = new EvenOddPrinter();
        Thread oddThread = new Thread(printer::printOdd);
        Thread evenThread = new Thread(printer::printEven);

        oddThread.start();
        evenThread.start();
    }

    public void printEven() {
        for (int i = 2; i <= MAX; i += 2) {
            synchronized(lock) {
                while (!evenTurn) {
                    try {
                        lock.wait();
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                System.out.println("Even: " + i);
                evenTurn = false;
                lock.notify();
            }
        }
    }

    public void printOdd() {
        for (int i = 1; i <= MAX; i += 2) {
            synchronized(lock) {
                while (evenTurn) {
                    try {
                        lock.wait();
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                System.out.println("Odd: " + i);
                evenTurn = true;
                lock.notify();
            }
        }
    }

}
