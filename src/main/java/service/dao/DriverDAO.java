package service.dao;

import com.google.inject.Inject;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import service.models.Document;
import service.models.Driver;


import java.util.List;

import static service.constants.MysqlConstants.FIND_DRIVER_BY_USER_ID;

public class DriverDAO extends CustomAbstractDAO<Driver>{

    @Inject
    public DriverDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public boolean isDriverRegisteredForUser(Long userId){
        Query<Driver> query = currentSession().createQuery(FIND_DRIVER_BY_USER_ID, Driver.class);
        query.setParameter("userId", userId);
        List<Driver> drivers = query.getResultList();
        return !drivers.isEmpty();
    }
}
