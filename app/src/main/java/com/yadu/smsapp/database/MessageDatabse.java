package com.yadu.smsapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yadu.smsapp.constants.CommonStrings;
import com.yadu.smsapp.gettersetter.MessageGetterSetter;

import java.util.ArrayList;

/**
 * Created by yadu on 4/3/18.
 */

public class MessageDatabse extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MSG_DATABASE_1";
    public static final int DATABASE_VERSION = 4;
    private SQLiteDatabase db;
    Context context;

    public MessageDatabse(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void open() {
        try {
            db = this.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CommonStrings.CREATE_TABLE_SEND_MSG);
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean InsertMessageData(MessageGetterSetter msg) {
        ContentValues values = new ContentValues();
        try {

                values.put(CommonStrings.KEY_NAME, msg.getName());
                values.put(CommonStrings.KEY_OTP, msg.getOtp());
                values.put(CommonStrings.KEY_TIME, msg.getTime());
                values.put(CommonStrings.KEY_CONTACT, msg.getMob_no());

                long id = db.insert("SEND_MSG", null, values);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Exception ", " in MSG insert " + ex.toString());
            return false;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList<MessageGetterSetter> getCityMer() {
        Log.d("Fetchingdata-Start<-",
                "------------------");
        ArrayList<MessageGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * FROM SEND_MSG ORDER BY MSG_TIME DESC" , null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MessageGetterSetter msg = new MessageGetterSetter();

                    msg.setName(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonStrings.KEY_NAME)));
                    msg.setOtp(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonStrings.KEY_OTP)));
                    msg.setTime(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonStrings.KEY_TIME)));
                    msg.setMob_no(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonStrings.KEY_CONTACT)));


                    list.add(msg);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching",
                    e.toString());
            return list;
        }

        return list;
    }
}
