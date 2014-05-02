package com.smokejumperit.guice.concurrent.threadFactory;

import java.util.Objects;

public enum ThreadPriority {
	HIGH("High", Thread.MAX_PRIORITY),
	MEDIUM("Medium", Thread.NORM_PRIORITY),
	LOW("Low", Thread.MIN_PRIORITY);

	private final String humanValue;
	private final int priority;

	private ThreadPriority(String humanValue, int priority) {
		Objects.requireNonNull(humanValue,
				"Human value for the thread priority");
		if (priority < Thread.MIN_PRIORITY) {
			throw new IllegalArgumentException("Priority (" + priority
					+ ") is lower than minimum priority (" + Thread.MIN_PRIORITY + ")");
		} else if (priority > Thread.MAX_PRIORITY) {
			throw new IllegalArgumentException("Priority (" + priority
					+ ") is greater than maximum priority (" + Thread.MAX_PRIORITY + ")");
		}
		this.humanValue = humanValue;
		this.priority = priority;
	}

	public String getHumanString() {
		return humanValue;
	}

	public int asInt() {
		return priority;
	}
}
