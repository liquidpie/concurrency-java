package com.vivek.concurrentutil;

import java.util.concurrent.atomic.*;

public class AtomicUtils {
	
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public static void main(String[] args) {
		/**
		 * AtomicBoolean
		 */
		AtomicBoolean bool = new AtomicBoolean(); // default initial value is false
		AtomicBoolean bool2 = new AtomicBoolean(true); // initial value is set to true
		
		boolean value = bool.get();
		bool2.set(false);
		
		boolean oldValue = bool.getAndSet(true); // returns the AtomicBoolean's current value, and sets a new value for it
		
		boolean expected = true;
		boolean newValue = false;
		
		boolean wasNewValueSet = bool.compareAndSet(expected, newValue);
		
		/**
		 * AtomicInteger
		 * 
		 * AtomicLong has same methods as AtomicInteger
		 */
		AtomicInteger atomicInteger = new AtomicInteger(); // initial value 0
		AtomicInteger atomicInteger2 = new AtomicInteger(123);
		
		int theValue = atomicInteger.get();
		atomicInteger.set(234);
		
		int expectedValue = 123;
		int newValue2      = 234;
		atomicInteger.compareAndSet(expectedValue, newValue2);
		
		System.out.println(atomicInteger.getAndAdd(10));
		System.out.println(atomicInteger.addAndGet(10));
		
		/**
		 * AtomicReference
		 */
		AtomicReference atomicRef = new AtomicReference();
		
		String initialReference = "the initially referenced string";
		AtomicReference atomicReference = new AtomicReference(initialReference);
		
		AtomicReference<String> atomicStringReference =
		    new AtomicReference<String>(initialReference);
		
		String reference = (String) atomicReference.get();
		String reference2 = atomicStringReference.get();
		
		atomicReference.set("New object referenced");
		
		/**
		 * AtomicStampedReference
		 */
		/*
		 The AtomicStampedReference is different from the AtomicReference in that the AtomicStampedReference keeps both an object reference and a stamp internally. The reference and stamp can be swapped using a single atomic compare-and-swap operation, via the compareAndSet() method.
		 The AtomicStampedReference is designed to be able to solve the A-B-A problem which is not possible to solve with an AtomicReference alone.
		 
		 The AtomicStampedReference is designed to solve the A-B-A problem. The A-B-A problem is when a reference is changed from pointing to A, then to B and then back to A.

		 When using compare-and-swap operations to change a reference atomically, and making sure that only one thread can change the reference from an old reference to a new, detecting the A-B-A situation is impossible.
		 
		 Thread 1 can copy the reference and stamp out of the AtomicStampedReference atomically using get(). If another thread changes the reference from A to B and then back to A, then the stamp will have changed 
		 */
		
		Object initialRef   = null;
		int    initialStamp = 0;

		AtomicStampedReference atomicStampedReference =
		    new AtomicStampedReference(initialRef, initialStamp);
		
		String initialRef1   = null;
		int    initialStamp1 = 0;

		AtomicStampedReference<String> atomicStampedStringReference =
		    new AtomicStampedReference<String>(
		        initialRef1, initialStamp1);
		
		String reference1 = (String) atomicStampedReference.getReference();
		int stamp = atomicStampedReference.getStamp();
		
		// You can obtain both reference and stamp from an AtomicStampedReference in a single, atomic operation using the get() method. 
		// The get() method returns the reference as return value from the method. 
		// The stamp is inserted into an int[] array that is passed as parameter to the get() method.
		int[] stampHolder = new int[1];
		Object ref = atomicStampedReference.get(stampHolder);

		String newRef = "New object referenced";
		int    newStamp = 1;

		atomicStampedReference.set(newRef, newStamp);
		

		boolean exchanged = atomicStampedStringReference
		    .compareAndSet(initialRef1, newRef, initialStamp, newStamp);
		 
		/**
		 * AtomicIntegerArray
		 * 
		 * AtomicLongArray & AtomicReferenceArray are same as AtomicIntegerArray
		 */
		AtomicIntegerArray array = new AtomicIntegerArray(10); // 10 being the capacity

		int[] ints = new int[10];
		ints[5] = 123;
		AtomicIntegerArray array1 = new AtomicIntegerArray(ints); // ints the integer array as input
		
		int value1 = array.get(5);
		array.set(5, 999);
		boolean swapped = array.compareAndSet(5, 999, 123);
		
		int newValue1 = array.addAndGet(5, 3);
		int oldValue1 = array.getAndAdd(5, 3);
		
		int newValue4 = array.incrementAndGet(5);
		int oldValue2 = array.getAndIncrement(5);
		
		int newValue3 = array.decrementAndGet(5);
		int oldValue3 = array.getAndDecrement(5);
		
		
	}

}
