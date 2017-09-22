package com.vivek.concurrentutil;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentMapImplementations {

	/**
	 * ConcurrentMap interface represents a Map which is capable of handling concurrent access (puts and gets) to it.
	 * 
	 * ConcurrentHashMap is the only implementation of ConcurrentMap
	 * 
	 * ConcurrentHashMap does not lock the Map while you are reading from it. 
	 * Additionally, ConcurrentHashMap does not lock the entire Map when writing to it. 
	 * It only locks the part of the Map that is being written to, internally.
	 * 
	 * Another difference is that ConcurrentHashMap does not throw ConcurrentModificationException 
	 * if the ConcurrentHashMap is changed while being iterated. The Iterator is not designed to be used by more than one thread though.
	 */
	public static void main(String[] args) {

		ConcurrentMap concurrentMap = new ConcurrentHashMap();

		concurrentMap.put("key", "value");
		Object value = concurrentMap.get("key");
		
	}

}
