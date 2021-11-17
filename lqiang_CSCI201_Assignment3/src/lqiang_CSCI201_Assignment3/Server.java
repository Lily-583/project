package lqiang_CSCI201_Assignment3;
import java.net.*;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Server {
	static ArrayList<serverThread>serverThreadList;
//	public static ArrayBlockingQueue<Event> queue;
	int port;
	ServerSocket serverSocket;
//	Socket socket;
	BufferedReader br;
	int count=1;
	Scanner scan;
	static double head_latitude;
	static double head_longitude;
	int numdriver;
	static long start;
//	ObjectOutputStream oos;
//	ObjectInputStream ois;
	
	
	
//	
	static ArrayList<Event> treeset = new ArrayList<Event>();
	static ArrayList<Order> orderlist=new ArrayList<Order>();

	//check the format of the file
//	public boolean checkFile()
//	{
//		return false;
//	}
	public Server(int port)
	{
		this.port=port;
	}
	
	//ask for user input
	public void userinput() {
		boolean file=false;
		while(file==false)
		{
			System.out.println("What is the name of the schedule file?");
			scan=new Scanner(System.in);
			if(!scan.hasNext())
			{
				continue;
			}
			String thefilename=scan.next();
    		thefilename=thefilename.trim();
    		 try {
 		        br = new BufferedReader(new FileReader(thefilename));
 		        
 		        
 		    }   
 	        catch(FileNotFoundException e)
 	        {
 	        	System.out.println("That file does not exist.");
 	        	continue;
 	        }
 	        catch(IOException e)
 	        {
 	        	System.out.println("Cannot read in from the file provided. ");
 	        	continue;
 	        }
 	        catch(Exception e)
 	        {
 	        	System.out.println("Invalid input file. ");
 	        	continue;
 	        }
 
		
		
		
		//read the schedule file into an array
		boolean read=true;
		boolean empty=true;
		String line;
		int cnt=0;
		while(read)
		{
			try{
				cnt+=1;
				line=br.readLine();
				if(line==null&&cnt==1)
	    		{
	    			empty=false;
	    			read=false;
	    			System.out.println("Empty file!");
	    			break;
	    		}
	    		else if(line==null&&cnt!=1)
	    		{
	    			file=true;
	    			break;
	    		}
	    		else if(line.trim().equals(""))
	    		{
	    			continue;
	    		}
	    		else
	    		{
	    			
		    		String[]feature=line.split(",");
//		    		System.out.println("feature: "+feature[0]+feature[1]+feature[2]);
		    		try {
		    			feature[0]=feature[0].trim();
		    			int time=Integer.parseInt(feature[0]);
		    			if(time<0)
			    		{
			    			System.out.println("order cannot start at negative time!");
			    			file=false;
			    			break;
			    		}
			    		feature[1]=feature[1].trim();
			    		String resname=feature[1];
			    		if(resname==null||resname.equals(""))
			    		{
			    			System.out.println("Invalid restaurant name!");
			    			file=false;
			    			break;
			    		}
			    		
			    		feature[2]=feature[2].trim();
			    		String item=feature[2];
			    		if(item==null||item.equals(""))
			    		{
			    			System.out.println("Invalid food name!");
			    			file=false;
			    			break;
			    		}
			    		
			    		
			    		Event finalpair=new Event(time,resname,item);
			    		//add each line from schedule to the treeset in sorted order
			    		treeset.add(finalpair);
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println(e.getMessage());
		    			System.out.println("Order starting time must be integer!");
		    			file=false;
		    			break;
		    		}
//		    		if(time<0)
//		    		{
//		    			System.out.println("order cannot start at negative time!");
//		    			file=false;
//		    			break;
//		    		}
//		    		feature[1]=feature[1].trim();
//		    		String resname=feature[1];
//		    		
//		    		feature[2]=feature[2].trim();
//		    		String item=feature[2];
//		    		
//		    		
//		    		Event finalpair=new Event(time,resname,item);
//		    		//add each line from schedule to the treeset in sorted order
//		    		treeset.add(finalpair);
		    		
//		    		
		    		
	    		}
				
			}
			catch(IOException e)
	        {
		    	System.out.println("Cannot read line!");
		    	read=false;
		    	break;
	        }
	    	catch (NumberFormatException e)
	    	{
	    		System.out.println("Some Number cannot be parsed!");
	    		read=false;
	    		break;
	    	}
	    	catch (ArrayIndexOutOfBoundsException e)
	    	{
	    		System.out.println("Missing or Invalid Data! ");
	    		read=false;
	    		break;
	    		
	    	}

//			System.out.println("Check treeset: ");
//			for(int i=0;i<treeset.size();i++)
//			{
//				System.out.println(treeset.get(i).getItemName());
//			}
		}
		
		}
		
		while(true)
		{
			System.out.println("What is your latitude?");
			Scanner sa=new Scanner(System.in);
			if(!sa.hasNextDouble())
			{
				System.out.println("Must enter a double!");
				continue;
			}
			head_latitude=sa.nextDouble();

			break;
		}
		
		
		
		
		
		while(true)
		{
			System.out.println("What is your longitude?");
			Scanner sc=new Scanner(System.in);
			if(!sc.hasNextDouble())
			{
				System.out.println("Must enter a double!");
				continue;
			}
			head_longitude=sc.nextDouble();
			break;
		}
		//enter latitude
//		while(true)
//		{
//			System.out.println("What is your latitude?");
//			if(!scan.hasNextDouble())
//			{
//				System.out.println("Must enter a double!");
//				continue;
//			}
//			head_latitude=scan.nextDouble();
//
//			break;
//		}
		
		//convert to order list:
				for(int i=0;i<treeset.size();i++)
				{
					Event e=treeset.get(i);
					int time=e.getTime();
					String restaurant=e.getStartLocation();
					String item=e.getItemName();
					Location head=new Location(head_latitude,head_longitude);
					Location loc=YelpAPIParser.getLocation(restaurant, head);
					Order o=new Order(time,restaurant,item,loc,0);
					orderlist.add(o);
				}
		
		while(true)
		{
			System.out.println("How many drivers will be in service today?");
			if(!scan.hasNextInt())
			{
				System.out.println("Must enter a double!");
				continue;
			}
			numdriver=scan.nextInt();
//			System.out.println("numdriver:"+numdriver);
			if(numdriver<=0)
			{
				System.out.println("Driver number cannot be less than or equal to 0!");
				continue;
			}
			break;
		}
		
//		//convert to order list:
//		for(int i=0;i<treeset.size();i++)
//		{
//			Event e=treeset.get(i);
//			int time=e.getTime();
//			String restaurant=e.getStartLocation();
//			String item=e.getItemName();
//			Location head=new Location(head_latitude,head_longitude);
//			Location loc=YelpAPIParser.getLocation(restaurant, head);
//			Order o=new Order(time,restaurant,item,loc,0);
//			orderlist.add(o);
//		}
//		
		establishConnection();
		
}
		
	
	
	public void establishConnection() {		
		try {
				this.serverSocket=new ServerSocket(port);
				serverThreadList=new ArrayList<serverThread>(numdriver);
				
				System.out.println("Listening on port "+port+".");
				
				System.out.println("Waiting for drivers...");
				System.out.println("");
				Integer num=numdriver;
				

				
				
				while(num>0)
				{
					try {
//						
						Socket socket=serverSocket.accept();
						serverThread st=new serverThread(socket,this);
						num--;
						serverThreadList.add(st);
						for(int i=0;i<serverThreadList.size();i++)
						{
							serverThreadList.get(i).PassNum(num);
//							System.out.println("Passed num!");
						}
						String ipAddress =  "127.0.0.1";
						System.out.println("Connection from "+ipAddress);
						
						
//						
						if(num>0)
						{
							System.out.println("Waiting for "+num+" more driver(s)...");
						}
//						System.out.println("Waiting for "+num+" more driver(s)...");
						System.out.println("");
					}
					catch(ConnectException e)
					{
						System.out.println("Error in Connection!");
					}
					catch(Exception e)
					{
//						System.out.println("Hello");
						System.out.println(e.getMessage());
					}
					
					

				}
				System.out.println("Starting service.");
				System.out.println("");

				for(int i=0;i<serverThreadList.size();i++)
				{
					serverThreadList.get(i).start();
				}
				AssignTask();
				
			
				
		}
		catch(ConnectException e)
		{
			System.out.println("Error in Connection!");
		}
		catch(Exception e)
		{
			
			System.out.println(e.getMessage());
		}

				
	}
	
	void AssignTask()
	{
		try {
			start=System.currentTimeMillis();
			
			while(!orderlist.isEmpty())
			{
	//			System.out.println("Time4: "+TimeFormatter.getTimestamp());
				LinkedList<Order>neworderlist=new LinkedList<Order>();
				long current=System.currentTimeMillis();
				int diff=(int)(current-start)/1000;
	//			if(orderlist.get(0).getStartTime()==4)
	//			{
	//				System.out.println("the old actual diff time is: "+TimeFormatter.getTimestamp());
	//				
	//			}
					
				if(diff<orderlist.get(0).getStartTime()||AnyAvailable()==false)
				{
					continue;
				}
	//			int count=1;
	//			int time=treeset.get(0).getTime();
	//			if(orderlist.get(0).getStartTime()==1)
	//			{
	//				System.out.println("The new time is: "+TimeFormatter.getTimestamp());
	//			}
				while(!orderlist.isEmpty()&&diff>=orderlist.get(0).getStartTime())
				{
					
					Order item=orderlist.get(0);
					neworderlist.add(item);
					
					orderlist.remove(0);
	
					
				}
				Location headquarter=new Location(head_latitude,head_longitude);
				
				boolean found=false;
				
	//			
				DriverInformation driverInfo = new DriverInformation(neworderlist,headquarter);
				
				while(found==false)
				{
					if(diff>=1)
					{
	//					System.out.println("At time: "+TimeFormatter.getTimestamp());
	//					System.out.println("driver 2:"+serverThreadList.get(1).isAvailable());
	//					System.out.println("driver 1:"+serverThreadList.get(1).isAvailable());
						
					}
					for(int i=0;i<serverThreadList.size();i++)
					{
							if(serverThreadList.get(i).isAvailable()==true)
							{
								
	//						
								serverThreadList.get(i).receiveOrder(driverInfo);
								found=true;
	//							System.out.println("Time3: "+TimeFormatter.getTimestamp());
								break;
							}
	//						
					}
				}
			}
	//		System.out.println("END "+TimeFormatter.getTimestamp());
			while(true)
			{
				System.out.print("");
				
				
	//			boolean test=AllAvailable();
	//			System.out.println(TimeFormatter.getTimestamp());
	//			System.out.println(test);
				if(AllAvailable())
				{
					System.out.println("All orders completed!");
					for(int i=0;i<serverThreadList.size();i++)
					{
						serverThreadList.get(i).addOrder(null);
					}
					break;
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		}
		
	
		public static double getLatitude()
		{
			return head_latitude;
		}
		
		public static double getLongitude()
		{
			return head_longitude;
		}
		
		public static void main(String args[])
		{
			Server server=new Server(3456);
			server.userinput();
			
			
				
		}
		public boolean AnyAvailable()
		{
			for(int i=0;i<serverThreadList.size();i++)
			{
				if(serverThreadList.get(i).isAvailable()==true)
				{
					return true;
				}
			}
			return false;
		}
		
		public static boolean AllAvailable()
		{
			for(int i=0;i<serverThreadList.size();i++)
			{
				if(serverThreadList.get(i).isAvailable()==false)
				{
					return false;
				}
			}
			return true;
		}
	
}
