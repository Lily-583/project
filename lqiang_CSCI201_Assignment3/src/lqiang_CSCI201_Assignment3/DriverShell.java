package lqiang_CSCI201_Assignment3;
import java.net.Socket;
import java.util.Scanner;
import java.util.Random;

import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.*;


//
class DriverShell {
    public static void main(String[] args) {
//    	Random random = new Random();
//
//		int value = random.nextInt(10 +1) + 1;
//		System.out.println(value);
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to SalEats v2.0!");
        Socket s;
//        int count=1;
        while (true) {
            String ans = null;
            try {
                System.out.print("Enter the server hostname: ");
                String hostname = in.nextLine();
                System.out.print("Enter the server port: ");
                ans = in.nextLine();
                int port = Integer.parseInt(ans);
                s = new Socket(hostname, port);
                System.out.println();
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("The given input " + ans + " is not a number.\n");
            } catch (UnknownHostException uhe) {
                System.out.println("The given host is unknown.\n");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        in.close();

        try {
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            while (true) {
            	Integer num;
            	try {
            			num =(Integer) ois.readObject();
//            			System.out.println("HERE"+num);
//            			if(num.equals("Hello"))
//            			{
//            				break;
//            			}
            			if (num == 0) {
                            System.out.println("All drivers have arrived!");
                            System.out.println("Starting service.\n");
                            break;
                        } else {
                            System.out.println(num + " more driver is needed before the service can begin.");
                            System.out.println("Waiting...\n");
                        }
            	}
            	catch(EOFException e)
            	{
            		
            	}
                
            }
            
            oos.writeObject("done");
            int cnt=0;
            while (true) {
            	try {
	                DriverInformation info = (DriverInformation) ois.readObject();
//	                if(info.getItems().get(0).equals("A"))
//	                {
//	                	System.out.println("A1"+TimeFormatter.getTimestamp());
//	                }
	                if (info == null) {
	                    System.out.println(TimeFormatter.getTimestamp() + " All orders completed!");
	                    break;
	                }
//	                System.out.println("CHecking food: ");
//	                System.out.println(info.getItems().get(0));
//	                if(info.getItems().get(0).equals("A"))
//	                {
//	                	System.out.println("A1"+TimeFormatter.getTimestamp());
//	                }
////	                DriverInformation driverInfo = new DriverInformation(info);
//	                if(info.getItems().get(0).equals("A"))
//	                {
//	                	System.out.println("A2"+TimeFormatter.getTimestamp());
//	                }
//	                oos.writeObject("done");
	                deliver(info);
//	                String tmp=info.getItems().get(0);
//	                if(info.getItems().get(0).equals("B"))
//	                {
//	                	System.out.println("OK");
//	                }
	                oos.writeObject("done");
//	                System.out.println("Hello");
//	                System.out.println("cnt: "+cnt);
//	                cnt++;
//                pw.flush();
            	}
	            catch(EOFException e)
	            {
	            	 
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
    
    @SuppressWarnings("BusyWait")
    private static void deliver(DriverInformation info) {
        for (Order order : info.getOrders()) {
            System.out.print(TimeFormatter.getTimestamp() + " Starting delivery of ");
            System.out.println(order.getItemName() + " to " + order.getRestaurantName() + ".");
//            if(order.getItemName().equals("B"))
//            {
//            	System.out.println(System.currentTimeMillis());
//            }
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
}
