package com.ericsson.edeehhk.printermaven.task;

public class Task implements Comparable<Task> {
	private String name;
	private int pages;
	private int processtime;
	private int waittime;
	private long starttime;
	
	public Task(String name, int pages) {
		super();
		this.name = name;
		this.pages = pages;
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
	
	public void updateWaittime() {
		waittime = (int)Math.ceil((double)(System.currentTimeMillis()-starttime)/1000);
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
