package com.ericsson.edeehhk.printermaven.task;

import java.util.Random;
import java.util.UUID;

public class TaskFactory {
	public static Task getTask() {
		String name = UUID.randomUUID().toString().replace("-", "");
		Random random = new Random(System.currentTimeMillis());
		int pages = random.nextInt(100);
		return new Task(name, pages);
	}
	
}
