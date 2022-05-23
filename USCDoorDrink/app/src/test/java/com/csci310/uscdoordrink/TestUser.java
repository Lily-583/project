package com.csci310.uscdoordrink;
import static org.junit.Assert.*;
import org.junit.*;

public class TestUser {
    private User usr;

    @Before
    public void initialize(){
        usr = new User("Victor", "1234");
    }

    @Test
    public void getUsrNameTest(){
        assertEquals("Victor",usr.getUsrName());
    }

    @Test
    public void getUsrPassword(){
        assertEquals("1234",usr.getUsrPassword());
    }

    @Test
    public void constructorTest(){
        assertEquals("Victor",usr.getUsrName());
        assertEquals("1234",usr.getUsrPassword());
    }

    @Test
    public void setUsrName(){
        usr.setUsrName("Maria");
        assertEquals("Maria", usr.getUsrName());
    }

    @Test
    public void setUsrPassword(){
        usr.setUsrPassword("23456");
        assertEquals("23456", usr.getUsrPassword());
    }


}
