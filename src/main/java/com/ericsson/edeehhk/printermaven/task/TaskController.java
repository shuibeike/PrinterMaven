package com.ericsson.edeehhk.printermaven.task;

import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

public class TaskController {
	public static Task getTask() {
		String name = UUID.randomUUID().toString().replace("-", "");
		Random random = new Random(System.currentTimeMillis());
		int pages = random.nextInt(100);
		return new Task(name, pages);
	}
	
	public static void updateWaittime(Task t) {
		t.setWaittime((int)Math.ceil((double)(System.currentTimeMillis()
				-t.getStarttime())/1000));
	}
	
	public static void updateWaittime(LinkedList<Task> tasks) {
		for(Task t : tasks) {
			updateWaittime(t);
		}
	}
}
