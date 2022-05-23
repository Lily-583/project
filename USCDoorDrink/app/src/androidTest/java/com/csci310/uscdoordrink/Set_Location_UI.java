package com.csci310.uscdoordrink;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;

import static com.csci310.uscdoordrink.ConnectionClass.CONN;

import android.content.Intent;
import android.util.Log;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class Set_Location_UI {





    /**
     * this function will be called in tests that involves inserting new data into the data base
     * so that the tests may be ran without changing parameters for multiple times
     */
    void rollBackSQL(){
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM sys.MERCHANT WHERE USERNAME = '" + "test_merchant_name1"+"'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
    }


    @Rule
    public ActivityTestRule<EnterLocation> activityTestRule = new ActivityTestRule<EnterLocation>(EnterLocation.class){
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            intent.putExtra("userID", "test_merchant_name1");
            return intent;
        }
    };

    @Test
    public void check_Merchant_Successful_EnterLocation() throws InterruptedException {
        onView(withId(R.id.Latitude)).perform(typeText("129.0"), closeSoftKeyboard());
        onView(withId(R.id.Longitude)).perform(typeText("-123.1"), closeSoftKeyboard());
        onView(withId(R.id.submit_location)).perform(click()); //register account
        //wait for the view to be loaded
        Thread.sleep(5000);
        onView(withId(R.id.button_map)).check(matches(isDisplayed()));
        rollBackSQL();
    }

}
