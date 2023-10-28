package com.module.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.module.conf.AppConfig;
import com.module.factory.NotificationFactory;
import com.module.models.Document;
import com.module.models.Driver;
import com.module.models.User;
import com.module.service.NotificationService;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import org.hibernate.SessionFactory;

import javax.inject.Named;

import static com.module.constants.AppConstants.*;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {

    }

    private final HibernateBundle<AppConfig> hibernateBundle = new HibernateBundle<AppConfig>(
            Driver.class, User.class, Document.class
    ) {
        @Override
        public DataSourceFactory getDataSourceFactory(AppConfig appConfig) {
            return appConfig.getDatabase();
        }
    };

    public ServiceModule(Bootstrap<AppConfig> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Provides
    @Singleton
    @Named(SESSION_FACTORY)
    public SessionFactory getSessionFactory() {
        return hibernateBundle.getSessionFactory();
    }

    @Provides
    @Singleton
    @Named(UNIT_OF_WORK)
    public UnitOfWorkAwareProxyFactory getUnitOfWork() {
        return new UnitOfWorkAwareProxyFactory(hibernateBundle);
    }

    @Provides
    @Singleton
    @Named(NOTIFICATION_FACTORY)
    public NotificationFactory getNotificationFactory() {
        return new NotificationFactory();
    }

    @Provides
    @Singleton
    @Named(NOTIFICATION_SERVICE)
    public NotificationService getNotificationService(@Named(NOTIFICATION_FACTORY) NotificationFactory notificationFactory) {
        return new NotificationService(notificationFactory);
    }
}
