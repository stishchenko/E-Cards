package com.tish.e_cards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardsConnector {
    static String TABLE_NAME = "cards";
    static String COLUMN_CARD_ID = "_id";
    static String COLUMN_CARD_FOLDER_NAME = "FolderName";
    static String COLUMN_CARD_WORD = "Word";
    static String COLUMN_CARD_TRANSLATION = "Translation";
    static String COLUMN_CARD_TAG = "Tag";
    static String COLUMN_CARD_DESCRIPTION = "Description";
    static String COLUMN_CARD_MARK = "Mark";
    public static final String CREATE_TABLE_CARDS = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" +
            COLUMN_CARD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_CARD_FOLDER_NAME + " TEXT, " +
            COLUMN_CARD_WORD + " TEXT NOT NULL, " +
            COLUMN_CARD_TRANSLATION + " TEXT NOT NULL, " +
            COLUMN_CARD_TAG + " TEXT, " +
            COLUMN_CARD_DESCRIPTION + " TEXT, " +
            COLUMN_CARD_MARK + " INTEGER NOT NULL, " +
            "FOREIGN KEY (" + COLUMN_CARD_FOLDER_NAME + ") REFERENCES " +
            FoldersConnector.TABLE_NAME + "(" + FoldersConnector.COLUMN_FOLDER_NAME + ") " +
            "ON UPDATE CASCADE" + ")";

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cardCursor;

    public CardsConnector(Context context) {
        dbHelper = new DBHelper(context);
    }

    public List<Card> getAllCards(String folderName) {
        List<Card> cards = new ArrayList<Card>();
        db = dbHelper.getReadableDatabase();
        if (!folderName.equals("Все слова"))
            cardCursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_CARD_FOLDER_NAME + "=?", new String[]{folderName});
        else
            cardCursor = db.rawQuery("select * from " + TABLE_NAME, null);

        cardCursor.moveToFirst();
        while (cardCursor.moveToNext()) {
            int id = cardCursor.getInt(cardCursor.getColumnIndex(COLUMN_CARD_ID));
            String word = cardCursor.getString(cardCursor.getColumnIndex(COLUMN_CARD_WORD));
            String translation = cardCursor.getString(cardCursor.getColumnIndex(COLUMN_CARD_TRANSLATION));
            String tag = cardCursor.getString(cardCursor.getColumnIndex(COLUMN_CARD_TAG));
            String description = cardCursor.getString(cardCursor.getColumnIndex(COLUMN_CARD_DESCRIPTION));
            int mark = cardCursor.getInt(cardCursor.getColumnIndex(COLUMN_CARD_MARK));
            boolean bMark = mark == 1;
            Card card = new Card(word, translation, tag, description, bMark);
            card.setId(id);
            cards.add(card);
        }
        cardCursor.close();
        db.close();
        return cards;
    }

    public List<Card> getCardsFromFolderAndTag(String folderName, String searchTag) {
        List<Card> cards = new ArrayList<Card>();
        db = dbHelper.getReadableDatabase();
        cardCursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_CARD_FOLDER_NAME + "=? and " + COLUMN_CARD_TAG + "=?", new String[]{folderName, searchTag});
        cardCursor.moveToFirst();
        while (cardCursor.moveToNext()) {
            String word = cardCursor.getString(cardCursor.getColumnIndex(COLUMN_CARD_WORD));
            String translation = cardCursor.getString(cardCursor.getColumnIndex(COLUMN_CARD_TRANSLATION));
            String tag = cardCursor.getString(cardCursor.getColumnIndex(COLUMN_CARD_TAG));
            String description = cardCursor.getString(cardCursor.getColumnIndex(COLUMN_CARD_DESCRIPTION));
            int mark = cardCursor.getInt(cardCursor.getColumnIndex(COLUMN_CARD_MARK));
            boolean bMark = mark == 1;
            Card card = new Card(word, translation, tag, description, bMark);
            cards.add(card);
        }
        cardCursor.close();
        db.close();
        return cards;
    }

    public List<Card> getCardsWithTag(String searchTag) {
        List<Card> cards = new ArrayList<Card>();
        db = dbHelper.getReadableDatabase();
        cardCursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_CARD_TAG + "=?", new String[]{searchTag});
        cardCursor.moveToFirst();
        while (cardCursor.moveToNext()) {
            String word = cardCursor.getString(cardCursor.getColumnIndex(COLUMN_CARD_WORD));
            String translation = cardCursor.getString(cardCursor.getColumnIndex(COLUMN_CARD_TRANSLATION));
            String tag = cardCursor.getString(cardCursor.getColumnIndex(COLUMN_CARD_TAG));
            String description = cardCursor.getString(cardCursor.getColumnIndex(COLUMN_CARD_DESCRIPTION));
            int mark = cardCursor.getInt(cardCursor.getColumnIndex(COLUMN_CARD_MARK));
            boolean bMark = mark == 1;
            Card card = new Card(word, translation, tag, description, bMark);
            cards.add(card);
        }
        cardCursor.close();
        db.close();
        return cards;
    }

    public List<String> getCardTags() {
        List<String> cardTags = new ArrayList<String>();
        db = dbHelper.getReadableDatabase();
        cardCursor = db.rawQuery("select " + COLUMN_CARD_TAG + " from " + TABLE_NAME + " group by " + COLUMN_CARD_TAG, null);
        cardCursor.moveToFirst();
        while (cardCursor.moveToNext()) {
            String tag = cardCursor.getString(cardCursor.getColumnIndex(COLUMN_CARD_TAG));
            cardTags.add(tag);
        }
        cardCursor.close();
        db.close();
        return cardTags;
    }

    public List<Card> getRandomCards(String x) {
        List<Card> cards = new ArrayList<Card>();
        db = dbHelper.getReadableDatabase();
        cardCursor = db.rawQuery("select * from " + TABLE_NAME + " order by random() limit " + (Integer.parseInt(x) + 1), null);
        cardCursor.moveToFirst();
        while (cardCursor.moveToNext()) {
            int id = cardCursor.getInt(cardCursor.getColumnIndex(COLUMN_CARD_ID));
            String word = cardCursor.getString(cardCursor.getColumnIndex(COLUMN_CARD_WORD));
            String translation = cardCursor.getString(cardCursor.getColumnIndex(COLUMN_CARD_TRANSLATION));
            String tag = cardCursor.getString(cardCursor.getColumnIndex(COLUMN_CARD_TAG));
            String description = cardCursor.getString(cardCursor.getColumnIndex(COLUMN_CARD_DESCRIPTION));
            int mark = cardCursor.getInt(cardCursor.getColumnIndex(COLUMN_CARD_MARK));
            boolean bMark = mark == 1;
            Card card = new Card(word, translation, tag, description, bMark);
            card.setId(id);
            cards.add(card);
        }
        cardCursor.close();
        db.close();
        return cards;
    }

    public long insertNewCard(Card newCard) {
        ContentValues cvNewCard = new ContentValues();
        cvNewCard.put(COLUMN_CARD_FOLDER_NAME, newCard.getFolderName());
        cvNewCard.put(COLUMN_CARD_WORD, newCard.getWord());
        cvNewCard.put(COLUMN_CARD_TRANSLATION, newCard.getTranslation());
        cvNewCard.put(COLUMN_CARD_TAG, newCard.getTag());
        cvNewCard.put(COLUMN_CARD_DESCRIPTION, newCard.getDescription());
        int mark = newCard.getMark() ? 1 : 0;
        cvNewCard.put(COLUMN_CARD_MARK, mark);
        db = dbHelper.getWritableDatabase();
        long result = db.insert(TABLE_NAME, null, cvNewCard);
        db.close();
        return result;
    }

    public int updateLearnedCards(List<Card> cardsList) {
        ContentValues cvUpdatingCards = new ContentValues();
        cvUpdatingCards.put(COLUMN_CARD_MARK, 1);
        db = dbHelper.getWritableDatabase();
        int result = 0;
        for (Card card : cardsList) {
            if (card.getMark())
                result += db.update(TABLE_NAME, cvUpdatingCards, COLUMN_CARD_WORD + "=?", new String[]{card.getWord()});
        }
        db.close();
        return result;
    }

    public int updateEditedCard(Card forEditCard, String word) {
        ContentValues cvForEditCard = new ContentValues();
        cvForEditCard.put(COLUMN_CARD_WORD, forEditCard.getWord());
        cvForEditCard.put(COLUMN_CARD_TRANSLATION, forEditCard.getTranslation());
        cvForEditCard.put(COLUMN_CARD_TAG, forEditCard.getTag());
        cvForEditCard.put(COLUMN_CARD_DESCRIPTION, forEditCard.getDescription());
        db = dbHelper.getWritableDatabase();
        int result = db.update(TABLE_NAME, cvForEditCard, COLUMN_CARD_WORD + "=?", new String[]{word});
        db.close();
        return result;
    }

    public int deleteCard(String word) {
        db = dbHelper.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_CARD_WORD + "=?", new String[]{word});
        db.close();
        return result;
    }
}
