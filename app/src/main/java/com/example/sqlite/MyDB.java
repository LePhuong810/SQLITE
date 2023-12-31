package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class MyDB extends SQLiteOpenHelper {
      public static final String TableName="ContactTable";
    public static final String Id="Id";
    public static final String Name="FullName";
    public static final String Fee="Fee";
    public static final String Image="Image";

    public MyDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MyDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public MyDB(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //tao cau sql de tao bang tablecontext
        String sqlCreate="Create table if not exists "+ TableName +"("
                + Id +" Integer Primary key, "
                + Image +" Text,"
                + Name +" Text,"
                + Fee +" Text)";
        //chay truy van sql de tao bang
        sqLiteDatabase.execSQL(sqlCreate);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //xoa bang tablecontact da co
        sqLiteDatabase.execSQL("Drop table if exists "+ TableName);
        //tao lai
        onCreate(sqLiteDatabase);

    }
    //lay tat ca cac dong cua bang tablecontact tra ve dang arraylist
    public ArrayList<Tour> getAllContact()
    {
        ArrayList<Tour> list=new ArrayList<>();
        //cau truy van
        String sql="Select * from " + TableName;
        //lay doi tuong cua csdl sqlite
        SQLiteDatabase db=this.getReadableDatabase();
        //chay cau truy van tra ve dang curcor
        Cursor cursor =db.rawQuery(sql,null);
        //tao ArrayList<Contact> de tra ve;
        if(cursor!=null)
            while (cursor.moveToNext())
            {
                Tour tour=new Tour(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                cursor.getInt(3));
                list.add(tour);
            }
        return list;
    }
    //ham them moit contact vafo bang tablecontact
    public void addContact(Tour tour)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues value= new ContentValues();
        value.put(Id,tour.getId());
        value.put(Image,tour.getHinhanh());
        value.put(Name,tour.getTourName());
        value.put(Fee,tour.getGia());
        db.insert(TableName,null,value);
        db.close();


    }
    //ham update
     public void updateContact(int id,Tour tour)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues value= new ContentValues();
       value.put(Id,tour.getId());
        value.put(Image,tour.getHinhanh());
        value.put(Name,tour.getTourName());
        value.put(Fee,tour.getGia());
        db.update(TableName,value,Id +"s?",
                new String[]{String.valueOf(id)});
        db.close();


    }
    public void deleteContact(int id)
    {
        SQLiteDatabase db=getWritableDatabase();
        String sql="Delete From " + TableName +"Where ID=" +id;
        db.delete(TableName,Id+"=?",new String[]{String.valueOf(id)});
        db.execSQL(sql);
        db.close();
    }


}
