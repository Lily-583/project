package com.csci310.uscdoordrink;
import static com.csci310.uscdoordrink.ConnectionClass.CONN;

import org.junit.*;
import static org.junit.Assert.*;

import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestInsertLeo {
    private insertLeo il = new insertLeo();

    @Test
    public void insertIntoSqlTest1(){
        boolean success = il.insertIntoSql("Victor", "123",0);
        assertFalse(success);
    }

    @Test
    public void insertIntoSqlTest2(){
        boolean success = il.insertIntoSql("Maria", "123",0);
        assertTrue(success);
        try
        {
            Connection con= CONN();
            String query = "SELECT * FROM sys.USER WHERE UserName = 'Maria' AND UserPassword = '123' AND IsMerchant = 0";
            Statement stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
            int counter = 0;
            while (result.next()){
                counter++;
            }
            assertEquals(1,counter);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }

        try
        {
            Connection con= CONN();
            String query = "DELETE FROM sys.USER WHERE UserName = 'Maria'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
    }

    @Test
    public void insertIntoSqlTest3(){
        boolean success = il.insertIntoSql("Boba Queen", "123",1);
        assertTrue(success);
        try
        {
            Connection con= CONN();
            String query = "SELECT * FROM sys.USER WHERE UserName = 'Boba Queen' AND UserPassword = '123' AND IsMerchant = 1";
            Statement stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
            int counter = 0;
            while (result.next()){
                counter++;
            }
            assertEquals(1,counter);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }

        try
        {
            Connection con= CONN();
            String query = "DELETE FROM sys.USER WHERE UserName = 'Boba Queen'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
    }

    @Test
    public void insertIntoSqlTest4(){
        boolean success = il.insertIntoSql("Tea King", "123",1);
        assertFalse(success);
    }

}
