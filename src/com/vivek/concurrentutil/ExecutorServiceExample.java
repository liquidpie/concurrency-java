package com.vivek.concurrentutil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class ExecutorServiceExample {

	/*
	 * ExecutorService interface represents an asynchronous execution mechanism which is capable of executing tasks in the background.
	 */
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		executorService.execute(new Runnable() {
		    public void run() {
		        System.out.println("Asynchronous task");
		    }
		});

		executorService.shutdown();
		
		
		// Creating an ExecutorService
		ExecutorService executorService1 = Executors.newSingleThreadExecutor();
		ExecutorService executorService2 = Executors.newFixedThreadPool(10);
		ExecutorService executorService3 = Executors.newScheduledThreadPool(10);
		
		// There are a few different ways to delegate tasks for execution to an ExecutorService:
		// 		execute(Runnable)
		//		submit(Runnable)
		//		submit(Callable)
		//		invokeAny(...)
		// 		invokeAll(...)
		
		/**
		 * execute(Runnable)
		 * 
		 * There is no way of obtaining the result of the executed Runnable, if necessary.
		 */
		executorService = Executors.newSingleThreadExecutor();

		executorService.execute(new Runnable() {
		    public void run() {
		        System.out.println("Asynchronous task");
		    }
		});

		executorService.shutdown();
		
		/**
		 * submit(Runnable)
		 */
		Future future = executorService.submit(new Runnable() {
		    public void run() {
		        System.out.println("Asynchronous task");
		    }
		});

		try {
			future.get(); //returns null if the task has finished correctly.
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		/**
		 * submit(Callable)
		 */
		Future future1 = executorService.submit(new Callable(){
		    public Object call() throws Exception {
		        System.out.println("Asynchronous Callable");
		        return "Callable Result";
		    }
		});

		try {
			System.out.println("future.get() = " + future1.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		/**
		 * invokeAny()
		 * 
		 * The invokeAny() method takes a collection of Callable objects, or subinterfaces of Callable. 
		 * Invoking this method does not return a Future, but returns the result of one of the Callable objects. 
		 * You have no guarantee about which of the Callable's results you get. Just one of the ones that finish.
		 * If one of the tasks complete (or throws an exception), the rest of the Callable's are cancelled.
		 */
		ExecutorService singleExecutor1 = Executors.newSingleThreadExecutor();
		Set<Callable<String>> callables = new HashSet<Callable<String>>();
		callables.add(new Callable<String>() {
			public String call() throws Exception {
				return "Task 1";
			}
		});
		
		callables.add(new Callable<String>() {
			public String call() throws Exception {
				return "Task 2";
			}
		});
		
		callables.add(new Callable<String>() {
			public String call() throws Exception {
				return "Task 3";
			}
		});
		
		try {
			System.out.println(singleExecutor1.invokeAny(callables));
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		/**
		 * invokeAll()
		 * 
		 * The invokeAll() method invokes all of the Callable objects you pass to it in the collection passed as parameter. 
		 * The invokeAll() returns a list of Future objects via which you can obtain the results of the executions of each Callable.
		 */
		ExecutorService singleExecutor2 = Executors.newSingleThreadExecutor();
		List<Future<String>> futures;
		
		try {
			futures = singleExecutor2.invokeAll(callables);
			for(Future<String> future11 : futures){
			    System.out.println("future.get = " + future11.get());
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		
		// When you are done using the ExecutorService you should shut it down, so the threads do not keep running.
		// if your application is started via a main() method and your main thread exits your application, 
		// the application will keep running if you have an active ExexutorService in your application. 
		// The active threads inside this ExecutorService prevents the JVM from shutting down.
		singleExecutor2.shutdown();
	}

}
