package com.module.dao;

import com.module.models.Document;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DocumentDAOTest {

    @InjectMocks
    private DocumentDAO documentDAO;

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<Document> query;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(Document.class))).thenReturn(query);
    }

    @Test
    public void testVerifyDoc() {
        Long docId = 1L;
        when(query.executeUpdate()).thenReturn(1);
        boolean result = documentDAO.verifyDoc(docId);
        assertTrue(result);
    }

    @Test
    public void testVerifyDocFailure() {
        Long docId = 2L;
        when(query.executeUpdate()).thenReturn(0);
        boolean result = documentDAO.verifyDoc(docId);
        assertFalse(result);
    }

    @Test
    public void testGetAllPendingDoc() {
        List<Document> pendingDocs = new ArrayList<>();
        Document doc1 = new Document();
        doc1.setVerified(false);
        Document doc2 = new Document();
        doc2.setVerified(false);
        pendingDocs.add(doc1);
        pendingDocs.add(doc2);

        when(query.setParameter("verified", false)).thenReturn(query);
        when(query.list()).thenReturn(pendingDocs);

        List<Document> result = documentDAO.getAllPendingDoc();
        assertEquals(pendingDocs, result);
    }

    @Test
    public void testGetAllPendingDocEmptyList() {
        List<Document> pendingDocs = new ArrayList<>();
        when(query.setParameter("verified", false)).thenReturn(query);
        when(query.list()).thenReturn(pendingDocs);

        List<Document> result = documentDAO.getAllPendingDoc();
        assertTrue(result.isEmpty());
    }
}

