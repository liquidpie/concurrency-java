package com.vivek.threading;

import java.util.concurrent.FutureTask;

/**
 * Created by VJaiswal on 29/01/17.
 */
public class FutureTaskPreloader {

    private final FutureTask<ProductInfo> future = new FutureTask<ProductInfo>(
            () -> {return getProduct();}
    );

    private final Thread t = new Thread(future);

    public void start() {
        t.start();
    }

    public ProductInfo get() throws Exception {
        try {
            return future.get();
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof  Error) {
                System.out.println("Its an error");
            } else if (cause instanceof RuntimeException) {
                System.out.println("Its a runtime error");
            } else {
                System.out.println("Some exception");
            }
            throw e;
        }
    }

    private static ProductInfo getProduct() {
        return new ProductInfo("Macbook", 234567L, 565.5f);
    }

    static class ProductInfo {
        private String name;
        private long id;
        private float price;

        public ProductInfo(String name, long id, float price) {
            this.name = name;
            this.id = id;
            this.price = price;
        }

        public String toString() {
            return "[ name : {" + name + "}, id : {" + id + "}, price : {" + price + "} ]";
        }
    }

    public static void main(String... args) throws Exception {
        FutureTaskPreloader loader = new FutureTaskPreloader();
        loader.start();
        System.out.println(loader.get());
    }
}
