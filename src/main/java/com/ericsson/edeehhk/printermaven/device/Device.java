package com.ericsson.edeehhk.printermaven.device;

import java.io.Serializable;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ericsson.edeehhk.printermaven.task.Task;

@Entity
@Table(name="devices")
public class Device implements Comparable<Device>, Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	@Column(name="_id")
	private int id;
	
	@Column(name="_name")
	private String name;
	@Transient
	private String status;
	@Column(name="_speed")
	private int speed;
	@Transient
	private LinkedList<Task> oldtask = new LinkedList<Task>();

	public Device() {
		status = "idle";
	}
	public Device(String name, int speed) {
		super();
		this.name = name;
		this.speed = speed;
		status = "idle";
	}
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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

	public int compareTo(Device device) {
		return this.speed - device.speed;
	}

}
