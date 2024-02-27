package com.tish.e_cards;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Parcelable {

    private int id;
    private String word;
    private String translation;
    private String tag;
    private String description;
    private boolean mark;
    private String folderName;


    public Card(String word, String translation) {
        this.word = word;
        this.translation = translation;
        this.tag = null;
        this.description = null;
        this.mark = false;
    }

    public Card(String word, String translation, String tag, String description) {
        this.word = word;
        this.translation = translation;
        this.tag = tag;
        this.description = description;
        this.mark = false;
    }

    public Card(String word, String translation, String tag, String description, boolean mark) {
        this.word = word;
        this.translation = translation;
        this.tag = tag;
        this.description = description;
        this.mark = mark;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{word, translation, tag, description});
    }

    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {

        @Override
        public Card createFromParcel(Parcel source) {
            String[] sCard = new String[4];
            source.readStringArray(sCard);
             return new Card(sCard[0],sCard[1],sCard[2],sCard[3]);

        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };
}
