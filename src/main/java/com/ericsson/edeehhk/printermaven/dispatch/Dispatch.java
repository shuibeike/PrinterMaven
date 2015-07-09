package com.ericsson.edeehhk.printermaven.dispatch;

import java.util.Collections;
import java.util.LinkedList;

import com.ericsson.edeehhk.printermaven.device.Device;
import com.ericsson.edeehhk.printermaven.device.DeviceController;
import com.ericsson.edeehhk.printermaven.postgre.OpenJPATools;
import com.ericsson.edeehhk.printermaven.task.Task;
import com.ericsson.edeehhk.printermaven.task.TaskController;

public class Dispatch implements Runnable {
	private LinkedList<Device> devices;
	private LinkedList<Task> tasks;
	
	public Dispatch(LinkedList<Device> devices, LinkedList<Task> tasks) {
		super();
		this.devices = devices;
		this.tasks = tasks;
	}

	public void run() {
		while(true) {
			synchronized(devices) {
				dispatch();
			}
		}
	}
	
	public void dispatch() {
//		System.out.println("---------------------------");
		if(!devices.isEmpty() && !tasks.isEmpty()) {
			Collections.sort(devices);
			TaskController.updateWaittime(tasks);
			Collections.sort(tasks);
			for(Device d : devices) {
				if(tasks.isEmpty()) {
					break;
				}
				
				if(d.getStatus().equals("busy")){
//					System.out.println(d.getName()+" "+d.getStatus());
					Task t = d.getOldtask().getLast();
					int processtime = (int)(System.currentTimeMillis()-t.getStarttime()-t.getWaittime());
					if(processtime >= t.getProcesstime()*1000) {
//						System.out.println(processtime+":"+t.getProcesstime());
						d.setStatus("idle");
						t.setStatus(true);
						t.setDeviceId(d.getId());
						new OpenJPATools().save(t);
					}
				}

				if(d.getStatus().equals("idle")) {
					Task t = tasks.pollFirst();
//					System.out.println(d.getName()+" "+t.getPages()+" "+tasks.size());
					DeviceController.print(d, t);
				}
			}
		}
		
	}

}
