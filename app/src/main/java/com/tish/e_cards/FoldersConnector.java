package com.tish.e_cards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FoldersConnector {
    static String TABLE_NAME = "folders";
    static String COLUMN_FOLDER_ID = "_id";
    static String COLUMN_FOLDER_NAME = "FolderName";
    static String COLUMN_FOLDER_CARD_AMOUNT = "CardAmount";
    static String COLUMN_FOLDER_LEARNED_CARD_AMOUNT = "LearnedCardAmount";
    public static final String CREATE_TABLE_FOLDERS = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" +
            COLUMN_FOLDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FOLDER_NAME + " TEXT NOT NULL, " +
            COLUMN_FOLDER_CARD_AMOUNT + " INTEGER NOT NULL, " +
            COLUMN_FOLDER_LEARNED_CARD_AMOUNT + " INTEGER NOT NULL" + ")";
    public static final String ADD_FOLDER_ALL = "INSERT INTO " + TABLE_NAME + " (" +
            COLUMN_FOLDER_NAME + ", " + COLUMN_FOLDER_CARD_AMOUNT + ", " +
            COLUMN_FOLDER_LEARNED_CARD_AMOUNT + ") VALUES ('Все слова', 0, 0)";

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor folderCursor;

    public FoldersConnector(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public List<Folder> getAllFolders() {
        List<Folder> folders = new ArrayList<Folder>();
        db = dbHelper.getReadableDatabase();
        folderCursor = db.rawQuery("select * from " + TABLE_NAME, null);
        folderCursor.moveToFirst();
        while (folderCursor.moveToNext()) {
            String folderName = folderCursor.getString(folderCursor.getColumnIndex(COLUMN_FOLDER_NAME));
            int cardAmount = folderCursor.getInt(folderCursor.getColumnIndex(COLUMN_FOLDER_CARD_AMOUNT));
            int learnedCardAmount = folderCursor.getInt(folderCursor.getColumnIndex(COLUMN_FOLDER_LEARNED_CARD_AMOUNT));
            folders.add(new Folder(folderName, cardAmount, learnedCardAmount));
        }
        folderCursor.close();
        db.close();
        return folders;
    }

    public List<String> getAllFolderNames() {
        List<String> folderNames = new ArrayList<String>();
        db = dbHelper.getReadableDatabase();
        folderCursor = db.rawQuery("select " + COLUMN_FOLDER_NAME + " from " + TABLE_NAME, null);
        folderCursor.moveToFirst();
        while (folderCursor.moveToNext()) {
            String folderName = folderCursor.getString(folderCursor.getColumnIndex(COLUMN_FOLDER_NAME));
            folderNames.add(folderName);
        }
        folderCursor.close();
        db.close();
        return folderNames;
    }

    public long insertNewFolder(String newFolderName) {
        ContentValues cvNewFolder = new ContentValues();
        cvNewFolder.put(COLUMN_FOLDER_NAME, newFolderName);
        cvNewFolder.put(COLUMN_FOLDER_CARD_AMOUNT, 0);
        cvNewFolder.put(COLUMN_FOLDER_LEARNED_CARD_AMOUNT, 0);
        db = dbHelper.getWritableDatabase();
        long result = db.insert(TABLE_NAME, null, cvNewFolder);
        db.close();
        return result;
    }

    public void updateCardAmount(String folderName) {
        int currentCardAmount = getCardAmountInFolder(folderName);
        if (currentCardAmount != -1) {
            db = dbHelper.getWritableDatabase();
            ContentValues cvUpdateFolder = new ContentValues();
            cvUpdateFolder.put(COLUMN_FOLDER_CARD_AMOUNT, (currentCardAmount + 1));
            db.update(TABLE_NAME, cvUpdateFolder, COLUMN_FOLDER_NAME + "=?", new String[]{folderName});
            db.close();
        }
    }

    public void updateLearnedCardAmount(String folderName, int amountOfCards) {
        db = dbHelper.getWritableDatabase();
        ContentValues cvUpdateFolder = new ContentValues();
        cvUpdateFolder.put(COLUMN_FOLDER_LEARNED_CARD_AMOUNT, amountOfCards);
        db.update(TABLE_NAME, cvUpdateFolder, COLUMN_FOLDER_NAME + "=?", new String[]{folderName});
        db.close();
    }

    public int getCardAmountInFolder(String folderName) {
        int amount = 0;
        db = dbHelper.getReadableDatabase();
        folderCursor = db.rawQuery("select count(*) from " + TABLE_NAME + " where " + COLUMN_FOLDER_NAME + "=?", new String[]{folderName});
        if (folderCursor.moveToFirst())
            amount = folderCursor.getInt(0);
        db.close();
        return amount;
    }

    public int deleteFolder(String folderName) {
        db = dbHelper.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_FOLDER_NAME + "=?", new String[]{folderName});
        db.close();
        return result;
    }
}
