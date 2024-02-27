package com.tish.e_cards;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "ecard.db";
    private static final int DB_VERSION = 5;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FoldersConnector.CREATE_TABLE_FOLDERS);
        db.execSQL(FoldersConnector.ADD_FOLDER_ALL);
        db.execSQL(CardsConnector.CREATE_TABLE_CARDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            //db.execSQL(FoldersConnector.CREATE_TABLE_FOLDERS);
            //db.execSQL(FoldersConnector.ADD_FOLDER_ALL);
            //db.execSQL(CardsConnector.CREATE_TABLE_CARDS);
            db.execSQL(StatisticConnector.CREATE_TABLE_STAT);
        }
    }
}
