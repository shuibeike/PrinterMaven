package com.ericsson.edeehhk.printermaven.main;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.ericsson.edeehhk.printermaven.device.Device;
import com.ericsson.edeehhk.printermaven.dispatch.Dispatch;
import com.ericsson.edeehhk.printermaven.monitor.Monitor;
import com.ericsson.edeehhk.printermaven.postgre.OpenJPATools;
import com.ericsson.edeehhk.printermaven.task.Task;
import com.ericsson.edeehhk.printermaven.task.TaskController;

public class Main {
	public static void main(String[] args) {
		LinkedList<Device> devices = new LinkedList<Device>();
//		ConnPostgre.load(devices);
		OpenJPATools openJPATools = new OpenJPATools();
		openJPATools.load(devices);        
		
        for(Device d : devices) {
        	System.out.println(d.getName()+":"+d.getSpeed()+":"+d.getStatus()+":"+d.getOldtask().size());
        }
		LinkedList<Task> tasks = new LinkedList<Task>();
		Thread thread = new Thread(new Dispatch(devices, tasks));
		thread.start();
		String filename = "monitor.txt";
		File file = new File(filename);
		file.deleteOnExit();
		System.out.println(file.exists());

		thread = new Thread(new Monitor(devices, filename));
//		thread.start();
		
		System.out.println("let's begin!");
		begin();
		Scanner scanner = new Scanner(System.in);
		
		label:
		while(scanner.hasNext()) {
			try{
				int option = Integer.parseInt(scanner.next());

				if(option == 1) {
					System.out.println("Please input device's info - name, speed: ");
					String name = scanner.next();
					int speed = scanner.nextInt();
					Device device = new Device(name, speed);
					for(Device d : devices) {
						if(d.getName().equals(device.getName())) {
							System.out.println("A same printer device is runing!\nPleasse input again!");
							continue label;
						}
					}
					
					openJPATools.save(device);
//					ConnPostgre.save(device);
					
					devices.add(device);
				}else if(option == 2) {
					System.out.println("Task would be random generated!");
					Task task = TaskController.getTask();
					task.setStarttime(System.currentTimeMillis());
					tasks.add(task);
				}
				
				begin();
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("Option input must be a number!");
			}

		}
		
		openJPATools.close();
		scanner.close();
	}

	public static void begin() {
		System.out.println("1. If you want add printer device, please input 1!");
		System.out.println("2. If you want add printer task, please input 2!");
	}

}
