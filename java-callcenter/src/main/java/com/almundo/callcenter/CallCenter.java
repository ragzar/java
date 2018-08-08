package com.almundo.callcenter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.almundo.listeners.Operator;
import com.almundo.model.Caller;

public class CallCenter {

	public static void main(String[] args) throws InterruptedException {

		Dispatcher dispatcher = Dispatcher.getInstance();

		AtomicInteger id = new AtomicInteger(0);
		
		Runnable task = () -> {
			String name  = Thread.currentThread().getName() + " " ;
			dispatcher.dispatchCall(new Caller(name + id.incrementAndGet()));
			dispatcher.dispatchCall(new Caller(name + id.incrementAndGet()));
			dispatcher.dispatchCall(new Caller(name + id.incrementAndGet()));
			dispatcher.dispatchCall(new Caller(name + id.incrementAndGet()));
			dispatcher.dispatchCall(new Caller(name + id.incrementAndGet()));
			dispatcher.dispatchCall(new Caller(name + id.incrementAndGet()));
			dispatcher.dispatchCall(new Caller(name + id.incrementAndGet()));
			dispatcher.dispatchCall(new Caller(name + id.incrementAndGet()));
			dispatcher.dispatchCall(new Caller(name + id.incrementAndGet()));
			dispatcher.dispatchCall(new Caller(name + id.incrementAndGet()));
		};
		Thread thread = new Thread(task);
		thread.start();

		thread = new Thread(task);
		thread.start();

		TimeUnit.SECONDS.sleep(5);
		dispatcher.registerEmployee(new Operator("LEO"));

		thread = new Thread(task);
		thread.start();
		dispatcher.exit();
	}
}
