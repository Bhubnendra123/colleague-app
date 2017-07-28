/*
 *  This file contains Good Sample Code subject to the Good Dynamics SDK Terms and Conditions.
 *  (c) 2016 Good Technology Corporation. All rights reserved.
 */

package com.good.gd.example.skeleton;

import android.support.test.runner.AndroidJUnit4;

import com.good.automated_test_support.GDAutomatedTestSupport;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/*
Tests purpose - Ensure SecureSQL sample app correct basic operation
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class test_suite1 {

    /*
    Note - The test order of these tests is significant hence the explict test numbering
     */
    
    /*
    Setup Test, like all tests makes use of helper functions in GD_UIAutomator_Lib Test library project
     */
    @BeforeClass
    public static void setUpClass() {

        //Setup test support and register a GDStateListener
        GDAutomatedTestSupport.setupGDAutomatedTestSupport();

        GDAutomatedTestSupport.wakeUpDeviceIfNeeded();

        //Android Emulator when booted sometimes has error dialogues to dismiss
        GDAutomatedTestSupport.acceptSystemDialogues();

    }

    /*
    Test 1, This is a negative test case which will fail because of invalid GDApplicationID. User can manually modifiy the GDApplicationID to get the
    app activation pass
    */
    @Test
    public void test_1_Skeleton( ) throws Exception {

        GDAutomatedTestSupport.launchAppUnderTest();

        assertTrue(GDAutomatedTestSupport.loginOrProvisionGDApp());

        assertTrue(GDAutomatedTestSupport.checkGDAuthorized());

        GDAutomatedTestSupport.pressHome();
    }



    @After
    public void tearDown() throws Exception {

    }
}