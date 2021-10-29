package com.example.abillion.response;

public class CollectionApiResponse {

    private String id;
    private String author;
    private String visibility;

    private CollectionApiResponse() {}

    public CollectionApiResponse(String id, String author, String visibility) {
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
