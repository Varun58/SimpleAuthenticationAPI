package com.example.abillion.domain;

public class Collection {

    private String id;
    private String author;
    private String title;
    private String visibility;

    private Collection() {}

    public Collection(String id, String author, String visibility) {
        this.id = id;
        this.author = author;
        this.visibility = visibility;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getVisibility() {
        return visibility;
    }
}
