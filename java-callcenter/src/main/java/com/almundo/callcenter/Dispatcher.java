package com.almundo.callcenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almundo.listeners.Director;
import com.almundo.listeners.EmployeeListener;
import com.almundo.listeners.Operator;
import com.almundo.listeners.Supervisor;
import com.almundo.model.Caller;

public class Dispatcher {

	private static Dispatcher instance;
	private BlockingQueue<Caller> eventQueue;
	private AtomicInteger calls = new AtomicInteger(0);
	private final CallProcessor processor = new CallProcessor();
	private List<EmployeeListener> employees = new ArrayList<>();
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private static final Logger LOG = LoggerFactory.getLogger(Dispatcher.class);

	// Private constructor
	private Dispatcher() {
	}

	// Singleton Instance of dispatcher
	public static Dispatcher getInstance() {
		if (instance == null) {
			instance = new Dispatcher();
		}
		return instance;
	}
	
	// Creating Queue to manage the calls, employee list, and queue monitor 
	private void initialize() {

		if (eventQueue == null) {
			LOG.info("INIT DISPATCHER");
			employees.addAll(Operator.of("A", "B", "C", "D", "E"));
			employees.addAll(Supervisor.of("A", "B", "C"));
			employees.addAll(Director.of("A", "B"));
			Collections.sort(employees);
			eventQueue = new LinkedBlockingQueue<Caller>();
			processor.start();
		}
	}

	
	// Dispatch call to employees
	public void dispatchCall(Caller caller) {
		try {
			readWriteLock.writeLock().lock();
			initialize();
			eventQueue.put(caller);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	//Current count of received calls
	public int processedCalls() {
		return calls.get();
	}

	//cleaning the dispatcher
	public void exit() {
		processor.run = false;
		calls = new AtomicInteger(0);
	}

	//Add employee to list of listeners
	public void registerEmployee(EmployeeListener employee) {
		readWriteLock.writeLock().lock();
		this.employees.add(employee);
		Collections.sort(employees);
		readWriteLock.writeLock().unlock();
	}

	
	//  Class to make a continuous monitoring of the queue of calls
	private class CallProcessor extends Thread {
		private boolean run = true;

		@Override
		public void run() {
			while (run || !eventQueue.isEmpty()) {
				if (!eventQueue.isEmpty()) {
					readWriteLock.readLock().lock();
					for (EmployeeListener employee : employees) {
						if (!employee.isOnCall() && !eventQueue.isEmpty()) {
							try {
								employee.recieveCall(eventQueue.take());
								calls.incrementAndGet();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					readWriteLock.readLock().unlock();
				}
			}
			employees = new ArrayList<>();
			eventQueue = null;
			EmployeeListener.pool.shutdown();
		}
	}
}
