package com.csci310.uscdoordrink;
import static com.csci310.uscdoordrink.ConnectionClass.CONN;
import static org.junit.Assert.*;

import android.util.Log;

import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestInsertLocation {
    private insertLocation il = new insertLocation();
    @Test
    public void insertIntoSqlTest(){
        il.insertIntoSql("Boba Queen",2.55f, 2.46f);
        try
        {
            Connection con= CONN();
            String query = "SELECT * FROM sys.MERCHANT WHERE UserName = 'Boba Queen' AND Location_Latitude like 2.55 AND Location_Longtitude like 2.46";
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
            String query = "DELETE FROM sys.MERCHANT WHERE UserName = 'Boba Queen'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
    }
}
