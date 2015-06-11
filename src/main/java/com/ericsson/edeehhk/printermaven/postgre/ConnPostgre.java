package com.ericsson.edeehhk.printermaven.postgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.ericsson.edeehhk.printermaven.device.Device;

public class ConnPostgre {

	public static void save(Device d) {
		Connection conn = connectToDatabaseOrDie();
		try 
		{
			Statement st = conn.createStatement();
			StringBuilder sql = new StringBuilder()
				.append("insert into devices(_name, _speed) values(\'")
				.append(d.getName()).append("\', ")
				.append(d.getSpeed()).append(")");
						
//			System.out.println(sql);
			
			st.executeUpdate(sql.toString());
			st.close();
		}
		catch (SQLException se) {
			System.err.println("Threw a SQLException saving the list of devices.");
			System.err.println(se.getMessage());
		}

	}

	public static void load(LinkedList<Device> devices)
	{
		Connection conn = connectToDatabaseOrDie();
		try 
		{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select _id, _name, _speed from devices");
			while ( rs.next() )
			{
				Device d = new Device(rs.getString("_name"), rs.getInt("_speed"));
				devices.addLast(d);
			}
			rs.close();
			st.close();
		}
		catch (SQLException se) {
			System.err.println("Threw a SQLException creating the list of devices.");
			System.err.println(se.getMessage());
		}
	}

	private static Connection connectToDatabaseOrDie()
	{
		Connection conn = null;
		try
		{
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://127.0.0.1:5432/bmc";
			conn = DriverManager.getConnection(url, "bmc", "bmc");
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.exit(2);
		}
		return conn;
	}
}
