package com.csci310.uscdoordrink;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Assert.*;

public class TestItem {
    private Item item;

    @Before
    public void initialize(){
        item = new Item("Tea",2.98f,2,20);
    }

    @Test
    public void getItemNameTest(){
        assertEquals("Tea",item.getItemName());
    }

    @Test
    public void setItemNameTest(){
        item.setItemName("Coffee");
        assertEquals("Coffee", item.getItemName());
    }

    @Test
    public void getItemPriceTest(){
        Assert.assertEquals(2.98f, item.getItemPrice(),0.0f);
    }

    @Test
    public void setItemPriceTest(){
        item.setItemPrice(3.98f);
        Assert.assertEquals(3.98f,item.getItemPrice(),0.0f);
    }

    @Test
    public void getItemQtyInOrderTest1(){
        assertEquals(Integer.valueOf(2),item.getItemQtyInOrder());
    }

    @Test
    public void getItemQtyInOrderTest2(){
        item = new Item("Tea",2.98f,20);
        assertEquals(Integer.valueOf(0),item.getItemQtyInOrder());
    }

    @Test
    public void setItemQtyInOrderTest(){
        item.setItemQtyInOrder(3);
        assertEquals(Integer.valueOf(3), item.getItemQtyInOrder());
    }

    @Test
    public void getItemCaffeineTest(){
        assertEquals(Integer.valueOf(20), item.getItemCaffeine());
    }

    @Test
    public void setItemCaffeineTest(){
        item.setItemCaffeine(40);
        assertEquals(Integer.valueOf(40), item.getItemCaffeine());
    }

    @Test
    public void constructorTest1(){
        assertEquals("Tea",item.getItemName());
        Assert.assertEquals(2.98f, item.getItemPrice(),0.0f);
        assertEquals(Integer.valueOf(20), item.getItemCaffeine());
        assertEquals(Integer.valueOf(2),item.getItemQtyInOrder());
    }

    @Test
    public void constructorTest2(){
        item = new Item("Tea",2.98f,20);
        assertEquals("Tea",item.getItemName());
        Assert.assertEquals(2.98f, item.getItemPrice(),0.0f);
        assertEquals(Integer.valueOf(20), item.getItemCaffeine());
        assertEquals(Integer.valueOf(0),item.getItemQtyInOrder());
    }




}
