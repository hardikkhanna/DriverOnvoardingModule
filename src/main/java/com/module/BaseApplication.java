package com.module;

import com.google.inject.Injector;
import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.module.conf.AppConfig;
import com.module.resources.DocumentResource;
import com.module.resources.RegistrationResource;
import com.module.resources.VerificationResource;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import com.module.module.DaoModule;
import com.module.module.ServiceModule;
import com.module.resources.HelloWorldResource;

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
