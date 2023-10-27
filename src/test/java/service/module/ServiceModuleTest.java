//package service.module;
//
//import io.dropwizard.hibernate.HibernateBundle;
//import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
//import io.dropwizard.setup.Bootstrap;
//import io.dropwizard.setup.Environment;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.hibernate.SessionFactory;
//import service.conf.AppConfig;
//
//import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.verify;
//
public class ServiceModuleTest {
//
//    private ServiceModule serviceModule;
//
//    @Mock
//    private Bootstrap<AppConfig> bootstrap;
//
//    @Mock
//    private AppConfig appConfig;
//
//    @Mock
//    private HibernateBundle<AppConfig> hibernateBundle;
//
//    @Mock
//    private Environment environment;
//
//    @Before
//    public void setup() {
//        hibernateBundle = Mockito.mock(HibernateBundle.class);
//        bootstrap = Mockito.mock(Bootstrap.class);
//        appConfig = Mockito.mock(AppConfig.class);
//        serviceModule = new ServiceModule(bootstrap);
//    }
//
//    @Test
//    public void testConfigure() {
//        when(hibernateBundle.getDataSourceFactory(appConfig)).thenReturn(appConfig.getDatabase());
//
//        serviceModule.configure();
//
//        // Verify any expected behavior
//        verify(bootstrap).addBundle(hibernateBundle);
//    }
//
//    @Test
//    public void testGetSessionFactory() {
//        // Mock the behavior of HibernateBundle
//        SessionFactory sessionFactory = hibernateBundle.getSessionFactory();
//        when(sessionFactory.isClosed()).thenReturn(false);
//
//        SessionFactory result = serviceModule.getSessionFactory();
//
//        // Verify that the result is not null and the session factory is not closed
//        Assert.assertNotNull(result);
//        Assert.assertFalse(result.isClosed());
//    }
//
//    @Test
//    public void testGetUnitOfWork() {
//        // Mock the behavior of UnitOfWorkAwareProxyFactory
//        UnitOfWorkAwareProxyFactory unitOfWorkFactory = new UnitOfWorkAwareProxyFactory(hibernateBundle);
//
//        UnitOfWorkAwareProxyFactory result = serviceModule.getUnitOfWork();
//
//        // Verify that the result is not null
//        Assert.assertNotNull(result);
//    }
}
