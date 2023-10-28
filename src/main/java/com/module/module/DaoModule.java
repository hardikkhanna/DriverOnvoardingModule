package com.module.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.module.constants.AppConstants;
import com.module.controller.DocumentController;
import com.module.controller.RegistrationController;
import com.module.dao.DocumentDAO;
import com.module.dao.DriverDAO;
import com.module.dao.UserDAO;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import org.hibernate.SessionFactory;

public class DaoModule extends AbstractModule {
    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    @Named(AppConstants.USER_DAO)
    private UserDAO getUserDAO(@Named(AppConstants.SESSION_FACTORY) SessionFactory sessionFactory,
                               @Named(AppConstants.UNIT_OF_WORK) UnitOfWorkAwareProxyFactory unitOfWorkAwareProxyFactory) {
        return unitOfWorkAwareProxyFactory.create(UserDAO.class,
                new Class[]{SessionFactory.class}, new Object[]{sessionFactory});
    }


    @Provides
    @Singleton
    @Named(AppConstants.DRIVER_DAO)
    private DriverDAO getDriverDAO(@Named(AppConstants.SESSION_FACTORY) SessionFactory sessionFactory,
                                   @Named(AppConstants.UNIT_OF_WORK) UnitOfWorkAwareProxyFactory unitOfWorkAwareProxyFactory) {
        return unitOfWorkAwareProxyFactory.create(DriverDAO.class,
                new Class[]{SessionFactory.class}, new Object[]{sessionFactory});
    }

    @Provides
    @Singleton
    @Named(AppConstants.DOCUMENT_DAO)
    private DocumentDAO getDocumentDAO(@Named(AppConstants.SESSION_FACTORY) SessionFactory sessionFactory,
                                       @Named(AppConstants.UNIT_OF_WORK) UnitOfWorkAwareProxyFactory unitOfWorkAwareProxyFactory) {
        return unitOfWorkAwareProxyFactory.create(DocumentDAO.class,
                new Class[]{SessionFactory.class}, new Object[]{sessionFactory});
    }

    @Provides
    @Singleton
    private RegistrationController getRegistrationController(@Named(AppConstants.USER_DAO) UserDAO userDAO,
                                                             @Named(AppConstants.DRIVER_DAO) DriverDAO driverDAO) {
        return new RegistrationController(userDAO, driverDAO);
    }

    @Provides
    @Singleton
    private DocumentController provideDocumentController(@Named(AppConstants.DOCUMENT_DAO) DocumentDAO documentDAO,
                                                         @Named(AppConstants.DRIVER_DAO) DriverDAO driverDAO) {
        return new DocumentController(documentDAO, driverDAO);
    }

}
