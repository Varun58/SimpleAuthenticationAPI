package com.example.abillion.controller;

import com.example.abillion.dao.CollectionsDao;
import com.example.abillion.dao.TokenDao;
import com.example.abillion.dao.UserDao;
import com.example.abillion.domain.*;
import com.example.abillion.request.CollectionRequest;
import com.example.abillion.response.CollectionApiResponse;
import com.example.abillion.response.LoginApiResponse;
import com.example.abillion.response.UsersApiResponse;
import com.example.abillion.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.abillion.util.JwtTokenUtil.*;

@RestController("/")
public class AppController {

    private static Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private CollectionsDao collectionsDao;

    @PostMapping("/users")
    public ResponseEntity<?> users(@RequestBody User user) {
        if(invalidRequest(user)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String uuid = UUID.randomUUID().toString();
        Optional<String> error = userDao.saveUser(uuid, user);

        return error.map(s -> new ResponseEntity<>( HttpStatus.BAD_REQUEST))
                .orElseGet(() -> new ResponseEntity<>(new UsersApiResponse(uuid), HttpStatus.CREATED));
    }

    @PostMapping(value = "/auth/login")
    public ResponseEntity<LoginApiResponse> login(@RequestBody User user) {
        if(invalidRequest(user)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Optional<User> foundUserOpt = userDao.getUser(user.getUsername());
        if (!foundUserOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User foundUser =  foundUserOpt.get();
        if (!foundUser.getPassword().equals(user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = getJWTToken(user.getUsername());
        TokenDao.saveUser(user.getUsername(), token);
        return new ResponseEntity<>(new LoginApiResponse(token), HttpStatus.OK);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse res) {
        if (!checkJWTToken(request, res)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Claims claims = validateToken(request);
        String username = claims.getSubject();
        TokenDao.removeTokenForUser(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/collections")
    public ResponseEntity<List<CollectionApiResponse>> collections() {
        List<CollectionApiResponse> collectionApiResponse = collectionsDao.getAll().stream()
                .map(x-> new CollectionApiResponse(x.getId(), x.getAuthor(), x.getVisibility()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(collectionApiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/collections")
    public ResponseEntity<?> collections(@RequestBody CollectionRequest request) {
        if(invalidRequest(request)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        String id  =  UUID.randomUUID().toString();
        try {
            Collection newCollection = Optional.of(request)
                    .map(x-> new Collection(id, request.getTitle(), request.getVisibility()))
                    .get();

            collectionsDao.saveCollection(id, newCollection);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private static boolean invalidRequest(User user) {
        return (user == null) || (user.getUsername() == null || user.getUsername().equals(""))
                || (user.getPassword() == null || user.getPassword().equals(""));
    }

    private static boolean invalidRequest(CollectionRequest request) {
        return (request == null) || (request.getTitle() == null || request.getTitle().equals(""))
                || (request.getVisibility() == null || request.getVisibility().equals(""));
    }
}
