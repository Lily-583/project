package com.csci310.uscdoordrink;
import static org.junit.Assert.*;
import org.junit.*;

import java.util.ArrayList;

public class TestOrder {
    private Order order;

    @Before
    public void initialize(){
        ArrayList<Item> list = new ArrayList<>();
        Item tea = new Item("Tea",2.98f,2,40);
        Item coffee = new Item("Coffee", 3.55f, 3, 100);
        Item water = new Item("Water", 0.99f, 1, 0);
        list.add(tea);
        list.add(coffee);
        list.add(water);
        DeliveryRoute route = new DeliveryRoute("Pot of Cha", "Victor","2022-04-12", "18:36:35", "2022-04-12", "18:43:35");
        order = new Order(list, route);
    }

    @Test
    public void getTotalPriceTest(){
        assertEquals(17.6f,order.getTotalPrice(),0.0f);
    }

    @Test
    public void setTotalPriceTest(){
        order.setTotalPrice(20.22f);
        assertEquals(20.22f,order.getTotalPrice(),0.0f);
    }

    @Test
    public void getTotalCaffeineTest(){
        assertEquals(Integer.valueOf(380), order.getTotalCaffeine());
    }

    @Test
    public void setTotalCaffeineTest(){
        order.setTotalCaffeine(500);
        assertEquals(Integer.valueOf(500), order.getTotalCaffeine());
    }

    @Test
    public void getDeliveryRouteTest(){
        assertEquals("Pot of Cha", order.getDeliveryRoute().getMerchantUsrName());
        assertEquals("Victor", order.getDeliveryRoute().getCustomerUsrName());
        assertEquals("2022-04-12", order.getDeliveryRoute().getOrderPlacedDate());
        assertEquals("18:36:35", order.getDeliveryRoute().getOrderPlacedTime());
        assertEquals("2022-04-12", order.getDeliveryRoute().getDeliveryDate());
        assertEquals("18:43:35", order.getDeliveryRoute().getDeliveryTime());
    }

    @Test
    public void setDeliveryRouteTest(){
        DeliveryRoute tempRoute = new DeliveryRoute("Boba Time", "Maria","2022-03-12", "15:33:33", "2022-03-13", "18:22:22");
        order.setDeliveryRoute(tempRoute);
        assertEquals("Boba Time", order.getDeliveryRoute().getMerchantUsrName());
        assertEquals("Maria", order.getDeliveryRoute().getCustomerUsrName());
        assertEquals("2022-03-12", order.getDeliveryRoute().getOrderPlacedDate());
        assertEquals("15:33:33", order.getDeliveryRoute().getOrderPlacedTime());
        assertEquals("2022-03-13", order.getDeliveryRoute().getDeliveryDate());
        assertEquals("18:22:22", order.getDeliveryRoute().getDeliveryTime());
    }

    @Test
    public void getOrderItemsTest(){
        ArrayList<Item> tmp = order.getOrderItems();
        assertEquals(3, tmp.size());
        for (int i = 0; i < 3; i++){
            if (i == 0){
                assertEquals("Tea", tmp.get(i).getItemName());
                assertEquals(2.98f,tmp.get(i).getItemPrice(), 0.0f);
                assertEquals(Integer.valueOf(2),tmp.get(i).getItemQtyInOrder());
                assertEquals(Integer.valueOf(40), tmp.get(i).getItemCaffeine());
            }
            else if (i == 1){
                assertEquals("Coffee", tmp.get(i).getItemName());
                assertEquals(3.55f,tmp.get(i).getItemPrice(), 0.0f);
                assertEquals(Integer.valueOf(3),tmp.get(i).getItemQtyInOrder());
                assertEquals(Integer.valueOf(100), tmp.get(i).getItemCaffeine());
            }
            else{
                assertEquals("Water", tmp.get(i).getItemName());
                assertEquals(0.99f,tmp.get(i).getItemPrice(), 0.0f);
                assertEquals(Integer.valueOf(1),tmp.get(i).getItemQtyInOrder());
                assertEquals(Integer.valueOf(0), tmp.get(i).getItemCaffeine());
            }
        }
        assertEquals(Float.valueOf(17.6f),order.getTotalPrice());
        assertEquals(Integer.valueOf(380), order.getTotalCaffeine());
    }

    @Test
    public void addOrderItemTest(){
        Item blackTea = new Item("Black Tea",3.88f,4,60);
        order.addOrderItem(blackTea);
        assertEquals(4, order.getOrderItems().size());
        for (int i = 0; i < 4; i++){
            if (i == 0){
                assertEquals("Tea", order.getOrderItems().get(i).getItemName());
                assertEquals(2.98f,order.getOrderItems().get(i).getItemPrice(), 0.0f);
                assertEquals(Integer.valueOf(2),order.getOrderItems().get(i).getItemQtyInOrder());
                assertEquals(Integer.valueOf(40), order.getOrderItems().get(i).getItemCaffeine());
            }
            else if (i == 1){
                assertEquals("Coffee", order.getOrderItems().get(i).getItemName());
                assertEquals(3.55f,order.getOrderItems().get(i).getItemPrice(), 0.0f);
                assertEquals(Integer.valueOf(3),order.getOrderItems().get(i).getItemQtyInOrder());
                assertEquals(Integer.valueOf(100), order.getOrderItems().get(i).getItemCaffeine());
            }
            else if (i == 2){
                assertEquals("Water", order.getOrderItems().get(i).getItemName());
                assertEquals(0.99f,order.getOrderItems().get(i).getItemPrice(), 0.0f);
                assertEquals(Integer.valueOf(1),order.getOrderItems().get(i).getItemQtyInOrder());
                assertEquals(Integer.valueOf(0), order.getOrderItems().get(i).getItemCaffeine());
            }
            else{
                assertEquals("Black Tea", order.getOrderItems().get(i).getItemName());
                assertEquals(3.88f,order.getOrderItems().get(i).getItemPrice(), 0.0f);
                assertEquals(Integer.valueOf(4),order.getOrderItems().get(i).getItemQtyInOrder());
                assertEquals(Integer.valueOf(60), order.getOrderItems().get(i).getItemCaffeine());
            }
        }
        assertEquals(Float.valueOf(33.12f),order.getTotalPrice());
        assertEquals(Integer.valueOf(620), order.getTotalCaffeine());
    }

}
