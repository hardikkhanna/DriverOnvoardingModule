package service.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import service.models.Document;

import javax.inject.Inject;
import java.util.List;

import static service.constants.MysqlConstants.APPROVE_DOCUMENT_QUERY;
import static service.constants.MysqlConstants.GET_PENDING_DOCS;


public class DocumentDAO extends CustomAbstractDAO<Document> {

    @Inject
    public DocumentDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public boolean verifyDoc(Long docId) {
        Query<Document> query = currentSession().createQuery(APPROVE_DOCUMENT_QUERY, Document.class);
        query.setParameter("id", docId);
        return query.executeUpdate() > 0;
    }

    public List<Document> getAllPendingDoc(){
        Query<Document> query = currentSession().createQuery(GET_PENDING_DOCS, Document.class);
        query.setParameter(":isVerified", false);
        return query.list();
    }

}
