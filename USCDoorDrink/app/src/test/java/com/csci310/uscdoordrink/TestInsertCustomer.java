package com.csci310.uscdoordrink;
import static com.csci310.uscdoordrink.ConnectionClass.CONN;

import org.junit.*;
import static org.junit.Assert.*;

import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestInsertCustomer {
    private insertCustomer ic = new insertCustomer();

    @Test
    public void insertCusTest(){
        ic.insertCus("Maria");
        try
        {
            Connection con= CONN();
            String query = "SELECT * FROM sys.CUSTOMER WHERE UserName = 'Maria' AND CaffeineIntake = 0";
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
            String query = "DELETE FROM sys.CUSTOMER WHERE UserName = 'Maria'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
    }
}
