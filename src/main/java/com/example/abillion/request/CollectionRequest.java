package com.example.abillion.request;


public class CollectionRequest {

    private String title;
    private String visibility;

    private CollectionRequest() {}

    public CollectionRequest(String title, String visibility) {
        this.title = title;
        this.visibility = visibility;
    }

    public String getTitle() {
        return title;
    }

    public String getVisibility() {
        return visibility;
    }
}
