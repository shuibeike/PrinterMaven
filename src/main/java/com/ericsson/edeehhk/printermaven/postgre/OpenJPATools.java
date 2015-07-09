package com.ericsson.edeehhk.printermaven.postgre;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.ericsson.edeehhk.printermaven.device.Device;
import com.ericsson.edeehhk.printermaven.task.Task;

public class OpenJPATools {
	private EntityManagerFactory defactory;
	private EntityManager entitymanager;
	
	public OpenJPATools() {
		defactory = Persistence.createEntityManagerFactory("printermaven");
		entitymanager = defactory.createEntityManager();
	}
	
	public EntityManagerFactory getDefactory() {
		return defactory;
	}

	public void setDefactory(EntityManagerFactory defactory) {
		this.defactory = defactory;
	}

	public EntityManager getEntitymanager() {
		return entitymanager;
	}

	public void setEntitymanager(EntityManager entitymanager) {
		this.entitymanager = entitymanager;
	}

	public void save(Device device) {
		entitymanager.getTransaction().begin();
		entitymanager.persist(device);
	    entitymanager.getTransaction().commit();
	}

	public void load(LinkedList<Device> devices) {
		if(entitymanager==null)
			System.out.print("000000");
		Query q = entitymanager.createQuery("SELECT d FROM Device d");
		
        List<Device> ld = q.getResultList();
        devices = toLinkedList(ld);
        ld = null;
	}
	
	private LinkedList<Device> toLinkedList(List<Device> devices) {
		LinkedList<Device> ll = new LinkedList<Device>();
		for(Device d : devices) {
			ll.addLast(d);
		}
		return ll;
	}
	
	public void close() {
		entitymanager.close();
	    defactory.close( );
	}

	public void save(Task task) {
		entitymanager.getTransaction().begin( );
		entitymanager.persist(task);
	    entitymanager.getTransaction( ).commit( );
	}

}
