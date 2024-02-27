package com.tish.e_cards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class StatisticConnector {
    static String TABLE_NAME = "statistic";
    static String COLUMN_STAT_ID = "_id";
    static String COLUMN_STAT_TYPE = "Type";
    static String COLUMN_STAT_FOLDER = "Folder";
    static String COLUMN_STAT_TAG = "Tag";
    static String COLUMN_STAT_RESULT = "Result";
    public static final String CREATE_TABLE_STAT = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" +
            COLUMN_STAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_STAT_TYPE + " TEXT NOT NULL, " +
            COLUMN_STAT_FOLDER + " TEXT, " +
            COLUMN_STAT_TAG + " TEXT, " +
            COLUMN_STAT_RESULT + " TEXT NOT NULL)";

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor statCursor;

    public StatisticConnector(Context context) {
        dbHelper = new DBHelper(context);
    }

    public List<StatisticItem> getStatistic(String trainType) {
        List<StatisticItem> statisticItems = new ArrayList<StatisticItem>();
        db = dbHelper.getReadableDatabase();
        if (!trainType.equals("Нет"))
            statCursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_STAT_TYPE + "=?", new String[]{trainType});
        else
            statCursor = db.rawQuery("select * from " + TABLE_NAME, null);

        statCursor.moveToFirst();
        while (statCursor.moveToNext()) {
            String type = statCursor.getString(statCursor.getColumnIndex(COLUMN_STAT_TYPE));
            String folder = statCursor.getString(statCursor.getColumnIndex(COLUMN_STAT_FOLDER));
            String tag = statCursor.getString(statCursor.getColumnIndex(COLUMN_STAT_TAG));
            String result = statCursor.getString(statCursor.getColumnIndex(COLUMN_STAT_RESULT));
            statisticItems.add(new StatisticItem(type, folder, tag, result));
        }
        statCursor.close();
        db.close();

        return statisticItems;
    }

    public void saveStatistic(StatisticItem statistic) {
        ContentValues cvNewStat = new ContentValues();
        cvNewStat.put("type", statistic.getType());
        cvNewStat.put("folder", statistic.getFolder());
        cvNewStat.put("tag", statistic.getTag());
        cvNewStat.put("result", statistic.getResult());
        db = dbHelper.getReadableDatabase();
        db.insert(TABLE_NAME, null, cvNewStat);
        db.close();
    }
}
