package com.csci310.uscdoordrink;

import static com.csci310.uscdoordrink.ConnectionClass.CONN;

import android.os.AsyncTask;
import android.os.Parcelable;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;

public class RecommendationQuery {
    HashMap<String,String>result=new HashMap<>();
    //    String merchant;
//    String item;
    public String getMerchant(String customerName) {
        //String s=null;
        List<HashMap<String,String>> allResult=new ArrayList<HashMap<String,String>>();
        try
        {
            Connection con= CONN();
//            connection =conStr.CONN();
            String query ="Select UserName_Merchant,Count(UserName_Merchant) as count" +" from sys.ORDER" +" where UserName_Customer="+"'"+customerName+"'"+" group by UserName_Merchant" +" Order by count DESC;";
            //System.out.println("GetQUery: "+query);
            Statement stmt = con.createStatement();

            ResultSet rs=stmt.executeQuery(query);
            if(rs.isBeforeFirst() ) {

                rs.next();
                System.out.println("Debug1");
                System.out.println(rs.getString("UserName_Merchant"));
                return (rs.getString("UserName_Merchant"));
            }
//                rs.next();
//                System.out.println("Debug2");
//                System.out.println(rs.getString("UserName_Merchant"));
//                return rs.getString("UserName_Merchant");
            //}
            //            if(rs!=null&&rs.next()!=false) {
//                rs.next();
//                System.out.println("Helll");
//                System.out.println("customerName "+customerName);
//                System.out.println(rs.getString("UserName_Merchant"));
//
//                return rs.getString("UserName_Merchant");
//            }

        }
        catch (SQLException se)
        {
            se.printStackTrace();
        }
        return null;
    }
}




