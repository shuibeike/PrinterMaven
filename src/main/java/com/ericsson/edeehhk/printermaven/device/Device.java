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

import com.ericsson.edeehhk.printermaven.task.Task;

public class Device implements Comparable<Device> {
	private String name;
	private String status;
	private int speed;
	private LinkedList<Task> oldtask = new LinkedList<Task>();

	public Device(String name, int speed) {
		super();
		this.name = name;
		this.speed = speed;
		status = "idle";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public LinkedList<Task> getOldtask() {
		return oldtask;
	}
	public void setOldtask(LinkedList<Task> oldtask) {
		this.oldtask = oldtask;
	}

	public void print(Task task) {
		task.updateWaittime();
//		System.out.println("Device "+name+" is process task "+task.getName());
		int processtime = (int) Math.ceil((double)task.getPages()/speed);
		task.setProcesstime(processtime);
		System.out.println("Expected process time is "+processtime);
		status = "busy";
		
		oldtask.addLast(task); //current job also in oldtask!
//		System.out.println(task);
	}
	
	public void updateOldtask() {
		ListIterator<Task> iter = oldtask.listIterator();
		while(iter.hasNext()) {
			Task t = iter.next();
			int time = (int)((double)(System.currentTimeMillis()-t.getStarttime())/1000)
					-t.getWaittime()-t.getProcesstime();
			
			if(time > 30) {
				iter.remove();
			}
		}
	}
	
	public int compareTo(Device device) {
		return this.speed - device.speed;
	}

	public void printlog(File file) throws IOException {
		if(oldtask.isEmpty()) {
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
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置����������
		bufw.write(df.format(new Date()).toString());// new Date()为获�������系统时����
		bufw.newLine();
		bufw.write(name+" is "+status+" now and print history: ");
		bufw.newLine();
		Iterator<Task> iter = oldtask.iterator();
		while(iter.hasNext()) {
			Task t = iter.next();
			StringBuilder sb = new StringBuilder().append(name).append(" print ").append(t.getName())
					.append(", pages is ").append(t.getPages())
					.append(", processtime is ").append(t.getProcesstime())
					.append("s, task waittime is ").append(t.getWaittime())
					.append("s.");
			bufw.write(sb.toString());
			bufw.newLine();
		}
		bufw.newLine();
		
		bufw.close();
		fos.close();
	}
}
