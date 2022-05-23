package com.csci310.uscdoordrink;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.CoreMatchers.not;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class Create_Menu_UI {

    /**
     * this name has to match the test_merchant_name in the previously ran Register_UI.java
     * ex: change test_customer_name1 to test_customer_name2, etc
     */
    String test_merchant_name= "test_merchant_name1";

    @Rule
    public ActivityTestRule<AddToMenu> activityTestRule =
            new ActivityTestRule<AddToMenu>(AddToMenu.class){
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra("userID", test_merchant_name);
                    return intent;
                }
            };

    @Test
    public void a_check_Merchant_Add_Item_to_Menu() throws InterruptedException {

        //enter information for a single item
        onView(withId(R.id.itemName)).perform(typeText("Ricapple"), closeSoftKeyboard());
        onView(withId(R.id.itemPrice)).perform(typeText("6.99"), closeSoftKeyboard());
        onView(withId(R.id.itemCaf)).perform(typeText("200"), closeSoftKeyboard());

        //add the item to menu
        onView(withId(R.id.add)).perform(click());//check the merchant box

        //enter information for another item
        onView(withId(R.id.itemName)).perform(replaceText("Jelly Tea"), closeSoftKeyboard());
        onView(withId(R.id.itemPrice)).perform(replaceText("4.99"), closeSoftKeyboard());
        onView(withId(R.id.itemCaf)).perform(replaceText("100"), closeSoftKeyboard());
        //add the item to menu
        onView(withId(R.id.add)).perform(click());
        onView(withId(R.id.submit_menu)).check(matches(isDisplayed()));
    }

    @Test
    public void b_check_Merchant_Remove_Item_from_Menu() throws InterruptedException {
        //enter information for a single item
        onView(withId(R.id.itemName)).perform(typeText("Ricapple"), closeSoftKeyboard());
        onView(withId(R.id.itemPrice)).perform(typeText("6.99"), closeSoftKeyboard());
        onView(withId(R.id.itemCaf)).perform(typeText("200"), closeSoftKeyboard());
        //add the item to menu
        onView(withId(R.id.add)).perform(click());//check the merchant box
        //remove both items from the menu
        onView(withId(R.id.remove)).perform(click());
        onView(withId(R.id.remove)).check(doesNotExist());
    }

    @Test
    public void c_check_Merchant_Submit_Menu() {
        //submit the menu
        onView(withId(R.id.submit_menu)).perform(click());
        onView(withId(R.id.submit_location)).check(matches(isDisplayed()));
    }

}
