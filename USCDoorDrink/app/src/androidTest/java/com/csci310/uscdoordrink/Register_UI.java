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

import android.util.Log;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class Register_UI {


    String test_customer_name= "test_customer_name1";
    String test_merchant_name= "test_merchant_name1";

    /**
     * this function will be called in tests that involves inserting new data into the data base
     * so that the tests may be ran without changing parameters for multiple times
     */
    void rollBackSQL(String a){
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM sys.USER WHERE USERNAME = '" + a+"'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
    }

    void rollBackSQLCustomer(String a){
        try
        {
            Connection con= CONN();
            String query = "DELETE FROM sys.CUSTOMER WHERE USERNAME = '" + a+"'";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
    }


    @Rule
    public ActivityTestRule<Register> activityTestRule = new ActivityTestRule<>(Register.class);

    @Test
    public void check_Merchant_Successful_Register() throws InterruptedException {
        onView(withId(R.id.usernameR)).perform(typeText(test_merchant_name), closeSoftKeyboard());
        onView(withId(R.id.passwordR))
                .perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.checkbox_isMerchant)).perform(click());//check the merchant box
        onView(withId(R.id.btn_register_acc)).perform(click()); //register account
        //wait for the view to be loaded
        Thread.sleep(5000);
        onView(withId(R.id.submit_menu)).check(matches(isDisplayed()));
        rollBackSQL(test_merchant_name);
    }

    @Test
    public void check_Customer_Successful_Register() throws InterruptedException {
        onView(withId(R.id.usernameR)).perform(typeText(test_customer_name), closeSoftKeyboard());
        onView(withId(R.id.passwordR)).perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.btn_register_acc)).perform(click()); //register account
        //wait for the view to be loaded
        Thread.sleep(5000);
        onView(withId(R.id.buttons)).check(matches(isDisplayed()));
        rollBackSQL(test_customer_name);
        rollBackSQLCustomer(test_customer_name);
    }

    @Test
    public void checkRegisterWithoutPassword() throws InterruptedException {
        onView(withId(R.id.usernameR)).perform(typeText("test_username"), closeSoftKeyboard());
        onView(withId(R.id.btn_register_acc)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.passwordR)).check(matches(hasErrorText("Password is empty")));
    }

    @Test
    public void checkRegisterWithoutUserName() throws InterruptedException {
        onView(withId(R.id.passwordR)).perform(typeText("test_password"), closeSoftKeyboard());
        onView(withId(R.id.btn_register_acc)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.usernameR)).check(matches(hasErrorText("Username is empty")));
    }

}
