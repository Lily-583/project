package lqiang_CSCI201_Assignment3;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.io.*;
public class driver {
	boolean busy=false;
	ObjectOutputStream oos;
	PrintWriter pw;
	ObjectInputStream ois;
	Scanner in;
	Socket s;
	String hostname;
	int port;
	
	public static long start;
	
//	public static void setHeadQuarter(double la, double lo)
//	{
//		headquarter.latitude=la;
//		headquarter.longitude=lo;
//	}
//	public static void setBlockingQueue(ArrayBlockingQueue<Event> q)
//	{
//		queue=q;
//	}
//	public driver()
//	{
//		busy=false;
//		
//		
//	}
	
	public void establishConnection()
	{
		 	in = new Scanner(System.in);
	        System.out.println("Welcome to SalEats v2.0!");
	        
	        while (true) {
	            String ans = null;
	            try {
	                System.out.print("Enter the server hostname: ");
	                String hostname = in.nextLine();
	                System.out.print("Enter the server port number: ");
	                ans = in.nextLine();
	                int port = Integer.parseInt(ans);
	                s = new Socket(hostname, port);
	                System.out.println("client finished connection");
	                break;
	            } catch (NumberFormatException nfe) {
	                System.out.println("The given input " + ans + " is not a number.\n");
	            } catch (UnknownHostException uhe) {
	                System.out.println("The given host is unknown.\n");
	            } catch (IOException ioe) {
	                ioe.printStackTrace();
	            }
	        }
	        
	        try {
	        	oos=new ObjectOutputStream(s.getOutputStream());
	            
//	           
	            ois = new ObjectInputStream(s.getInputStream());
//	           
	            while (true) {
//	            	
	                Integer num = (Integer) ois.readObject();
//	                System.out.println(num);
	                if (num == 0) {
	                    System.out.println("All drivers have arrived!");
	                    System.out.println("Starting service.\n");
	                    break;
	                } else {
	                    System.out.println(num + " more driver is needed before the service can begin.");
	                    System.out.println("Waiting...\n");
	                }
	            }
	        } catch (SocketException se) {
	            System.out.println(TimeFormatter.getTimestamp() + " Server dropped connection. All orders completed!");
	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	        } catch (ClassNotFoundException cnfe) {
	            System.out.println(cnfe.getMessage());
	        }
	        
	        
	        
	        
	}
	public void ReceiveTask()
	{
		
		
//	public void ReceiveTask()
//	{
//		while(true)
//		{
//			if(busy==false)
//			{
//				long current;
//				current=System.currentTimeMillis();
//				int diff=(int)(current-start)/1000;
//				
//				List<String> restaurants=new ArrayList<String>();
//				List<String> items=new ArrayList<String>();
//				while(!queue.isEmpty()&&diff>queue.peek().getTime())
//				{
//						Event event;
//						try {
//							event=(Event)queue.take();
//							restaurants.add(event.getStartLocation());
//							items.add(event.getItemName());
//						}
//						catch(Exception e)
//						{
//							System.out.println(e.getMessage());
//						}
//						
////						
//						
//						 
////			             .println("done");
////		                 .flush();
//				}
//				Location headquarter=new Location(Server.getLatitude(),Server.getLongitude());
//				DeliveryInformation delinfo=new DeliveryInformation(restaurants,items,headquarter);
//				DriverInformation driverInfo = new DriverInformation(delinfo);
//				 
//				busy=true;
//	            deliver(driverInfo);
//	            busy=false;
//					
//					
////					else
////					{
////						info=null;
////						System.out.println(TimeFormatter.getTimestamp() + " All orders completed!");
////		                 break;
////					}
//					 
//		            
//		             
//				}
//				
//				
//	}
}
	
	@SuppressWarnings("BusyWait")
    private static void deliver(DriverInformation info) {
		
        for (Order order : info.getOrders()) {
            System.out.print(TimeFormatter.getTimestamp() + " Starting delivery of ");
            System.out.println(order.getItemName() + " to " + order.getRestaurantName() + ".");
        }
        try {
            Order currentOrder = info.getNext();
            if (currentOrder != null) {
                String prevName = currentOrder.getRestaurantName();
                Location prevLocation = currentOrder.getLocation();
                while (true) {
                    info.reorder(prevLocation);
                    //noinspection BusyWait
                    Thread.sleep((long) (1000 * currentOrder.getDistance()));
                    while (currentOrder != null && prevName.equals(currentOrder.getRestaurantName())) {
                        System.out.print(TimeFormatter.getTimestamp() + " Finished delivery of ");
                        System.out
                                .println(currentOrder.getItemName() + " to " + currentOrder.getRestaurantName() + ".");
                        currentOrder = info.getNext();
                    }
                    if (currentOrder == null) {
                        // Go back to HQ
                        System.out.println(
                                TimeFormatter.getTimestamp() + " Finished all deliveries, returning back to HQ.");
                        Thread.sleep((long) (1000 * DistanceCalc.getDistance(prevLocation, info.getHQLocation())));
                        System.out.println(TimeFormatter.getTimestamp() + " Returned to HQ.");
                        break;
                    }
                    prevLocation = currentOrder.getLocation();
                    prevName = currentOrder.getRestaurantName();
                    System.out.println(TimeFormatter.getTimestamp() + " Continuing delivery to "
                            + currentOrder.getRestaurantName() + ".");
                }
            }
        } catch (InterruptedException ie) {
            System.out.println("Interrupted");
        }
    }
	
	public static void main(String[] args)
	{
//		
		System.out.println(Server.head_latitude);
//		
	}

}
