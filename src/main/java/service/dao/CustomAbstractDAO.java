package service.dao;

import com.google.inject.Inject;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public abstract class CustomAbstractDAO<T> extends AbstractDAO<T> {

    @Inject
    public CustomAbstractDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<T> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public T create(T entity) {
        return persist(entity);
    }

    public T update(T entity) {
        return persist(entity);
    }

    public void delete(T entity) {
        currentSession().delete(entity);
    }

    public List<T> findAll() {
        return list(namedQuery("findAll"));
    }
}
