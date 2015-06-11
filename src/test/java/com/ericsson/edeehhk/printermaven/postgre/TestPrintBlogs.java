package com.ericsson.edeehhk.printermaven.postgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;

public class TestPrintBlogs
{
  class Blog
  {
    public int id;
    public String subject;
    public String permalink;
  }

  public static void main(String[] args)
  {
    new TestPrintBlogs();
  }

  public TestPrintBlogs() 
  {
    Connection conn = null;
    LinkedList listOfBlogs = new LinkedList();

    // connect to the database
    conn = connectToDatabaseOrDie();

    // get the data
//    populateListOfTopics(conn, listOfBlogs);
    
    // print the results
//    printTopics(listOfBlogs);
  }
  
  private void printTopics(LinkedList listOfBlogs)
  {
    Iterator it = listOfBlogs.iterator();
    while (it.hasNext())
    {
      Blog blog = (Blog)it.next();
      System.out.println("id: " + blog.id + ", subject: " + blog.subject);
    }
  }

  private void populateListOfTopics(Connection conn, LinkedList listOfBlogs)
  {
    try 
    {
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("SELECT id, subject, permalink FROM blogs ORDER BY id");
      while ( rs.next() )
      {
        Blog blog = new Blog();
        blog.id        = rs.getInt    ("id");
        blog.subject   = rs.getString ("subject");
        blog.permalink = rs.getString ("permalink");
        listOfBlogs.add(blog);
      }
      rs.close();
      st.close();
    }
    catch (SQLException se) {
      System.err.println("Threw a SQLException creating the list of blogs.");
      System.err.println(se.getMessage());
    }
  }

  private Connection connectToDatabaseOrDie()
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
