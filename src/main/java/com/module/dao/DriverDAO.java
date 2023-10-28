package com.module.dao;

import com.google.inject.Inject;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.module.models.Driver;
import com.module.models.User;


import static com.module.constants.MysqlConstants.FIND_DRIVER_BY_USER_ID;

public class DriverDAO extends CustomAbstractDAO<Driver>{

    @Inject
    public DriverDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public boolean isDriverRegistered(User user){
        Query<Long> query = currentSession().createQuery(FIND_DRIVER_BY_USER_ID, Long.class);
        query.setParameter("userId", user);
        Long count = query.getSingleResult();
        return count > 0;
    }
}

