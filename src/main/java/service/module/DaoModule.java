package service.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import org.hibernate.SessionFactory;
import service.controller.DocumentController;
import service.controller.RegistrationController;
import service.dao.DocumentDAO;
import service.dao.DriverDAO;
import service.dao.UserDAO;

import static service.constants.AppConstants.*;

public class DaoModule extends AbstractModule {
    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    @Named(USER_DAO)
    private UserDAO getUserDAO(@Named(SESSION_FACTORY) SessionFactory sessionFactory,
                               @Named(UNIT_OF_WORK) UnitOfWorkAwareProxyFactory unitOfWorkAwareProxyFactory) {
        return unitOfWorkAwareProxyFactory.create(UserDAO.class,
                new Class[]{SessionFactory.class}, new Object[]{sessionFactory});
    }


    @Provides
    @Singleton
    @Named(DRIVER_DAO)
    private DriverDAO getDriverDAO(@Named(SESSION_FACTORY) SessionFactory sessionFactory,
                                   @Named(UNIT_OF_WORK) UnitOfWorkAwareProxyFactory unitOfWorkAwareProxyFactory) {
        return unitOfWorkAwareProxyFactory.create(DriverDAO.class,
                new Class[]{SessionFactory.class}, new Object[]{sessionFactory});
    }

    @Provides
    @Singleton
    @Named(DOCUMENT_DAO)
    private DocumentDAO getDocumentDAO(@Named(SESSION_FACTORY) SessionFactory sessionFactory,
                                       @Named(UNIT_OF_WORK) UnitOfWorkAwareProxyFactory unitOfWorkAwareProxyFactory) {
        return unitOfWorkAwareProxyFactory.create(DocumentDAO.class,
                new Class[]{SessionFactory.class}, new Object[]{sessionFactory});
    }

    @Provides
    @Singleton
    private RegistrationController getRegistrationController(@Named(USER_DAO) UserDAO userDAO,
                                                             @Named(DRIVER_DAO) DriverDAO driverDAO) {
        return new RegistrationController(userDAO, driverDAO);
    }

    @Provides
    @Singleton
    private DocumentController provideDocumentController(@Named(DOCUMENT_DAO) DocumentDAO documentDAO,
                                                         @Named(DRIVER_DAO) DriverDAO driverDAO) {
        return new DocumentController(documentDAO, driverDAO);
    }

}
