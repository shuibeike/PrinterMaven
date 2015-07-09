package com.ericsson.edeehhk.printermaven.monitor;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import com.ericsson.edeehhk.printermaven.device.Device;
import com.ericsson.edeehhk.printermaven.device.DeviceController;

public class Monitor implements Runnable {
	private LinkedList<Device> devices;
	private String filename;
	
	public Monitor(LinkedList<Device> devices, String filename) {
		super();
		this.devices = devices;
		this.filename = filename;
	}

	public void run() {
		while(true) {
			try {
				Thread.sleep(30*1000);
			}catch(Exception e) {
				System.out.println("sleep occur wrong!");
			}
			
			synchronized(devices) {
				scan();
			}
		}
	}

	private void scan() {
//		System.out.println("----------");
		File file = new File(filename);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException("Create file occur wrong!");
			}
		}
		
		DeviceController.monitor(devices, file);
	}
	
}
