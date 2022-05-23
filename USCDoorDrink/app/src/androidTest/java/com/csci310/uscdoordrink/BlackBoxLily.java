package com.csci310.uscdoordrink;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)

public class BlackBoxLily {

    private static final String BASIC_SAMPLE_PACKAGE
            = "com.csci310.uscdoordrink";
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String STRING_TO_BE_TYPED = "UiAutomator";
    private UiDevice device;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        // Start from the home screen
        device.pressHome();
        // Wait for launcher
        final String launcherPackage = device.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);
        // Launch the app
        Context context = ApplicationProvider.getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
                LAUNCH_TIMEOUT);
    }
    @Test
    public void CustomerTest()
    {
        UiObject name = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/email"));
        UiObject password = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/password"));
        UiObject button=device.findObject(new UiSelector()
                .packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/btn_login"));
        try {
            name.clickAndWaitForNewWindow();
            name.legacySetText("Jas");
            password.clickAndWaitForNewWindow();
            password.legacySetText("124");
            button.clickAndWaitForNewWindow(5000);

            UiObject MyLocationbutton=device.findObject(new UiSelector()
                    .descriptionContains("My Location"));
            assertEquals(true,MyLocationbutton.exists());
            assertEquals(true,MyLocationbutton.isClickable());
            MyLocationbutton.click();
            Thread.sleep(5000);


            UiObject marker = device.findObject(new UiSelector()
                    .descriptionContains("Boba"));
            marker.clickAndWaitForNewWindow();
            System.out.println(marker.getText());
            assertEquals(true,marker.getContentDescription().contains("driving"));

        } catch (UiObjectNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void MerchantTest()
    {
        UiObject name = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/email"));
        UiObject password = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/password"));
        UiObject button=device.findObject(new UiSelector()
                .packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/btn_login"));
        try {
            name.clickAndWaitForNewWindow();
            name.legacySetText("Boba God");
            password.clickAndWaitForNewWindow();
            password.legacySetText("123");
            button.clickAndWaitForNewWindow(10000);

            UiObject MyLocationbutton=device.findObject(new UiSelector()
                    .descriptionContains("My Location"));
            assertEquals(true,MyLocationbutton.exists());
            assertEquals(true,MyLocationbutton.isClickable());
            MyLocationbutton.click();
            Thread.sleep(10000);


            UiObject marker = device.findObject(new UiSelector()
                    .descriptionContains("Boba"));

            marker.clickAndWaitForNewWindow();
            System.out.println(marker.getText());
            assertEquals(true,marker.getContentDescription().contains("driving"));
        } catch (UiObjectNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    //This following test clicks the order history button
    @Test
    public void CusHistoryButtonTest()
    {
        UiObject name = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/email"));
        UiObject password = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/password"));
        UiObject button=device.findObject(new UiSelector()
                .packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/btn_login"));
        try {
            name.clickAndWaitForNewWindow();
            name.legacySetText("Jas");
            password.clickAndWaitForNewWindow();
            password.legacySetText("124");
            button.clickAndWaitForNewWindow(10000);

            UiObject Historybutton=device.findObject(new UiSelector()
                    .packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/button_order_history"));
            assertEquals(true,Historybutton.isClickable());
            assertEquals(true,Historybutton.getText().equals("HISTORY"));
            Historybutton.clickAndWaitForNewWindow(10000);
            device.waitForWindowUpdate(null, 5000);
            UiObject Screen=device.findObject(new UiSelector()
                    .packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/from"));
            Screen.click();
            Thread.sleep(10000);
            UiObject BackArrow = device.findObject(new UiSelector()
                    .descriptionContains("Navigate up"));
            BackArrow.click();
            Thread.sleep(10000);
            UiObject MyLocationbutton=device.findObject(new UiSelector()
                    .descriptionContains("My Location"));
            assertEquals(true,MyLocationbutton.exists());

        } catch (UiObjectNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void MHistoryButtonTest()
    {
        UiObject name = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/email"));
        UiObject password = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/password"));
        UiObject button=device.findObject(new UiSelector()
                .packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/btn_login"));
        try {
            name.clickAndWaitForNewWindow();
            name.legacySetText("Boba God");
            password.clickAndWaitForNewWindow();
            password.legacySetText("123");
            button.clickAndWaitForNewWindow(10000);

            UiObject Historybutton=device.findObject(new UiSelector()
                    .packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/button_order_history"));
            assertEquals(true,Historybutton.isClickable());
            assertEquals(true,Historybutton.getText().equals("HISTORY"));
            Historybutton.clickAndWaitForNewWindow(10000);
            device.waitForWindowUpdate(null, 10000);
            UiObject Screen=device.findObject(new UiSelector()
                    .packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/from"));
            Screen.click();

            UiObject BackArrow = device.findObject(new UiSelector()
                    .descriptionContains("Navigate up"));
            BackArrow.click();
            Thread.sleep(10000);
            UiObject MyLocationbutton=device.findObject(new UiSelector()
                    .descriptionContains("My Location"));
            assertEquals(true,MyLocationbutton.exists());


        } catch (UiObjectNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void CusProfileButtonTest()
    {
        UiObject name = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/email"));
        UiObject password = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/password"));
        UiObject button=device.findObject(new UiSelector()
                .packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/btn_login"));

        try {
            name.clickAndWaitForNewWindow();
            name.legacySetText("Jas");
            password.clickAndWaitForNewWindow();
            password.legacySetText("124");
            button.clickAndWaitForNewWindow(10000);

            UiObject Profilebutton=device.findObject(new UiSelector()
                    .packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/button_profile"));
            assertEquals(true,Profilebutton.isClickable());
            assertEquals(true,Profilebutton.getText().equals("PROFILE"));
            Profilebutton.clickAndWaitForNewWindow(10000);
            device.waitForWindowUpdate(null, 5000);
            UiObject Weekly=device.findObject(new UiSelector()
                    .packageName(BASIC_SAMPLE_PACKAGE).text("WEEKLY"));

            Weekly.click();
            Thread.sleep(3000);
            UiObject Monthly=device.findObject(new UiSelector()
                    .packageName(BASIC_SAMPLE_PACKAGE).text("MONTHLY"));

            Monthly.click();
            Thread.sleep(3000);
            UiObject Daily=device.findObject(new UiSelector()
                    .packageName(BASIC_SAMPLE_PACKAGE).text("DAILY"));

            Daily.click();
            Thread.sleep(3000);

        } catch (UiObjectNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void MProfileButtonTest()
    {
        UiObject name = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/email"));
        UiObject password = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/password"));
        UiObject button=device.findObject(new UiSelector()
                .packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/btn_login"));
        try {
            name.clickAndWaitForNewWindow();
            name.legacySetText("Boba God");
            password.clickAndWaitForNewWindow();
            password.legacySetText("123");
            button.clickAndWaitForNewWindow(10000);

            UiObject Profilebutton=device.findObject(new UiSelector()
                    .packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/button_profile"));
            assertEquals(true,Profilebutton.getText().equals("PROFILE"));
            Profilebutton.clickAndWaitForNewWindow(10000);
            Thread.sleep(3000);
            UiObject Historybutton=device.findObject(new UiSelector()
                    .packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/button_order_history"));
            assertEquals(true,Historybutton.exists());


        } catch (UiObjectNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void CusMapButtonTest()
    {
        UiObject name = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/email"));
        UiObject password = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/password"));
        UiObject button=device.findObject(new UiSelector()
                .packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/btn_login"));
        try {
            name.clickAndWaitForNewWindow();
            name.legacySetText("Jas");
            password.clickAndWaitForNewWindow();
            password.legacySetText("124");
            button.clickAndWaitForNewWindow(10000);

            UiObject Mapbutton=device.findObject(new UiSelector()
                    .packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/btn_map"));
            assertEquals(true,Mapbutton.isClickable());
            assertEquals(true,Mapbutton.getText().equals("MAP"));
            Mapbutton.clickAndWaitForNewWindow(10000);
            Thread.sleep(5000);
            UiObject MyLocationbutton=device.findObject(new UiSelector()
                    .descriptionContains("My Location"));
            assertEquals(true,MyLocationbutton.exists());

        } catch (UiObjectNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void MMapButtonTest()
    {
        UiObject name = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/email"));
        UiObject password = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/password"));
        UiObject button=device.findObject(new UiSelector()
                .packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/btn_login"));
        try {
            name.clickAndWaitForNewWindow();
            name.legacySetText("Boba God");
            password.clickAndWaitForNewWindow();
            password.legacySetText("123");
            button.clickAndWaitForNewWindow(10000);

            UiObject Mapbutton=device.findObject(new UiSelector()
                    .packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/btn_map"));
            assertEquals(true,Mapbutton.isClickable());
            assertEquals(true,Mapbutton.getText().equals("MAP"));
            Mapbutton.clickAndWaitForNewWindow(10000);
            Thread.sleep(5000);
            UiObject MyLocationbutton=device.findObject(new UiSelector()
                    .descriptionContains("My Location"));
            assertEquals(true,MyLocationbutton.exists());

        } catch (UiObjectNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void MyLocationandDailyRecommendationTest()
    {
        UiObject name = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/email"));
        UiObject password = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/password"));
        UiObject button=device.findObject(new UiSelector()
                .packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/btn_login"));
        try {
            name.clickAndWaitForNewWindow();
            name.legacySetText("Jas");
            password.clickAndWaitForNewWindow();
            password.legacySetText("124");
            button.clickAndWaitForNewWindow(10000);

            UiObject MyLocationbutton=device.findObject(new UiSelector()
                    .descriptionContains("My Location"));
            assertEquals(true,MyLocationbutton.isClickable());
            MyLocationbutton.click();
            Thread.sleep(5000);

            UiObject RecommendationWindow=device.findObject(new UiSelector().packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/recommendation"));
            assertEquals(true,RecommendationWindow.exists());
            Thread.sleep(5000);
            assertEquals(false,RecommendationWindow.isClickable());

        } catch (UiObjectNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void DirectionTest()
    {
        UiObject name = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/email"));
        UiObject password = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/password"));
        UiObject button=device.findObject(new UiSelector()
                .packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/btn_login"));
        try {
            name.clickAndWaitForNewWindow();
            name.legacySetText("Jas");
            password.clickAndWaitForNewWindow();
            password.legacySetText("124");
            button.clickAndWaitForNewWindow(10000);
            UiObject MyLocationbutton=device.findObject(new UiSelector()
                    .descriptionContains("My Location"));
            MyLocationbutton.click();
            Thread.sleep(5000);

            UiObject marker = device.findObject(new UiSelector()
                    .descriptionContains("Boba"));
            marker.clickAndWaitForNewWindow();
            Thread.sleep(4000);
            UiObject directionButton = device.findObject(new UiSelector()
                    .descriptionContains("Get directions"));
            assertEquals(true,directionButton.exists());
            assertEquals(true,directionButton.isClickable());
            directionButton.click();
            Thread.sleep(4000);
            device.pressHome();




        } catch (UiObjectNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void ExternalMapTest()
    {
        UiObject name = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/email"));
        UiObject password = device.findObject(new UiSelector().resourceId("com.csci310.uscdoordrink:id/password"));
        UiObject button=device.findObject(new UiSelector()
                .packageName(BASIC_SAMPLE_PACKAGE).resourceId("com.csci310.uscdoordrink:id/btn_login"));
        try {
            name.clickAndWaitForNewWindow();
            name.legacySetText("Jas");
            password.clickAndWaitForNewWindow();
            password.legacySetText("124");
            button.clickAndWaitForNewWindow(10000);
            UiObject MyLocationbutton=device.findObject(new UiSelector()
                    .descriptionContains("My Location"));
            MyLocationbutton.click();
            Thread.sleep(5000);

            UiObject marker = device.findObject(new UiSelector()
                    .descriptionContains("Boba"));
            marker.clickAndWaitForNewWindow();
            Thread.sleep(4000);
            UiObject directionButton = device.findObject(new UiSelector()
                    .descriptionContains("Open in Google Maps"));
            assertEquals(true,directionButton.exists());
            assertEquals(true,directionButton.isClickable());
            directionButton.click();
            Thread.sleep(5000);
            device.pressHome();
        } catch (UiObjectNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
