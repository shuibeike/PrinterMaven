package com.ericsson.edeehhk.printermaven.junit;

import junit.framework.TestCase;

import com.ericsson.edeehhk.printermaven.device.Device;
import com.ericsson.edeehhk.printermaven.task.Task;
import com.ericsson.edeehhk.printermaven.task.TaskFactory;

public class DeviceTest extends TestCase {
	private TaskFactory taskFactory;
	
	protected void setUp() throws Exception {
		super.setUp();
		taskFactory = new TaskFactory();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		taskFactory = null;
	}
	
	public void testPrint() {
		Device d = new Device("d1", 5);
		Task t = TaskFactory.getTask();
//		System.out.println(t);
		d.print(t);
		Object expecteds = t;
		Object actuals = d.getOldtask().getLast();
		assertEquals(expecteds, actuals);
	}

}
