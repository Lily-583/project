package com.csci310.uscdoordrink;
import static com.csci310.uscdoordrink.ConnectionClass.CONN;
import static org.junit.Assert.*;

import android.util.Log;

import org.junit.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestInsertMenu {
    @Test
    public void InsertMenuTest(){
        Item herbal = new Item("Herbal Tea",3.33f,15);
        insertMenu.InsertMenu(herbal,"Tea King");
        int itemID = 0;
        try
        {
            Connection con= CONN();
            String query = "SELECT * FROM sys.MENU WHERE UserName = 'Tea King' AND ItemPrice like 3.33 AND ItemCaffeineAmount = 15";
            Statement stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
            int counter = 0;
            while (result.next()){
                itemID = result.getInt("ItemID");
                counter++;
            }
            assertTrue(itemID != 0);
            assertEquals(1,counter);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }

        try
        {
            Connection con= CONN();
            String query = "DELETE FROM sys.MENU WHERE ItemID = " + String.valueOf(itemID);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }

    }
}
