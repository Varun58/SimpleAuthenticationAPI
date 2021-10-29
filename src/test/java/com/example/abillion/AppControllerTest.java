package com.example.abillion;

import com.example.abillion.controller.AppController;
import com.example.abillion.dao.CollectionsDao;
import com.example.abillion.dao.UserDao;
import com.example.abillion.domain.Collection;
import com.example.abillion.domain.User;
import com.example.abillion.request.CollectionRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;

public class AppControllerTest extends AbstractTest {

    @Mock
    private UserDao userDao;

    @Mock
    private CollectionsDao collectionsDao;

    @InjectMocks
    private AppController appController;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        this.mvc = MockMvcBuilders.standaloneSetup(appController).build();
    }

    @Test
    public void getCollections() throws Exception {
        String uri = "/collections";
        Collection collection = new Collection("1","abc","public");

        Mockito.when(collectionsDao.getAll()).thenReturn(Arrays.asList(collection));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void createUsers() throws Exception {
        String uri = "/users";
        User user = new User("abc", "xyz");

        Mockito.when(userDao.saveUser(Mockito.anyString(), Mockito.any(User.class))).thenReturn(Optional.empty());
        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    public void login() throws Exception {
        String uri = "/auth/login";
        User user = new User("abc", "xyz");
        String inputJson = super.mapToJson(user);

        Mockito.when(userDao.getUser(Mockito.anyString())).thenReturn(Optional.of(user));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    public void createCollection() throws Exception {
        String uri = "/collections";
        CollectionRequest collectionRequest = new CollectionRequest("abc", "xyz");
        String inputJson = super.mapToJson(collectionRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertNotNull(content);
    }
}
