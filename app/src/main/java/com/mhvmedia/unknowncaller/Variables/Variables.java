package com.mhvmedia.unknowncaller.Variables;
/** Created by AwsmCreators * */
public class Variables {

    ////Google play product id
    public static final String SMALL_PACK_ID="calling_20rupees";
    public static final String MEDIUM_PACK_ID="calling_50";
    public static final String BIG_PACK_ID="calling_100";

    //Setup google pay(tez) upi payment id for indian users only
    public static String UPI_ID ="sn3hashis@okhdfcbank";
    public static String UPI_NAME = "Snehashis Mukherjee";

    //enable or disable UPI   //True to Enable, False to Disable
    public static Boolean ENABLE_UPI = true;

    //set your own welcome credits // Do not put number alone/ for example if u want to give 100 credits, make it 100.0
    //or if you want to set 0 , make it 0.0 ==== otherwise app will crash
    public static Double WELCOME_CREDITS = 80.0;

    //minimum credits need to make a call
    public static Double MINIMUM_CREDITS = 60.0;

    //Minimum call rate if somehow system undetected the country code
    public static Double MINIMUM_CALL_RATE = 120.0;

    //Setup credit packs
    public static Integer SMALL_PACK_CREDITS = 400;
    public static Integer MEDIUM_PACK_CREDITS = 1000;
    public static Integer BIG_PACK_CREDITS = 2000;

    //setup pack names
    public static String SMALL_PACK_NAME = "Mini pack";
    public static String MEDIUM_PACK_NAME = "Smart pack";
    public static String BIG_PACK_NAME = "Big bundle";

    //SETUP PACKAGE COST FOR (UPI) INDIAN USERS (INR) Indian rupee (put same amount as google play billing)
    public static Integer SMALL_PACK_COST = 20;//INR
    public static Integer MEDIUM_PACK_COST = 50;//INR
    public static Integer BIG_PACK_COST = 100;//INR

    //ENABLE OR DISABLE REFERRAL SYSTEM
    public static Boolean ENABLE_REFERRAL = true;
    public static Double REFERRAL_POINTS = 50.0;

    //FIRST MIN CALL COST (if u enable this option, app will deduct credits for the first min as advanced even if user talks less then 1 min)
    //Because sinch also cost you for first min based
    public static Boolean ENABLE_ADVANCE_CREDITS_DEDUCTION = true;
}
