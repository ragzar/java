package com.almundo.listeners;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almundo.callcenter.Dispatcher;
import com.almundo.model.Caller;

public interface EmployeeListener extends Comparable<EmployeeListener> {

	ExecutorService pool = Executors.newFixedThreadPool(10);

	AtomicInteger processed = new AtomicInteger(0);
	Logger LOGGER = LoggerFactory.getLogger(Dispatcher.class);

	void recieveCall(Caller caller);

	public default Runnable task(Caller caller) {
		return () -> {

			Instant start = Instant.now();
			int duration = ThreadLocalRandom.current().nextInt(5, 11);
			try {
				TimeUnit.SECONDS.sleep(duration);
			} catch (InterruptedException e) {
				LOGGER.error(e.getMessage(), e);
			} finally {
				LOGGER.info(getName() + "\tStart " + start + "\tEnd " + Instant.now() + "\tCaller " + caller.getId()
						+ "\tDuration" + "" + duration);
				setOnCall(false);
			}
		};
	}

	boolean isOnCall();

	void setOnCall(boolean status);

	String getName();

	Integer getPriority();
}
