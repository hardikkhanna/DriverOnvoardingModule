package com.module.dao;

import com.module.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DriverDAOTest {

    @InjectMocks
    private DriverDAO driverDAO;

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<Long> query;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(Long.class))).thenReturn(query);
    }

    @Test
    public void testIsDriverRegistered() {
        User user = new User();
        user.setId(1L);

        when(query.setParameter("userId", user)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1L);

        boolean result = driverDAO.isDriverRegistered(user);
        assertTrue(result);
    }

    @Test
    public void testIsDriverNotRegistered() {
        User user = new User();
        user.setId(2L);

        when(query.setParameter("userId", user)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);

        boolean result = driverDAO.isDriverRegistered(user);
        assertFalse(result);
    }
}
