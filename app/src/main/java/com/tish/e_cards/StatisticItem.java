package com.tish.e_cards;

public class StatisticItem {

    private String type;
    private String folder;
    private String tag;
    private String result;

    public StatisticItem(String type, String folder, String tag, String result) {
        this.type = type;
        this.folder = folder;
        this.tag = tag;
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
