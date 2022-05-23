package com.csci310.uscdoordrink;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class Login_UI {

    @Rule
    public ActivityTestRule<Login> activityTestRule =
            new ActivityTestRule<>(Login.class);

    @Test
    public void checkShowRegisterView() {
        onView(withId(R.id.btn_register)).perform(click());
        onView(withId(R.id.passwordR)).check(matches(isDisplayed()));
    }

    @Test
    public void checkSuccessFulLogin() throws InterruptedException {
        onView(withId(R.id.email)).perform(typeText("Leo"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("1223"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        //wait for the view to be loaded
        Thread.sleep(5000);
        onView(withId(R.id.buttons)).check(matches(isDisplayed()));
    }

    @Test
    public void checkFailedLogin() throws Exception {
        onView(withId(R.id.email)).perform(typeText("Leo"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        onView(withId(R.id.email)).check(matches(isDisplayed()));
    }

    @Test
    public void checkMissingPassword() throws InterruptedException {
        onView(withId(R.id.email)).perform(typeText("Leo"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.password)).check(matches(hasErrorText("Password is empty")));
    }

    @Test
    public void checkMissingUserName() throws InterruptedException {
        onView(withId(R.id.password)).perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.email)).check(matches(hasErrorText("Username is empty")));
    }

}