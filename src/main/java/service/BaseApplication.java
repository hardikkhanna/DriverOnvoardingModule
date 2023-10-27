package service;

import com.google.inject.Injector;
import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import service.conf.AppConfig;
import service.module.DaoModule;
import service.module.ServiceModule;
import service.resources.DocumentResource;
import service.resources.HelloWorldResource;
import service.resources.RegistrationResource;
import service.resources.VerificationResource;

@Slf4j
public class BaseApplication extends Application<AppConfig> {

    private Injector injector;

    public static void main(final String[] args) throws Exception {
        new BaseApplication().run(args);
    }

    @Override
    public String getName() {
        return "driver-onboarding-app";
    }

    @Override
    public void initialize(final Bootstrap<AppConfig> bootstrap) {
        super.initialize(bootstrap);
        GuiceBundle<Configuration> guiceBundle = GuiceBundle.newBuilder()
                .addModule(new ServiceModule(bootstrap))
                .addModule(new DaoModule())
                .build(Stage.DEVELOPMENT);
        bootstrap.addBundle(guiceBundle);
        injector = guiceBundle.getInjector();

    }

    @Override
    public void run(final AppConfig configuration,
                    final Environment environment) {

        environment.jersey().register(injector.getInstance(HelloWorldResource.class));
        environment.jersey().register(injector.getInstance(RegistrationResource.class));
        environment.jersey().register(injector.getInstance(DocumentResource.class));
        environment.jersey().register(injector.getInstance(VerificationResource.class));
    }

}
