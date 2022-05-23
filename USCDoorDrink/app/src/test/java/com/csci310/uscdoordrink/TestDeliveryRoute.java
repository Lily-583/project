package com.csci310.uscdoordrink;
import static org.junit.Assert.*;
import org.junit.*;

public class TestDeliveryRoute {
    private DeliveryRoute route;

    @Before
    public void initialize(){
        route = new DeliveryRoute("Pot of Cha", "Victor","2022-04-12", "18:36:35", "2022-04-12", "18:43:35");
    }

    @Test
    public void getMerchantUsrNameTest(){
        assertEquals("Pot of Cha", route.getMerchantUsrName());
    }

    @Test
    public void setMerchantUsrNameTest(){
        route.setMerchantUsrName("Boba Time");
        assertEquals("Boba Time", route.getMerchantUsrName());
    }

    @Test
    public void getCustomerUsrNameTest(){
        assertEquals("Victor", route.getCustomerUsrName());
    }

    @Test
    public void setCustomerUsrNameTest(){
        route.setCustomerUsrName("Maria");
        assertEquals("Maria", route.getCustomerUsrName());
    }

    @Test
    public void getOrderPlacedDateTest(){
        assertEquals("2022-04-12", route.getOrderPlacedDate());
    }

    @Test
    public void setOrderPlacedDateTest(){
        route.setOrderPlacedDate("2022-04-10");
        assertEquals("2022-04-10", route.getOrderPlacedDate());
    }

    @Test
    public void getOrderPlacedTimeTest(){
        assertEquals("18:36:35", route.getOrderPlacedTime());
    }

    @Test
    public void setOrderPlacedTimeTest(){
        route.setOrderPlacedTime("17:32:33");
        assertEquals("17:32:33", route.getOrderPlacedTime());
    }

    @Test
    public void getDeliveryDateTest(){
        assertEquals("2022-04-12", route.getDeliveryDate());
    }

    @Test
    public void setDeliveryDateTest(){
        route.setDeliveryDate("2022-04-13");
        assertEquals("2022-04-13", route.getDeliveryDate());
    }

    @Test
    public void getDeliveryTimeTest(){
        assertEquals("18:43:35", route.getDeliveryTime());
    }

    @Test
    public void setDeliveryTimeTest(){
        route.setDeliveryTime("19:00:03");
        assertEquals("19:00:03", route.getDeliveryTime());
    }

    @Test
    public void constructorTest(){
        assertEquals("Pot of Cha", route.getMerchantUsrName());
        assertEquals("Victor", route.getCustomerUsrName());
        assertEquals("2022-04-12", route.getOrderPlacedDate());
        assertEquals("18:36:35", route.getOrderPlacedTime());
        assertEquals("2022-04-12", route.getDeliveryDate());
        assertEquals("18:43:35", route.getDeliveryTime());
    }
}
