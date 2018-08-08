package com.almundo.listeners;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.almundo.model.Caller;

public class Director implements EmployeeListener {

	private boolean onCall = false;
	private Integer priority = 30;
	private String name;

	public Director(String name) {
		this.name = "Director " + name;
	}

	@Override
	public void recieveCall(Caller caller) {
		onCall = true;
		Thread thread = new Thread(task(caller));
		pool.execute(thread);
	}

	@Override
	public boolean isOnCall() {
		return onCall;
	}

	@Override
	public void setOnCall(boolean onCall) {
		this.onCall = onCall;
	}

	@Override
	public Integer getPriority() {
		return priority;
	}

	@Override
	public int compareTo(EmployeeListener o) {
		return priority.compareTo(o.getPriority());
	}

	@Override
	public String getName() {
		return name;
	}

	public static List<EmployeeListener> of(String... names) {
		return Arrays.stream(names).map(Director::new).collect(Collectors.toList());
	}

}
