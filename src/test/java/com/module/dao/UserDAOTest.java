package com.module.dao;

import com.module.models.Role;
import com.module.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserDAOTest {

    @InjectMocks
    private UserDAO userDAO;

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<User> query;

    @Mock
    private Query<Long> longQuery;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(session.createQuery(anyString(), eq(Long.class))).thenReturn(longQuery);
    }

    @Test
    public void testFindByUsername() {
        String username = "testuser";
        User user = new User();
        when(query.setParameter("username", username)).thenReturn(query);
        when(query.uniqueResult()).thenReturn(user);

        Optional<User> result = userDAO.findByUsername(username);
        assertEquals(Optional.of(user), result);
    }

    @Test
    public void testFindByUsernameNotFound() {
        String username = "nonexistentuser";
        when(query.setParameter("username", username)).thenReturn(query);
        when(query.uniqueResult()).thenReturn(null);

        Optional<User> result = userDAO.findByUsername(username);
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void testFindUsersByRole() {
        String role = Role.ADMIN.name();
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setRole(Role.ADMIN);
        userList.add(user1);
        when(query.setParameter("role", role)).thenReturn(query);
        when(query.list()).thenReturn(userList);

        List<User> result = userDAO.findUsersByRole(role);
        assertEquals(userList, result);
    }

    @Test
    public void testIsUsernameTaken() {
        String username = "existinguser";
        when(longQuery.setParameter("username", username)).thenReturn(longQuery);
        when(longQuery.uniqueResult()).thenReturn(1L);

        boolean result = userDAO.isUsernameTaken(username);
        assertTrue(result);
    }

    @Test
    public void testIsUsernameNotTaken() {
        String username = "newuser";
        when(longQuery.setParameter("username", username)).thenReturn(longQuery);
        when(longQuery.uniqueResult()).thenReturn(0L);

        boolean result = userDAO.isUsernameTaken(username);
        assertFalse(result);
    }

    @Test
    public void testFindByEmail() {
        String email = "test@example.com";
        User user = new User();
        when(query.setParameter("email", email)).thenReturn(query);
        when(query.uniqueResult()).thenReturn(user);

        Optional<User> result = userDAO.findByEmail(email);
        assertEquals(Optional.of(user), result);
    }

    @Test
    public void testFindByEmailNotFound() {
        String email = "nonexistent@example.com";
        when(query.setParameter("email", email)).thenReturn(query);
        when(query.uniqueResult()).thenReturn(null);

        Optional<User> result = userDAO.findByEmail(email);
        assertEquals(Optional.empty(), result);
    }
}

