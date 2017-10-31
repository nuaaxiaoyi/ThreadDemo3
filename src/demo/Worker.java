package demo;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Worker {
	
	private Random random = new Random();
	
	private Object lock1 = new Object();
	private Object lock2 = new Object();
	
	private List<Integer> list1 = new LinkedList<>();
	private List<Integer> list2 = new LinkedList<>();
	
	// the synchronized here will lock the whole object
	public synchronized void stageOne() {
		
		// the sync here will lock on different object, 
		// so two thread can enter the method in the same time
		// but if one of the thread is running the sync block, others should wait
		// untill the lock was released
/*		synchronized (lock1) { */
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			list1.add(random.nextInt(100));
	//	}
	}
	
	public synchronized void stageTwo() {
		
	/*	synchronized (lock2) { */
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			list2.add(random.nextInt(100));
		//}
	}
	
	public void process() {
		
		for(int i = 0; i < 1000; i++){
			stageOne();
			stageTwo();
		}
		
		
	}
	public void main() {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
			
		Thread t1 = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				process();
			}
			
		});

		
		Thread t2 = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				process();
			}
			
		});
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		long end = System.currentTimeMillis();
		System.out.println("Time take: " + (end - start) + " ms");
		System.out.println("list1: " + list1.size() + ", " + "list2: " + list2.size());
	}

}
