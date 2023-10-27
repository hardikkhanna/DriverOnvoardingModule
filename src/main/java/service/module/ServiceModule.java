package service.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import org.hibernate.SessionFactory;
import service.conf.AppConfig;
import service.models.Document;
import service.models.Driver;
import service.models.User;

import javax.inject.Named;

import static service.constants.AppConstants.SESSION_FACTORY;
import static service.constants.AppConstants.UNIT_OF_WORK;

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
    public SessionFactory getSessionFactory(){
        return hibernateBundle.getSessionFactory();
    }

    @Provides
    @Singleton
    @Named(UNIT_OF_WORK)
    public UnitOfWorkAwareProxyFactory getUnitOfWork(){
        return new UnitOfWorkAwareProxyFactory(hibernateBundle);
    }

}
