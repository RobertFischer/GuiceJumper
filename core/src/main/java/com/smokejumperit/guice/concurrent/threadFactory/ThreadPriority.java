package com.smokejumperit.guice.concurrent.threadFactory;

import com.google.common.base.Strings;

public enum ThreadPriority {
	HIGH("High", Thread.MAX_PRIORITY), MEDIUM("Medium", Thread.NORM_PRIORITY), LOW("Low",
			Thread.MIN_PRIORITY);

	private final String humanValue;
	private final int priority;

	private ThreadPriority(String humanValue, int priority) {
		// Validate humanValue
		if (Strings.isNullOrEmpty(humanValue)) {
			throw new IllegalArgumentException("Need a value for the human value, got: " + humanValue);
		}

		// Validate priority
		if (priority < Thread.MIN_PRIORITY) {
			throw new IllegalArgumentException("Priority (" + priority
					+ ") is lower than minimum priority (" + Thread.MIN_PRIORITY + ")");
		} else if (priority > Thread.MAX_PRIORITY) {
			throw new IllegalArgumentException("Priority (" + priority
					+ ") is greater than maximum priority (" + Thread.MAX_PRIORITY + ")");
		}

		// Assign values
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
