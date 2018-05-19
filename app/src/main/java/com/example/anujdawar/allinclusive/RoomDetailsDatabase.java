package com.example.anujdawar.allinclusive;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RoomDetailsDatabase extends SQLiteOpenHelper {

//    Room Name
//    IP ADDRESS
//    PORT NUMBER
//    DEVICE NAME 1
//    DEVICE NAME 2
//    DEVICE NAME 3
//    DEVICE NAME 4
//    DEVICE NAME 5
//    DIGITAL 1
//    DIGITAL 2
//    DIGITAL 3
//    DIGITAL 4
//    DIGITAL 5

    public static final String DATABASE_NAME = "Room_details_database.db";
    public static final String TABLE_NAME = "Room_Details";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "ROOM_NAME";
    public static final String COL_3 = "IP_ADDRESS";
    public static final String COL_4 = "PORT_NUMBER";
    public static final String COL_5 = "DEVICE_NAME_1";
    public static final String COL_6 = "DEVICE_NAME_2";
    public static final String COL_7 = "DEVICE_NAME_3";
    public static final String COL_8 = "DEVICE_NAME_4";
    public static final String COL_9 = "DEVICE_NAME_5";
    public static final String COL_10 = "TYPE_1";
    public static final String COL_11 = "TYPE_2";
    public static final String COL_12 = "TYPE_3";
    public static final String COL_13 = "TYPE_4";
    public static final String COL_14 = "TYPE_5";

    public RoomDetailsDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY,ROOM_NAME TEXT,IP_ADDRESS TEXT,PORT_NUMBER TEXT," +
                "DEVICE_NAME_1 TEXT,DEVICE_NAME_2 TEXT,DEVICE_NAME_3 TEXT,DEVICE_NAME_4 TEXT,DEVICE_NAME_5 TEXT," +
                "TYPE_1 TEXT,TYPE_2 TEXT,TYPE_3 TEXT,TYPE_4 TEXT,TYPE_5 TEXT)");
    }

    public boolean insertData(String id, String Room_Name, String IP_Address, String Port_Number,
                              String Device_Name1, String Device_Name2, String Device_Name3, String Device_Name4, String Device_Name5,
                              String Type1, String Type2, String Type3, String Type4, String Type5)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, id);
        contentValues.put(COL_2, Room_Name);
        contentValues.put(COL_3, IP_Address);
        contentValues.put(COL_4, Port_Number);
        contentValues.put(COL_5, Device_Name1);
        contentValues.put(COL_6, Device_Name2);
        contentValues.put(COL_7, Device_Name3);
        contentValues.put(COL_8, Device_Name4);
        contentValues.put(COL_9, Device_Name5);
        contentValues.put(COL_10, Type1);
        contentValues.put(COL_11, Type2);
        contentValues.put(COL_12, Type3);
        contentValues.put(COL_13, Type4);
        contentValues.put(COL_14, Type5);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor viewAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public Integer deleteData(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }

    public boolean updateData(String id, String Room_Name, String IP_Address, String Port_Number,
                              String Device_Name1, String Device_Name2, String Device_Name3, String Device_Name4, String Device_Name5,
                              String Type1, String Type2, String Type3, String Type4, String Type5)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, id);
        contentValues.put(COL_2, Room_Name);
        contentValues.put(COL_3, IP_Address);
        contentValues.put(COL_4, Port_Number);
        contentValues.put(COL_5, Device_Name1);
        contentValues.put(COL_6, Device_Name2);
        contentValues.put(COL_7, Device_Name3);
        contentValues.put(COL_8, Device_Name4);
        contentValues.put(COL_9, Device_Name5);
        contentValues.put(COL_10, Type1);
        contentValues.put(COL_11, Type2);
        contentValues.put(COL_12, Type3);
        contentValues.put(COL_13, Type4);
        contentValues.put(COL_14, Type5);

        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});

        return true;
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
    }
}
