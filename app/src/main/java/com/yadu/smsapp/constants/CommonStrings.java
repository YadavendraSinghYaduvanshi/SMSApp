package com.yadu.smsapp.constants;

/**
 * Created by yadu on 4/3/18.
 */

public class CommonStrings {

    public static String KEY_CONTACTS = "CONTACTS";
    public static String OTP_MSG = "Hi. Your OTP is: ";

    public static final String TABLE_SEND_MSG = "SEND_MSG";
    public static final String KEY_ID = "Id";
    public static final String KEY_NAME = "RECEIVER_NAME";
    public static final String KEY_OTP = "OTP";
    public static final String KEY_TIME = "MSG_TIME";
    public static final String KEY_CONTACT = "CONTACT";

    public static final String CREATE_TABLE_SEND_MSG = "CREATE TABLE "
            + TABLE_SEND_MSG + " (" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + KEY_NAME + " VARCHAR,"
            + KEY_OTP + " VARCHAR,"
            + KEY_TIME + " VARCHAR,"
            + KEY_CONTACT + " VARCHAR)";
}
