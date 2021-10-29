package com.example.abillion.dao;


import com.example.abillion.domain.Collection;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CollectionsDao {

    private static final Map<String, Collection> collections = new HashMap<>();

    private CollectionsDao() {
    }

    public Optional<String> saveCollection(String id, Collection collection) {
        try {
            collections.putIfAbsent(id, collection);
        } catch (Exception e) {
            return Optional.of("Error occurred while creating new collection having id" + id);
        }

        return Optional.empty();
    }

    public List<Collection> getAll() {
        return new ArrayList<>(collections.values());
    }
}
