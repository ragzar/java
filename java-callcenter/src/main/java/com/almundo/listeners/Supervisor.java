package com.almundo.listeners;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.almundo.model.Caller;

public class Supervisor implements EmployeeListener {

	private boolean onCall = false;
	private Integer priority = 20;
	private String name;

	public Supervisor(String name) {
		this.name = "Supervisor " + name;
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
	public int compareTo(EmployeeListener o) {
		return priority.compareTo(o.getPriority());
	}

	@Override
	public Integer getPriority() {
		return priority;
	}

	@Override
	public String getName() {
		return name;
	}

	public static List<EmployeeListener> of(String... names) {
		return Arrays.stream(names).map(Supervisor::new).collect(Collectors.toList());
	}

}