package com.csci310.uscdoordrink;

import static com.csci310.uscdoordrink.MapsActivity.downloadUrl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.mysql.fabric.xmlrpc.base.Array;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(MockitoJUnitRunner.class)

public class WhiteBoxLily {

    @Test
    public void GetDurationTest1() throws InterruptedException {
//        final List<HashMap<String, String>>[] allResult;
//        allResult=new ArrayList<HashMap<String,String>>()[1];
        final String[] s = new String[1];
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    String a = "https://maps.googleapis.com/maps/api/directions/json?origin=Disneyland&destination=Universal+Studios+Hollywood&key=AIzaSyAPLYER1TPpF9RSypNubp_yz6TzCG5hogk";
                    s[0] = MapsActivity.getDuration(a);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        Thread.sleep(10000);
        assertEquals("47 mins", s[0]);
    }


    @Test
    public void insertDanielTest1() throws InterruptedException {
        // final HashMap<String, String>[] hm = new HashMap[]{new HashMap<>()};
        final List<HashMap<String, String>>[] allResult=new ArrayList[]{new ArrayList<HashMap<String,String>>()};
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    insertDaniel in=new insertDaniel();
                    allResult[0]=in.doInBackground();
//                    hm[0] =in.doInBackground().get(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        Thread.sleep(10000);
        //assertEquals(true,hm[0].containsKey("username"));
        System.out.println(allResult[0]);
        assertEquals(true,allResult[0].get(0).containsKey("username"));
        assertEquals(true,allResult[0].get(0).containsKey("latitude"));
        assertEquals(true,allResult[0].get(0).containsKey("lon"));
    }

    @Test
    public void RecommendationTestNormal() throws InterruptedException {
        final String[] s = new String[1];
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    RecommendationQuery q=new RecommendationQuery();
                    s[0] =q.getMerchant("Jas");
//                    hm[0] =in.doInBackground().get(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        Thread.sleep(10000);
        System.out.println(s[0]);
        assertEquals(true,s!=null);
    }

    @Test
    public void RecommendationNoSuchCustomer() throws InterruptedException {
        final String[] s = new String[1];
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    RecommendationQuery q=new RecommendationQuery();
                    s[0] =q.getMerchant("NoName");
//                    hm[0] =in.doInBackground().get(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        Thread.sleep(10000);
//        System.out.println(s[0]);
        assertEquals(null,s[0]);
    }

    @Test
    public void RecommendationTestNULL() throws InterruptedException {
        final String[] s = new String[1];
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    RecommendationQuery q=new RecommendationQuery();
                    s[0] =q.getMerchant(null);
//                    hm[0] =in.doInBackground().get(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        Thread.sleep(10000);
//        System.out.println(s[0]);
        assertEquals(null,s[0]);
    }

    @Test
    public void RecommendationNoOrder() throws InterruptedException {
        final String[] s = new String[1];
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    RecommendationQuery q=new RecommendationQuery();
                    s[0] =q.getMerchant("No Order");
//                    hm[0] =in.doInBackground().get(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        Thread.sleep(10000);
//        System.out.println(s[0]);
        assertEquals(null,s[0]);
    }
}