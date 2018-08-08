package com.almundo.callcenter;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.almundo.model.Caller;
import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;

@RunWith(ConcurrentTestRunner.class)
public class DispatcherTest {

	Dispatcher dispatcher;
	AtomicInteger caller = new AtomicInteger(0);

	@Before
	public void init() {
		dispatcher = Dispatcher.getInstance();
	}

	@After
	public void countCalls() {
		assertEquals(dispatcher.processedCalls(), caller.get());
	}

	@Test
	@ThreadCount(10)
	public void dispatCallTest() throws InterruptedException {
		for (int i = 0; i < 5; i++) {
			dispatcher.dispatchCall(new Caller("" + caller.incrementAndGet()));
		}
		TimeUnit.MINUTES.sleep(1);
	}

}