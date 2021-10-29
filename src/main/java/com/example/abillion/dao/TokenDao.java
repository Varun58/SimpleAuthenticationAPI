package com.example.abillion.dao;



import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TokenDao {

    private static final Map<String, String> userTokens = new HashMap<>();

    public static Optional<String> saveUser(String username, String token) {
        try {
            userTokens.putIfAbsent(username, token);
        } catch (Exception e) {
            return Optional.of("Error occurred while storing token for user" + username);
        }

        return Optional.empty();
    }

    public static Optional<String> getTokenForUser(String username) {
        return Optional.ofNullable(userTokens.get(username));
    }

    public static String removeTokenForUser(String username) {
        return userTokens.remove(username);
    }

}
