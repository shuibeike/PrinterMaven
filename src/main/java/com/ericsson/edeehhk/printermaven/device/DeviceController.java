package com.ericsson.edeehhk.printermaven.device;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import com.ericsson.edeehhk.printermaven.postgre.OpenJPATools;
import com.ericsson.edeehhk.printermaven.task.Task;
import com.ericsson.edeehhk.printermaven.task.TaskController;

public class DeviceController {
	public static void monitor(LinkedList<Device> devices, File file) {
		for(Device d : devices) {
			updateOldtask(d);
			try {
				printlog(d, file);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("scan wrong!");
			}
		}
	}
	
	public static void print(Device device, Task task) {
		TaskController.updateWaittime(task);
//		System.out.println("Device "+name+" is process task "+task.getName());
		int processtime = (int) Math.ceil((double)task.getPages()/device.getSpeed());
		task.setProcesstime(processtime);
		System.out.println("Expected process time is "+processtime);
		device.setStatus("busy");
		
		device.getOldtask().addLast(task); //current job also in oldtask!
//		System.out.println(task);
	}
	
	public static void updateOldtask(Device device) {
		ListIterator<Task> iter = device.getOldtask().listIterator();
		while(iter.hasNext()) {
			Task t = iter.next();
			int time = (int)((double)(System.currentTimeMillis()-t.getStarttime())/1000)
					-t.getWaittime()-t.getProcesstime();
			
			if(time > 30) {
				iter.remove();
			}
		}
	}
	
	public static void printlog(Device device, File file) throws IOException {
		if(device.getOldtask().isEmpty()) {
			return ;
		}
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file, true);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found!");
		}
		
		BufferedWriter bufw = new BufferedWriter(
				new OutputStreamWriter(fos));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		bufw.write(df.format(new Date()).toString());
		bufw.newLine();
		bufw.write(device.getName()+" is "+device.getStatus()+" now and print history: ");
		bufw.newLine();
		Iterator<Task> iter = device.getOldtask().iterator();
		while(iter.hasNext()) {
			Task t = iter.next();
			StringBuilder sb = new StringBuilder().append(device.getName()).append(" print ").append(t.getName())
					.append(", pages is ").append(t.getPages())
					.append(", processtime is ").append(t.getProcesstime())
					.append("s, task waittime is ").append(t.getWaittime())
					.append("s, task complete status is ").append(t.getStatus()).append(".");
			bufw.write(sb.toString());
			bufw.newLine();
		}
		bufw.newLine();
		
		bufw.close();
		fos.close();
	}
}
