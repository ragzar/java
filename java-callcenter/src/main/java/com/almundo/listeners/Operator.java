package com.almundo.listeners;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.almundo.model.Caller;

public class Operator implements EmployeeListener {

	private String name;
	private Integer priority = 10;
	private boolean onCall = false;

	public Operator(String name) {
		this.name = "Operator " + name;
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
	public String getName() {
		return name;
	}

	@Override
	public int compareTo(EmployeeListener o) {
		return priority.compareTo(o.getPriority());
	}

	public static List<EmployeeListener> of(String... names) {
		return Arrays.stream(names).map(Operator::new).collect(Collectors.toList());
	}

}
