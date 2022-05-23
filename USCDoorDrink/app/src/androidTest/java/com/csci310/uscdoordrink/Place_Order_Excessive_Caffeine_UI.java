package com.csci310.uscdoordrink;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;
import android.view.View;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class Place_Order_Excessive_Caffeine_UI {

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }


    @Rule
    public ActivityTestRule<MenuActivity> activityTestRule =
            new ActivityTestRule<MenuActivity>(MenuActivity.class){
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra("merchantID", "Tea Kingdom");
                    intent.putExtra("customerID", "CaffeineLover");
                    intent.putExtra("NeededTime", 5);
                    return intent;
                }
            };

    @Test
    public void checkExcessiveCaffeine() throws InterruptedException {
        Thread.sleep(2000);
        onView(withIndex(withId(R.id.addToCartButton),1)).perform(click());
        int a = 20;
        while(a>0){
            onView(withIndex(withId(R.id.imageAdd),1)).perform(click());
            a--;
        }
        onView(withId(R.id.buttonCheckout)).perform(click());
        Thread.sleep(4000);
        onView(withId(R.id.buttonPlaceOrder)).perform(click());
        Thread.sleep(4000);
        onView(withId(R.id.buttonPlaceOrder)).perform(click());
        Thread.sleep(4000);
        onView(withId(R.id.button_map)).check(matches(isDisplayed()));
    }

}