package service.dao;

import com.google.inject.Inject;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import service.models.User;

import java.util.List;
import java.util.Optional;

import static service.constants.MysqlConstants.*;

public class UserDAO extends CustomAbstractDAO<User> {

    @Inject
    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<User> findByUsername(String username) {
        Query<User> query = currentSession().createQuery(FIND_BY_USERNAME_QUERY, User.class);
        query.setParameter("username", username);
        return Optional.ofNullable(query.uniqueResult());
    }

    public List<User> findUsersByRole(String role) {
        Query<User> query = currentSession().createQuery(FIND_USER_BY_ROLE, User.class);
        query.setParameter("role", role);
        return list(query);
    }

    public boolean isUsernameTaken(String username) {
        Query<Long> query = currentSession().createQuery(IS_USERNAME_TAKEN_QUERY, Long.class);
        query.setParameter("username", username);
        return query.uniqueResult() > 0;
    }

    public Optional<User> findByEmail(String email){
        Query<User> query = currentSession().createQuery(FIND_USER_BY_EMAIL, User.class);
        query.setParameter("email", email);
        return Optional.ofNullable(query.uniqueResult());
    }
}
