package models;

public class Entry implements Cloneable{
    private String date;
    private String timestamp;
    private String note;
    private String diffWord;

    public Entry(String date, String timestamp, String note) {
        this.date = date;
        this.timestamp = timestamp;
        this.note = note;
    }

    public Entry() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDiffWord() {
        return diffWord;
    }

    public void setDiffWord(String diffWord) {
        this.diffWord = diffWord;
    }

    public Entry clone() throws CloneNotSupportedException
    {
        return (Entry) super.clone();
    }
}
