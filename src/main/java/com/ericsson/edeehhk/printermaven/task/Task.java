package com.ericsson.edeehhk.printermaven.task;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tasks")
public class Task implements Comparable<Task>, Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	@Column(name="_id")
	private int id;
	
	@Column(name="_name")
	private String name;
	@Column(name="_pages")
	private int pages;
	@Column(name="_processtime")
	private int processtime;
	@Column(name="_waittime")
	private int waittime;
	@Column(name="_starttime")
	private long starttime;
	@Column(name="_status")
	private boolean status;
	@Column(name="_deviceId")
	private int deviceId;
	
	public Task() {
		status = false;
	}
	
	public Task(String name, int pages) {
		super();
		this.name = name;
		this.pages = pages;
		status = false;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public int getProcesstime() {
		return processtime;
	}
	public void setProcesstime(int processtime) {
		this.processtime = processtime;
	}
	public int getWaittime() {
		return waittime;
	}
	public void setWaittime(int waittime) {
		this.waittime = waittime;
	}
	public long getStarttime() {
		return starttime;
	}
	public void setStarttime(long starttime) {
		this.starttime = starttime;
	}
	
	public int compareTo(Task task) {
		return (this.getPages() - task.getPages())*waittime;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Task)) {
			return false;
		}
		Task t = (Task)obj;
		
		return this.name.equals(t.getName());
	}
	
	
}
