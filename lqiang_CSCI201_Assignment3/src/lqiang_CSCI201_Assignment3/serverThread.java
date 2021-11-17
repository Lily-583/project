package lqiang_CSCI201_Assignment3;
import java.net.*;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;


public class serverThread extends Thread {
	ObjectOutputStream oos;
	ObjectInputStream ois;
	Scanner scan;
	Socket s;
	Server server;
	Random random = new Random();

	int value = random.nextInt(10 +1) + 1;

	static int drivernum;
//	static ArrayBlockingQueue<Event>q;
	boolean isavailable;
	DriverInformation info;
	boolean completed;
	
	public serverThread(Socket s,Server server)
	{
		
//		allcompleted=false;
//		busy=false;
		this.s=s;
		this.server=server;
		this.isavailable=true;
		this.completed=false;
		this.info=null;
		try {
			oos=new ObjectOutputStream(this.s.getOutputStream());
			
			ois = new ObjectInputStream(this.s.getInputStream());
//			start();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void  PassNum(Integer num)
	{
		
		
			try {
				oos.writeObject(num);
				oos.flush();
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			
			
		
	}
	
	public boolean isAvailable()
	{
		return isavailable;
	}
	
	public void receiveOrder(DriverInformation info)
	{
		this.info=info;
		isavailable=false;
//		System.out.println("Order from server: "+info.getItems().get(0));
//		if(info.getItems().get(0).equals("nice"))
//		{
//			System.out.println("OKK");
//		}
	}
	
	public void addOrder(DriverInformation info)
	{
		try {
//			System.out.println("Order write to driver: "+info.getItems().get(0));
			oos.writeObject(info);
//			System.out.println("Checking Order: ");
//			
//			System.out.println(info.getItems().get(0));
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void run()
	{
		try {
			while(true)
			{
				try {
					String read=(String)ois.readObject();
					if(read.equals("done"))
					{
	//					System.out.println("thread "+value+"done received at: "+TimeFormatter.getTimestamp());
	//					System.out.println("done received");
						isavailable=true;
						while(info==null)
						{
							continue;
							
						}
						isavailable=false;
						addOrder(info);
						info=null;
	//					System.out.println()
	//					System.out.println(info.getItems().get(0));
						
					}
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
				if(completed==true)
				{
					break;
				}
				else
				{
					continue;
				}
				
			}
		
	}
	catch(Exception e)
	{
		System.out.println(e.getMessage());
	}
}
}