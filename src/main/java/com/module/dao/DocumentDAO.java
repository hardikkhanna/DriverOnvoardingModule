package com.module.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.module.models.Document;

import javax.inject.Inject;
import java.util.List;

import static com.module.constants.MysqlConstants.APPROVE_DOCUMENT_QUERY;
import static com.module.constants.MysqlConstants.GET_PENDING_DOCS;


public class DocumentDAO extends CustomAbstractDAO<Document> {

    @Inject
    public DocumentDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public boolean verifyDoc(Long docId) {
        Query query = currentSession().createQuery(APPROVE_DOCUMENT_QUERY);
        query.setParameter("id", docId);
        return query.executeUpdate() > 0;
    }

    public List<Document> getAllPendingDoc(){
        Query<Document> query = currentSession().createQuery(GET_PENDING_DOCS, Document.class);
        query.setParameter("verified", false);
        return query.list();
    }

}
