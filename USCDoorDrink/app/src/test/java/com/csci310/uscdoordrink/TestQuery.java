package com.csci310.uscdoordrink;
import static com.csci310.uscdoordrink.ConnectionClass.CONN;
import static org.junit.Assert.*;

import android.util.Log;

import com.github.mikephil.charting.data.BarEntry;

import org.junit.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestQuery {
    private Query q;

    @Before
    public void initialize(){
        q = new Query();
    }

    @Test
    public void getMenuFromDatabaseTest(){
        ArrayList<Item> items = q.getMenuFromDatabase("Tea King");
        for (int i = 0; i < items.size(); i++){
            if (i == 0){
                assertEquals("Black Tea",items.get(i).getItemName());
                assertEquals(Float.valueOf(2.55f), items.get(i).getItemPrice());
                assertEquals(Integer.valueOf(20), items.get(i).getItemCaffeine());
            }
            else if (i == 1){
                assertEquals("Green Tea",items.get(i).getItemName());
                assertEquals(Float.valueOf(2.33f), items.get(i).getItemPrice());
                assertEquals(Integer.valueOf(30), items.get(i).getItemCaffeine());
            }
            else{
                assertTrue(1 == 0);
            }
        }
    }

    @Test
    public void insertOrderIntoDatabaseTest(){
        ArrayList<Item> items = new ArrayList<>();
        Item black = new Item("Black Tea",2.55f,2, 20);
        Item green = new Item("Green Tea",2.33f,3, 30);
        items.add(black);
        items.add(green);

        DeliveryRoute route = new DeliveryRoute("Tea King","Victor", "2022-04-13", "02:41:30", "2022-04-13", "02:49:30");
        Order order = new Order(items, route);
        q.insertOrderIntoDatabase(order);
        int orderID = 0;
        try
        {
            Connection con= CONN();
            String query = "SELECT * FROM sys.ORDER WHERE UserName_Customer = 'Victor' AND UserName_Merchant = 'Tea King' AND CreatedDate = '2022-04-13' AND CreatedTime = '02:41:30' AND DeliveredDate = '2022-04-13' AND DeliveredTime = '02:49:30' AND totalPrice like 12.09";
            Statement stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
            int counter = 0;
            while (result.next()){
                orderID = result.getInt("OrderID");
                counter++;
            }
            assertTrue(orderID != 0);
            assertEquals(1,counter);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }

        try
        {
            Connection con= CONN();
            String query = "SELECT * FROM ITEMS_IN_ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
            while (result.next()){
                int itemID = result.getInt("ItemID");
                int Qty = result.getInt("Quantity");
                if (itemID == 38){
                    assertTrue(Qty == 2);
                }
                else if (itemID == 39){
                    assertTrue(Qty == 3);
                }
                else{
                    assertTrue(1 == 0);
                }
            }
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM sys.ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM ITEMS_IN_ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
    }

    @Test
    public void getOrderFromDatabaseTest1(){
        ArrayList<Item> items = new ArrayList<>();
        Item black = new Item("Black Tea",2.55f,2, 20);
        Item green = new Item("Green Tea",2.33f,3, 30);
        items.add(black);
        items.add(green);
        DeliveryRoute route = new DeliveryRoute("Tea King","Victor", "2022-04-13", "02:41:30", "2022-04-13", "02:49:30");
        Order order = new Order(items, route);
        q.insertOrderIntoDatabase(order);

        ArrayList<Order> orderList = q.getOrderFromDatabase("Victor", false);
        assertTrue(orderList.size() == 1);
        for (int i = 0; i < orderList.size(); i++){
            assertEquals("Victor",orderList.get(i).getDeliveryRoute().getCustomerUsrName());
        }

        int orderID = 0;
        try
        {
            Connection con= CONN();
            String query = "SELECT * FROM sys.ORDER WHERE UserName_Customer = 'Victor' AND UserName_Merchant = 'Tea King' AND CreatedDate = '2022-04-13' AND CreatedTime = '02:41:30' AND DeliveredDate = '2022-04-13' AND DeliveredTime = '02:49:30' AND totalPrice like 12.09";
            Statement stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
            int counter = 0;
            while (result.next()){
                orderID = result.getInt("OrderID");
                counter++;
            }
            assertTrue(orderID != 0);
            assertEquals(1,counter);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM sys.ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM ITEMS_IN_ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }

    }

    @Test
    public void getOrderFromDatabaseTest2(){
        ArrayList<Item> items = new ArrayList<>();
        Item black = new Item("Black Tea",2.55f,2, 20);
        Item green = new Item("Green Tea",2.33f,3, 30);
        items.add(black);
        items.add(green);
        DeliveryRoute route = new DeliveryRoute("Tea King","Victor", "2022-04-13", "02:41:30", "2022-04-13", "02:49:30");
        Order order = new Order(items, route);
        q.insertOrderIntoDatabase(order);

        ArrayList<Order> orderList = q.getOrderFromDatabase("Tea King", true);
        assertTrue(orderList.size() == 1);
        for (int i = 0; i < orderList.size(); i++){
            assertEquals("Tea King",orderList.get(i).getDeliveryRoute().getMerchantUsrName());
        }

        int orderID = 0;
        try
        {
            Connection con= CONN();
            String query = "SELECT * FROM sys.ORDER WHERE UserName_Customer = 'Victor' AND UserName_Merchant = 'Tea King' AND CreatedDate = '2022-04-13' AND CreatedTime = '02:41:30' AND DeliveredDate = '2022-04-13' AND DeliveredTime = '02:49:30' AND totalPrice like 12.09";
            Statement stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
            int counter = 0;
            while (result.next()){
                orderID = result.getInt("OrderID");
                counter++;
            }
            assertTrue(orderID != 0);
            assertEquals(1,counter);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM sys.ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM ITEMS_IN_ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
    }

    @Test
    public void getCaffeineTest(){
        assertEquals(Integer.valueOf(200), q.getCaffeine("Victor"));
    }

    @Test
    public void updateCaffeineTest(){
        q.updateCaffeine("Victor",Integer.valueOf(22));
        assertEquals(Integer.valueOf(222),q.getCaffeine("Victor"));
        q.updateCaffeine("Victor",-22);
    }

    @Test
    public void refreshCaffeineTest1(){
        assertEquals(Integer.valueOf(200), q.getCaffeine("Victor"));
        q.refreshCaffeine("2022-04-13","Victor");
        assertEquals(Integer.valueOf(0), q.getCaffeine("Victor"));
        q.updateCaffeine("Victor", Integer.valueOf(200));
    }

    @Test
    public void refreshCaffeineTest2(){
        assertEquals(Integer.valueOf(200), q.getCaffeine("Victor"));
        ArrayList<Item> items = new ArrayList<>();
        Item black = new Item("Black Tea",2.55f,2, 20);
        Item green = new Item("Green Tea",2.33f,3, 30);
        items.add(black);
        items.add(green);
        DeliveryRoute route = new DeliveryRoute("Tea King","Victor", "2022-04-13", "02:41:30", "2022-04-13", "02:49:30");
        Order order = new Order(items, route);
        q.insertOrderIntoDatabase(order);
        q.refreshCaffeine("2022-04-13","Victor");
        assertEquals(Integer.valueOf(200), q.getCaffeine("Victor"));
        int orderID = 0;
        try
        {
            Connection con= CONN();
            String query = "SELECT * FROM sys.ORDER WHERE UserName_Customer = 'Victor' AND UserName_Merchant = 'Tea King' AND CreatedDate = '2022-04-13' AND CreatedTime = '02:41:30' AND DeliveredDate = '2022-04-13' AND DeliveredTime = '02:49:30' AND totalPrice like 12.09";
            Statement stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
            int counter = 0;
            while (result.next()){
                orderID = result.getInt("OrderID");
                counter++;
            }
            assertTrue(orderID != 0);
            assertEquals(1,counter);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM sys.ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM ITEMS_IN_ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
    }

    @Test
    public void getDailyAnalysisTest(){
        assertEquals(Integer.valueOf(200), q.getCaffeine("Victor"));
        ArrayList<Item> items = new ArrayList<>();
        Item black = new Item("Black Tea",2.55f,2, 20);
        Item green = new Item("Green Tea",2.33f,3, 30);
        items.add(black);
        items.add(green);
        DeliveryRoute route = new DeliveryRoute("Tea King","Victor", "2022-04-13", "02:41:30", "2022-04-13", "02:49:30");
        Order order = new Order(items, route);
        q.insertOrderIntoDatabase(order);
        HashMap<String,Float> hm = q.getDailyAnalysis("Victor","2022-04-13");
        assertEquals(1,hm.size());
        for (Map.Entry<String, Float> set : hm.entrySet()) {
            assertEquals("Tea King",set.getKey());
            assertEquals(12.09f,set.getValue(),0.0f);
        }
        int orderID = 0;
        try
        {
            Connection con= CONN();
            String query = "SELECT * FROM sys.ORDER WHERE UserName_Customer = 'Victor' AND UserName_Merchant = 'Tea King' AND CreatedDate = '2022-04-13' AND CreatedTime = '02:41:30' AND DeliveredDate = '2022-04-13' AND DeliveredTime = '02:49:30' AND totalPrice like 12.09";
            Statement stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
            int counter = 0;
            while (result.next()){
                orderID = result.getInt("OrderID");
                counter++;
            }
            assertTrue(orderID != 0);
            assertEquals(1,counter);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM sys.ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM ITEMS_IN_ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
    }

    @Test
    public void getWeeklyAnalysisTest(){
        ArrayList<Item> items = new ArrayList<>();
        Item black = new Item("Black Tea",2.55f,2, 20);
        Item green = new Item("Green Tea",2.33f,3, 30);
        items.add(black);
        items.add(green);
        DeliveryRoute route = new DeliveryRoute("Tea King","Victor", "2022-04-13", "02:41:30", "2022-04-13", "02:49:30");
        Order order = new Order(items, route);
        q.insertOrderIntoDatabase(order);
        route = new DeliveryRoute("Tea King","Victor", "2022-04-12", "02:41:30", "2022-04-12", "02:49:30");
        order = new Order(items, route);
        q.insertOrderIntoDatabase(order);
        route = new DeliveryRoute("Tea King","Victor", "2022-04-11", "02:41:30", "2022-04-11", "02:49:30");
        order = new Order(items, route);
        q.insertOrderIntoDatabase(order);

        LinkedHashMap<String, Float> lhm = q.getWeeklyAnalysis("Victor", "2022-04-13");
        assertEquals(3,lhm.size());
        for (Map.Entry<String, Float> set : lhm.entrySet()) {
            if (set.getKey() == "2022-04-11"){
                assertEquals(12.09f,set.getValue(),0.0f);
            }
            else if (set.getKey() == "2022-04-12"){
                assertEquals(12.09f,set.getValue(),0.0f);
            }
            else{
                assertEquals(12.09f,set.getValue(),0.0f);
            }
        }
        int orderID = 0;
        try
        {
            Connection con= CONN();
            String query = "SELECT * FROM sys.ORDER WHERE UserName_Customer = 'Victor' AND UserName_Merchant = 'Tea King' AND CreatedDate = '2022-04-13' AND CreatedTime = '02:41:30' AND DeliveredDate = '2022-04-13' AND DeliveredTime = '02:49:30' AND totalPrice like 12.09";
            Statement stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
            int counter = 0;
            while (result.next()){
                orderID = result.getInt("OrderID");
                counter++;
            }
            assertTrue(orderID != 0);
            assertEquals(1,counter);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM sys.ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM ITEMS_IN_ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }

        orderID = 0;
        try
        {
            Connection con= CONN();
            String query = "SELECT * FROM sys.ORDER WHERE UserName_Customer = 'Victor' AND UserName_Merchant = 'Tea King' AND CreatedDate = '2022-04-12' AND CreatedTime = '02:41:30' AND DeliveredDate = '2022-04-12' AND DeliveredTime = '02:49:30' AND totalPrice like 12.09";
            Statement stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
            int counter = 0;
            while (result.next()){
                orderID = result.getInt("OrderID");
                counter++;
            }
            assertTrue(orderID != 0);
            assertEquals(1,counter);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM sys.ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM ITEMS_IN_ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }

        orderID = 0;
        try
        {
            Connection con= CONN();
            String query = "SELECT * FROM sys.ORDER WHERE UserName_Customer = 'Victor' AND UserName_Merchant = 'Tea King' AND CreatedDate = '2022-04-11' AND CreatedTime = '02:41:30' AND DeliveredDate = '2022-04-11' AND DeliveredTime = '02:49:30' AND totalPrice like 12.09";
            Statement stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
            int counter = 0;
            while (result.next()){
                orderID = result.getInt("OrderID");
                counter++;
            }
            assertTrue(orderID != 0);
            assertEquals(1,counter);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM sys.ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM ITEMS_IN_ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
    }

    @Test
    public void getMonthlyAnalysisTest(){
        ArrayList<Item> items = new ArrayList<>();
        Item black = new Item("Black Tea",2.55f,2, 20);
        Item green = new Item("Green Tea",2.33f,3, 30);
        items.add(black);
        items.add(green);
        DeliveryRoute route = new DeliveryRoute("Tea King","Victor", "2022-04-12", "02:41:30", "2022-04-12", "02:49:30");
        Order order = new Order(items, route);
        q.insertOrderIntoDatabase(order);
        route = new DeliveryRoute("Tea King","Victor", "2022-04-05", "02:41:30", "2022-04-05", "02:49:30");
        order = new Order(items, route);
        q.insertOrderIntoDatabase(order);
        route = new DeliveryRoute("Tea King","Victor", "2022-03-29", "02:41:30", "2022-03-29", "02:49:30");
        order = new Order(items, route);
        q.insertOrderIntoDatabase(order);
        route = new DeliveryRoute("Tea King","Victor", "2022-03-22", "02:41:30", "2022-03-22", "02:49:30");
        order = new Order(items, route);
        q.insertOrderIntoDatabase(order);

        ArrayList<Float> list = q.getMonthlyAnalysis("Victor", "2022-04-13");
        assertEquals(4,list.size());
        for (int i = 0; i < list.size(); i++){
            assertEquals(12.09f,list.get(i),0.0f);
        }
        int orderID = 0;
        try
        {
            Connection con= CONN();
            String query = "SELECT * FROM sys.ORDER WHERE UserName_Customer = 'Victor' AND UserName_Merchant = 'Tea King' AND CreatedDate = '2022-04-12' AND CreatedTime = '02:41:30' AND DeliveredDate = '2022-04-12' AND DeliveredTime = '02:49:30' AND totalPrice like 12.09";
            Statement stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
            int counter = 0;
            while (result.next()){
                orderID = result.getInt("OrderID");
                counter++;
            }
            assertTrue(orderID != 0);
            assertEquals(1,counter);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM sys.ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM ITEMS_IN_ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }

        orderID = 0;
        try
        {
            Connection con= CONN();
            String query = "SELECT * FROM sys.ORDER WHERE UserName_Customer = 'Victor' AND UserName_Merchant = 'Tea King' AND CreatedDate = '2022-04-05' AND CreatedTime = '02:41:30' AND DeliveredDate = '2022-04-05' AND DeliveredTime = '02:49:30' AND totalPrice like 12.09";
            Statement stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
            int counter = 0;
            while (result.next()){
                orderID = result.getInt("OrderID");
                counter++;
            }
            assertTrue(orderID != 0);
            assertEquals(1,counter);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM sys.ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM ITEMS_IN_ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }

        orderID = 0;
        try
        {
            Connection con= CONN();
            String query = "SELECT * FROM sys.ORDER WHERE UserName_Customer = 'Victor' AND UserName_Merchant = 'Tea King' AND CreatedDate = '2022-03-29' AND CreatedTime = '02:41:30' AND DeliveredDate = '2022-03-29' AND DeliveredTime = '02:49:30' AND totalPrice like 12.09";
            Statement stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
            int counter = 0;
            while (result.next()){
                orderID = result.getInt("OrderID");
                counter++;
            }
            assertTrue(orderID != 0);
            assertEquals(1,counter);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM sys.ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM ITEMS_IN_ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }

        orderID = 0;
        try
        {
            Connection con= CONN();
            String query = "SELECT * FROM sys.ORDER WHERE UserName_Customer = 'Victor' AND UserName_Merchant = 'Tea King' AND CreatedDate = '2022-03-22' AND CreatedTime = '02:41:30' AND DeliveredDate = '2022-03-22' AND DeliveredTime = '02:49:30' AND totalPrice like 12.09";
            Statement stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
            int counter = 0;
            while (result.next()){
                orderID = result.getInt("OrderID");
                counter++;
            }
            assertTrue(orderID != 0);
            assertEquals(1,counter);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM sys.ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM ITEMS_IN_ORDER WHERE OrderID = " + String.valueOf(orderID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
    }




}
