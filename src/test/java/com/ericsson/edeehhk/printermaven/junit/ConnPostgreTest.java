package com.ericsson.edeehhk.printermaven.junit;

import java.util.LinkedList;

import junit.framework.TestCase;

import com.ericsson.edeehhk.printermaven.device.Device;
import com.ericsson.edeehhk.printermaven.postgre.ConnPostgre;

public class ConnPostgreTest extends TestCase {
	private Device d;
	private String name;
	private int speed;
	private LinkedList<Device> devices;
	
	public void setUp() throws Exception {
		name = "test";
		speed = 6;
		d = new Device(name, speed);
		devices = new LinkedList<Device>();
	}

	public void tearDown() throws Exception {
		name = null;
		speed = -1;
		d = null;
	}

	public void testSave() {
		ConnPostgre.save(d);
		ConnPostgre.load(devices);
		assertFalse(devices.contains(d));
	}

}
