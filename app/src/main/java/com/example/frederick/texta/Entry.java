package com.example.frederick.texta;

public class Entry {

    private String id;
    private String date;
    private String title;
    private String content;


    public Entry() {
    }

    public Entry(String id, String date, String title, String content) {
        this.id=id;
        this.date = date;
        this.title = title;
        this.content = content;

    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

}
