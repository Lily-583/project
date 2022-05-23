package com.csci310.uscdoordrink;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class TestCart {
    private Cart cart;

    @Before
    public void initialize(){
        ArrayList<Item> list = new ArrayList<>();
        Item tea = new Item("Tea",2.98f,2,40);
        Item coffee = new Item("Coffee", 3.55f, 3, 100);
        Item water = new Item("Water", 0.99f, 1, 0);
        list.add(tea);
        list.add(coffee);
        list.add(water);
        cart = new Cart(list);
    }

    @Test
    public void getItemsInCartTest(){
        ArrayList<Item> tmp = cart.getItemsInCart();
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
    }

    @Test
    public void constructorTest(){
        ArrayList<Item> tmp = cart.getItemsInCart();
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
    }

    @Test
    public void setItemsInCartTest(){
        ArrayList<Item> list = new ArrayList<>();
        Item blackTea = new Item("Black Tea",3.88f,4,60);
        Item cuppucino = new Item("Cuppucino", 2.55f, 2, 50);
        Item greenTea = new Item("Green Tea", 2.59f, 6, 40);
        Item latte = new Item("Latte", 4.99f, 5, 80);
        list.add(blackTea);
        list.add(cuppucino);
        list.add(greenTea);
        list.add(latte);
        cart.setItemsInCart(list);
        ArrayList<Item> tmp = cart.getItemsInCart();
        assertEquals(4, tmp.size());
        for (int i = 0; i < 4; i++){
            if (i == 0){
                assertEquals("Black Tea", tmp.get(i).getItemName());
                assertEquals(3.88f,tmp.get(i).getItemPrice(), 0.0f);
                assertEquals(Integer.valueOf(4),tmp.get(i).getItemQtyInOrder());
                assertEquals(Integer.valueOf(60), tmp.get(i).getItemCaffeine());
            }
            else if (i == 1){
                assertEquals("Cuppucino", tmp.get(i).getItemName());
                assertEquals(2.55f,tmp.get(i).getItemPrice(), 0.0f);
                assertEquals(Integer.valueOf(2),tmp.get(i).getItemQtyInOrder());
                assertEquals(Integer.valueOf(50), tmp.get(i).getItemCaffeine());
            }
            else if (i == 2){
                assertEquals("Green Tea", tmp.get(i).getItemName());
                assertEquals(2.59f,tmp.get(i).getItemPrice(), 0.0f);
                assertEquals(Integer.valueOf(6),tmp.get(i).getItemQtyInOrder());
                assertEquals(Integer.valueOf(40), tmp.get(i).getItemCaffeine());
            }
            else{
                assertEquals("Latte", tmp.get(i).getItemName());
                assertEquals(4.99f,tmp.get(i).getItemPrice(), 0.0f);
                assertEquals(Integer.valueOf(5),tmp.get(i).getItemQtyInOrder());
                assertEquals(Integer.valueOf(80), tmp.get(i).getItemCaffeine());
            }
        }
    }

    @Test
    public void addItemTest(){
        Item boba = new Item("Boba", 6.66f, 2, 30);
        cart.addItem(boba);
        assertEquals(4, cart.getItemsInCart().size());
        assertEquals("Boba", cart.getItemsInCart().get(3).getItemName());
        assertEquals(6.66f, cart.getItemsInCart().get(3).getItemPrice(), 0.0f);
        assertEquals(Integer.valueOf(2), cart.getItemsInCart().get(3).getItemQtyInOrder());
        assertEquals(Integer.valueOf(30), cart.getItemsInCart().get(3).getItemCaffeine());
    }
}
