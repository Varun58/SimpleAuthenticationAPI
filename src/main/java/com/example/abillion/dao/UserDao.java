package com.example.abillion.dao;


import com.example.abillion.domain.User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserDao {

    private static final Map<String, User> users = new HashMap<>();

    public Optional<String> saveUser(String id, User user) {
        boolean duplicateUser = users.values()
                .stream()
                .anyMatch(u -> u.getPassword().equals(user.getPassword()));

        if(duplicateUser) {
            return Optional.of("User with name " + user.getUsername() +" already exists!");
        }

        try {
            users.putIfAbsent(user.getUsername(), user);
        } catch (Exception e) {
            return Optional.of("Error occurred while creating new User having name" + user.getUsername());
        }

        return Optional.empty();
    }

    public void updateUser(User user) {
        users.put(user.getUsername(), user);
    }

    public Optional<User> getUser(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public Iterable<String> getAllUsernames() {
        return users.values().stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }
}
